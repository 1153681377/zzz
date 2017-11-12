package com.zzz.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zzz.model.Project;
import com.zzz.model.Projectman;
@Repository
@Transactional
public class ProjectmanRepository {

	
		@PersistenceContext
		private EntityManager entityManager;
		public Session getSession() {
			return entityManager.unwrap(Session.class);
		}
		public void save(Projectman projectman) {
			this.getSession().save(projectman);
		}
		public void update(Projectman projectman) {
			this.getSession().merge(projectman);
		}
		public void delete(Projectman projectman) {
			this.getSession().delete(projectman);
		}
		public List<Projectman> mandelete(Project project){
			DetachedCriteria dc = DetachedCriteria.forClass(Projectman.class);
			Criteria criteria = dc.getExecutableCriteria(getSession());//在线获取seesion
			criteria.add(Restrictions.eq("project", project));
			List<Projectman> list=criteria.list();
			return list;
		}
   

}
