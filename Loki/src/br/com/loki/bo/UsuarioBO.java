package br.com.loki.bo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.loki.entity.Usuario;
import br.com.loki.util.CryptMD5;

public class UsuarioBO extends BO<Usuario> {
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(UsuarioBO.class);

    public UsuarioBO() {
        super();
        setClazz(Usuario.class);
    }

    public Usuario doLogin(Usuario usuarioLogin) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("UPPER(o.email) = '"+usuarioLogin.getEmail().toUpperCase() +"'", BO.FILTRO_GENERICO_QUERY);

        Usuario usuarioBanco = super.findByFields(param);
        if(usuarioBanco != null){
            String senhaBanco =  usuarioBanco.getSenha();           
            String senhaLogin = CryptMD5.encrypt(usuarioLogin.getEmail().toUpperCase(), usuarioLogin.getSenha().toUpperCase());
            if(senhaBanco.equals(senhaLogin)){
                return usuarioBanco;
            }
        }
        return null;
    }
}
