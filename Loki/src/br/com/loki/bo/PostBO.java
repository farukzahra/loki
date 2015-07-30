package br.com.loki.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.loki.entity.Post;
import br.com.loki.entity.Usuario;
import br.com.loki.exception.BancoDadosException;

public class PostBO extends BO<Post> {
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(PostBO.class);

    public PostBO() {
        super();
        setClazz(Post.class);
    }
    
    public List<Post> listByUsuario(Usuario usuario) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usuario.id", usuario.getId());
        return super.listByFields(map, new String[] { "id desc" });
    }
}