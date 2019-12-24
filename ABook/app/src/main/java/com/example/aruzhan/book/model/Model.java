package com.example.aruzhan.book.model;

public class Model {
    private String name, gender;
    private Integer age;

    public Model(String name, String gender, Integer age){
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getNama() {
        return name;
    }

    public void setNama(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
