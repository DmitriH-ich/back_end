package app.carRide.driverSteps;


import app.carRide.Car.startOfMotor.CarIgnition;
import app.carRide.Car.startOfMotor.MotorStart;
import org.springframework.beans.factory.annotation.Autowired;

public class Step2 {
    @Autowired
    private CarIgnition carIgnition;
    @Autowired
    private MotorStart motorStart;

    public void setCarIgnition(CarIgnition carIgnition) {
        this.carIgnition = carIgnition;
    }

    public void setMotorStart(MotorStart motorStart) {
        this.motorStart = motorStart;
    }
    public void performStart() {
        carIgnition.perform();
        motorStart.perform();
    }
}
