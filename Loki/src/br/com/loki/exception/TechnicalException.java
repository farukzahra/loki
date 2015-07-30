package br.com.loki.exception;

public class TechnicalException extends Exception {
    
    public TechnicalException(Exception e) {    
        super(e);
    }
}
