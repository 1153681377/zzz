package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zzz.model.Task;

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
	public List<Task> select(){
		DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
		List list=criteria.list();
		return list;
	}
}
