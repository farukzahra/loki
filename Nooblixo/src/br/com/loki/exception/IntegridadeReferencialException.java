package br.com.loki.exception;

import br.com.loki.util.Mensagens;

public class IntegridadeReferencialException extends BusinessException {
    
    public IntegridadeReferencialException(){
        super(Mensagens.getMensagem("exception.integridade.violada"));
    }
    
    public IntegridadeReferencialException(Throwable cause){
        super(cause, Mensagens.getMensagem("tj.exception.integridade.violada"));
    }
    
}
