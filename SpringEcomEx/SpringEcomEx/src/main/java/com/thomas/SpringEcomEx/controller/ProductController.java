package com.thomas.SpringEcomEx.controller;

import com.thomas.SpringEcomEx.model.Product;
import com.thomas.SpringEcomEx.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity< List<Product> >getProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity< Product > getProductById(@PathVariable("id") int id){
        Product product = productService.getProductById(id);//What if the data is null

        if(product.getId() > 0)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte []> getImageByProductId(@PathVariable("productId") int productId){
        Product product = productService.getProductById(productId);

        if(product.getId() > 0)
            return  new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
        else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product , @RequestPart MultipartFile imageFile){

        try {
            Product saveProduct = productService.addOrUpdateProduct(product ,imageFile);
            return new ResponseEntity<>(saveProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") int id,@RequestPart Product product , @RequestPart MultipartFile imageFile){

        try {
            productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>("updated",HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("product/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable("id") int id){
        Product deleteProduct = productService.getProductById(id);
        if(deleteProduct.getId() >0){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("products/search")
    public  ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword){
        List<Product> searchProducts = productService.searchProduct(keyword);
        System.out.println("Searching with "+keyword);
        return new ResponseEntity<>(searchProducts,HttpStatus.OK);
    }

}
