package com.project.shopping.service;


import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    // 상품 생성
    public Product create(Product product){

        System.out.println(product.getName());
        if(product.getName() == ""){
            System.out.println("Asdf");
        }
        // 왜 안걸림??

        if(product == null  || product.getName() == "" ||  product.getTitle()== ""
                && product.getContent() == "" ||  product.getPrice() == 0 ||  product.getTotal() == 0
                ||  product.getImgUrl() == ""){
            throw  new RuntimeException();
        }

        return productRepository.save(product);
    }

    public Product findProductNameUser(int id, User user){
        return productRepository.findByIdAndUserId(id, user);
    }
    public void deleteProduct(Product product){productRepository.delete(product);
    }
    public Product findproductid(int id){
        return productRepository.findById(id);
    }

    public List<Product> findall(){
        return productRepository.findAll();
    }

    public List<Product> getProductList(String title){return productRepository.getProductList(title);}

    public List<Product> findallByUserId(User user){return  productRepository.findAllByUserId(user); }
}
