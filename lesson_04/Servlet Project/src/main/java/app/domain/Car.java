package app.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private Long id;
    private BigDecimal price;
    private String brand;
    private int year;

    // full constructor
    public Car(Long id, BigDecimal price, String brand, int year) {
        this.id = id;
        this.price = price;
        this.brand = brand;
        this.year = year;
    }
    // only 3 fields constructor, no id
    public Car(String brand, BigDecimal price,  int year) {
        this.price = price;
        this.brand = brand;
        this.year = year;
    }
    // empty constructor
    public Car(){
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getBrand() {
        return brand;
    }
    public int getYear() {
        return year;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return year == car.year && Objects.equals(id, car.id) && Objects.equals(brand, car.brand) && Objects.equals(price, car.price);
    }
    @Override
        public int hashCode() {
            return Objects.hash(id, brand, price, year);
        }
    public String toString() {
        return String.format("Car: id - %d, brand - %s, price - %s, year - %d",
                id, brand, price, year);
    }
}
