package homework_02.src.app.carRide.driverSteps;

import homework_02.src.app.carRide.Car.MoveOfCar.Drive;
import homework_02.src.app.carRide.Car.MoveOfCar.Gear;

public class Step3 {

    private Gear gear;

    private Drive drive;

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public void performCarMovement() {
        gear.perform();
        drive.perform();
    }
}
