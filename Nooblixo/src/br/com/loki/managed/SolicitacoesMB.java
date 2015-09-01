package br.com.loki.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.loki.bo.SolicitacaoBO;
import br.com.loki.entity.Post;
import br.com.loki.entity.Solicitacao;
import br.com.loki.exception.BancoDadosException;

@ManagedBean
@ViewScoped
public class SolicitacoesMB extends LokiManagedBean<Solicitacao> {
    private List<Solicitacao> solicitacoes;

    private Post post;

    public SolicitacoesMB() {
        this.setClazz(Solicitacao.class);
        setBo(new SolicitacaoBO());
        try {
            solicitacoes = new SolicitacaoBO().listByUsuario(getUsuarioLogado());
        } catch (BancoDadosException e) {
            e.printStackTrace();
        }
    }

    public String actionVerPost(Post post) {
        this.post = post;
        return "pm:second";
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
