package com.valtech;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceOptions;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Test {
    public static void main(String[] args) throws Exception {
//        int startNum = 1;
//        for (int i = startNum; i< 200; i++) {
//            String outStr = String.format("%04d", i);
//            System.out.println(outStr);
//        }
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHH");
//        System.out.println(fmt.format(new Date()));


        String docRootDir = "/Users/ly/Desktop/doc_sample";
        String outputDir = "/Users/ly/Desktop/designs";
        FindReplaceOptions options = new FindReplaceOptions();
        String[] jpgStrArray = {"220414200312","220414200318","220414200325","220414200333","220414200342"};

//        //\cases\designs\69beedf6-89b9-45ae-beab-97a670be2989\others\bc9c304e-ae53-4b4c-b798-79edcf8b1eed\100016\100016.doc
//        Document d16Doc = new Document(docRootDir + File.separator + "100016" + File.separator + "100016.doc");
//        d16Doc.getRange().replace("2018306628598", "2018306659308", options);
//        d16Doc.getRange().replace("汽车全景360面壳(奥迪系列4)", "汽车全景360面壳(大众系列2)", options);
//
//        String d16Dir = outputDir + File.separator +
//                "69beedf6-89b9-45ae-beab-97a670be2989" + File.separator +
//                "others" + File.separator +
//                "bc9c304e-ae53-4b4c-b798-79edcf8b1eed" + File.separator +
//                "100016";
//        new File(d16Dir).mkdirs();
//        d16Doc.save(d16Dir + File.separator + "100016.doc");
//
//        String d16XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100016" + File.separator + "100016.xml"), "UTF-8");
//        d16XMLStr = d16XMLStr.replaceAll("2018306628598", "2018306659308");
//        d16XMLStr = d16XMLStr.replaceAll("汽车全景360面壳\\(奥迪系列4\\)", "汽车全景360面壳(大众系列2)");
//        File temp16File = new File(d16Dir + File.separator + "100016.xml");
//        FileUtils.writeStringToFile(temp16File, d16XMLStr, "UTF-8");
//
//        //------------
//        Document d104Doc = new Document(docRootDir + File.separator + "100104" + File.separator + "100104-1.doc");
//        d104Doc.getRange().replace("2018306628598", "2018306659308", options);
//        d104Doc.getRange().replace("汽车全景360面壳(奥迪系列4)", "汽车全景360面壳(大众系列2)", options);
//
//        String d104Dir = outputDir + File.separator +
//                "69beedf6-89b9-45ae-beab-97a670be2989" + File.separator +
//                "others" + File.separator +
//                "bc9c304e-ae53-4b4c-b798-79edcf8b1eed" + File.separator +
//                "100104";
//        new File(d104Dir).mkdirs();
//        d16Doc.save(d104Dir + File.separator + "100104-1.doc");
//
//        String d104XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100104" + File.separator + "100104-1.xml"), "UTF-8");
//        d104XMLStr = d104XMLStr.replaceAll("2018306628598", "2018306659308");
//        d104XMLStr = d104XMLStr.replaceAll("汽车全景360面壳\\(奥迪系列4\\)", "汽车全景360面壳(大众系列2)");
//        File temp104File = new File(d104Dir + File.separator + "100104-1.xml");
//        FileUtils.writeStringToFile(temp104File, d104XMLStr, "UTF-8");
//        for (String jpgStr : jpgStrArray) {
//            FileUtils.copyFile(new File(docRootDir + File.separator + "100104" + File.separator + jpgStr +".jpg"),
//                    new File(d104Dir + File.separator + jpgStr +".jpg"));
//        }
    }
}
