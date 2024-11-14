package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductRepository implements IProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        if (product.getId() == 0) {
            entityManager.persist(product);
        } else {
            entityManager.merge(product);
        }
    }

    @Override
    public void deleteById(int id) {
        Product product = findById(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }
    @Override
    public List<Product> searchByName(String name) {
        return entityManager.createQuery("from Product where name like :name", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}