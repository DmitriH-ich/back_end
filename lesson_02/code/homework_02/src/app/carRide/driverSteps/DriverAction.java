package app.carRide.driverSteps;


import app.carRide.Car.Driver;
import org.springframework.beans.factory.annotation.Autowired;

public class DriverAction {

    @Autowired
    private Driver driver;
    @Autowired
    private Step1 step1;
    @Autowired
    private Step2 step2;
    @Autowired
    private Step3 step3;


    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setStep1(Step1 step1) {
        this.step1 = step1;
    }

    public void setStep2(Step2 step2) {
        this.step2 = step2;
    }

    public void setStep3(Step3 step3) {
        this.step3 = step3;
    }

    public void performDriverAction() {
        driver.perform();
        step1.performSafetyCheck();
        step2.performStart();
        step3.performCarMovement();
    }
}
