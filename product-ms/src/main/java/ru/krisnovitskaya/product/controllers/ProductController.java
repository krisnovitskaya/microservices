package ru.krisnovitskaya.product.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.krisnovitskaya.dtos.ProductDto;
import ru.krisnovitskaya.product.entities.Product;
import ru.krisnovitskaya.product.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @PostMapping("/getproducts")
    public ArrayList<ProductDto> getProducts(HttpEntity<List<Long>> longs){
        List<Long> ids = longs.getBody();
        ArrayList<ProductDto> pdto = new ArrayList<>();
        List<Product> products = productService.findAllByListId(ids);
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setTitle(product.getTitle());
            productDto.setPrice(product.getPrice());
            pdto.add(productDto);
        }
        return pdto;
    }

}
