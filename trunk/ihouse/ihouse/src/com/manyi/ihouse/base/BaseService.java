package com.manyi.ihouse.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private EntityManager entityManager;
    
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public <T,Y> List<T> execEqualQueryList(Y query,Class<T> clazz,SingularAttribute<? super T, Y> _query) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.and(cb.equal(root.get((SingularAttribute<? super T, Y>) _query), query))).select(root);
		return entityManager.createQuery(cq).getResultList();
	}
    
    public <T,Y> T execEqualQuerySingle(Y query,Class<T> clazz,SingularAttribute<? super T, Y> _query) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.and(cb.equal(root.get((SingularAttribute<? super T, Y>) _query), query))).select(root);
		return entityManager.createQuery(cq).getSingleResult();
	}
    
    public <T> T executeFindQuery(Class<T> clazz,Object query) {
		return entityManager.find(clazz, query);
	}
}
