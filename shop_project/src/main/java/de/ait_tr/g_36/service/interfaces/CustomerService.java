package de.ait_tr.g_36.service.interfaces;

import de.ait_tr.g_36.domain.entity.Customer;
import de.ait_tr.g_36.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllActiveCustomers();
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomerById(Long id);
    void deleteCustomerByName(String name);
    Customer restoreCustomerById(Long id);
    int getActiveCustomerCount();
    BigDecimal getCartTotalPrice(Long customerId);
    BigDecimal getCartAveragePrice(Long customerId);
    void addProductToCart(Long customerId, Product product);
    void removeProductFromCart(Long customerId, Long productId);
    void clearCart(Long customerId);
}

