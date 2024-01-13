package org.firstinspires.ftc.teamcode;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;


public class Vision2 extends LinearOpMode {



    WebcamName camera = hardwareMap.get(WebcamName.class, "Webcam 1");

    TfodProcessor tfodProcessor = TfodProcessor.easyCreateWithDefaults();

    VisionPortal visionPortal = VisionPortal.easyCreateWithDefaults(camera,tfodProcessor);
    public void runOpMode() throws InterruptedException {

        WebcamName camera = hardwareMap.get(WebcamName.class, "Webcam 1");

        TfodProcessor tfodProcessor = TfodProcessor.easyCreateWithDefaults();

        VisionPortal visionPortal = VisionPortal.easyCreateWithDefaults(camera,tfodProcessor);

        List < Recognition> recognitions = tfodProcessor.getRecognitions();

        for (Recognition recognition: recognitions)

        {

            String label = recognition.getLabel();

            float confidence = recognition.getConfidence();

            telemetry.addLine("Recognized Object!: " + label);
            telemetry.addLine("Confidence:" + confidence);
        }

    }
}
