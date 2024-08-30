package app.carRide.driverSteps;

import app.carRide.Car.safetyCheck.Gearbox;
import app.carRide.Car.safetyCheck.SeatBelt;
import org.springframework.beans.factory.annotation.Autowired;

public class Step1 {
    @Autowired
    private SeatBelt seatBelt;
    @Autowired
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
