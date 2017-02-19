package uk.co.jakestanley.services;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import lombok.Getter;
import uk.co.jakestanley.exceptions.RouletteGameException;

@Service
public class RouletteService {
	
	public static final int NUMBERS = 37;
	public static final int MAX_PAYOUT = 360;
	
	@Getter
	public enum BetType {
		
		ODD(1, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36),
		EVEN(1, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35);
		
		private final int 			payout;
		private final Set<Integer>  numbers;
		
		BetType(int payout, Integer... numbers) {
			this.payout = payout;
			this.numbers = Arrays.asList(numbers).stream().collect(Collectors.toSet());
		}
	}
	
	public int placeBet(int number) throws RouletteGameException {

		if(number >= 37 || number < 0) {
			throw new RouletteGameException("Placed bet cannot be less than zero or greater than 36");
		}

		int spin = spin();
		
		if (number == spin) {
			return MAX_PAYOUT;
		}
		
		return 0;
	}
	
	public int placeBet(BetType type) throws RouletteGameException {

		if(type != BetType.ODD && type != BetType.EVEN) {
			throw new RouletteGameException("Unsupported bet type");
		}

		Integer spin = spin();
		
		if(type.getNumbers().contains(spin)) {
			return type.payout;
		}

		return 0;

	}
	
	private int spin() {
		return RandomUtils.nextInt(NUMBERS);
	}

}
