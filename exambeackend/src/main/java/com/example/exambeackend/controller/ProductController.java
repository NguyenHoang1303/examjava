package com.example.exambeackend.controller;

import com.example.exambeackend.entity.Product;
import com.example.exambeackend.entity.SellRequest;
import com.example.exambeackend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product p) {
        return productRepo.save(p);
    }

    @PostMapping("sell")
    public ResponseEntity sellProduct(@RequestBody SellRequest sell) {
        Product product = productRepo.findById((long) sell.getId()).orElse(null);
        if (product == null){
            return ResponseEntity.ok("Sản phẩm không tồn tại!");
        }
        int result = product.getQuantity() - sell.getQuantity();
        if (result < 0){
            return ResponseEntity.ok("Số lượng sản phẩm lớn hơn lượng tồn kho!");
        }
        product.setQuantity(result);
        return ResponseEntity.ok(productRepo.save(product));
    }
}
