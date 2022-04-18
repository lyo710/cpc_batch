package com.valtech;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ImgResizeTool {
    public static void main(String[] args) throws Exception {
        String[] extensions = new String[] { "jpg"};
        File dir = new File("/Users/ly/Desktop/designs");
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for (File file : files) {
            System.out.println("Process: " + file.getCanonicalPath());
            BufferedImage bimg = ImageIO.read(file);
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(file));
            JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(bimg);
            jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
            jpegEncoder.setJPEGEncodeParam(jpegEncodeParam); jpegEncodeParam.setQuality(1f, false);
            jpegEncodeParam.setXDensity(192); jpegEncodeParam.setYDensity(192);

            jpegEncoder.encode(bimg, jpegEncodeParam);
            bimg.flush();
        }
    }
}
