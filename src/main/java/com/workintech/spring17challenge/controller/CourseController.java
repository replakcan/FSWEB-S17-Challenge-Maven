package com.workintech.spring17challenge.controller;


import com.workintech.spring17challenge.entity.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Aynı isimde birden fazla course ekleyemeyiz.
//credit değeri hiçbir şekilde 0'dan küçük olamaz. 4'ten büyük olamaz.
@RestController
@RequestMapping("/courses")
public class CourseController {

    private LowCourseGpa lowCourseGpa;
    private MediumCourseGpa mediumCourseGpa;
    private HighCourseGpa highCourseGpa;

    private List<Course> courses;

    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public List<Course> init() {
        return courses = new ArrayList<>();
    }

    @GetMapping
    public List<Course> findAll() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course findByName(@PathVariable("name") String name) {
        Course course = courses.stream().filter((crs) -> crs.getName().equalsIgnoreCase(name)).findFirst().get();

        return course;
    }

    @PostMapping
    public List addCourse(@RequestBody Course course) {

        List list = new ArrayList();
        int credit = course.getCredit();
        int totalGPA;
        courses.add(course);

        if(credit <= 2) {
            totalGPA = course.getGrade().getCoefficient() * credit * lowCourseGpa.getGpa();
        } else if (credit == 3) {
            totalGPA = course.getGrade().getCoefficient() * credit * mediumCourseGpa.getGpa();
        } else if (credit == 4) {
            totalGPA = course.getGrade().getCoefficient() * credit * highCourseGpa.getGpa();
        } else {
            throw new IllegalArgumentException("Invalid credit value: " + credit);
        }

        list.add(course);
        list.add(totalGPA);

        return list;
    }

    @PutMapping("/{id}")
    public List update(@PathVariable("id") Integer id, @RequestBody Course course) {

        List listToReturn = new ArrayList();
        Course courseToRemove = courses.stream().filter((crs) -> crs.getId() == id).findFirst().get();

        courses.remove(id);
        course.setId(id);
        courses.add(course);

        int credit = course.getCredit();
        int totalGPA;

        if(credit <= 2) {
            totalGPA = course.getGrade().getCoefficient() * credit * lowCourseGpa.getGpa();
        } else if (credit == 3) {
            totalGPA = course.getGrade().getCoefficient() * credit * mediumCourseGpa.getGpa();
        } else if (credit == 4) {
            totalGPA = course.getGrade().getCoefficient() * credit * highCourseGpa.getGpa();
        } else {
            throw new IllegalArgumentException("Invalid credit value: " + credit);
        }

        listToReturn.add(course);
        listToReturn.add(totalGPA);

        return listToReturn;
    }

    @DeleteMapping("/{id}")
    public Course removeById(@PathVariable("id") Integer id) {
        Course course = courses.stream().filter(crs -> crs.getId() == id).findFirst().get();

        courses.remove(id);

        return course;
    }

}
