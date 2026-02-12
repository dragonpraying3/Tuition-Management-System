package model;

public class Admin extends User{
    private String FullName;
    private String DateOfBirth;
    private String Age;
    private String Gender;
    private String ContactNumber;
    private String Email;
    private String Race;
    private String IC;
    private String Address;
    private String Salary;
    private String WorkStatus;
    private String dateJoined;

    public Admin(String username,String password){
        super(username,password,"admin");
    }

    public Admin(String username,String password,String FullName,String DateOfBirth, String Age, String Gender,
                 String ContactNumber,String Email,String Race, String IC, String Address, String Salary, String WorkStatus,
                 String dateJoined){
        super(username,password,"admin");
        this.FullName=FullName;
        this.DateOfBirth=DateOfBirth;
        this.Age=Age;
        this.Gender=Gender;
        this.ContactNumber=ContactNumber;
        this.Email=Email;
        this.Race=Race;
        this.IC=IC;
        this.Address=Address;
        this.Salary=Salary;
        this.WorkStatus=WorkStatus;
        this.dateJoined=dateJoined;
    }

    public String getFullName(){return FullName;}
    public String getDateOfBirth(){return DateOfBirth;}
    public String getAge(){return Age;}
    public String getGender(){return Gender;}
    public String getContactNumber(){return ContactNumber;}
    public String getEmail(){return Email;}
    public String getRace(){return Race;}
    public String getIC(){return IC;}
    public String getAddress(){return Address;}
    public String getSalary(){return Salary;}
    public String getWorkStatus(){return WorkStatus;}
    public String getDateJoined(){return dateJoined;}

    public void setFullName(String fullName){this.FullName=fullName;}
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
    public void setSalary(String salary){this.Salary=salary;}
    public void setWorkStatus(String workStatus){this.WorkStatus=workStatus;}
    public void setDateJoined(String dateJoined){this.dateJoined=dateJoined;}

    public String toString(){
        return getUsername()+";"+FullName+";"+DateOfBirth+";"+Age+";"+Gender+";"+ContactNumber+
                ";"+Email+";"+Race+";"+IC+";"+Address+";"+Salary+";"+WorkStatus+";"+dateJoined;
    }

}
