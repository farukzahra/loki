package br.com.loki.managed;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.loki.bo.UsuarioBO;
import br.com.loki.entity.Usuario;
import br.com.loki.util.JSFHelper;

@ManagedBean
@RequestScoped
public class LoginMB extends LokiManagedBean<Usuario> {
    private String email, nome, idfacebook;

    public LoginMB() {
        this.setClazz(Usuario.class);
        setBo(new UsuarioBO());
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String logoff = JSFHelper.getRequestParameterMap("logoff");
        if(logoff != null && logoff.equals("true")){
            setUsuarioLogado(null);
            JSFHelper.redirect("login.jsf?faces-redirect=true");
        }        
    }

    public String getNomeUsuarioLogado() {
        return getUsuarioLogado().getNome();
    }

    public void actionLogin() {
        try {
            Usuario usuLogin = actionLoginFacebook();
            if(usuLogin == null) {
                usuLogin = ((UsuarioBO) getBo()).doLogin(getEntity());
            }
            if (usuLogin != null) {
                setUsuarioLogado(usuLogin);
                if (JSFHelper.getSession().getAttribute("ID_POST") != null) {
                    JSFHelper.redirect("solicitacao.jsf?faces-redirect=true");
                } else {
                    JSFHelper.redirect("post.jsf?faces-redirect=true");
                }
            }else{
                addError("Usuário/Senha inválidos.", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario actionLoginFacebook() throws Exception{
        email = JSFHelper.getRequestParameterMap("email");
        nome = JSFHelper.getRequestParameterMap("nome");
        idfacebook = JSFHelper.getRequestParameterMap("idfacebook");
        if(email != null && !email.isEmpty()) {
            Usuario usuario = ((UsuarioBO)getBo()).findByEmail(email);
            if(usuario == null){
                usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setIdFacebook(idfacebook);
                usuario.setNome(nome);
                ((UsuarioBO)getBo()).persist(usuario);                
            }else{
                if(usuario.getIdFacebook() == null){
                    usuario.setIdFacebook(idfacebook);
                    usuario.setNome(nome);
                    ((UsuarioBO)getBo()).persist(usuario);        
                }
            }
            return usuario;
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdfacebook() {
        return idfacebook;
    }

    public void setIdfacebook(String idfacebook) {
        this.idfacebook = idfacebook;
    }
}
