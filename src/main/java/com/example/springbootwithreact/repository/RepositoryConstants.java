package com.example.springbootwithreact.repository;

public final class RepositoryConstants {

    private RepositoryConstants() {
        super();
    }

    public final static String FETCH_ALL_STUDENTS = "select s from Student s";

    public final static String GET_STUDENTS_BY_NAME = "select s from Student s where lower(s.name) like %:name%";

    public final static String DELETE_RECORDS_ID_IN = "DELETE FROM Student s WHERE s.id IN ?1";

    public final static String GET_STUDENTS_BY_IDS = "select s.id from Student s where s.id IN ?1";

    public final static String INSERT_STUDENT_RECORDS = "INSERT INTO Student (id, name, email, rollno, mobile, aadhar, address, pincode, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

}
