package br.com.loki.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

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

    public List<Noob> listRelatorio100Mais() throws Exception{
        String sql = "SELECT UPPER(INVOCADOR), COUNT(1) as Total FROM noob GROUP BY UPPER(INVOCADOR) ORDER BY 2 DESC limit 0,100 ";
        Query query = getDao().createNativeQuery(sql);
        List<Object[]> lista = query.getResultList();
        List<Noob> retorno = new ArrayList<Noob>();
        for (Object[] o : lista) {
            Noob n = new Noob();
            n.setInvocador(o[0]+"");
            n.setQtd(Integer.parseInt(""+o[1]));
            retorno.add(n);
        }
        return retorno ;
    }

    public List<Noob> listByUsuario(Usuario usuario) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usuario.id", usuario.getId());
        return super.listByFields(map, new String[] { "id desc" });
    }

    public List<Noob> listByUsuarioAndInvocador(Usuario usuario, String invocador) throws BancoDadosException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usuario.id", usuario.getId());
        map.put("UPPER(o.invocador) = '" + invocador + "'", BO.FILTRO_GENERICO_QUERY);
        return super.listByFields(map, new String[] { "id desc" });
    }
}
