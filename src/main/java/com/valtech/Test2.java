package com.valtech;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Test2 {
    public static void main(String[] args) throws Exception {
//        String docRootDir = "/Users/ly/Desktop/doc_sample";
//
//        String d16XMLStr = FileUtils.readFileToString(new File(docRootDir + File.separator + "100016" + File.separator + "100016.xml"),"UTF-8");
//
//        System.out.println(d16XMLStr);
//
//        d16XMLStr = d16XMLStr
//                .replaceAll("\\$申请人\\$", "测试")
//                .replaceAll("\\$申请号\\$", "123")
//        ;
//
//        System.out.println(d16XMLStr);
        for (int i = 1; i < 30; i ++) {
            System.out.println(String.format("%02d", i));
        }

    }
}
