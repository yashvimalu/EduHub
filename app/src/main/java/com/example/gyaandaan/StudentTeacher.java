package com.example.gyaandaan;

public class StudentTeacher {
    String student_name, teacher_name,subject,standard,board;

    public StudentTeacher(String student_name, String teacher_name, String subject, String standard,String board) {
        this.student_name = student_name;
        this.teacher_name = teacher_name;
        this.subject = subject;
        this.standard = standard;
        this.board= board;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}