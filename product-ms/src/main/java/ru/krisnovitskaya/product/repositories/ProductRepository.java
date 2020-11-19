package ru.krisnovitskaya.product.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.krisnovitskaya.product.entities.Product;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from products where id in :ids", nativeQuery = true)
    List<Product> findAllByListId(@Param("ids") List<Long> ids);

}
