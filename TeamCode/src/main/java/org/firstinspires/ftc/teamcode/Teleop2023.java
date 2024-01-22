package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Test: By Curious Monkey", group ="Test")
public class Teleop2023 extends LinearOpMode{
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    Servo launcher;
    DcMotor armRight;
    DcMotor armLeft;
    Servo intakeServo;
    Servo seatBeltL;
    Servo seatBeltR;
    Servo ElbowL;
    Servo ElbowR;
    Servo Wrist;
    double armPosition, gripPosition;
    double MIN_POSITION = 0, MAX_POSITION = 0.5;

    // Declare variables
    boolean secondHalf = false;                 // Use to hint the drivers for end game start
    final double HALF_TIME = 60.0;              // Wait this many seconds before alert for half-time
    ElapsedTime runtime = new ElapsedTime();    // Use to determine when end game is starting.

    static final double MOTOR_TICK_COUNT = 1120;
    static final double MAX_POS = 1.0;     // Maximums rotational position for gripper
    static final double MIN_POS = 0.0;     // Minimum rotational position for gripper
    double position = MIN_POS; // Start at minimum position position  for gripper
    static final double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    double SERVOposition =CLAW_HOME;
    final double SERVOspeed = 0.01;
    public final static double CLAW_HOME = 0.0; //Starting position
    public final static double CLAW_MIN_RANGE = 0.0; //Minimum value allowed
    public final static double CLAW_MAX_RANGE = 0.4; //Maximum Range: It might break past this point
    public final double armpower = 0.5;


    public final double buttonpower = 1;
    public final double armpower2 = 0.75;
    public  int timer = 0;
    boolean apressed = false;
    boolean bpressed = false;
    double mainPower = 0.7; // maintain ratio, change this to change speed of robot
    boolean fastMode = true;

    public void runOpMode() throws InterruptedException {

        motorFrontLeft = hardwareMap.dcMotor.get("upperLeft"); //motorFrontLeft
        motorBackLeft = hardwareMap.dcMotor.get("lowerLeft"); //motorBackLeft
        motorFrontRight = hardwareMap.dcMotor.get("upperRight"); //motorFrontRight
        motorBackRight = hardwareMap.dcMotor.get("lowerRight"); //motorBackRight
        launcher = hardwareMap.servo.get("launcher");
        armRight = hardwareMap.dcMotor.get("armRight"); //Calling the arm
        armLeft = hardwareMap.dcMotor.get("armLeft");
        intakeServo = hardwareMap.servo.get("intakeServo");
        seatBeltL = hardwareMap.servo.get("seatBeltL");
        seatBeltR = hardwareMap.servo.get("seatBeltR");
        ElbowL = hardwareMap.servo.get("ElbowL");
        ElbowR = hardwareMap.servo.get("ElbowR");
        Wrist = hardwareMap.servo.get("Wrist");

        //Reverse front motors and back left motors
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//      motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        armLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("TeleOp>", "Press Start");
        telemetry.update();
        waitForStart();
        runtime.reset();    // Start game timer.

        telemetry.addData("TeleOp>", "Stage 1");
        telemetry.update();

        if (isStopRequested()) return;

        armPosition = .5;                   // set arm to half way up.
        gripPosition = MAX_POSITION;        // set grip to full open.

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            if ((runtime.seconds() > HALF_TIME) && !secondHalf) {
                secondHalf = true;
            }

            if (!secondHalf) {
                telemetry.addData(">", "Halftime Alert Countdown: %3.0f Sec \n", (HALF_TIME - runtime.seconds()));
            }

            if (gamepad2.left_stick_y>0) {
                armRight.setPower(armpower*gamepad2.left_stick_y);
                armLeft.setPower(armpower*gamepad2.left_stick_y);
            } else if (gamepad2.left_stick_y<0) {
                armRight.setPower(armpower2*gamepad2.left_stick_y);
                armLeft.setPower(armpower2*gamepad2.left_stick_y);
            }
            else if (gamepad2.left_stick_y == 0) {
                armRight.setPower(0);
                armLeft.setPower(0);
            }




            if(gamepad1.right_bumper && fastMode){

                mainPower= mainPower - 0.3;
                fastMode = false;

            }
            else if(gamepad1.right_bumper && !fastMode){

                mainPower= mainPower +0.3;
                fastMode = true;

            }



            if (gamepad2.left_trigger > 0) {

                telemetry.addData("POSITION", intakeServo.getPosition());
                telemetry.addData("adirection", intakeServo.getDirection());
                telemetry.update();
                intakeServo.setDirection(Servo.Direction.REVERSE);
                intakeServo.setPosition(1.0);

            } if (gamepad2.right_trigger > 0) {
                telemetry.addData("POSITION", intakeServo.getPosition());
                telemetry.update();
                intakeServo.setDirection(Servo.Direction.FORWARD);
                intakeServo.setPosition(1.0);

            } if (gamepad2.right_trigger == 0 && gamepad2.left_trigger == 0) {
                telemetry.addData("POSITION", intakeServo.getPosition());
                telemetry.update();
                intakeServo.setDirection(Servo.Direction.FORWARD);
                intakeServo.setPosition(0.5);
            }




         if (gamepad1.left_bumper) {

             if (gripPosition < MAX_POSITION) {
                 gripPosition=gripPosition+0.7;
                 telemetry.addData("bumperLeft", "hello");
             } else if (gripPosition > MIN_POSITION) {
                 gripPosition=gripPosition-0.7;
                 telemetry.addData("bumperRight", "hi");

             }
             telemetry.addData("gripPosition", gripPosition);
             telemetry.update();
             SERVOposition = Range.clip(gripPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);
             telemetry.addData("targetPosition", SERVOposition);
             telemetry.update();
             launcher.setPosition(SERVOposition);
            }

            if(gamepad2.dpad_down) {
                   ElbowR.setPosition(0.8);
                   ElbowL.setPosition(0.2);
                Wrist.setPosition(0.5);
                }
            if(gamepad2.dpad_up) {
                ElbowR.setPosition(0.0);
                ElbowL.setPosition(1.0);
                Wrist.setPosition(0.9);
            }

            if (gamepad2.dpad_right){
                Wrist.setPosition(0.5);
            }

            if (gamepad2.dpad_left){
                Wrist.setPosition(0.9);
            }



            if (gamepad2.left_bumper){
                // opens

                seatBeltR.setPosition(0.65);
                seatBeltL.setPosition(0.3);


            }
            if (gamepad2.right_bumper){
                // closes
                seatBeltL.setPosition(0.45);
                seatBeltR.setPosition(0.45);


            }
            if (gamepad2.y){
//                Elbow.setPosition(0.5);
            }

            if (gamepad2.x){
                armRight.setPower(-0.5);
                armLeft.setPower(-0.5);
//                Elbow.setPosition(0.7);
                Wrist.setPosition(0.5);
                sleep(500);
                armRight.setPower(0.0);
                armLeft.setPower(0.0);
            }



            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y - x*1.2 + rx) / denominator;
            double backLeftPower = (y + x*1.2 + rx) / denominator;
            double frontRightPower = (y + x*1.2 - rx) / denominator;
            double backRightPower = (y - x*1.2 - rx) / denominator;
            //Slower speed so that is easier to control
            motorFrontLeft.setPower(frontLeftPower * mainPower);
            motorBackLeft.setPower(backLeftPower * mainPower );
            motorFrontRight.setPower(frontRightPower * mainPower);
            motorBackRight.setPower(backRightPower * mainPower);



            if(gamepad2.a&& !apressed){

                apressed=true;

            }

//            if(apressed==true){
//                timer=timer+1;
//         //   arm.setPower(buttonpower);
//                telemetry.addData("timer>", timer);
//                telemetry.update();
//            }
//
//
//            if(timer>1000){
//
//                arm.setPower(0);
//                apressed = false;
//                timer=0;
//
//
//            }
//
//            if(gamepad2.b&& bpressed==false){
//
//                bpressed=true;
//
//            }
//
//            if(bpressed==true){
//                timer=timer+1;
//                arm.setPower(buttonpower);
//                telemetry.addData("timer>", timer);
//                telemetry.update();
//            }
//
//
//            if(timer>1500){
//
//                arm.setPower(0);
//                bpressed = false;
//                timer=0;
//
//
//            }



            telemetry.update();



        telemetry.addData("Game>", "Over");

        telemetry.update();


        }


    }

}