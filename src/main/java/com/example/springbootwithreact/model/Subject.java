package com.example.springbootwithreact.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subjectName;
    private Integer studentScore;
    private Integer studentClass;
    private String section;
    @ManyToOne
    @JoinColumn(name = "student_fk_id") // Changed to a distinct name
    private Student student;

    public Subject() {
    }

    public Subject(Long id, String subjectName, Integer studentScore, Integer studentClass, String section) {
        this.id = id;
        this.subjectName = subjectName;
        this.studentScore = studentScore;
        this.studentClass = studentClass;
        this.section = section;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Integer studentScore) {
        this.studentScore = studentScore;
    }

    public Integer getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Integer studentClass) {
        this.studentClass = studentClass;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subjectName == null) ? 0 : subjectName.hashCode());
        result = prime * result + ((studentScore == null) ? 0 : studentScore.hashCode());
        result = prime * result + ((studentClass == null) ? 0 : studentClass.hashCode());
        result = prime * result + ((section == null) ? 0 : section.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subject other = (Subject) obj;
        if (subjectName == null) {
            if (other.subjectName != null)
                return false;
        } else if (!subjectName.equals(other.subjectName))
            return false;
        if (studentScore == null) {
            if (other.studentScore != null)
                return false;
        } else if (!studentScore.equals(other.studentScore))
            return false;
        if (studentClass == null) {
            if (other.studentClass != null)
                return false;
        } else if (!studentClass.equals(other.studentClass))
            return false;
        if (section == null) {
            if (other.section != null)
                return false;
        } else if (!section.equals(other.section))
            return false;
        return true;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
