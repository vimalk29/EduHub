package com.eduhub.company.model;

public class AttendancePOJO {
    String studentId;
    Boolean is_present;
    String imgUrl;
    String  studentName;

    public String getImgUrl() { return imgUrl; }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Boolean getIs_present() {
        return is_present;
    }

    public void setIs_present(Boolean is_present) {
        this.is_present = is_present;
    }
}
