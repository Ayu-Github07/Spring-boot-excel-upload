package com.example.springbootwithreact.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootwithreact.dto.Response;
import com.example.springbootwithreact.model.Student;
import com.example.springbootwithreact.service.StudentServiceImpl;

@RestController
@RequestMapping("/api/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @PostMapping("/saveDetails")
    public ResponseEntity<Response> saveStudentDetails(@RequestBody Student student) {

        try {
            String saveResponse = studentServiceImpl.saveStudentDetails(student);
            Response response = new Response(saveResponse, LocalDateTime.now());
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Student>> fetchAllStudentRecords() {
        try {
            List<Student> getAllStudents = studentServiceImpl.getAllStudentList();
            return ResponseEntity.ok().body(getAllStudents);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name")
    public ResponseEntity<List<Student>> fetchAllStudentRecordsByName(@RequestParam("name") String name) {
        try {
            return ResponseEntity.ok().body(studentServiceImpl.getAllStudentListByName(name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/id")
    public ResponseEntity<Student> fetchStudentById(@RequestParam("id") int id) {
        try {
            Optional<Student> student = studentServiceImpl.getStudentById(id);
            return ResponseEntity.ok().body(student.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateStudentRecordById(@RequestBody Student student) {
        try {
            String saveResponse = studentServiceImpl.updateStudentRecords(student);
            Response response = new Response(saveResponse, LocalDateTime.now());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteStudentRecordById(@PathVariable("id") int id) {
        try {
            String saveResponse = studentServiceImpl.deleteStudentRecords(id);
            Response response = new Response(saveResponse, LocalDateTime.now());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteMany")
    public ResponseEntity<Response> deleteManyStudentRecordsByIds(@RequestBody List<Integer> ids) {
        try {
            String saveResponse = studentServiceImpl.deleteStudentRecordsByIds(ids);
            Response response = new Response(saveResponse, LocalDateTime.now());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/excelUpload")
    public ResponseEntity<List<Response>> insertTheRecordsByExcelUpload(@RequestParam("file") MultipartFile file)
            throws IOException {
        try {
            List<Response> responseAll = studentServiceImpl.insertStudentsUsingExcelUpload(file);
            return ResponseEntity.ok().body(responseAll);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
