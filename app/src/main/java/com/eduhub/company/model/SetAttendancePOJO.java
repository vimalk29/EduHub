package com.eduhub.company.model;

public class SetAttendancePOJO {
    String studentIdAtt;
    Boolean is_presentAtt;
    String  studentNameAtt;

    public String getStudentIdAtt() {
        return studentIdAtt;
    }

    public void setStudentIdAtt(String studentIdAtt) {
        this.studentIdAtt = studentIdAtt;
    }

    public Boolean getIs_presentAtt() {
        return is_presentAtt;
    }

    public void setIs_presentAtt(Boolean is_presentAtt) {
        this.is_presentAtt = is_presentAtt;
    }

    public String getStudentNameAtt() {
        return studentNameAtt;
    }

    public void setStudentNameAtt(String studentNameAtt) {
        this.studentNameAtt = studentNameAtt;
    }
}
