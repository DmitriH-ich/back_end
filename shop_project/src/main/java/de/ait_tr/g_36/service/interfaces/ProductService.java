package de.ait_tr.g_36.service.interfaces;

import de.ait_tr.g_36.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto product);
    List<ProductDto> getAllActiveProducts();
    ProductDto getById(Long id);
    ProductDto update (ProductDto product);
    void deleteById(Long id);
    void deleteByTitle(String title);
    void restoreById(Long id); // восстанавливание
    long getAllActiveProductsQuantity(); // вернуть все активные товары
    BigDecimal getAllActiveProductsTotalPrice();// общая цена
    BigDecimal getAllActiveProductsAveragePrice();// средняя цена


}
