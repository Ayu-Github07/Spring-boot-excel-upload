package com.example.springbootwithreact.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.example.springbootwithreact.model.Student;

public class ExcelExport {

    private static final String[] headerColumns = {
            "ID",
            "Name",
            "Email Id",
            "Roll No",
            "Mobile No",
            "Aadhar",
            "Address",
            "Pincode",
            "Gender"
    };

    private static final String SHEET_NAME = "Student_Records";

    public static ByteArrayInputStream dataToExcel(List<Student> studentList) throws IOException {

        // Create a workbook
        Workbook workbook = new SXSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // Create a sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // Create a header row
            Row row = sheet.createRow(3);

            // Header Row Font Styling
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            // Header Row Cell Styling
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            applyBorders(headerCellStyle);

            // Initialize header column
            int colIndex = 0;
            for (String headers : headerColumns) {
                Cell cell = row.createCell(colIndex++);
                cell.setCellValue(headers);
                cell.setCellStyle(headerCellStyle);
            }

            // Data Rows
            int rowIndex = 4;
            for (Student student : studentList) {
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                colIndex = 0;
                dataRow.createCell(colIndex++).setCellValue(student.getId());
                dataRow.createCell(colIndex++).setCellValue(student.getName());
                dataRow.createCell(colIndex++).setCellValue(student.getEmail());
                dataRow.createCell(colIndex++).setCellValue(student.getRollno());
                dataRow.createCell(colIndex++).setCellValue(student.getMobile());
                dataRow.createCell(colIndex++).setCellValue(student.getAadhar());
                dataRow.createCell(colIndex++).setCellValue(student.getAddress());
                dataRow.createCell(colIndex++).setCellValue(student.getPincode());
                dataRow.createCell(colIndex++).setCellValue(student.getGender());
            }

            // Displaying the total records
            Row totalRow = sheet.createRow(rowIndex);

            // Styling
            Font totalRecordFont = workbook.createFont();
            totalRecordFont.setBold(true);

            CellStyle totalRecordCellStyle = workbook.createCellStyle();
            totalRecordCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            totalRecordCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            totalRecordCellStyle.setFont(totalRecordFont);
            applyBorders(totalRecordCellStyle);

            Cell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("Total Student Records: ");
            totalLabelCell.setCellStyle(totalRecordCellStyle);

            Cell totalValueCell = totalRow.createCell(1);
            totalValueCell.setCellValue(studentList.size());
            totalValueCell.setCellStyle(totalRecordCellStyle);

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to export excel");
            return null;

        } finally {
            workbook.close();
            outputStream.close();
        }

    }

    public static void applyBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }

}
