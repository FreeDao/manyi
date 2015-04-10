package com.manyi.hims;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseService {

	public interface IPredicate<T> {

		public Predicate[] initPredicate(Root<T> root, CriteriaBuilder cb);
	}

	public interface IPredicateParam<T> {

		public void initPredicate(Root<T> root, CriteriaBuilder cb, List<Predicate> list);
	}

	protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void executeDel(Object... objs) {
		for (Object obj : objs) {
			this.getEntityManager().remove(obj);
		}
	}

	public <T, Y> List<T> execEqualQueryList(final Y query, Class<T> clazz, final SingularAttribute<? super T, Y> _query) {

		return execPredicate(1, clazz, new IPredicateParam<T>() {

			@Override
			public void initPredicate(Root<T> root, CriteriaBuilder cb, List<Predicate> list) {
				list.add(cb.and(cb.equal(root.get((SingularAttribute<? super T, Y>) _query), query)));
			}
		});
	}

	public <T, Y> T execEqualQuerySingle(final Y query, Class<T> clazz, final SingularAttribute<? super T, Y> _query) {

		return execPredicateSingle(1, clazz, new IPredicateParam<T>() {

			@Override
			public void initPredicate(Root<T> root, CriteriaBuilder cb, List<Predicate> list) {
				list.add(cb.and(cb.equal(root.get((SingularAttribute<? super T, Y>) _query), query)));
			}
		});
	}

	public <T> List<T> execPredicate(int paramLength, Class<T> clazz, IPredicateParam<T> iPredicate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		List<Predicate> list = new ArrayList<Predicate>(paramLength);
		iPredicate.initPredicate(root, cb, list);
		cq.where(list.toArray(new Predicate[paramLength])).select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	public <T> List<T> execPredicate(Class<T> clazz, IPredicate<T> iPredicate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(iPredicate.initPredicate(root, cb)).select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	public <T> List<T> execPredicate(Class<T> clazz, Predicate... predicates) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(predicates).select(root);
		return entityManager.createQuery(cq).getResultList();
	}

	public <T> T execPredicateSingle(int paramLength, Class<T> clazz, IPredicateParam<T> iPredicate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		List<Predicate> list = new ArrayList<Predicate>(paramLength);
		iPredicate.initPredicate(root, cb, list);
		cq.where(list.toArray(new Predicate[paramLength])).select(root);
		return entityManager.createQuery(cq).getSingleResult();
	}

	public <T> T executeFindQuery(Class<T> clazz, Object query) {
		return entityManager.find(clazz, query);
	}
}
