package org.example.dao;

import org.example.model.Product;

import java.util.List;

public interface ProductDao {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();
}
