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

import com.zzz.model.Task;
import com.zzz.model.User;

@Repository
@Transactional
public class TaskRepository {
	@PersistenceContext
	private EntityManager entityManager;
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void save(Task task) {
		this.getSession().save(task);
	}
	public List<Task> select(User user){
		DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		criteria.add(Restrictions.eq("user", user));
		List list=criteria.list();
		return list;
	}
	public List selecttask(String proid) {
		   String hql = "SELECT t FROM Project AS p,Task AS t "
				   + "WHERE t.project=p.pro_id AND  p.pro_id='"+proid+"'";
		   Query query = getSession().createQuery(hql);
		return query.list();
	
	}
}
