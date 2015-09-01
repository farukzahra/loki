package br.com.loki.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.loki.bo.PostBO;
import br.com.loki.bo.SolicitacaoBO;
import br.com.loki.entity.Post;
import br.com.loki.entity.Solicitacao;
import br.com.loki.exception.BancoDadosException;
import br.com.loki.util.Gmail;
import br.com.loki.util.Mensagens;

@ManagedBean
@ViewScoped
public class MeusPostMB extends LokiManagedBean<Post> {
    private List<Solicitacao> solicitacoes;

    public MeusPostMB() {
        this.setClazz(Post.class);
        setBo(new PostBO());
    }

    public String actionBuscarSolicitacao(Post p) {
        try {
            solicitacoes = new SolicitacaoBO().listByPost(p);
        } catch (BancoDadosException e) {
            e.printStackTrace();
        }
        return "pm:second";
    }

    public String actionAprovarSolicitacao(Solicitacao s) {
        try {
            s.setStatus(Solicitacao.APROVADO);
            new SolicitacaoBO().persist(s);
            Gmail.sendMail(s.getSolicitante().getEmail(), "Solicitação Aprovada!", "Solicitação Aprovada: "+Mensagens.getMensagem("url.segredu")+"/segredu/solicitacoes.jsf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pm:second";
    }

    public String actionNegarSolicitacao(Solicitacao s) {
        try {
            s.setStatus(Solicitacao.NEGADO);
            new SolicitacaoBO().persist(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pm:second";
    }

    public List<Post> getPostUsuarioLogado() {
        try {
            return ((PostBO) getBo()).listByUsuario(getUsuarioLogado());
        } catch (BancoDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
}
