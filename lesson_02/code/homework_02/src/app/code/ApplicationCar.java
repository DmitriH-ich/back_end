package app.code;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import app.carRide.driverSteps.DriverAction;



public class ApplicationCar {

    public static void main(String[] args) {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext("app.config");
        DriverAction driverAction= context.getBean(DriverAction.class);
        driverAction.performDriverAction();
    }
}
