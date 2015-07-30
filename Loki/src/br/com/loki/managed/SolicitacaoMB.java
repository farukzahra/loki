package br.com.loki.managed;

import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.loki.bo.BO;
import br.com.loki.bo.PostBO;
import br.com.loki.bo.SolicitacaoBO;
import br.com.loki.entity.Post;
import br.com.loki.entity.Solicitacao;
import br.com.loki.util.Utils;

@ManagedBean
@ViewScoped
public class SolicitacaoMB extends LokiManagedBean<Solicitacao> {
    private String mensagem, idpost;

    public SolicitacaoMB() {
        idpost = (String) Utils.getValorParametro("idpost");
        this.setClazz(Solicitacao.class);
        setBo(new SolicitacaoBO());
    }

    public void actionPersistSolicitacao(ActionEvent event) {
        Post post = null;
        try {
            post = new PostBO().find(Long.parseLong(idpost));
        } catch (Exception e) {
            mensagem = "ID inválido!";
        }
        if (post != null) {
            try {
                if (!((SolicitacaoBO) getBo()).existeSolicitacao(post, getUsuarioLogado())) {
                    getEntity().setPost(post);
                    getEntity().setSolicitante(getUsuarioLogado());
                    getEntity().setData(Calendar.getInstance().getTime());
                    getEntity().setStatus(Solicitacao.CRIADO);
                    // super.actionPersist(event);
                    ((SolicitacaoBO) getBo()).persist(getEntity());
                    mensagem = "Solicitação feita! Você vai receber um email se for LIBERADO!";
                } else {
                    mensagem = "Você já solicitou acesso para este POST.";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mensagem = "ID inválido!";
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Obrigado", mensagem));
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }
}
