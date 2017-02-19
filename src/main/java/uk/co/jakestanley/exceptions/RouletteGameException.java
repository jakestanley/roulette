package uk.co.jakestanley.exceptions;

import lombok.NoArgsConstructor;

public class RouletteGameException extends Exception {

    public RouletteGameException (String message){
        super(message);
    }
    
}
