package ru.krisnovitskaya.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private long id;
    private List<ProductDto> orderItems = new ArrayList<>();
    private int price;
}
