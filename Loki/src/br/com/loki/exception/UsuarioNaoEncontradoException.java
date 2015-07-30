package br.com.loki.exception;

import br.com.loki.util.Mensagens;


public class UsuarioNaoEncontradoException extends BusinessException {
    
    private static String mensagem = Mensagens.getMensagem("exception.usuario.nao.encontrado");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
