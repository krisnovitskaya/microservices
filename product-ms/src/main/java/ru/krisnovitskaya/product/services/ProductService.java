package ru.krisnovitskaya.product.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krisnovitskaya.product.entities.Product;
import ru.krisnovitskaya.product.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllByListId(List<Long> ids){
        return productRepository.findAllByListId(ids);
    }
}
