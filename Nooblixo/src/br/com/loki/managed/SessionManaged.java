package br.com.loki.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import br.com.loki.bo.UsuarioBO;
import br.com.loki.entity.Usuario;

@ManagedBean
@SessionScoped
public class SessionManaged extends LokiManagedBean<Usuario> {
    private static final long serialVersionUID = 1L;

    private Usuario usuarioLogado;

    private String idPost;

    private static Logger log = Logger.getLogger(SessionManaged.class);

    public SessionManaged() {
        setClazz(Usuario.class);
        setBo(new UsuarioBO());
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }
}
