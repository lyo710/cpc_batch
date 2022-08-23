package com.valtech;

import com.valtech.model.PatentCase;
import com.valtech.model.PatentCustomer;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class CPCTool2 {
    public static void main(String[] args) throws Exception {

        SimpleDateFormat ajFmt = new SimpleDateFormat("yyyyMMddHH");
        SimpleDateFormat picNameFmt = new SimpleDateFormat("yyMMddHHmm");

        String sqFilePath = "/Users/ly/Desktop/xls_2/DZSQ_KHD_SHENQINGXX.xlsx";
        String ajFilePath = "/Users/ly/Desktop/xls_2/DZSQ_KHD_AJ.xlsx";
        String wjFilePath = "/Users/ly/Desktop/xls_2/DZSQ_KHD_SQWJ.xlsx";

        String sqOutFilePath = "/Users/ly/Desktop/xls_2/DZSQ_KHD_SHENQINGXX_2.xlsx";
        String ajOutFilePath = "/Users/ly/Desktop/xls_2/DZSQ_KHD_AJ_2.xlsx";
        String wjOutFilePath = "/Users/ly/Desktop/xls_2/DZSQ_KHD_SQWJ_2.xlsx";

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

        String taskFileRootDir = "/Users/ly/Desktop/zl_input";
        String docRootDir = "/Users/ly/Desktop/doc_sample";

        String d16XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100016" + File.separator + "100016.xml"),"UTF-8");
        String d104XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100104" + File.separator + "100104-1.xml"), "UTF-8");

        for (File taskFolder : new File(taskFileRootDir).listFiles()) {
            if (taskFolder.isFile()) {
                continue;
            }
            //每个客户
            List<PatentCase> patentCaseList = new ArrayList<>();
            PatentCustomer patentCustomer = new PatentCustomer();

            File txtFile = null;
            Map<Integer, File> picFilesMap = new TreeMap<>();
            for (File taskFile : taskFolder.listFiles()) {
                if (taskFile.getName().endsWith(".txt")) {
                    txtFile = taskFile;
//                    System.out.println(txtFile.getAbsolutePath());
                } else if (taskFile.getName().endsWith(".jpg")) {
                    String picName = taskFile.getName();
                    //01.jpg
                    int key = Integer.valueOf(picName.substring(picName.length()-6, picName.length()-4)) -1;
                    picFilesMap.put(key, taskFile);
                }
            }
            List<File> picFiles = picFilesMap.values()
                    .stream()
                    .collect(Collectors.toList());
            if (txtFile == null || picFiles.size() == 0) {
                System.out.println("输入数据异常, 目录名: " + taskFolder.getName());
                return;
            }
            patentCustomer.setPicFiles(picFiles);

            //开始逐客户处理
            //解析txt
            String txtFileContentStr = FileUtils.readFileToString(txtFile, "GBK");
//            System.out.println("--- " + taskFolder.getName() + " ---");
//            System.out.println(txtFileContentStr);
            String[] txtFileContentLines = txtFileContentStr.split("\r\n");

            for (String txtFileContentLine : txtFileContentLines) {
                System.out.println("txtFileContentLine = " + txtFileContentLine);
                String sqSerial = null;

                PatentCase patentCase = new PatentCase();
                if (txtFileContentLine.length() == 0) {
                    continue;
                }
                if (Character.isDigit(txtFileContentLine.charAt(0))) {
//                    System.out.println(txtFileContentLine);
                    sqSerial = txtFileContentLine.substring(0, 13);
//                    System.out.println(sqSerial);
                    String sqName = txtFileContentLine.substring(13).trim();
//                    System.out.println(sqName);
                    patentCase.setSqSerial(sqSerial);
                    patentCase.setSqName(sqName);
                    patentCaseList.add(patentCase);
                } else if (txtFileContentLine.startsWith("申请人：")) {
//                    System.out.println(txtFileContentLine.substring(4).trim());
                    patentCustomer.setCustomerName(txtFileContentLine.substring(4).trim());
                    System.out.println("----");
                    System.out.println(patentCustomer.getCustomerName());
                    System.out.println("----");
                } else if (txtFileContentLine.startsWith("统一编号：")) {
//                    System.out.println(txtFileContentLine.substring(5).trim());
                    patentCustomer.setCustomerSerial(txtFileContentLine.substring(5).trim());
                } else if (txtFileContentLine.startsWith("地址：")) {
//                    System.out.println(txtFileContentLine.substring(3).trim());
                    patentCustomer.setCustomerAddress(txtFileContentLine.substring(3).trim());
                } else if (txtFileContentLine.startsWith("邮编：")) {
//                    System.out.println(txtFileContentLine.substring(3).trim());
                    patentCustomer.setCustomerZipCode(txtFileContentLine.substring(3).trim());
                } else if (txtFileContentLine.startsWith("联系人：")) {
//                    System.out.println(txtFileContentLine.substring(4).trim());
                    patentCustomer.setContactName(txtFileContentLine.substring(4).trim());
                } else if (txtFileContentLine.startsWith("电话：")) {
//                    System.out.println(txtFileContentLine.substring(3).trim());
                    patentCustomer.setContactMobile(txtFileContentLine.substring(3).trim());
                }
            }

            for (PatentCase patentCase : patentCaseList) {
                Date createdTime = new Date();
                String sqUId = UUID.randomUUID().toString();
                String ajUId = UUID.randomUUID().toString();
                String sqSerial = patentCase.getSqSerial();
                String sqName = patentCase.getSqName();
                String outputDir = "/Users/ly/Desktop/" + getTypeFolderName(Integer.valueOf(sqSerial.substring(4,5)));
                String d16Dir = outputDir + File.separator +
                        sqUId + File.separator +
                        "others" + File.separator +
                        ajUId + File.separator +
                        "100016";
                new File(d16Dir).mkdirs();
                System.out.println(patentCustomer.getCustomerName());
                String d16XMLGenStr = d16XMLStr
                        .replaceAll("\\$申请人\\$", patentCustomer.getCustomerName())
                        .replaceAll("\\$申请号\\$", sqSerial)
                        .replaceAll("\\$申请名称\\$", sqName)
                        .replaceAll("\\$统一编码\\$", patentCustomer.getCustomerSerial())
                        .replaceAll("\\$联系人\\$", patentCustomer.getContactName())
                        .replaceAll("\\$地址\\$", patentCustomer.getCustomerAddress())
                        .replaceAll("\\$邮编\\$", patentCustomer.getCustomerZipCode())
                        .replaceAll("\\$电话\\$", patentCustomer.getContactMobile())
                ;
                File temp16File = new File(d16Dir + File.separator + "100016.xml");
                FileUtils.writeStringToFile(temp16File, d16XMLGenStr, "UTF-8");

                //--------------------------

                String d104Dir = outputDir + File.separator +
                        sqUId + File.separator +
                        "others" + File.separator +
                        ajUId + File.separator +
                        "100104";
                new File(d104Dir).mkdirs();
                int picNum = 0;
                //<图片或照片 顺序="1"><说明>专利代理机构委托解聘书-1</说明><文件名称>220414214844.jpg</文件名称></图片或照片>
                //图片尺寸上限 165mm x 245mm resize to 134.94mm
                StringBuffer sb = new StringBuffer();
                for (File picFile : picFiles) {
                    String picName = picNameFmt.format(new Date()) + String.format("%02d", ++picNum);
                    FileUtils.copyFile(picFile,
                            new File(d104Dir + File.separator + picName + ".jpg"));
                    sb.append("<图片或照片 顺序=\"" + picNum + "\"><说明>专利代理机构委托解聘书-" + picNum +
                            "</说明><文件名称>" + picName + ".jpg</文件名称></图片或照片>");
                }
                String d104XMLGenStr = d104XMLStr
                        .replaceAll("\\$申请人\\$", patentCustomer.getCustomerName())
                        .replaceAll("\\$申请号\\$", sqSerial)
                        .replaceAll("\\$申请名称\\$", sqName)
                        .replaceAll("\\$统一编码\\$", patentCustomer.getCustomerSerial())
                        .replaceAll("\\$联系人\\$", patentCustomer.getContactName())
                        .replaceAll("\\$地址\\$", patentCustomer.getCustomerAddress())
                        .replaceAll("\\$邮编\\$", patentCustomer.getCustomerZipCode())
                        .replaceAll("\\$电话\\$", patentCustomer.getContactMobile())
                        .replaceAll("<图片或照片></图片或照片>", sb.toString())
                ;
                File temp104File = new File(d104Dir + File.separator + "100104-1.xml");
                FileUtils.writeStringToFile(temp104File, d104XMLGenStr, "UTF-8");

                //-----------------------------

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
                wjRow1.createCell(7).setCellValue("\\cases\\" + getTypeFolderName(Integer.valueOf(sqSerial.substring(4,5))) + "\\"+sqUId+"\\others\\"+ajUId+"\\100016\\100016.doc");
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
                wjRow2.createCell(7).setCellValue("\\cases\\" + getTypeFolderName(Integer.valueOf(sqSerial.substring(4,5))) + "\\"+sqUId+"\\others\\"+ajUId+"\\100104\\100104-1.doc");
                wjRow2.createCell(8).setCellValue("1");
                wjRow2.createCell(9).setCellValue(0);
                wjRow2.createCell(11).setCellValue(2);
            }
            patentCustomer.setPatentCaseList(patentCaseList);
            // 所有客户循环结束
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
            return "designs";
        } else if (type == 2) {
            return "utility_models";
        } else if (type == 1){
            return "inventions";
        } else {
            return "unknown";
        }
    }
}
