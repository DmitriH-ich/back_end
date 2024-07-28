package homework_02.src.app.carRide.driverSteps;

import homework_02.src.app.carRide.Car.MoveOfCar.Gear;
import homework_02.src.app.carRide.Car.safetyCheck.Gearbox;
import homework_02.src.app.carRide.Car.safetyCheck.SeatBelt;

public class Step1 {

    private SeatBelt seatBelt;

    private Gearbox gearbox;

    public void setSeatBelt(SeatBelt seatBelt) {
        this.seatBelt = seatBelt;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    public void performSafetyCheck() {
        seatBelt.perform();
        gearbox.perform();
    }
}
