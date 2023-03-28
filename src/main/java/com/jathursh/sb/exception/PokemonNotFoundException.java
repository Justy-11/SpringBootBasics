package com.jathursh.sb.exception;

import lombok.Data;

public class PokemonNotFoundException extends RuntimeException{
    private int statusCode;
    private String message;
    private Data timestamp;
}
