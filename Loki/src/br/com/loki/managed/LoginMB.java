package br.com.loki.managed;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.loki.bo.UsuarioBO;
import br.com.loki.entity.Usuario;
import br.com.loki.util.JSFHelper;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

@ManagedBean
@RequestScoped
public class LoginMB extends LokiManagedBean<Usuario> {
    private String uid;

    private String accessToken;

    public LoginMB() {
        this.setClazz(Usuario.class);
        setBo(new UsuarioBO());
    }
    
    public String getNomeUsuarioLogado(){
        return getUsuarioLogado().getNome();
    }
    
    public void actionLogin() {
        try {
            Usuario usuLogin = ((UsuarioBO)getBo()).doLogin(getEntity());
            if(usuLogin != null){
                setUsuarioLogado(usuLogin);
                JSFHelper.redirect("post.jsf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionLogin_() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        uid = params.get("uidParam");
        accessToken = params.get("accessTokenParam");
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken ,Version.VERSION_2_4);
        System.out.println(uid);
        System.out.println(accessToken);
        User user = facebookClient.fetchObject("me", User.class);
        System.out.println("User name: " + user.getName());
        System.out.println("User name: " + user.getEmail());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
