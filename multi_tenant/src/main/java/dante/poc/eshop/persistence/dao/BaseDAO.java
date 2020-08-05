package dante.poc.eshop.persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * To provide all DAO common functions
 * <p>
 * @author Dante
 */
public class BaseDAO<T> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	protected EntityManager em;

	protected final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDAO() {
		ParameterizedType parameterizedType = null;
		Class<?> clazz = getClass();

		while (true) {
			Type type = clazz.getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				parameterizedType = (ParameterizedType)type;
				break;
			}

			clazz = clazz.getSuperclass();
		}

		entityClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
	}

	/**
	 * Find data by id
	 */
	public T find(Object id) {
		return em.find(entityClass, id);
	}

	/**
	 * Update data
	 */
	public T merge(T entity) {
		return em.merge(entity);
	}

	/**
	 * Insert data
	 */
	public void persist(T entity) {
		em.persist(entity);
	}

	/**
	 * Remove data
	 */
	public void remove(T entity) {
		em.remove(entity);
	}

	/**
	 * flush data
	 */
	public void flush() {
		em.flush();
	}

	/**
	 * clear data
	 */
	public void clear() {
		em.clear();
	}

	/**
	 * Detach data
	 */
	public void detach(T entity) {
		em.detach(entity);
	}

	/**
	 * Create TypedQuery
	 */
	protected TypedQuery<T> createQuery(String jpql) {
		return em.createQuery(jpql, entityClass);
	}

	protected TypedQuery<T> createQuery(String jpql, Map<String,Object> params) {
		TypedQuery<T> query = createQuery(jpql);
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> e : entrySet) {
			query.setParameter(e.getKey(), e.getValue());
		}
		return query;
	}

	protected long queryTotal(String jpql, Map<String,Object> params) {
		Query query = em.createQuery(jpql);
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> e : entrySet) {
			query.setParameter(e.getKey(), e.getValue());
		}
		return (Long)query.getSingleResult();
	}

	/**
	 * Create TypedQuery
	 */
	protected TypedQuery<T> createNamedQuery(String name) {
		return em.createNamedQuery(name, entityClass);
	}

	/**
	 * Create TypedQuery, for specific field used
	 */
	protected <M> TypedQuery<M> createQuery(String jpql, Map<String,Object> params, Class<M> clazz) {
		TypedQuery<M> query = em.createQuery(jpql, clazz);
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> e : entrySet) {
			query.setParameter(e.getKey(), e.getValue());
		}
		return query;
	}

	protected boolean isDefined(String field) {
		return !StringUtils.isEmpty(field);
	}

	protected boolean hasOwnProperty(Class<?> object, String property) {
		try {
			object.getDeclaredField(property);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
