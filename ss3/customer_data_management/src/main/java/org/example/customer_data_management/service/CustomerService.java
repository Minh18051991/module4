package org.example.customer_data_management.service;

import org.example.customer_data_management.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.example.customer_data_management");
            entityManager = entityManagerFactory.createEntityManager();
            logger.info("SessionFactory and EntityManager initialized successfully.");
        } catch (HibernateException e) {
            logger.error("Initialization of SessionFactory failed.", e);
        }
    }

    @Override
    public List<Customer> findAll() {
        String queryString = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(queryString, Customer.class);
        return query.getResultList();
    }

    @Override
    public void save(Customer customer) {
        Transaction transaction = null;
        Customer origin;
        if (customer.getId() == 0) {
            origin = new Customer();
        } else {
            origin = findById(customer.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(customer.getName());
            origin.setEmail(customer.getEmail());
            origin.setAddress(customer.getAddress());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Error saving customer.", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Customer findById(int id) {
        String queryString = "SELECT c FROM Customer AS c WHERE c.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(queryString, Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void update(int id, Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Customer origin = session.get(Customer.class, id);
            origin.setName(customer.getName());
            origin.setEmail(customer.getEmail());
            origin.setAddress(customer.getAddress());
            session.update(origin);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Error updating customer.", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            session.delete(customer);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Error deleting customer.", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}