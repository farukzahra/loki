package br.com.loki.managed;

import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.loki.bo.BO;
import br.com.loki.bo.PostBO;
import br.com.loki.entity.Post;

@ManagedBean
@ViewScoped
public class PostMB extends LokiManagedBean<Post> {
    private String mensagem;

    public PostMB() {
        this.setClazz(Post.class);
        setBo(new PostBO());
    }

    public String actionPersist() {
        getEntity().setUsuario(getUsuarioLogado());
        getEntity().setData(Calendar.getInstance().getTime());
        // super.actionPersist(event);
        try {
            new BO<Post>().persist(getEntity());
            mensagem = "Eu tenho um SEGREDU, para descobrir acesse http://play4gold.htiweb.inf.br:8080/segredu/solicitacao.jsf?idpost="+getEntity().getId();
            setEntity(new Post());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pm:second";
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
