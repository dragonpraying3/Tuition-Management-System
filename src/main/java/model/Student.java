package model;

public class Student extends User {

    private String Name;
    private String Ic;
    private String Age;
    private String DateOfBirth;
    private String Email;
    private String Contact;
    private String Address;
    private String Gender;
    private String Race;
    private String Level;
    private String StudyStatus;

    public Student () {
    }

    public Student(String username, String password) {
        super(username, password, "student");
    }

    public Student(String username, String password, String name, String ic, String age,
                   String dateOfBirth, String email, String contact, String address, String gender, String race,
                   String level,String studyStatus) {
        super(username, password, "student");
        Name = name;
        Ic = ic;
        Age = age;
        DateOfBirth = dateOfBirth;
        Email = email;
        Contact = contact;
        Address = address;
        Gender = gender;
        Race = race;
        Level = level;
        StudyStatus = studyStatus;
    }

    public Student(String username, String name, String ic, String age, String dateOfBirth,
                   String email, String contact, String address, String gender, String race,
                   String level,String studyStatus) {
        super(username);
        Name = name;
        Ic = ic;
        Age = age;
        DateOfBirth = dateOfBirth;
        Email = email;
        Contact = contact;
        Address = address;
        Gender = gender;
        Race = race;
        Level = level;
        StudyStatus = studyStatus;
    }

    public String toFileString() {
        return String.join(";",
                super.getUsername(),
                Name,
                Ic,
                Age,
                DateOfBirth,
                Email,
                Contact,
                Address,
                Gender,
                Race,
                Level,
                StudyStatus
        );
    }

    public String getName() {
        return Name;
    }
    public String getIc() {
        return Ic;
    }
    public String getEmail() {
        return Email;
    }
    public String getContact() {
        return Contact;
    }
    public String getLevel() {
        return Level;
    }
    public String getAddress() {
        return Address;
    }
    public String getGender() {
        return Gender;
    }
    public String getRace() {
        return Race;
    }
    public String getAge() {
        return Age;
    }
    public String getDateOfBirth() {
        return DateOfBirth;
    }
    public String getStudyStatus() {
        return StudyStatus;
    }

    public void setName(String name) {
        Name = name;
    }
    public void setIc(String ic) {
        Ic = ic;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setContact(String contact) {
        Contact = contact;
    }
    public void setLevel(String level) {
        Level = level;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public void setGender(String gender) {
        Gender = gender;
    }
    public void setRace(String race) {
        Race = race;
    }
    public void setAge(String age) {
        Age = age;
    }
    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }
    public void setStudyStatus(String studyStatus) {
        StudyStatus = studyStatus;
    }
}

