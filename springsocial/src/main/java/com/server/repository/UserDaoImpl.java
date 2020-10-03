package com.server.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.server.entity.User;
@Repository("userDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	 private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sf) {
		  this.sessionFactory = sf;
		 }
		 
	@Override
	@Transactional
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where username=:username");
		query.setParameter("username", username);
		List<User> list = query.getResultList();
		if(list.size()==1){
			return list.get(0);
		}
		return null;
	}


	
	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where id=:id");
		query.setParameter("id", id);
		List<User> list = query.getResultList();
		if(list.size()==1){
			return list.get(0);
		}
		return null;
	}

	@Override
	public User findByProviderIdAndProviderUserId(String providerId, String providerUserId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where providerId=:providerId and providerUserId=:providerUserId");
		query.setParameter("providerId", providerId);
		query.setParameter("providerUserId", providerUserId);
		List<User> list = query.getResultList();
		if(list.size()==1){
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void save(User user) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		  session.persist(user);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		  session.saveOrUpdate(user);
	}

	
}
