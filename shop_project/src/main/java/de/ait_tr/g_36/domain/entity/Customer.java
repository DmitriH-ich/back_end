package de.ait_tr.g_36.domain.entity;

import java.util.Objects;

public class Customer {
    private Long id;
    private String name;
    private Cart cart;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return id == customer.id && isActive == customer.isActive && Objects.equals(name, customer.name) && Objects.equals(cart, customer.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cart, isActive);
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, cart - %s, active - %s",
                id, name, cart, isActive ? "yes" : "no");
    }
}


