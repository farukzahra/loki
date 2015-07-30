package br.com.loki.exception;

import br.com.loki.util.Mensagens;


public class SenhaInvalidaException extends BusinessException{
    
    private String mensagem = Mensagens.getMensagem("exception.senha.invalida");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
