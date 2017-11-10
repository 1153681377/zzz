package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.zzz.model.Project;

@Repository
@Transactional
public class ProjectRepository {
	@PersistenceContext
	private EntityManager entityManager;
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void save(Project project) {
		this.getSession().save(project);
	}
	public List select(String id){
		   String hql = "SELECT p FROM User AS u,Project AS p,Projectman AS m "
				   + "WHERE u.user_id=m.user AND p.pro_id=m.project AND u.user_id='"+id+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
	public List selectpro(String proid) {
		   String hql = "SELECT p FROM Project AS p WHERE p.pro_id='"+proid+"'";
		   Query query= getSession().createQuery(hql);
		return query.list();
	}
}
