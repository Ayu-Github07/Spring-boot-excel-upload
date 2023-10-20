package com.example.springbootwithreact.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.springbootwithreact.dto.Response;
import com.example.springbootwithreact.model.Student;

public interface StudentService {

    public String saveStudentDetails(Student student);

    public List<Student> getAllStudentList();

    public List<Student> getAllStudentListByName(String name);

    public Optional<Student> getStudentById(int id);

    public String updateStudentRecords(Student student);

    public String deleteStudentRecords(int id);

    public String deleteStudentRecordsByIds(List<Integer> ids);

    public List<Response> insertStudentsUsingExcelUpload(MultipartFile file);

    public ByteArrayInputStream getStudentDataInExcel();

}
