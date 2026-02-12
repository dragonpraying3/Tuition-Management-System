package model;

public class Receptionist extends User{
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
    private String WorkingShift;
    private String WorkStatus;

    public Receptionist(String username,String password){
        super(username,password,"receptionist");
    }

    public Receptionist(String username,String password,String FullName,String DateOfBirth,String Age,String Gender,
                        String ContactNumber, String Email,String Race, String IC,String Address,String Salary,
                        String WorkingShift,String WorkStatus){
        super(username,password,"receptionist");
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
        this.WorkingShift=WorkingShift;
        this.WorkStatus=WorkStatus;
    }

    public String getFullName() {return FullName;}
    public String getDateOfBirth() {return DateOfBirth;}
    public String getAge() {return Age;}
    public String getGender() {return Gender;}
    public String getContactNumber() {return ContactNumber;}
    public String getEmail() {return Email;}
    public String getRace() {return Race;}
    public String getIC() {return IC;}
    public String getAddress() {return Address;}
    public String getSalary() {return Salary;}
    public String getWorkingShift() {return WorkingShift;}
    public String getWorkStatus() {return WorkStatus;}

    public void setFullName(String fullName) { this.FullName = fullName; }
    public void setDateOfBirth(String dateOfBirth) { this.DateOfBirth = dateOfBirth; }
    public void setAge(String age) { this.Age = age; }
    public void setGender(String gender) { this.Gender = gender; }
    public void setContactNumber(String contactNumber) { this.ContactNumber = contactNumber; }
    public void setEmail(String email) { this.Email = email; }
    public void setRace(String race) { this.Race = race; }
    public void setIC(String IC) { this.IC = IC; }
    public void setAddress(String address) { this.Address = address; }
    public void setSalary(String salary) { this.Salary = salary; }
    public void setWorkingShift(String workingShift) { this.WorkingShift = workingShift; }
    public void setWorkStatus(String workStatus) { this.WorkStatus = workStatus; }


    @Override
    public String toString() {
        return super.getUsername() + ";" +
               this.FullName + ";" +
               this.DateOfBirth + ";" +
               this.Age + ";" +
               this.Gender + ";" +
               this.ContactNumber + ";" +
               this.Email + ";" +
               this.Race + ";" +
               this.IC + ";" +
               this.Address + ";" +
               this.Salary + ";" +
               this.WorkingShift + ";" +
               this.WorkStatus;
    }
}

