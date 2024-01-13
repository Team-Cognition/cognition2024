package org.firstinspires.ftc.teamcode;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class objectdetectionpipeline extends OpenCvPipeline {

    private String output = "nothing";

    public objectdetectionpipeline() {

    }


    @Override
    public Mat processFrame(Mat input) {
        output = "Sample Pipeline is Running!";
        return input;
    }

    public String getOutput() {
        return output;
    }
}
