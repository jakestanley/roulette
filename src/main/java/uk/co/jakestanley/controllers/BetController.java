package uk.co.jakestanley.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jakestanley.exceptions.RouletteGameException;
import uk.co.jakestanley.services.CashierService;
import uk.co.jakestanley.services.RouletteService;
import uk.co.jakestanley.services.RouletteService.BetResult;

@RestController
@RequestMapping(value="/bet")
public class BetController {
	
	@Autowired
	private RouletteService rouletteService;
	
	@Autowired
	private CashierService cashierService;

    @RequestMapping(value="/pocket", method=RequestMethod.POST)
    private ResponseEntity<?> placePocketBet(
    		@RequestParam("amount") int amount,
    		@RequestParam("number") int number) {

    	int winnings = 0;

    	HttpHeaders responseHeaders = new HttpHeaders();
    	ResponseEntity<String> rsp;
    	
    	try {

			BetResult result = rouletteService.placeBet(number);
			if(result == BetResult.WON){
				winnings = amount * 36;
			}
			
			rsp = new ResponseEntity<String>("You won £" + String.valueOf(winnings), 
					responseHeaders, HttpStatus.OK);
		} catch (RouletteGameException e) {
			
			rsp = new ResponseEntity<String>(e.getMessage(), 
					responseHeaders, HttpStatus.BAD_REQUEST);
		}
    	
    	return rsp;
    }

    @RequestMapping(value="/even", method=RequestMethod.POST)
    private ResponseEntity<?> placeEvenBet(@RequestParam("amount") int amount) {
    	return null;
    }

    @RequestMapping(value="/odd", method=RequestMethod.POST)
    private ResponseEntity<?> placeOddBet(@RequestParam("amount") int amount) {
    	return null;
    }

}