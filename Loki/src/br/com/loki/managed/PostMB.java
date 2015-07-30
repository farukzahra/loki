package br.com.loki.managed;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.loki.bo.BO;
import br.com.loki.bo.PostBO;
import br.com.loki.bo.SolicitacaoBO;
import br.com.loki.entity.Post;
import br.com.loki.entity.Solicitacao;
import br.com.loki.exception.BancoDadosException;
import br.com.loki.exception.IntegridadeReferencialException;
import br.com.loki.exception.RegistroExistenteException;

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
            mensagem = "Eu fiz um POST SECRETO no LOKI.ME (http://loki.me), se quiser descobrir o que eu escrevi acesso o LOKI.ME com este código: " + getEntity().getId() + " ou clique aqui http://localhost:8080/Loki/solicitacao.jsf?idpost="+getEntity().getId();
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
