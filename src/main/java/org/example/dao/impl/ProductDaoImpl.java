package org.example.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.dao.ProductDao;
import org.example.exception.DataProcessingException;
import org.example.model.Product;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class ProductDaoImpl implements ProductDao {


    @Override
    public Product create(Product product) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = sessionFactory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(product);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException(
                    "Can not add product " + product + " into DB ", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return product;
    }

    @Override
    public Product get(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (EntityManager entityManager = sessionFactory.openSession()) {
            return entityManager.find(Product.class, id);
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can not get product by id " + id + " from DB ", e);
        }
    }

    @Override
    public List<Product> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (EntityManager entityManager = sessionFactory.openSession()) {
            Query query = entityManager.createQuery("from Product ", Product.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get all products", e);
        }
    }
}
