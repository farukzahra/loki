package br.com.loki.managed;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.loki.bo.BO;
import br.com.loki.bo.UsuarioBO;
import br.com.loki.entity.Usuario;
import br.com.loki.exception.BancoDadosException;
import br.com.loki.exception.IntegridadeReferencialException;
import br.com.loki.exception.RegistroExistenteException;
import br.com.loki.util.JSFHelper;
import br.com.loki.util.Mensagens;

public abstract class LokiManagedBean<E extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private BO<E> bo;

    private Class<E> clazz;

    private E entity;

    private List<E> entityList;

    private static Logger log = Logger.getLogger(LokiManagedBean.class);

    private static SelectItem[] status = new SelectItem[2];

    private static SelectItem[] statusProtocolo = new SelectItem[3];

    private TimeZone timeZone = TimeZone.getDefault();

    private Locale locale = new Locale("pt", "BR");

    private SessionManaged sessionManaged;

    @PostConstruct
    public void init() {
        getEntity();
    }

    public LokiManagedBean() {
        this.bo = new BO<E>();
    }

    public Class<E> getClazz() {
        return clazz;
    }

    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
        this.bo.setClazz(clazz);
    }

    @SuppressWarnings("unchecked")
    public E getEntity() {
        if (entity == null) {
            try {
                entity = (E) Class.forName(getClazz().getName()).newInstance();
                // entity = (E) clazz.newInstance();
            } catch (Exception e) {
                log.error("Erro no getEntity", e);
            }
        }
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public void actionNew(ActionEvent event) {
        entity = null;
        // getLumeSecurity().clearLumeSecurity();
        JSFHelper.initUIViewRoot();
    }

    public void initUIViewRoot() {
        JSFHelper.initUIViewRoot();
    }

    public void actionPersist(ActionEvent event) {
        try {
            if (bo.persist(getEntity())) {
                // actionNew(event);
                addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
            } else
                addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
        } catch (BancoDadosException e) {
            addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
        } catch (RegistroExistenteException e) {
            addWarn(e.getMensagem(), "");
        } catch (IntegridadeReferencialException e) {
            addWarn(e.getMensagem(), "");
        }
    }

    public void actionRemove(ActionEvent event) {
        try {
            if (bo.remove(getEntity())) {
                actionNew(event);
                addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
            } else
                addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
        } catch (IntegridadeReferencialException e) {
            log.error("Erro no actionRemove", e);
            addError(e.getMensagem(), "");
        } catch (BancoDadosException e) {
            log.error("Erro no actionRemove", e);
            addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
        }
    }

    public List<E> getEntityList() {
        return entityList;
    }

    public void validationFailed(FacesContext context, UIComponent validate) {
        ((UIInput) validate).setValid(false);
        context.validationFailed();
        context.addMessage(validate.getClientId(context), new FacesMessage(((UIInput) validate).getValidatorMessage()));
    }

    public void addInfo(String summary, String detail) {
        addMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    public void addWarn(String summary, String detail) {
        addMessage(FacesMessage.SEVERITY_WARN, summary, detail);
    }

    public void addError(String summary, String detail, Exception ex) {
        log.error(summary, ex);
        addMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
    }

    public void addError(String summary, String detail) {
        addError(summary, detail, null);
    }

    public void addFatal(String summary, String detail) {
        addMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
    }

    private void addMessage(Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public SelectItem[] getStatus() {
        return status;
    }

    public SelectItem[] getStatusProtocolo() {
        return statusProtocolo;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public BO<E> getBo() {
        return bo;
    }

    public void setBo(BO<E> bo) {
        this.bo = bo;
    }

    public void setEntityList(List<E> entityList) {
        this.entityList = entityList;
    }

    public Usuario getUsuarioLogado() {
        /**
        try {
            return new UsuarioBO().find(1L);
        } catch (BancoDadosException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        **/
        return getSessionManaged().getUsuarioLogado();
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.getSessionManaged().setUsuarioLogado(usuarioLogado);
    }

    public SessionManaged getSessionManaged() {
        HttpSession httpSession = JSFHelper.getSession();
        sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
        if (sessionManaged == null) {
            sessionManaged = new SessionManaged();
            httpSession.setAttribute("sessionManaged", sessionManaged);
        }
        return sessionManaged;
    }

    public static SessionManaged createInstance() {
        HttpSession httpSession = JSFHelper.getSession();
        SessionManaged sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
        if (sessionManaged == null) {
            sessionManaged = new SessionManaged();
            httpSession.setAttribute("sessionManaged", sessionManaged);
        }
        return sessionManaged;
    }

    public void setSessionManaged(SessionManaged sessao) {
        this.sessionManaged = sessao;
    }
}
