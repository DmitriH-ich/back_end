package de.ait_tr.g_36.service;


import de.ait_tr.g_36.domain.entity.Customer;
import de.ait_tr.g_36.domain.entity.Product;
import de.ait_tr.g_36.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Override
    public Customer saveCustomer(Customer customer) {
        return customer;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return List.of();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return customer;
    }

    @Override
    public void deleteCustomerById(Long id) {

    }

    @Override
    public void deleteCustomerByName(String name) {

    }

    @Override
    public Customer restoreCustomerById(Long id) {
        return null;
    }

    @Override
    public int getActiveCustomerCount() {
        return 0;
    }

    @Override
    public BigDecimal getCartTotalPrice(Long customerId) {
        return null;
    }

    @Override
    public BigDecimal getCartAveragePrice(Long customerId) {
        return null;
    }

    @Override
    public void addProductToCart(Long customerId, Product product) {

    }

    @Override
    public void removeProductFromCart(Long customerId, Long productId) {

    }

    @Override
    public void clearCart(Long customerId) {

    }
}
