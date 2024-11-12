package org.example.product_management.service;

import org.example.product_management.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService implements IProductService {
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, "Iphone 12", 1000, "New", "Apple"));
        products.add(new Product(2, "Samsung Galaxy S21", 900, "New", "Samsung"));
        products.add(new Product(3, "Xiaomi Mi 11", 800, "New", "Xiaomi"));
        products.add(new Product(4, "Oppo Find X3", 700, "New", "Oppo"));
        products.add(new Product(5, "Vivo X60", 600, "New", "Vivo"));
    }


    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void remove(int id) {
        Product product = findById(id);
        products.remove(product);
    }

    @Override
    public void update(int id, Product product) {
        Product productFound = findById(id);
        if (productFound != null) {
            productFound.setName(product.getName());
            productFound.setPrice(product.getPrice());
            productFound.setDescription(product.getDescription());
            productFound.setManufacturer(product.getManufacturer());
        }

    }

    @Override
    public List<Product> search(String name) {
        List<Product> productsFound = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(name)) {
                productsFound.add(product);
            }
        }
        return productsFound;

    }
}
