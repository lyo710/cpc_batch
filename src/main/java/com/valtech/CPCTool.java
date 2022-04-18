package com.valtech;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceOptions;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CPCTool {
    public static void main(String[] args) throws Exception {

        SimpleDateFormat ajFmt = new SimpleDateFormat("yyyyMMddHH");

        String taskFilePath = "/Users/ly/Desktop/专利代理机构委托解聘书-惠州市博越汽车零部件制造有限公司/新建文本文档2.txt";
        String taskFileContentStr = FileUtils.readFileToString(new File(taskFilePath), "GBK");
        String[] taskFileContentLines = taskFileContentStr.split("\r\n");

        String sqFilePath = "/Users/ly/Desktop/xls_1/DZSQ_KHD_SHENQINGXX.xlsx";
        String ajFilePath = "/Users/ly/Desktop/xls_1/DZSQ_KHD_AJ.xlsx";
        String wjFilePath = "/Users/ly/Desktop/xls_1/DZSQ_KHD_SQWJ.xlsx";

        String sqOutFilePath = "/Users/ly/Desktop/xls_1/DZSQ_KHD_SHENQINGXX_1.xlsx";
        String ajOutFilePath = "/Users/ly/Desktop/xls_1/DZSQ_KHD_AJ_1.xlsx";
        String wjOutFilePath = "/Users/ly/Desktop/xls_1/DZSQ_KHD_SQWJ_1.xlsx";

        FileInputStream sqFileStream = new FileInputStream(sqFilePath);
        FileInputStream ajFileStream = new FileInputStream(ajFilePath);
        FileInputStream wjFileStream = new FileInputStream(wjFilePath);

        Workbook sqWorkbook = new XSSFWorkbook(sqFileStream);
        Sheet sqSheet = sqWorkbook.getSheetAt(0);
        int sqLastRow= sqSheet.getLastRowNum();

        Workbook ajWorkbook = new XSSFWorkbook(ajFileStream);
        Sheet ajSheet = ajWorkbook.getSheetAt(0);
        int ajLastRow= ajSheet.getLastRowNum();
        int startAjNum = 0;

        Workbook wjWorkbook = new XSSFWorkbook(wjFileStream);
        Sheet wjSheet = wjWorkbook.getSheetAt(0);
        int wjLastRow= wjSheet.getLastRowNum();
        double startWjNum = wjSheet.getRow(wjLastRow).getCell(0).getNumericCellValue();

        String docRootDir = "/Users/ly/Desktop/doc_sample";

        FindReplaceOptions options = new FindReplaceOptions();
        String[] jpgStrArray = {"220414214844","220414214851","220414214859","220414214909","220414214916"};

        for (String taskFileLineStr : taskFileContentLines) {
            Date createdTime = new Date();
            String sqUId = UUID.randomUUID().toString();
            String ajUId = UUID.randomUUID().toString();
            String sqSerial = taskFileLineStr.substring(0,13);
            String sqName = taskFileLineStr.substring(14);

            String outputDir = "/Users/ly/Desktop/" + getTypeFolderName(Integer.valueOf(sqSerial.substring(4,5)));

            Row sqRow = sqSheet.createRow(++sqLastRow);
            sqRow.createCell(0).setCellValue("{"+ sqUId.toUpperCase()  +"}");
            sqRow.createCell(1).setCellValue(String.valueOf(Integer.valueOf(sqSerial.substring(4,5)) -1));
            sqRow.createCell(2).setCellValue(sqName);
            sqRow.createCell(3).setCellValue(sqSerial);
            sqRow.createCell(8).setCellValue(createdTime);
            sqRow.createCell(9).setCellValue("0");

            Row ajRow = ajSheet.createRow(++ajLastRow);
            ajRow.createCell(0).setCellValue("{"+ ajUId.toUpperCase()  +"}");
            ajRow.createCell(2).setCellValue(ajFmt.format(new Date()) + String.format("%04d", ++startAjNum));
            ajRow.createCell(3).setCellValue("2");
            ajRow.createCell(4).setCellValue("1");
            ajRow.createCell(5).setCellValue("{"+ sqUId.toUpperCase()  +"}");
            ajRow.createCell(6).setCellValue(createdTime);
            ajRow.createCell(8).setCellValue("0");
            ajRow.createCell(11).setCellValue("Upload\\");
            ajRow.createCell(13).setCellValue("44428_002");

            Row wjRow1 = wjSheet.createRow(++wjLastRow);
            wjRow1.createCell(0).setCellValue(++startWjNum);
            wjRow1.createCell(1).setCellValue("著录项目变更申报书");
            wjRow1.createCell(2).setCellValue("100016");
            wjRow1.createCell(3).setCellValue("2");
            wjRow1.createCell(4).setCellValue("{"+ ajUId.toUpperCase()  +"}");
            wjRow1.createCell(5).setCellValue("0");
            wjRow1.createCell(6).setCellValue(createdTime);
            wjRow1.createCell(7).setCellValue("\\cases\\"+getTypeFolderName(Integer.valueOf(sqSerial.substring(4,5)))+"\\"+sqUId+"\\others\\"+ajUId+"\\100016\\100016.doc");
            wjRow1.createCell(8).setCellValue("1");
            wjRow1.createCell(9).setCellValue(0);
            wjRow1.createCell(11).setCellValue(2);

            Row wjRow2 = wjSheet.createRow(++wjLastRow);
            wjRow2.createCell(0).setCellValue(++startWjNum);
            wjRow2.createCell(1).setCellValue("著录项目变更理由证明");
            wjRow2.createCell(2).setCellValue("100104");
            wjRow2.createCell(3).setCellValue("2");
            wjRow2.createCell(4).setCellValue("{"+ ajUId.toUpperCase()  +"}");
            wjRow2.createCell(5).setCellValue("0");
            wjRow2.createCell(6).setCellValue(createdTime);
            wjRow2.createCell(7).setCellValue("\\cases\\"+getTypeFolderName(Integer.valueOf(sqSerial.substring(4,5)))+"\\"+sqUId+"\\others\\"+ajUId+"\\100104\\100104-1.doc");
            wjRow2.createCell(8).setCellValue("1");
            wjRow2.createCell(9).setCellValue(0);
            wjRow2.createCell(11).setCellValue(2);

            //\cases\designs\69beedf6-89b9-45ae-beab-97a670be2989\others\bc9c304e-ae53-4b4c-b798-79edcf8b1eed\100016\100016.doc
            Document d16Doc = new Document(docRootDir + File.separator + "100016" + File.separator + "100016.doc");
            d16Doc.getRange().replace("2018306628598", sqSerial, options);
            d16Doc.getRange().replace("汽车全景360面壳(奥迪系列4)", sqName, options);

            String d16Dir = outputDir + File.separator +
                    sqUId + File.separator +
                    "others" + File.separator +
                    ajUId + File.separator +
                    "100016";
            new File(d16Dir).mkdirs();
            d16Doc.save(d16Dir + File.separator + "100016.doc");

            String d16XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100016" + File.separator + "100016.xml"), "UTF-8");
            d16XMLStr = d16XMLStr.replaceAll("2018306628598", sqSerial);
            d16XMLStr = d16XMLStr.replaceAll("汽车全景360面壳\\(奥迪系列4\\)", sqName);
            File temp16File = new File(d16Dir + File.separator + "100016.xml");
            FileUtils.writeStringToFile(temp16File, d16XMLStr, "UTF-8");

            //------------
            Document d104Doc = new Document(docRootDir + File.separator + "100104" + File.separator + "100104-1.doc");
            d104Doc.getRange().replace("2018306628598", sqSerial, options);
            d104Doc.getRange().replace("汽车全景360面壳(奥迪系列4)", sqName, options);

            String d104Dir = outputDir + File.separator +
                    sqUId + File.separator +
                    "others" + File.separator +
                    ajUId + File.separator +
                    "100104";
            new File(d104Dir).mkdirs();
            d104Doc.save(d104Dir + File.separator + "100104-1.doc");

            String d104XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100104" + File.separator + "100104-1.xml"), "UTF-8");
            d104XMLStr = d104XMLStr.replaceAll("2018306628598", sqSerial);
            d104XMLStr = d104XMLStr.replaceAll("汽车全景360面壳\\(奥迪系列4\\)", sqName);
            File temp104File = new File(d104Dir + File.separator + "100104-1.xml");
            FileUtils.writeStringToFile(temp104File, d104XMLStr, "UTF-8");
            for (String jpgStr : jpgStrArray) {
                FileUtils.copyFile(new File(docRootDir + File.separator + "100104" + File.separator + jpgStr +".jpg"),
                        new File(d104Dir + File.separator + jpgStr +".jpg"));
            }

            //------------
            String listXmlDir = outputDir + File.separator +
                    sqUId + File.separator +
                    "others" + File.separator +
                    ajUId;
            String listXMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "list.xml"), "UTF-8");
            listXMLStr = listXMLStr.replaceAll("d0f55d50-1a76-40e3-94f3-5eb1ed927731", ajUId);
            listXMLStr = listXMLStr.replaceAll("2018306628598", sqSerial);
            listXMLStr = listXMLStr.replaceAll("汽车全景360面壳\\(奥迪系列4\\)", sqName);
            File tempListFile = new File(listXmlDir + File.separator + "list.xml");
            FileUtils.writeStringToFile(tempListFile, listXMLStr, "UTF-8");
        }

        sqFileStream.close();
        FileOutputStream sqOutFileStream =new FileOutputStream(sqOutFilePath);
        sqWorkbook.write(sqOutFileStream);
        sqOutFileStream.close();
        System.out.println("SQ file is successfully written");

        ajFileStream.close();
        FileOutputStream ajOutFileStream =new FileOutputStream(ajOutFilePath);
        ajWorkbook.write(ajOutFileStream);
        ajOutFileStream.close();
        System.out.println("AJ file is successfully written");

        wjFileStream.close();
        FileOutputStream wjOutFileStream =new FileOutputStream(wjOutFilePath);
        wjWorkbook.write(wjOutFileStream);
        wjOutFileStream.close();
        System.out.println("WJ file is successfully written");
    }

    private static String getTypeFolderName(int type) {
        if (type == 3) {
            return "design";
        } else if (type == 2) {
            return "utility_models";
        } else if (type == 1){
            return "inventions";
        } else {
            return "unknown";
        }
    }
}
