package ru.stqa.pft.rest.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.rest.model.UserData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbHelper {

  private final SessionFactory sessionFactory;

  DbHelper() {

    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Set<UserData> users() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List result = session.createQuery("from UserData where not username = 'administrator'").list();
    session.getTransaction().commit();
    session.close();
    return new HashSet<UserData>(result);
  }
}
