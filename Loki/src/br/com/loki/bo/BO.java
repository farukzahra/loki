package br.com.loki.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.loki.dao.GenericDAO;
import br.com.loki.exception.BancoDadosException;
import br.com.loki.exception.IntegridadeReferencialException;
import br.com.loki.exception.RegistroExistenteException;

public class BO<E extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FILTRO_GENERICO_QUERY = "FILTRO_GENERICO_QUERY";

    private transient GenericDAO<E> dao;

    private Class<E> clazz;

    private static Logger log = Logger.getLogger(BO.class);

    private String persistenceUnitName;

    public BO(
            String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public BO() {
        this.persistenceUnitName = GenericDAO.LOKI;
    }

    public GenericDAO<E> getDao() {
        if (this.dao == null) {
            this.dao = new GenericDAO<E>(persistenceUnitName);
        }
        return this.dao;
    }

    public E find(E entity) throws BancoDadosException {
        return getDao().find(entity);
    }

    public E find(Object primaryKey) throws BancoDadosException {
        return getDao().find(getClazz(), primaryKey);
    }

    public boolean refresh(E entity) throws BancoDadosException {
        return getDao().refresh(entity);
    }

    public boolean persist(E entity) throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
        if (getDao().find(entity) != null) {
            return getDao().merge(entity);
        } else {
            return getDao().persist(entity);
        }
    }

    public boolean persistForce(E entity) throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
        return getDao().persist(entity);
    }

    public void persistBatch(List<E> entities) throws BancoDadosException, RegistroExistenteException {
        getDao().persistBatch(entities);
    }

    public void mergeBatch(List<E> entities) throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
        getDao().mergeBatch(entities);
    }

    public boolean remove(E entity) throws BancoDadosException, IntegridadeReferencialException {
        return getDao().remove(entity);
    }

    public void removeBatch(List<E> entities) throws BancoDadosException, IntegridadeReferencialException {
        getDao().removeBatch(entities);
    }

    public List<E> list(final String jpql) throws BancoDadosException {
        return getDao().list(jpql);
    }

    public List<E> list(final Query jpql) throws BancoDadosException {
        return getDao().list(jpql);
    }

    public List<E> listAll() throws BancoDadosException {
        return getDao().listAll(getClazz());
    }

    public Class<E> getClazz() {
        return clazz;
    }

    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    protected E findByFields(Map<String, Object> filtros) throws BancoDadosException {
        return getDao().findByFields(clazz, filtros);
    }

    protected List<E> listByFields(Map<String, Object> filtros, String[] ordenacao) throws BancoDadosException {
        return getDao().listByFields(clazz, filtros, ordenacao);
    }

    protected List<E> listByFields(Map<String, Object> filtros) throws BancoDadosException {
        return listByFields(filtros, null);
    }
}
