package de.ait_tr.g_36.controller;

import de.ait_tr.g_36.domain.entity.Customer;
import de.ait_tr.g_36.domain.entity.Product;
import de.ait_tr.g_36.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customers")

public class CustomerController {

private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
    @GetMapping()
    public List<Customer> getAllCustomers() {
        return customerService.getAllActiveCustomers();
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer); // обновление покупателя по ID
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id); // удаление покупателя по ID
    }

    @DeleteMapping("/name/{name}")
    public void deleteCustomerByName(@PathVariable String name) {
        customerService.deleteCustomerByName(name); // удаление покупателя по имени
    }

    @PostMapping("/{id}/restore")
    public Customer restoreCustomer(@RequestParam Long id) {
        return customerService.restoreCustomerById(id); // восстановление удаленного покупателя
    }
    @GetMapping("/count")
    public long countActiveCustomers() {
        return customerService.getActiveCustomerCount(); // получение количества активных покупателей
    }

    @GetMapping("/{id}/cart/total")
    public BigDecimal getCustomerCartTotal(@PathVariable Long id) {
        return customerService.getCartTotalPrice(id); // получение суммы товаров в корзине покупателя
    }

    @GetMapping("/{id}/cart/average")
    public BigDecimal getAverageProductPriceInBasket(@PathVariable Long id) {
        return customerService.getCartAveragePrice(id); // получение средней стоимости товаров в корзине покупателя
    }

    @PostMapping("/{customerId}/cart/{productId}")
    public void addProductToCart(@PathVariable Long customerId, @PathVariable Product productId) {
        customerService.addProductToCart(customerId, productId); // добавление товара в корзину покупателя
    }

    @DeleteMapping("/{customerId}/cart/{productId}")
    public void removeProductFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        customerService.removeProductFromCart(customerId, productId); // удаление товара из корзины покупателя
    }

    @DeleteMapping("/{customerId}/cart")
    public void clearCustomerCart(@PathVariable Long customerId) {
        customerService.clearCart(customerId); // очистка корзины покупателя
    }
}
