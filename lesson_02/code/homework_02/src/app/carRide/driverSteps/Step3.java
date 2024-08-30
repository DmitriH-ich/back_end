package app.carRide.driverSteps;


import app.carRide.Car.MoveOfCar.Drive;
import app.carRide.Car.MoveOfCar.Gear;
import org.springframework.beans.factory.annotation.Autowired;

public class Step3 {
    @Autowired
    private Gear gear;
    @Autowired
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
