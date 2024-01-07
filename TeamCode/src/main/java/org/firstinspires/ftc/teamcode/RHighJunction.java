//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//import com.qualcomm.hardware.bosch.BNO055IMU;
//
//
//
//
//@Autonomous(name="RHighJunction", group="Autonomous")
//public class RHighJunction extends LinearOpMode {
//
//
//    HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware
//    public BNO055IMU imu;
//    private ElapsedTime runtime = new ElapsedTime();
//    double gripPosition = 0.5;
//
//    static final double TURN_SPEED = 0.4;
//    static final double armSpeed  =  2;
//    public final static double CLAW_HOME = 0.0; //Starting position
//    public final static double CLAW_MIN_RANGE = 0.0; //Minimum value allowed
//    public final static double CLAW_MAX_RANGE = 0.4; //Maximum Range: It might break past this point
//    double SERVOposition =CLAW_HOME;
//
//    @Override
//    public void runOpMode() {
//
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        telemetry.addData("kmjnibgyftvtfr", "IMU is found to be gay");
//        telemetry.update();
//        if (imu != null) {
//            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
//            imu.initialize(parameters);
//            telemetry.addData("ninjago", "IMU is found in hardwareMap");
//            telemetry.update();
//        } else {
//            telemetry.addData("Error", "IMU not found in hardwareMap");
//            telemetry.update();
//            sleep(1000);
//            stop(); // Stop the program
//        }
//
//        if (imu.isGyroCalibrated()) {
//            telemetry.addData("IMU", "Calibrated");
//        } else {
//            telemetry.addData("IMU", "Not Calibrated");
//        }
//        telemetry.update();
//
//        /*
//         * Initialize the drive system variables.
//         * The init() method of the hardware class does all the work here
//         */
//        robot.init(hardwareMap);
//
//
//        // Send telemetry message to signify robot waiting;
//        telemetry.addData("Status", "Ready to run");    //
//        telemetry.update();
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//        TurnRight(90);
////        closeClaw();
////        StrafeLeft(1400);
////        MoveForward(2200);
////        StrafeRight(800);
////        sleep(500);
////        liftArm(4200);
////        sleep(250);
////        MoveForward(100);
////        sleep(300);
////        openClaw();
////        MoveBackward(100);
////        lowerArm(2750);
////        StrafeRight(900);
////        TurnRight(1300);
////        sleep(300);
////        MoveForward(700);
////        sleep(300);
////        closeClaw();
////        sleep(300);
////        liftArm(2000);
////        sleep(300);
////        MoveBackward(1500);
//    }
//    //Motor 1: Reverse
//    //Motor 2: Normal
//    //Motor 3: Normal
//    //Motor 4: Reverse
//    public void MoveForward(long timeoutA) {
//        if (opModeIsActive()) {
//
//            robot.setMotorPowers(-TURN_SPEED, TURN_SPEED, 1.15*TURN_SPEED, -TURN_SPEED, 0);
//            runtime.reset();
//            sleep(timeoutA);
//            robot.setMotorPowers(0);
//        }
//    }
//
//
//    public void StrafeLeft(long timeoutC) {
//
//        if (opModeIsActive()) {
//
//            robot.setMotorPowers(TURN_SPEED, TURN_SPEED, 1.15*TURN_SPEED, TURN_SPEED, 0); //changed ratio just for this because for some reason it was going crazy
//            runtime.reset();
//            sleep(timeoutC);
//            robot.setMotorPowers(0);
//
//        }
//
//    }
//
//
//    public void MoveBackward(long timeoutB) {
//
//        if (opModeIsActive()) {
//
//            robot.setMotorPowers(TURN_SPEED, -TURN_SPEED, -1.2*TURN_SPEED, TURN_SPEED, 0);
//            runtime.reset();
//            sleep(timeoutB);
//            robot.setMotorPowers(0);
//
//
//        }
//
//
//
//    }
//
//    public void StrafeRight(long timeoutD) {
//
//        if (opModeIsActive()) {
//
//            robot.setMotorPowers(-TURN_SPEED, -TURN_SPEED, -1.15 * TURN_SPEED, -TURN_SPEED, 0);
//            runtime.reset();
//            sleep(timeoutD);
//            robot.setMotorPowers(0);
//        }}
//    public void TurnRight(double targetAngle) {
//        if (opModeIsActive()) {
//            double initialAngle = imu.getAngularOrientation().firstAngle;
//            double error;
//            double kp = 0.02; // Proportional constant (adjust as needed)
//            double maxPower = 0.5; // Maximum motor power
//            double minPower = 0.15; // Minimum motor power
//
//            while (opModeIsActive()) {
//                double currentAngle = imu.getAngularOrientation().firstAngle;
//                error = targetAngle - (currentAngle - initialAngle);
//
//                // Calculate the motor power based on the error and PID constants
//                double power = Range.clip(kp * error, -maxPower, maxPower);
//
//                // Apply power to your motors (Motor 1 and Motor 2 should turn in the opposite direction)
//                robot.setMotorPowers(power, power, -power, -power, 0);
//
//                if (Math.abs(error) < 1) { // Stop when close to the target angle
//                    robot.setMotorPowers(0);
//                    break;
//                }
//            }
//        }
//    }
//
//    public void TurnLeft(double targetAngle) {
//        if (opModeIsActive()) {
//            // Initialize the gyro sensor
//            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//
//            imu = hardwareMap.get(BNO055IMU.class, "imu");
//
//            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
//            imu.initialize(parameters);
//
//            imu.initialize(parameters);
//
//            double initialAngle = imu.getAngularOrientation().firstAngle;
//            double error;
//            double kp = 0.02; // Proportional constant (adjust as needed)
//            double maxPower = 0.5; // Maximum motor power
//            double minPower = 0.15; // Minimum motor power
//
//            while (opModeIsActive()) {
//                double currentAngle = imu.getAngularOrientation().firstAngle;
//                error = targetAngle - (initialAngle - currentAngle);
//
//                // Calculate the motor power based on the error and PID constants
//                double power = Range.clip(kp * error, -maxPower, maxPower);
//
//                // Apply power to your motors (Motor 1 and Motor 2 should turn in the opposite direction)
//                robot.setMotorPowers(-power, -power, power, power, 0);
//
//                if (Math.abs(error) < 1) { // Stop when close to the target angle
//                    robot.setMotorPowers(0);
//                    break;
//                }
//            }
//        }
//    }
//
//
//    public void liftArm(long timeoutG) {
//        if (opModeIsActive()) {
//            robot.arm.setPower(armSpeed);
//            sleep(timeoutG); // change
//            robot.arm.setPower(0);
//        }
//    }
//    public void lowerArm(long timeoutH) {
//        if (opModeIsActive()) {
//            robot.arm.setPower(-armSpeed);
//            sleep(timeoutH); // change
//            robot.arm.setPower(0);
//        }
//    }
//
//    public void openClaw() {
//        if (opModeIsActive()) {
//            gripPosition=gripPosition+0.2;
//            SERVOposition = Range.clip(gripPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);
//            robot.claw.setPosition(SERVOposition);
//            sleep(500);
//        }
//    }
//
//    public void closeClaw() {
//        if (opModeIsActive()) {
//            gripPosition=gripPosition-0.2;
//            SERVOposition = Range.clip(gripPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);
//            robot.claw.setPosition(SERVOposition);
//            sleep(500);
//        }
//    }
//
//}
//
//
//
//
//
//
