package org.firstinspires.ftc.teamcode;
import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Config
@Autonomous(name = "gangAutoRFar", group = "Autonomous")
public class auto1 extends LinearOpMode {

    HardwarePushbot robot = new HardwarePushbot();


    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_20240111_202140.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "BlueBlock"
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    private ElapsedTime runtime = new ElapsedTime();



    @Override
    public void runOpMode() throws InterruptedException {

        initTfod();

//        robot.init(hardwareMap);

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(-35, -62, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        // mid
        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    drive.closeClaw();
                })
//                .addDisplacementMarker(() -> {
//                    drive.liftArm();
//                    sleep(1000);
//                    drive.liftArmEnd();
//                })
                .forward(23.5)
                .addDisplacementMarker(() -> {
                    drive.setMotorPowers(0, 0, 0, 0);
                    drive.openClawR();
                    sleep(400);
                })
                .forward(55)
                .turn(Math.toRadians(-81))
                .forward(52.5)
                .strafeRight(30.5)
                .addDisplacementMarker(() -> {
                    drive.setMotorPowers(0, 0, 0, 0);
                    drive.liftArm();
                    sleep(800);
                    drive.liftArmEnd();
                    drive.setMotorPowers(0.25, 0.25, 0.25, 0.25);
                    sleep(2550);
                    drive.setMotorPowers(0, 0, 0, 0);
                    drive.openClawL();
                    sleep(300);
                    drive.closeClaw();
                    drive.lowerArm();
                    sleep(100);
                    drive.lowerArmFinal();
                })
                .build();

        // left
        TrajectorySequence trajSeqZero = drive.trajectorySequenceBuilder(startPose)
                .forward(45)
                .turn(Math.toRadians(-90))
                .build();

        // right
        TrajectorySequence trajSeqTwo = drive.trajectorySequenceBuilder(startPose)
//                .forward(45)
                .turn(Math.toRadians(5))
                .addDisplacementMarker(() -> {
                    drive.openClawR();
                    sleep(1000);
                    drive.closeClaw();
                })
                .build();

//        robot.init(hardwareMap);

        waitForStart();

        runtime.reset();

        if (opModeIsActive()) {

            while (opModeIsActive()) {

                // idk if it reinitializes in the loop so this
                // is a precaution

                // object in middle
                if(telemetryTfod() == 1) {
                    drive.followTrajectorySequence(trajSeq);
                    sleep(35000);
                }
                // object in left
                else if (telemetryTfod() == 0) {
                    drive.followTrajectorySequence(trajSeq);
                    sleep(35000);

                } else if ((runtime.seconds() > 5) && telemetryTfod()==3) {
                    drive.followTrajectorySequence(trajSeq);
                    sleep(35000);
                }
            }
            // Push telemetry to the Driver Station.
            telemetry.update();


            // Save CPU resources; can resume streaming when needed.
//                if (gamepad1.dpad_down) {
//                    visionPortal.stopStreaming();
//                } else if (gamepad1.dpad_up) {
//                    visionPortal.resumeStreaming();
        }

        // Share the CPU.
//                sleep(20);


        if (!isStopRequested()){
//            int camPos = telemetryTfod();
//            telemetry.addData("bvlure", camPos);
//            telemetry.update();
//            sleep(500);
            drive.followTrajectorySequence(trajSeq);
        }
    }

    public void liftArm() {
        robot.armRight.setPower(0.5);
        robot.armLeft.setPower(0.5);
        robot.ElbowL.setPosition(0.1);
        robot.ElbowR.setPosition(0.1);
        robot.Wrist.setPosition(0.1);
        sleep(1000);
        robot.armRight.setPower(0.0);
        robot.armLeft.setPower(0.0);
    }

    public void lowerArm() {
        robot.armRight.setPower(-0.5);
        robot.armLeft.setPower(-0.5);
        robot.ElbowL.setPosition(0.9);
        robot.ElbowR.setPosition(0.9);
        robot.Wrist.setPosition(0.5);
        sleep(500);
        robot.armRight.setPower(0.0);
        robot.armLeft.setPower(0.0);
    }

//    private void initTfod() {
//
//        // Create the TensorFlow processor by using a builder.
//        tfod = new TfodProcessor.Builder()
//
//                .setModelAssetName("model_20240111_202140.tflite")
//                .setModelLabels(LABELS)
//
//                // With the following lines commented out, the default TfodProcessor Builder
//                // will load the default model for the season. To define a custom model to load,
//                // choose one of the following:
//                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
//                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
//                //.setModelAssetName(TFOD_MODEL_ASSET)
//                //.setModelFileName(TFOD_MODEL_FILE)
//
//                // The following default settings are available to un-comment and edit as needed to
//                // set parameters for custom models.
//                //.setModelLabels(LABELS)
//                //.setIsModelTensorFlow2(true)
//                //.setIsModelQuantized(true)
//                //.setModelInputSize(300)
//                //.setModelAspectRatio(16.0 / 9.0)
//
//                .build();
//
//        // Create the vision portal by using a builder.
//        VisionPortal.Builder builder = new VisionPortal.Builder();
//
//        // Set the camera (webcam vs. built-in RC phone camera).
//
//        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
//
//        builder.setCameraResolution(new Size(640, 480));
//
//        builder.enableLiveView(true);
//
//        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
//
//        builder.setAutoStopLiveView(false);
//
//
//        // Choose a camera resolution. Not all cameras support all resolutions.
//        //builder.setCameraResolution(new Size(640, 480));
//
//        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
//        //builder.enableLiveView(true);
//
//        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
//        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
//
//        // Choose whether or not LiveView stops if no processors are enabled.
//        // If set "true", monitor shows solid orange screen if no processors enabled.
//        // If set "false", monitor shows camera view without annotations.
//        //builder.setAutoStopLiveView(false);
//
//        // Set and enable the processor.
//        builder.addProcessor(tfod);
//
//        // Build the Vision Portal, using the above settings.
//        visionPortal = builder.build();
//
//        tfod.setMinResultConfidence(0.7f);
//        // Set confidence threshold for TFOD recognitions, at any time.
//        //tfod.setMinResultConfidence(0.75f);
//
//        // Disable or re-enable the TFOD processor at any time.
//        //visionPortal.setProcessorEnabled(tfod, true);
//
//    }   // end method initTfod()
//
//    /**
//     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
//     */
//    private int telemetryTfod() {
//
//
//        List<Recognition> currentRecognitions = tfod.getRecognitions();
//        telemetry.addData("# Objects Detected", currentRecognitions.size());
//
//        int numPos = 3;
//
//        // Step through the list of recognitions and display info for each one.
//        for (Recognition recognition : currentRecognitions) {
//            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
//            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
//            telemetry.addData(""," ");
//            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
//            telemetry.addData("- Position", "%.0f / %.0f", x, y);
//            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
//
//            if (x < 250) {
//                telemetry.addData("Custom Object at Left", "");
//                numPos = 0;
//            }else if(x > 250) {
//                telemetry.addData("Custom Object at Middle", "");
//                numPos = 1;
//            } else {
//                telemetry.addData("Custom Object at Right", "");
//                numPos = 2;
//            }
//
//            telemetry.update();
//
//        }   // end for() loop
//
//        return (numPos);
//
//    }   // end method telemetryTfod()


    private void initTfod() {

        // Create the TensorFlow processor the easy way.
        tfod = TfodProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), tfod);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private int telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        int numPos = 3;

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());

            if (x < 250) {
                telemetry.addData("Custom Object at Left", "");
                numPos = 0;
            }else if (x > 250) {
                telemetry.addData("Custom Object at Middle", "");
                numPos = 1;
            } else  {
                telemetry.addData("Custom Object at Left", "");
                numPos = 2;
            }

            telemetry.update();
        }   // end for() loop

        return (numPos);

    }   // end method telemetryTfod()

    public void openClaw() {
        robot.seatBeltR.setPosition(0.6);
        robot.seatBeltL.setPosition(0.6);
    }

    public void closeClaw() {
        robot.seatBeltL.setPosition(0.3);
        robot.seatBeltR.setPosition(0.3);
    }

}

