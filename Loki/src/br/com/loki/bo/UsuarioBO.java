package br.com.loki.bo;

import org.apache.log4j.Logger;

import br.com.loki.entity.Usuario;

public class UsuarioBO extends BO<Usuario> {
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(UsuarioBO.class);

    public UsuarioBO() {
        super();
        setClazz(Usuario.class);
    }
}