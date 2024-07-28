package homework_02.src.app.carRide.driverSteps;

import homework_02.src.app.carRide.Car.startOfMotor.CarIgnition;
import homework_02.src.app.carRide.Car.startOfMotor.MotorStart;

public class Step2 {
    private CarIgnition carIgnition;

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
