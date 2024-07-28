package app.config;
import app.carRide.Car.Driver;
import app.carRide.Car.MoveOfCar.Drive;
import app.carRide.Car.MoveOfCar.Gear;
import app.carRide.Car.safetyCheck.Gearbox;
import app.carRide.Car.safetyCheck.SeatBelt;
import app.carRide.Car.startOfMotor.CarIgnition;
import app.carRide.Car.startOfMotor.MotorStart;
import app.carRide.driverSteps.Step3;
import app.carRide.driverSteps.Step2;
import app.carRide.driverSteps.Step1;
import app.carRide.driverSteps.DriverAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
    public class AppConfigCar {

        @Bean
        public DriverAction driverAction() {
            return new DriverAction();
        }

        @Bean
        public Step1 step1() {
            return new Step1();
        }
        @Bean
        public Step2 step2() {
            return new Step2();
        }
        @Bean
        public Step3 step3() {
            return new Step3();
        }
        @Bean
        public Driver driver() {
            return new Driver();
        }
        @Bean
        public SeatBelt seatBelt() {
            return new SeatBelt();
        }
        @Bean
        public Gearbox gearbox() {
            return new Gearbox();
        }
        @Bean
        public CarIgnition carIgnition() {
            return new CarIgnition();
        }
        @Bean
        public MotorStart motorStart() {
            return new MotorStart();
        }
        @Bean
        public Gear gear() {
            return new Gear();
        }
        @Bean
        public Drive drive() {
            return new Drive();
        }
    }

