package org.firstinspires.ftc.teamcode;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class samplepipeline2 extends OpenCvPipeline {

    private String output = "nothing";

    public samplepipeline2() {

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
