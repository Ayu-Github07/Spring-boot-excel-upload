package com.example.springbootwithreact.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.springbootwithreact.utils.StringConstants;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = StringConstants.errorMessage)
    @NotEmpty(message = StringConstants.errorMessage)
    private String name;

    @NotNull(message = StringConstants.errorMessage)
    @Email(message = StringConstants.emailErrorMessage)
    private String email;

    @NotNull(message = StringConstants.errorMessage)
    @NotEmpty(message = StringConstants.errorMessage)
    private String rollno;
    private String mobile;
    private String aadhar;

    @NotNull(message = StringConstants.errorMessage)
    @NotEmpty(message = StringConstants.errorMessage)
    private String address;

    @NotNull(message = StringConstants.errorMessage)
    @NotEmpty(message = StringConstants.errorMessage)
    private String pincode;

    @NotNull(message = StringConstants.errorMessage)
    @NotEmpty(message = StringConstants.errorMessage)
    private String gender;

    @NotNull(message = StringConstants.emptyList)
    @NotEmpty(message = StringConstants.emptyList)
    @Column
    @OneToMany(targetEntity = Subject.class, mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<@NotNull Subject> subjectList;

    public Student() {
    }

    public Student(int id, String name, String email, String rollno, String mobile, String aadhar, String address,
            String pincode, String gender, List<Subject> subjectList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rollno = rollno;
        this.mobile = mobile;
        this.aadhar = aadhar;
        this.address = address;
        this.pincode = pincode;
        this.gender = gender;
        this.subjectList = subjectList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((rollno == null) ? 0 : rollno.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((aadhar == null) ? 0 : aadhar.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((pincode == null) ? 0 : pincode.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((subjectList == null) ? 0 : subjectList.hashCode());
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
        Student other = (Student) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (rollno == null) {
            if (other.rollno != null)
                return false;
        } else if (!rollno.equals(other.rollno))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        } else if (!mobile.equals(other.mobile))
            return false;
        if (aadhar == null) {
            if (other.aadhar != null)
                return false;
        } else if (!aadhar.equals(other.aadhar))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (pincode == null) {
            if (other.pincode != null)
                return false;
        } else if (!pincode.equals(other.pincode))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (subjectList == null) {
            if (other.subjectList != null)
                return false;
        } else if (!subjectList.equals(other.subjectList))
            return false;
        return true;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

}
