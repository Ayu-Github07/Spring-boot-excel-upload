package com.example.springbootwithreact.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootwithreact.dto.Response;
import com.example.springbootwithreact.model.Student;
import com.example.springbootwithreact.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public String saveStudentDetails(Student student) {

        Optional<Student> studentWithId = studentRepository.findById(student.getId());

        if (studentWithId.isPresent()) {
            return "Duplicate Record Entry!\nStudent with studentId: " + student.getId() + " already exists";
        }

        List<Student> listOfAllStudents = studentRepository.findAllStudentList();

        List<Student> fetchStudentList = listOfAllStudents.stream()
                .filter(s -> (s.getName().equals(student.getName()) && s.getEmail().equals(student.getEmail())))
                .collect(Collectors.toList());

        if (fetchStudentList.size() == 0) {
            List<Student> checkRollNo = listOfAllStudents.stream()
                    .filter(s -> s.getRollno().equals(student.getRollno())).collect(Collectors.toList());

            if (checkRollNo.size() == 0) {
                studentRepository.save(student);
            } else {
                return "Duplicate Student Record Entry!\nStudent with roll no: " + student.getRollno()
                        + " already exists";
            }
        } else {
            return "Duplicate Student Record Entry!\nStudent with name: " + student.getName() + " and email Id: "
                    + student.getEmail() + " already exists";
        }

        return "Student Record Saved Successfully!";

    }

    @Override
    public List<Student> getAllStudentList() {
        return studentRepository.findAllStudentList();
    }

    @Override
    public List<Student> getAllStudentListByName(String name) {
        return studentRepository.findAllStudentListByName(name);
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public String updateStudentRecords(Student student) {

        Optional<Student> getStudentById = studentRepository.findById(student.getId());
        if (getStudentById.isPresent()) {
            studentRepository.save(student);
            return "Records Updated Successfully!";
        }

        return "Record Not Found!";

    }

    @Override
    public String deleteStudentRecords(int id) {
        Optional<Student> getStudentById = studentRepository.findById(id);
        if (getStudentById.isPresent()) {
            studentRepository.deleteById(id);
            return "Records Deleted Successfully!";
        }

        return "Record Not Found!";
    }

    @Override
    public String deleteStudentRecordsByIds(List<Integer> ids) {

        if (!ids.isEmpty()) {
            List<Integer> studentList = studentRepository.findAllById(ids);
            List<Integer> studentIdPresentInDb = ids.stream().filter(s -> !studentList.contains(s))
                    .collect(Collectors.toList());

            studentRepository.deleteRecordsByIdIn(studentList);
            if (studentIdPresentInDb.size() != 0) {
                return "Records Deleted Successfully!\nThe following student ids are not present in database\n"
                        + studentIdPresentInDb.toString();
            }
            return "Records Deleted Successfully!\nRecords Deleted Are: \n" + studentList.toString();
        }
        return "Empty list is not allowed!";
    }

    @Override
    public List<Response> insertStudentsUsingExcelUpload(MultipartFile file) {

        List<Response> allResponseList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        Response response = new Response();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            for (Sheet sheet : workbook) {

                Row firstRow = sheet.getRow(0);

                if (!validateHeaders(firstRow)) {

                    Response responseHeader = new Response();
                    responseHeader.setMessage(
                            "Blank sheet is not allowed/ First row is required => Sheet Name : "
                                    + sheet.getSheetName());
                    responseHeader.setResponseTime(LocalDateTime.now());

                    allResponseList.add(responseHeader);

                    continue;
                }

                Map<String, Integer> headerRow = new HashMap<>();

                for (int i = 0; i <= firstRow.getLastCellNum(); i++) {
                    Cell cell = firstRow.getCell(i);
                    headerRow.put(dataFormatter.formatCellValue(cell).toLowerCase(), i);
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);

                    if (row == null || row.getLastCellNum() < 8) {
                        continue;
                    }

                    Student insertStudentRecord = new Student();
                    Response saveStudentResponse = new Response();

                    insertStudentRecord.setName(dataFormatter.formatCellValue(row.getCell(headerRow.get("name"))));
                    insertStudentRecord.setEmail(dataFormatter.formatCellValue(row.getCell(headerRow.get("emailid"))));
                    insertStudentRecord
                            .setAddress(dataFormatter.formatCellValue(row.getCell(headerRow.get("address"))));
                    insertStudentRecord.setGender(dataFormatter.formatCellValue(row.getCell(headerRow.get("gender"))));
                    insertStudentRecord.setMobile(dataFormatter.formatCellValue(row.getCell(headerRow.get("mobile"))));
                    insertStudentRecord
                            .setPincode(dataFormatter.formatCellValue(row.getCell(headerRow.get("pincode"))));
                    insertStudentRecord.setRollno(dataFormatter.formatCellValue(row.getCell(headerRow.get("rollno"))));
                    insertStudentRecord.setAadhar(dataFormatter.formatCellValue(row.getCell(headerRow.get("aadhar"))));

                    saveStudentResponse.setMessage(saveStudentDetails(insertStudentRecord));
                    saveStudentResponse.setResponseTime(LocalDateTime.now());

                    allResponseList.add(saveStudentResponse);
                }
            }
            return allResponseList;

        } catch (IOException e) {

            e.printStackTrace();
            response.setMessage("Provided Excel is not correct, Please check the file");
            response.setResponseTime(LocalDateTime.now());

            allResponseList.add(response);

            return allResponseList;
        }

    }

    public boolean validateHeaders(Row row) {

        if (row == null || row.getLastCellNum() < 8) {
            return false;
        }

        DataFormatter dataFormatter = new DataFormatter();
        List<String> expectedHeaders = Arrays.asList("name", "emailId", "rollno", "mobile", "aadhar", "address",
                "pincode", "gender");

        List<String> actualHeadersInFirstRow = new ArrayList<>();

        for (Cell cell : row) {

            actualHeadersInFirstRow.add(dataFormatter.formatCellValue(cell));

        }

        boolean isAllHeadersArePresent = true;

        for (String value : expectedHeaders) {
            if (!containsCaseInsensitive(value, actualHeadersInFirstRow)) {
                isAllHeadersArePresent = false;
                break;
            }
        }

        return isAllHeadersArePresent;
    }

    public boolean containsCaseInsensitive(String value, List<String> arrayList) {
        return arrayList.stream().anyMatch(x -> x.equalsIgnoreCase(value));
    }

}
