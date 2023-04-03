package com.globits.da.excel;

import com.globits.da.dto.EmployeeDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class excelUtils {
    public void exportEmployee(List<EmployeeDTO> employees, String fileName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("employees");

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Code");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone");
        headerRow.createCell(5).setCellValue("Age");

        int roww = 1;
        int stt = 1;
        for (EmployeeDTO employeeDTO : employees) {
            Row row = sheet.createRow(roww++);

            row.createCell(0).setCellValue(stt);
            row.createCell(1).setCellValue(employeeDTO.getName());
            row.createCell(2).setCellValue(employeeDTO.getCode());
            row.createCell(3).setCellValue(employeeDTO.getEmail());
            row.createCell(4).setCellValue(employeeDTO.getPhone());
            row.createCell(5).setCellValue(employeeDTO.getAge());
            stt++;
        }

        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
    }


}
