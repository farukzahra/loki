package br.com.loki.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.loki.entity.Noob;
import br.com.loki.entity.Usuario;
import br.com.loki.exception.BancoDadosException;

public class NoobBO extends BO<Noob> {
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(NoobBO.class);

    public NoobBO() {
        super();
        setClazz(Noob.class);
    }

    public List<Noob> listByUsuario(Usuario usuario) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usuario.id", usuario.getId());
        return super.listByFields(map, new String[] { "id desc" });
    }
    
    public List<Noob> listByUsuarioAndInvocador(Usuario usuario, String invocador) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usuario.id", usuario.getId());
        map.put("invocador", invocador);
        return super.listByFields(map, new String[] { "id desc" });
    }
}
