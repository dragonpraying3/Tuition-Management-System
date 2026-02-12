package model;


public class Tutor extends User{

    private String FullName;
    private String DateOfBirth;
    private String Age;
    private String Gender;
    private String ContactNumber;
    private String Email;
    private String Race;
    private String IC;
    private String Address;
    private String Qualification;
    private String Subject1;
    private String Subject2;
    private String Subject3;

    public Tutor(String username,String password){
        super(username,password,"tutor");
    }

    public Tutor(String username,String password,String FullName,String DateOfBirth,String Age,String Gender,String ContactNumber,
                 String Email,String Race, String IC,String Address,String Qualification,
                 String Subject1,String Subject2,String Subject3){
        super(username,password,"tutor");
        this.FullName=FullName;
        this.DateOfBirth=DateOfBirth;
        this.Age=Age;
        this.Gender=Gender;
        this.ContactNumber=ContactNumber;
        this.Email=Email;
        this.Race=Race;
        this.IC=IC;
        this.Address=Address;
        this.Qualification=Qualification;
        this.Subject1=Subject1;
        this.Subject2=Subject2;
        this.Subject3=Subject3;
    }

    public String getFullName() {
        return FullName;
    }
    public String getDateOfBirth() { return DateOfBirth; }
    public String getAge() { return Age; }
    public String getGender() { return Gender; }
    public String getContactNumber() { return ContactNumber; }
    public String getEmail() {
        return Email;
    }
    public String getRace() {
        return Race;
    }
    public String getIC() { return IC; }
    public String getAddress() {
        return Address;
    }
    public String getQualification() {
        return Qualification;
    }
    public String getSubject1() {
        return Subject1;
    }
    public String getSubject2() {
        return Subject2;
    }
    public String getSubject3() {
        return Subject3;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.DateOfBirth = dateOfBirth;
    }
    public void setAge(String age) {
        this.Age = age;
    }
    public void setGender(String gender) {
        this.Gender = gender;
    }
    public void setContactNumber(String contactNumber) {
        this.ContactNumber = contactNumber;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    public void setRace(String race) {
        this.Race = race;
    }
    public void setIC(String ic) {
        this.IC = ic;
    }
    public void setAddress(String address) {
        this.Address = address;
    }
    public void setQualification(String qualification) {
        this.Qualification = qualification;
    }
    public void setSubject1(String subject1) {
        this.Subject1 = subject1;
    }
    public void setSubject2(String subject2) {
        this.Subject2 = subject2;
    }
    public void setSubject3(String subject3) {
        this.Subject3 = subject3;
    }

    public String toString(){
        return getUsername()+";"+FullName+";"+DateOfBirth+";"+Age+";"+Gender+";"+ContactNumber+";"+Email+";"+Race+
                ";"+IC+";"+Address+";"+Qualification+";"+Subject1+";"+Subject2+";"+Subject3;
    }

}