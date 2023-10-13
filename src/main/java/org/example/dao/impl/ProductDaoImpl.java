package org.example.dao.impl;

import jakarta.persistence.*;
import org.example.dao.ProductDao;
import org.example.exception.DataProcessingException;
import org.example.model.Product;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
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

    @Override
    public void remove(Product product) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = sessionFactory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(product);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can not remove " + product + " from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Product getProductByName(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (EntityManager entityManager = sessionFactory.openSession()) {
            TypedQuery<Product> query = entityManager.createQuery(
                    "FROM Product WHERE name = :name", Product.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Якщо товар не знайдено, повертаємо null
            return null;
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can not get product by name: " + name, e);
        }
    }

    @Override
    public void update(Product product) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = sessionFactory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(product);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException(
                    "Can not update product: " + product, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    @Override
    public int getMaxId() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT MAX(id) FROM Product", Integer.class);
            Integer maxId = (Integer) query.getSingleResult();
            return maxId != null ? maxId : 0;
        } catch (Exception e) {
            throw new DataProcessingException("Error while getting max id: " + e.getMessage(), e);
        }
    }


    @Override
    public Product getProductBySerialNumber(String serialNumber) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (EntityManager entityManager = sessionFactory.openSession()) {
            TypedQuery<Product> query = entityManager.createQuery(
                    "FROM Product WHERE serialNumber = :serialNumber", Product.class);
            query.setParameter("serialNumber", serialNumber);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Якщо товар не знайдено, повертаємо null
            return null;
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can not get product by serial number: " + serialNumber, e);
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        EntityManager entityManager = null;
        try {
            entityManager = sessionFactory.openSession();
            entityManager.getTransaction().begin();

            TypedQuery<Product> query = entityManager.createQuery(
                    "FROM Product WHERE name LIKE :keyword OR serialNumber LIKE :keyword", Product.class);
            query.setParameter("keyword", "%" + keyword + "%");

            List<Product> searchResults = query.getResultList();
            entityManager.getTransaction().commit();

            return searchResults;
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new DataProcessingException("Error while searching for products: " + e.getMessage(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
