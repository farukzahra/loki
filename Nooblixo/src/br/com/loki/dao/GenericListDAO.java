package br.com.loki.dao;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.loki.bo.BO;
import br.com.loki.exception.BancoDadosException;

public class GenericListDAO<E> {
	protected final EntityManager entityManager;
	
	private static Logger log = Logger.getLogger(GenericListDAO.class);
	
	public GenericListDAO(String persistenceUnitName) {
		entityManager = Connection.getInstance().getEntityManager(persistenceUnitName);
	}

	public E find(Class<E> classEntity, Object primaryKey) throws BancoDadosException {	    
		E entity = null;
		if (classEntity != null && primaryKey != null) {
			try {
				entity = entityManager.find(classEntity, primaryKey);
			}catch (Exception e) {				
				throw new BancoDadosException(e);
			}
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public E find(E entity) throws BancoDadosException{
		if (entity != null) {
			for (Field field : entity.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)) {
					boolean oldAccessible = field.isAccessible();
					field.setAccessible(true);
					try{
                        Object key = field.get(entity);
                        field.setAccessible(oldAccessible);
                        return find((Class<E>) entity.getClass(), key);
                    }catch (Exception e) {
                        throw new BancoDadosException(e);
                    }
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<E> list(Query query) throws BancoDadosException {
		List<E> entities = null;
		if (query != null) {
		    try{
		        entities = query.getResultList();
		    }catch(Exception e){
		        throw new BancoDadosException(e);
		    }
		}
		return entities;
	}

	public List<E> list(String jpql) throws BancoDadosException {
		return list(createQuery(jpql));
	}
	
	public List<E> listAll(Class<E> classEntity) throws BancoDadosException {
		return list(createQuery("select o from "+classEntity.getSimpleName()+" o"));
	}
	
	@SuppressWarnings("unchecked")
    public Object getSingleResult(Query query) throws BancoDadosException{
        Object entity = null;
        if (query != null) {
            try{
                entity = query.getSingleResult();
            }catch(NoResultException nre) {
                return null;
            }catch(Exception e){
                throw new BancoDadosException(e);
            }
        }
        return entity;
    }

	private Query createQuery(String jpql) {
		Query query = null;
		if (jpql != null && !jpql.isEmpty()) {
			query = entityManager.createQuery(jpql);
		}
		return query;
	}
	
	public E findByField(Class<E> classEntity, String nomeCampo, Object valorCampo) throws BancoDadosException  {
        Query query = createQuery("select o from "+classEntity.getSimpleName()+" o where o."+nomeCampo+" = :"+nomeCampo.replace('.', '_'));
        query.setParameter(nomeCampo.replace('.', '_'), valorCampo);
        return (E)getSingleResult(query);
    }
    
    public E findByFields(Class<E> classEntity, Map<String, Object> filtros) throws BancoDadosException {
        Query query = montarQueryDinamica(classEntity, filtros);        
        return (E)getSingleResult(query);
    }
    
    public List<E> listByFields(Class<E> classEntity, Map<String, Object> filtros, String[] ordenacao) throws BancoDadosException {
        Query query = montarQueryDinamica(classEntity, filtros, ordenacao);            
        return (List<E>)list(query);
    }
    
    private Query montarQueryDinamica(Class<E> classEntity,
            Map<String, Object> filtros, String[] ordenacoes) {
        String sql = "select o from "+classEntity.getSimpleName()+" o where ";
        for(String nomeFiltro : filtros.keySet()){
            if(BO.FILTRO_GENERICO_QUERY.equals(filtros.get(nomeFiltro)))
                sql += " and "+nomeFiltro;
            else
                sql += " and o."+nomeFiltro+" = :"+nomeFiltro.replace('.', '_');
                
        }
        sql = sql.replaceFirst(" and ", " ");
        if(ordenacoes != null){
            sql += " order by ";
            for(String ordenacao : ordenacoes){
                sql += " ,o." + ordenacao;
            }
            sql = sql.replaceFirst(" ,", " ");
        }
        Query query = createQuery(sql);
        for(String nomeCampo : filtros.keySet()){
            if(!BO.FILTRO_GENERICO_QUERY.equals(filtros.get(nomeCampo)))
                query.setParameter(nomeCampo.replace('.', '_'), filtros.get(nomeCampo));
        }
        
        return query;
    }
    
    private Query montarQueryDinamica(Class<E> classEntity,
            Map<String, Object> filtros) {
        return montarQueryDinamica(classEntity, filtros, null);
    }
}
