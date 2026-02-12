package model;

public class Enrolment {
    private String username;
    private String MoE;
    private String subject1;
    private String subject1Tutor;
    private String subject2;
    private String subject2Tutor;
    private String subject3;
    private String subject3Tutor;

    public Enrolment() {
    }

    public Enrolment(String username, String moE, String subject1, String subject1Tutor, String subject2, String subject2Tutor, String subject3, String subject3Tutor) {
        this.username = username;
        MoE = moE;
        this.subject1 = subject1;
        this.subject1Tutor = subject1Tutor;
        this.subject2 = subject2;
        this.subject2Tutor = subject2Tutor;
        this.subject3 = subject3;
        this.subject3Tutor = subject3Tutor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject1Tutor() {
        return subject1Tutor;
    }

    public void setSubject1Tutor(String subject1Tutor) {
        this.subject1Tutor = subject1Tutor;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject2Tutor() {
        return subject2Tutor;
    }

    public void setSubject2Tutor(String subject2Tutor) {
        this.subject2Tutor = subject2Tutor;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getSubject3Tutor() {
        return subject3Tutor;
    }

    public void setSubject3Tutor(String subject3Tutor) {
        this.subject3Tutor = subject3Tutor;
    }

    public String getMoE() {
        return MoE;
    }

    public void setMoE(String moE) {
        MoE = moE;
    }

    public String toFileString(){
        return String.join(";",
                username,
                MoE,
                subject1 != null ? subject1 : "null",
                subject1Tutor,
                subject2 != null ? subject2 : "null",
                subject2Tutor,
                subject3 != null ? subject3 : "null",
                subject3Tutor
        );
    }

    public String getTutorBySubject(String subject) {
        if (subject != null) {
            if (subject.equalsIgnoreCase(subject1)) return subject1Tutor;
            if (subject.equalsIgnoreCase(subject2)) return subject2Tutor;
            if (subject.equalsIgnoreCase(subject3)) return subject3Tutor;
        }
        return "null";
    }





}
