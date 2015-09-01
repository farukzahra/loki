package br.com.loki.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

import br.com.loki.exception.BancoDadosException;
import br.com.loki.exception.IntegridadeReferencialException;
import br.com.loki.exception.RegistroExistenteException;

import com.ibm.db2.jcc.uw.DB2Exception;

public class GenericDAO<E> extends GenericListDAO<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String LOKI = "Loki";
	
	private static Logger log = Logger.getLogger(GenericDAO.class);
	
	public GenericDAO(String persistenceUnitName) {
	    super(persistenceUnitName);
	}
	
	public GenericDAO() {
        super(LOKI);
    }
	
	public EntityTransaction getTransaction(){
	    return entityManager.getTransaction();
	}

	public boolean persist(E entity) throws BancoDadosException, RegistroExistenteException {
		boolean persist = false;
		if (entity != null) {
			try {
				entityManager.getTransaction().begin();
				entityManager.persist(entity);
				entityManager.getTransaction().commit();
				persist = true;
			}catch (Exception e) {
				if (entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
				tratarExceptionRegistroExistente(e);
				//log.error("Erro no persist", e);
				throw new BancoDadosException(e);
			}
		}
		return persist;
	}

	public boolean merge(E entity) throws BancoDadosException, IntegridadeReferencialException, RegistroExistenteException {
		boolean merge = false;
		if (entity != null) {
			try {
				entityManager.getTransaction().begin();
				entityManager.merge(entity);
				entityManager.getTransaction().commit();
				merge = true;
			}
			catch (Exception e) {
				if (entityManager.getTransaction().isActive()) {
					entityManager.getTransaction().rollback();
				}
				tratarExceptionRegistroExistente(e);
				tratarExceptionIntegridade(e);
				log.error("Erro no merge", e);				
				throw new BancoDadosException(e);
			}
		}
		return merge;
	}
	
	public void persistBatch(List<E> entities) throws BancoDadosException, RegistroExistenteException {
	    entityManager.setFlushMode(FlushModeType.COMMIT);
        EntityTransaction tx = entityManager.getTransaction();
        if (!tx.isActive())
            tx.begin();
        int i = 0;
        for ( E entity : entities ) {
            try {
                entityManager.persist(entity);            
                if ( i++ % 5000 == 0 ) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }catch (Exception e) {
                if (tx.isActive())
                    tx.rollback();   
                tratarExceptionRegistroExistente(e);
                log.error("Erro no persistBatch", e);  
                throw new BancoDadosException(e);
            }
        }
        entityManager.flush();
        entityManager.clear();
        tx.commit();        
    }
    
    public void mergeBatch(List<E> entities) throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
        EntityTransaction tx = entityManager.getTransaction();
        if (!tx.isActive())
            tx.begin();
        int i = 0;
        for ( E entity : entities ) {
            try {
                entityManager.merge(entity);
                if ( i++ % 2000 == 0 ) { 
                    entityManager.flush();
                    entityManager.clear();
                }
            }catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                tratarExceptionRegistroExistente(e);
                tratarExceptionIntegridade(e);
                log.error("Erro no merge", e);  
                throw new BancoDadosException(e);
            }            
        }
        entityManager.flush();
        entityManager.clear();
        tx.commit();        
    }

	public boolean remove(E entity) throws BancoDadosException, IntegridadeReferencialException {
		boolean remove = false;
		try {
			if (entity != null) {
				entityManager.getTransaction().begin();
				entity = entityManager.merge(entity);
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
				remove = true;
			}
		}catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			tratarExceptionIntegridade(e);		
			throw new BancoDadosException(e);
		}
		return remove;
	}
	
	public void removeBatch(List<E> entities) throws BancoDadosException, IntegridadeReferencialException {        
        EntityTransaction tx = entityManager.getTransaction();
        if (!tx.isActive())
            tx.begin();
        int i = 0;
        for(E entity : entities){                
            try {
                entity = entityManager.merge(entity);
                entityManager.remove(entity);
                if ( i++ % 20 == 0 ) { 
                    entityManager.flush();
                    entityManager.clear();
                }
            }catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                tratarExceptionIntegridade(e);
                throw new BancoDadosException(e);
            }                
        }
        tx.commit();

    }

	public boolean refresh(E entity) throws BancoDadosException {
		boolean refresh = false;
		if (entity != null) {
			entityManager.refresh(entity);
			refresh = true;
		}
		return refresh;
	}

	public Query createQuery(String jpql) throws BancoDadosException {
		Query query = null;
		if (jpql != null && !jpql.isEmpty()) {
			query = entityManager.createQuery(jpql);
		}
		return query;
	}

	public Query createNamedQuery(String nameQuery) throws BancoDadosException {
		Query query = null;
		if (nameQuery != null && !nameQuery.isEmpty()) {
			query = entityManager.createNamedQuery(nameQuery);
		}
		return query;
	}

	public void executeQuery(Query query) throws BancoDadosException {
		if (query != null) {
			entityManager.getTransaction().begin();
			query.executeUpdate();
			entityManager.flush();
			entityManager.getTransaction().commit();
		}
	}
	
	public Query createNativeQuery(String sql, Class clazz) throws BancoDadosException {
		Query query = null;
		if (sql != null && !sql.isEmpty()) {
			query = entityManager.createNativeQuery(sql, clazz);
		}
		return query;
	}
	
	public Query createNativeQuery(String sql) throws BancoDadosException {
        Query query = null;
        if (sql != null && !sql.isEmpty()) {
            query = entityManager.createNativeQuery(sql);
        }
        return query;
    }
	
	public StoredProcedureQuery createStoredProcedure(String nome){
        return entityManager.createStoredProcedureQuery(nome);       
	}
	
	// FIXME TROCAR PARA CODIGO DB2
	private void tratarExceptionRegistroExistente(Exception e) throws RegistroExistenteException {
        if(e.getCause() instanceof DatabaseException){
            DatabaseException de = (DatabaseException)e.getCause();
            if(de.getInternalException() instanceof DB2Exception){   
                DB2Exception pe = (DB2Exception)de.getInternalException();
                if("23505".equals(pe.getSQLState())){
                    log.info("Erro de unique", e);  
                    String mensagem = "";
                    if(e.getMessage().toLowerCase().contains("unq_sala_nome_camara")){
                        mensagem = "Já existe uma Sala com esse Nome para a mesma Câmara.";
                    }else if(e.getMessage().toLowerCase().contains("unq_sala_numero_camara")){
                        mensagem = "Já existe uma Sala com esse Número para a mesma Câmara.";
                    }else if(e.getMessage().toLowerCase().contains("unq_ses_participante_doc")){
                        mensagem = "Já existe um Participante com esse Documento.";
                    }else if(e.getMessage().toLowerCase().contains("unq_ses_participante_email")){
                        mensagem = "Já existe um Participante com esse E-mail.";
                    }else if(e.getMessage().toLowerCase().contains("unq_ses_participante_nome")){
                        mensagem = "Já existe um Participante com esse Nome.";
                    }else if(e.getMessage().toLowerCase().contains("unq_seg_empresa_nome")){
                        mensagem = "Já existe um Empresa com esse Nome.";
                    }else if(e.getMessage().toLowerCase().contains("unq_sessao_nome")){
                        mensagem = "Já existe uma Sessão com esse Nome.";
                    }else if(e.getMessage().toLowerCase().contains("unq_camara_nome")){
                        mensagem = "Já existe uma Câmara com esse Nome.";
                    }else if(e.getMessage().toLowerCase().contains("unq_camara_nome_abrev")){
                        mensagem = "Já existe uma Câmara com esse Nome Abreviado.";
                    }else if(e.getMessage().toLowerCase().contains("unq_sessao_camara_data")){
                        mensagem = "Já existe uma Sessão com esse Câmara em mesma Data.";
                    }else if(e.getMessage().toLowerCase().contains("unq_sca_int_cod_externa")){
                        mensagem = "Já existe uma Câmara com esse Código Externo.";
                    }else{
                        mensagem = "Registro duplicado.";
                    }
                    throw new RegistroExistenteException(e.getCause(), mensagem);
                }
            }
        }
    }
	
	private void tratarExceptionIntegridade(Exception e) throws IntegridadeReferencialException {
        if(e.getCause() instanceof DatabaseException){
            DatabaseException de = (DatabaseException)e.getCause();
            if(de.getInternalException() instanceof DB2Exception){   
                DB2Exception pe = (DB2Exception)de.getInternalException();
                if("23503".equals(pe.getSQLState())){                           
                    log.info("Erro no remove", e);         
                    throw new IntegridadeReferencialException(e.getCause());
                }
            }
        }
    }
}
