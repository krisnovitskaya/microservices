package ru.krisnovitskaya.order.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.krisnovitskaya.dtos.OrderDto;
import ru.krisnovitskaya.dtos.ProductDto;

import ru.krisnovitskaya.order.entities.Order;
import ru.krisnovitskaya.order.services.OrderService;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostMapping("/makeorder")
    public List<String> makeOrder(@RequestBody List<Long> ids){

        List<String> strings = new ArrayList<>();

        //List<ProductDto> dtos = (List<ProductDto>)restTemplate.postForObject("http://product-ms/getproducts",ids, ResponseEntity.class);
        List<ProductDto> dtos = (List<ProductDto>)restTemplate.postForObject("http://product-ms/getproducts",ids, Object.class);
        //String dtos = restTemplate.postForObject("http://product-ms/getproducts",ids, String.class);
        //String dtos = restTemplate.getForObject("http://product-ms/get",String.class);

        for (Long id : ids) {
            strings.add(String.valueOf(id));
        }
        strings.add(dtos.toString());
        return strings;
    }

    @GetMapping("/showorders")
    public List<ProductDto> getOrders(){
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = orderService.findAll();
        Set<Long> unique = new HashSet<>();
        for (Order order : orders) {
            unique.addAll(order.getItems());
        }
        List<Long> ids = new ArrayList<>();
        for (Long id : unique) {
            ids.add(id);
        }
        List<ProductDto> dtos = (ArrayList<ProductDto>) restTemplate.postForObject("http://product-ms/getproducts", ids, Object.class);
        //Object dtos =  restTemplate.postForObject("http://product-ms/getproducts", ids, Object.class);
//        for (ProductDto product : dtos) {
//
//        }

        return dtos;
    }
}

//[
//        12,
//        121,
//        12,
//        1211
//        ]
