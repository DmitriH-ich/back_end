package app.repository;

import app.domain.Car;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static app.constans.Constans.*;

public class CarRepositoryDB implements CarRepository {


    private Connection getConnection(){
        try {
            Class.forName(DB_DRIVER_PATH);

            // http://10.1.2.3:8080/carss?id=3
            // это URI для http-запроса, URI для подключения к БД выглядит аналогично:
            // jdbc:postgresql://localhost:5432/cars_db?user=postgres&password=ppp77777
            String dbUrl = String.format("%s%s?user=%s&password=%s", DB_ADDRESS, DB_NAME, DB_USERNAME, DB_PASSWORD);
            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    public Car save(Car car) {
        try(Connection connection = getConnection()){
            String query = String.format("INSERT INTO cars(brand, price, year) VALUES ('%s', %s, %d);", car.getBrand(), car.getPrice(), car.getYear());
            Statement statment = connection.createStatement();
            statment.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statment.getGeneratedKeys();
            resultSet.next();
            //det ID from resultSet
            Long id = resultSet.getLong(1);
            car.setId(id);
            return car;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Car getById(Long id) {
        try (Connection connection = getConnection()) {

            String query = String.format("SELECT * FROM car WHERE id = %d;", id);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String brand = resultSet.getString("brand");
                BigDecimal price = resultSet.getBigDecimal("price");
                int year = resultSet.getInt("year");

                return new Car(id, price, brand,  year);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAll() {

        try (Connection connection = getConnection()) {

            // TODO домашнее задание
            // Подсказка: нужно реализовать цикл, который работает до тех пор,
            // пока не закончатся данные в resultSet, то есть до тех пор, пока
            // его метод next() не вернёт false.
            // До этого момента работаем в цикле и все автомобили складываем
            // в результирующий лист

            List<Car> cars = new ArrayList<>();

            String query = "SELECT * FROM cars;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String brand = resultSet.getString("brand");
                BigDecimal price = resultSet.getBigDecimal("price");
                int year = resultSet.getInt("year");
                cars.add(new Car(id, price, brand, year));
            }
            return cars;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //return null;
    }

    @Override
    public Car update(Car car) {
        try(Connection connection = getConnection()){
            // TODO домашнее задание (изменению подлежит только цена)
        } catch (Exception e){
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try(Connection connection = getConnection()){

        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
