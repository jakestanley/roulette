package uk.co.jakestanley.services;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import uk.co.jakestanley.exceptions.RouletteGameException;

@Log4j
@Service
public class RouletteService {
	
	public static final int NUMBERS = 37;
	public static final int MAX_PAYOUT = 360;
	
	public enum BetResult {
		WON,
		LOST
	}
	
	@Getter
	public enum BetType {
		ODD, EVEN
	}
	
	public BetResult placeBet(int bet) throws RouletteGameException {

		log.info("Bet is " + bet);
		
		if((bet > 36) || (bet < 0)) {
			throw new RouletteGameException("Placed bet cannot be less than zero or greater than 36");
		}

		int spin = spin();

		if (bet == spin) {
			return BetResult.WON;
		} else {
			return BetResult.LOST;
		}
	}
	
	public BetResult placeBet(BetType type) throws RouletteGameException {

		int spin = spin();
		BetResult br = BetResult.LOST;
		
		switch(type) {
			case ODD:
				if(spin % 2 == 1) {
					br = BetResult.WON;
				}
				break;
			case EVEN:
				if(spin % 2 == 0) {
					br = BetResult.WON;
				}
				break;
			default:
				throw new RouletteGameException("Unsupported bet type");
		}

		return br;
	}
	
	/**
	 * Returns a random number from zero to 36
	 * @return
	 */
	public int spin() {
		return RandomUtils.nextInt(NUMBERS);
	}

}
