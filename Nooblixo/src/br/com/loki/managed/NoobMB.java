package br.com.loki.managed;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.loki.bo.BO;
import br.com.loki.bo.NoobBO;
import br.com.loki.entity.Noob;
import br.com.loki.exception.BancoDadosException;

@ManagedBean
@ViewScoped
public class NoobMB extends LokiManagedBean<Noob> {
    private String mensagem;

    private List<Noob> noobs;

    public NoobMB() {
        this.setClazz(Noob.class);
        setBo(new NoobBO());
        try {
            noobs = ((NoobBO)getBo()).listByUsuario(getUsuarioLogado());
        } catch (Exception e) {
        }
    }

    public String actionPesquisar(){
        try {
            noobs = ((NoobBO)getBo()).listByUsuarioAndInvocador(getUsuarioLogado(), getEntity().getInvocador());
            if(noobs != null && !noobs.isEmpty()){
                return "pm:second";
            }else{
                addError("Invocador não cadastrado.", "");
            }
        } catch (BancoDadosException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @Override
    public void actionPersist(ActionEvent event) {
        getEntity().setUsuario(getUsuarioLogado());
        getEntity().setData(Calendar.getInstance().getTime());
        // super.actionPersist(event);
        try {
            ((NoobBO)getBo()).persist(getEntity());
            setEntity(new Noob());
            addInfo("Obrigado pelo cadastro!", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<Noob> getNoobs() {
        return noobs;
    }

    public void setNoobs(List<Noob> noobs) {
        this.noobs = noobs;
    }
    
    public String getUrl(){
        return "http://br.op.gg/summoner/userName=";
    }
}
