package app.repository;

import app.domain.Car;

import java.util.List;

// CRUD - create, read, update, delete

public interface CarRepository {

    Car save(Car car); // create

    Car getById(Long id); // read

    List<Car> getAll(); // read

    Car update(Car car); // update

    void delete(Long id);  // delete

}
