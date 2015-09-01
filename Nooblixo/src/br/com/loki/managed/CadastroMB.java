package br.com.loki.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.loki.bo.UsuarioBO;
import br.com.loki.entity.Usuario;
import br.com.loki.util.CryptMD5;
import br.com.loki.util.JSFHelper;
import br.com.loki.util.Mensagens;

@ManagedBean
@ViewScoped
public class CadastroMB extends LokiManagedBean<Usuario> {
    public CadastroMB() {
        this.setClazz(Usuario.class);
        setBo(new UsuarioBO());
    }

    @Override
    public void actionPersist(ActionEvent event) {
        Usuario usuario;
        try {
            usuario = ((UsuarioBO) getBo()).findByEmail(getEntity().getEmail());
            if (usuario == null) {
                getEntity().setSenha(CryptMD5.encrypt(getEntity().getEmail().toUpperCase(), getEntity().getSenha().toUpperCase()));
                super.actionPersist(event);
                JSFHelper.redirect("index.jsf?faces-redirect=true");
            }else{
                addError("Email já cadastrado", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
        }
    }
}
