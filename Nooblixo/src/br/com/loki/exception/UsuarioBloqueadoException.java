package br.com.loki.exception;

import br.com.loki.util.Mensagens;


public class UsuarioBloqueadoException extends BusinessException{
    
    private String mensagem = Mensagens.getMensagem("exception.usuario.bloqueado");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
