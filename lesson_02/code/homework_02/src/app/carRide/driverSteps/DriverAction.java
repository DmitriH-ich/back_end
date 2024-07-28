package homework_02.src.app.carRide.driverSteps;

import homework_02.src.app.carRide.Car.Driver;

public class DriverAction {

    private Driver driver;

    private Step1 step1;

    private Step2 step2;

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
