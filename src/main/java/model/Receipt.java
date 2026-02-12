package model;

public class Receipt {
    private String ReceiptNo;
    private String Date;
    private String PaymentMethod;
    private String StudentName;
    private String StudentLevel;
    private String EnrolmentMonth;
    private String Subject1, Subject2, Subject3;
    private String Tutor1, Tutor2, Tutor3;
    private String Amount1, Amount2, Amount3;
    private String TotalAmount;

    public Receipt() {
    }

    public Receipt(String receiptNo, String date, String paymentMethod, String studentName, String studentLevel,
                   String enrolmentMonth, String subject1, String subject2, String subject3, String tutor1,
                   String tutor2, String tutor3, String amount1, String amount2, String amount3, String totalAmount) {
        this.ReceiptNo = receiptNo;
        this.Date = date;
        this.PaymentMethod = paymentMethod;
        this.StudentName = studentName;
        this.StudentLevel = studentLevel;
        this.EnrolmentMonth = enrolmentMonth;
        this.Subject1 = subject1;
        this.Subject2 = subject2;
        this.Subject3 = subject3;
        this.Tutor1 = tutor1;
        this.Tutor2 = tutor2;
        this.Tutor3 = tutor3;
        this.Amount1 = amount1;
        this.Amount2 = amount2;
        this.Amount3 = amount3;
        this.TotalAmount = totalAmount;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public String getDate() {
        return Date;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getStudentLevel() {
        return StudentLevel;
    }

    public String getEnrolmentMonth() {
        return EnrolmentMonth;
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

    public String getTutor1() {
        return Tutor1;
    }

    public String getTutor2() {
        return Tutor2;
    }

    public String getTutor3() {
        return Tutor3;
    }

    public String getAmount1() {
        return Amount1;
    }

    public String getAmount2() {
        return Amount2;
    }

    public String getAmount3() {
        return Amount3;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public void setStudentLevel(String studentLevel) {
        StudentLevel = studentLevel;
    }

    public void setEnrolmentMonth(String enrolmentMonth) {
        EnrolmentMonth = enrolmentMonth;
    }

    public void setSubject1(String subject1) {
        Subject1 = subject1;
    }

    public void setSubject2(String subject2) {
        Subject2 = subject2;
    }

    public void setSubject3(String subject3) {
        Subject3 = subject3;
    }

    public void setTutor1(String tutor1) {
        Tutor1 = tutor1;
    }

    public void setTutor2(String tutor2) {
        Tutor2 = tutor2;
    }

    public void setTutor3(String tutor3) {
        Tutor3 = tutor3;
    }

    public void setAmount1(String amount1) {
        Amount1 = amount1;
    }

    public void setAmount2(String amount2) {
        Amount2 = amount2;
    }

    public void setAmount3(String amount3) {
        Amount3 = amount3;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
