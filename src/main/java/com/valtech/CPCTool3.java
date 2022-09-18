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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class CPCTool3 {

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
                    System.out.println(txtFile.getAbsolutePath().substring(27));
                } else if (taskFile.getName().endsWith(".jpg")) {
                    String picName = taskFile.getName();
                    //01.jpg
                    int key = Integer.valueOf(picName.substring(picName.length() - 6, picName.length() - 4)) - 1;
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
            System.out.println("--- " + taskFolder.getName() + " ---");
            System.out.println(txtFileContentStr);
            String[] txtFileContentLines = txtFileContentStr.split("\r\n");

            for (String txtFileContentLine : txtFileContentLines) {
                System.out.println("txtFileContentLine = " + txtFileContentLine);
            }
        }

        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://192.168.30.20:49154/cpc?"
                            + "user=cpc&password=123qwe");
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into cpc_customer (customer_name, customer_cid, customer_address, customer_zipcode, customer_contact, customer_phone, src, valid) values (?, ?, ?, ?, ?, ?, ?, 1)");
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setString(4, "TestWebpage");
            preparedStatement.setString(5, "Test");
            preparedStatement.setString(6, "TestEmail");
            preparedStatement.setString(7, txtFile.getAbsolutePath().substring(27));
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            connect.close();
        }

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
