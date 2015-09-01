package br.com.loki.exception;

public class BancoDadosException extends TechnicalException {
    
    public BancoDadosException(Exception e) {    
        super(e);
    }
}
