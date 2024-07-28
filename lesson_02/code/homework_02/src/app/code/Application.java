package homework_02.src.app.code;

import homework_02.src.app.carRide.Car.Driver;
import homework_02.src.app.carRide.Car.MoveOfCar.Drive;
import homework_02.src.app.carRide.Car.MoveOfCar.Gear;
import homework_02.src.app.carRide.Car.safetyCheck.Gearbox;
import homework_02.src.app.carRide.Car.safetyCheck.SeatBelt;
import homework_02.src.app.carRide.Car.startOfMotor.CarIgnition;
import homework_02.src.app.carRide.Car.startOfMotor.MotorStart;
import homework_02.src.app.carRide.driverSteps.DriverAction;
import homework_02.src.app.carRide.driverSteps.Step1;
import homework_02.src.app.carRide.driverSteps.Step2;
import homework_02.src.app.carRide.driverSteps.Step3;

public class Application {

    public static void main(String[] args) {
        Driver driver = new Driver();

        Drive drive = new Drive();
        Gear gear = new Gear();

        MotorStart motorStart = new MotorStart();
        CarIgnition carIgnition = new CarIgnition();

        Gearbox gearbox = new Gearbox();
        SeatBelt seatBelt = new SeatBelt();

        Step1 step1 = new Step1();
        step1.setSeatBelt(seatBelt);
        step1.setGearbox(gearbox);

        Step2 step2 = new Step2();
        step2.setCarIgnition(carIgnition);
        step2.setMotorStart(motorStart);

        Step3 step3 = new Step3();
        step3.setGear(gear);
        step3.setDrive(drive);

        DriverAction driverAction = new DriverAction();
        driverAction.setDriver(driver);
        driverAction.setStep1(step1);
        driverAction.setStep2(step2);
        driverAction.setStep3(step3);
        driverAction.performDriverAction();


    }
}
