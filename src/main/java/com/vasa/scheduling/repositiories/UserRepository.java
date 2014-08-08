package com.vasa.scheduling.repositiories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.User;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserRepository {
  @Autowired private SessionFactory sessionFactory;
   
  /**
   * @Transactional annotation below will trigger Spring Hibernate transaction manager to automatically create
   * a hibernate session. See src/main/webapp/WEB-INF/servlet-context.xml
   */
  @Transactional
  public List<User> findAll() {
    Session session = sessionFactory.getCurrentSession();
    List users = session.createQuery("from User").list();
    return users;
  }
}