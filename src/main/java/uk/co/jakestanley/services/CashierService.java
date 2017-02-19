package uk.co.jakestanley.services;

import org.springframework.stereotype.Service;

import uk.co.jakestanley.exceptions.RouletteGameException;

@Service
public class CashierService {

    public void validateBetAmount(int amount) throws RouletteGameException {
    	if(amount <= 0) {
    		throw new RouletteGameException("Invalid bet amount: " + amount);
    	}
    }
    
    public int getPayout(int matches, int amount) {
    	int winnings = Math.floorDiv(RouletteService.NUMBERS, matches) - 1;
    	return winnings * amount;
    }

}