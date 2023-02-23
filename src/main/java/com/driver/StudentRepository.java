package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    private HashMap<String, Student> studentMap;
    private HashMap<String, Teacher> teacherMap;
    private HashMap<String, List<String>> studentTeacherMap;

    StudentRepository() {
        studentMap = new HashMap<>();
        teacherMap = new HashMap<>();
        studentTeacherMap = new HashMap<>();
    }

    public void addStudent(Student student) {
        studentMap.put(student.getName(), student);
    }

    public void addTeacher(Teacher teacher) {
        teacherMap.put(teacher.getName(), teacher);
    }

    public void addStudentTeacher(String student, String teacher) {
        List<String> list;
        if (studentMap.containsKey(student) && teacherMap.containsKey(teacher)) {
            if (studentTeacherMap.containsKey(teacher)) {
                list = studentTeacherMap.get(teacher);
            } else {
                list = new ArrayList<>();
            }
            list.add(student);
            studentTeacherMap.put(teacher, list);
        }
    }

    public Student getStudentByName(String name) {
        return studentMap.getOrDefault(name, null);
    }

    public Teacher getTeacherByName(String name) {
        return teacherMap.getOrDefault(name, null);
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        return studentTeacherMap.getOrDefault(teacher, null);
    }

    public List<String> getAllStudents() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Student> s : studentMap.entrySet()) {
            list.add(s.getKey());
        }
        return list;
    }

    public void deleteTeacherByName(String teacher) {
        if (teacherMap.containsKey(teacher)) teacherMap.remove(teacher);

        if (studentTeacherMap.containsKey(teacher)) {
            List<String> list = studentTeacherMap.get(teacher);
            for (String name : list) {
                studentMap.remove(name);
            }
            studentTeacherMap.remove(teacher);
        }
    }

    public void deleteAllTeachers() {
        for (Map.Entry<String, Teacher> map : teacherMap.entrySet()) {
            String teacherName = map.getKey();
            teacherMap.remove(teacherName);
            for (String sname : studentTeacherMap.get(teacherName)) {
                studentMap.remove(sname);
            }
            studentTeacherMap.remove(teacherName);
        }
    }
}
