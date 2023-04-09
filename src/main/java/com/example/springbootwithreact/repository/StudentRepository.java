package com.example.springbootwithreact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootwithreact.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = RepositoryConstants.FETCH_ALL_STUDENTS)
    public List<Student> findAllStudentList();

    @Query(value = RepositoryConstants.GET_STUDENTS_BY_NAME)
    public List<Student> findAllStudentListByName(String name);

    @Transactional
    @Modifying
    @Query(value = RepositoryConstants.DELETE_RECORDS_ID_IN)
    public void deleteRecordsByIdIn(@Param("ids") List<Integer> ids);

    public void deleteAllByIdIn(List<Integer> ids);

    @Query(value = RepositoryConstants.GET_STUDENTS_BY_IDS)
    public List<Integer> findAllById(@Param("ids") List<Integer> ids);

}
