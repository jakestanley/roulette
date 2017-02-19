package uk.co.jakestanley.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.co.jakestanley.services.CashierService;
import uk.co.jakestanley.services.RouletteService;

@RestController
@RequestMapping(value="/bet")
public class BetController {
	
	@Autowired
	private RouletteService rouletteService;
	
	@Autowired
	private CashierService cashierService;

    @RequestMapping(value="/pocket", method=RequestMethod.POST)
    private ResponseEntity<?> placePocketBet(@RequestParam("amount") int amount) {
        return null;
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