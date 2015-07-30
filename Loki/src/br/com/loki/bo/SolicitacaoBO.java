package br.com.loki.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.loki.entity.Post;
import br.com.loki.entity.Solicitacao;
import br.com.loki.entity.Usuario;
import br.com.loki.exception.BancoDadosException;

public class SolicitacaoBO extends BO<Solicitacao> {
    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(SolicitacaoBO.class);

    public SolicitacaoBO() {
        super();
        setClazz(Solicitacao.class);
    }

    public List<Solicitacao> listByPost(Post post) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("post.id", post.getId());
        return super.listByFields(map);
    }

    public List<Solicitacao> listByUsuario(Usuario usuario) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("solicitante.id", usuario.getId());
        return super.listByFields(map, new String[] { "id desc" });
    }
    
    public boolean existeSolicitacao(Post post, Usuario usuario) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("solicitante.id", usuario.getId());
        map.put("post.id", post.getId());
        List<Solicitacao> sols = super.listByFields(map);
        return sols != null && sols.size() > 0;
    }
}
