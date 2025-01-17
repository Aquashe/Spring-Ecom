package com.thomas.SpringEcomEx.service;

import com.thomas.SpringEcomEx.model.Product;
import com.thomas.SpringEcomEx.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return  productRepo.findAll();

    }


    public Product getProductById(int id) {
        return  productRepo.findById(id).orElse(new Product(-1));
    }

    public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {

        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return  productRepo.save(product);
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
