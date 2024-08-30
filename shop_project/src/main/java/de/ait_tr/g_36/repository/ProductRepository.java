package de.ait_tr.g_36.repository;

import de.ait_tr.g_36.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByTitle(String title); // добавляем метод поиска по названию
}
