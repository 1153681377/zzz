package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.zzz.model.Project;
import com.zzz.model.Projectman;
import com.zzz.model.Task;
import com.zzz.model.User;

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
	public void update(Project project) {
		this.getSession().merge(project);
	}
	public void delete(Project project) {
		this.getSession().delete(project);
	}
	public List select(String id){
		   String hql = "SELECT p FROM User AS u,Project AS p,Projectman AS m "
				   + "WHERE u.user_id=m.user AND p.pro_id=m.project AND u.user_id='"+id+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	}
	public List<Project> selectpro(String proid) {
		   String hql = "SELECT p FROM Project AS p WHERE p.pro_id='"+proid+"'";
		   Query query= getSession().createQuery(hql);
		return query.list();
	}
	public List<Project> selectuser(User user){
		
		DetachedCriteria dc = DetachedCriteria.forClass(Project.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("user", user));
		List<Project> list=criteria.list();
		return list;
	}
}
