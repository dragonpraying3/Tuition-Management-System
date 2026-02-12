package model;

public class AssignmentModel {
    private String Username;
    private String subject1Levels;
    private String subject2Levels;
    private String subject3levels;

    public AssignmentModel(String Username, String subject1Levels, String subject2Levels, String subject3levels){
        this.Username=Username;
        this.subject1Levels=subject1Levels;
        this.subject2Levels=subject2Levels;
        this.subject3levels=subject3levels;
    }

    public AssignmentModel() {
        //if want to use setter must have this because cannot pass all argument at once
    }

    public String getUsername() {
        return Username;
    }
    public String getSubject1Levels() {
        return subject1Levels;
    }
    public String getSubject2Levels() {
        return subject2Levels;
    }
    public String getSubject3levels() {
        return subject3levels;
    }

    public void setUsername(String username) {
        Username = username;
    }
    public void setSubject1Levels(String subject1Levels) {
        this.subject1Levels = subject1Levels;
    }
    public void setSubject2Levels(String subject2Levels) {
        this.subject2Levels = subject2Levels;
    }
    public void setSubject3levels(String subject3levels) {
        this.subject3levels = subject3levels;
    }

    public String toString(){
        return Username+";"+subject1Levels+";"+subject2Levels+";"+subject3levels;
    }
}