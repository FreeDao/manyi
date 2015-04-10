/**
 * 
 */
package com.manyi.ihouse.interceptor;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author leo
 * 
 */
@Aspect
public class ConnectionReadOnlyInterceptor implements Ordered {

	private int order;
	private EntityManager entityManager;

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Around("@annotation(transactional)")
	public Object proceed(ProceedingJoinPoint pjp, Transactional transactional) throws Throwable {
		SessionImplementor session =entityManager.unwrap(SessionImplementor.class);
		Connection connection = session.connection();//entityManager.unwrap(java.sql.Connection.class);

		boolean autoCommit = connection.getAutoCommit();
		boolean readOnly = connection.isReadOnly();

		try {
//			connection.setAutoCommit(false);
//			connection.setReadOnly(transactional.readOnly());
			if(transactional.readOnly()){
			    connection.setReadOnly(true);
			    connection.setAutoCommit(false);
			}
			return pjp.proceed();
		} finally {
		    //connection.commit();
			// restore state
		    if(transactional.readOnly()){
		    connection.commit();
			connection.setReadOnly(readOnly);
			connection.setAutoCommit(autoCommit);
		    }
		}
	}
}
