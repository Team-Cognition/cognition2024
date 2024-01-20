package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwarePushbot
{
    /* Public OpMode members. */
    public DcMotor  upperLeft   = null;
    public DcMotor  upperRight = null;
    public DcMotor  lowerLeft = null;
    public DcMotor  lowerRight = null;
    public DcMotor  armRight = null;
    public DcMotor armLeft = null;
    public DcMotor intakeMotor = null;
    public Servo  launcher = null;
    public Servo intakeServo = null;
    public Servo seatBeltL = null;
    public Servo seatBeltR = null;
    public Servo Elbow = null;
    public Servo Wrist = null;
    public Servo intakeArm = null;

    public final static double CLAW_HOME = 0.0; //Starting position
    public final static double CLAWL_MIN_RANGE = 0.0; //Minimum value allowed
    public final static double CLAWL_MAX_RANGE = 0.5; //Maximum Range: It might break past this point
    public final static double CLAWR_MIN_RANGE = 0.0;
    public final static double CLAWR_MAX_RANGE = -0.5; //this is negative because the right one has to go negative I think
    //    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
//        upperLeft  = hwMap.get(DcMotor.class, "upperLeft");
//        upperRight  = hwMap.get(DcMotor.class, "upperRight");
//        lowerLeft  = hwMap.get(DcMotor.class, "lowerLeft");
//        lowerRight  = hwMap.get(DcMotor.class, "lowerRight");
        upperLeft = hwMap.dcMotor.get("upperLeft"); //motorFrontLeft
        upperRight = hwMap.dcMotor.get("upperRight"); //motorBackLeft
        lowerLeft = hwMap.dcMotor.get("lowerLeft"); //motorFrontRight
        lowerRight = hwMap.dcMotor.get("lowerRight"); //motorBackRight
        armRight = hwMap.dcMotor.get("armRight");
        armLeft = hwMap.dcMotor.get("armLeft");
        launcher = hwMap.servo.get("launcher");
        intakeMotor = hwMap.dcMotor.get("intakeMotor");
        intakeServo = hwMap.servo.get("intakeServo");
        seatBeltL = hwMap.servo.get("seatBeltL");
        seatBeltR = hwMap.servo.get("seatBeltR");
        Elbow = hwMap.servo.get("Elbow");
        Wrist = hwMap.servo.get("Wrist");
        intakeArm = hwMap.servo.get("intakeArm");

//        intakeServoL = hwMap.servo.get("intakeServoL");
//        intakeServoR = hwMap.servo.get("intakeServoR");

        // clawServoLeft = hwMap.servo.get("clawServoLeft");
      //  clawServoRight = hwMap.servo.get("clawServoRight");


    //    clawServoLeft.setPosition(CLAW_HOME);
      //  clawServoRight.setPosition(CLAW_HOME); //both of these ensure that at the point that the robot is turned on, the claw is at 0

        // Reversing left motors
//        upperLeft.setDirection(DcMotor.Direction.FORWARD );
//        upperRight.setDirection(DcMotor.Direction.REVERSE);
//        lowerLeft.setDirection(DcMotor.Direction.REVERSE);
//        lowerRight.setDirection(DcMotor.Direction.FORWARD);

        upperLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        upperRight.setDirection(DcMotorSimple.Direction.REVERSE);
        lowerLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        lowerRight.setDirection(DcMotorSimple.Direction.REVERSE);
        armRight.setDirection(DcMotorSimple.Direction.FORWARD);
        armLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    //    arm.setDirection(DcMotor.Direction.FORWARD );

        // Set all motors to zero power'
        setMotorPowers(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
//        upperLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        upperRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        lowerLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        lowerRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      //  arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Sets all motor powers to given speeds.
     * @param LFPower
     * @param RFPower
     * @param LBPower
     * @param RBPower
     */
    public void setMotorPowers(double LFPower, double RFPower, double LBPower, double RBPower, double APower) {
        upperLeft.setPower(LFPower);
        upperRight.setPower(RFPower);
        lowerLeft.setPower(LBPower);
        lowerRight.setPower(RBPower);
      //  arm.setPower(APower);

    }

    public void setMotorPowers(double allPower) {
        setMotorPowers(allPower, allPower, allPower, allPower, allPower);
    }
}

