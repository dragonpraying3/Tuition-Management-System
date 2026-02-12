package model;

public class Payment {
    private String Username;
    private String Subject;
    private String Date;
    private String PaymentMethod;
    private String Status;

    public Payment() {
    }

    public Payment(String username, String subject, String date, String paymentMethod, String status) {
        Username = username;
        Subject = subject;
        Date = date;
        PaymentMethod = paymentMethod;
        Status = status;
    }

    public String getUsername() {
        return Username;
    }
    public String getSubjects() {
        return Subject;
    }
    public String getDate() {
        return Date;
    }
    public String getPaymentMethod() {
        return PaymentMethod;
    }
    public String getStatus() {
        return Status;
    }

    public void setUsername(String username) {
        Username = username;
    }
    public void setSubjects(String subjects) {
        Subject = subjects;
    }
    public void setDate(String date) {
        Date = date;
    }
    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }
    public void setStatus(String status) {
        Status = status;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(Username).append(";")
                .append(String.join(";", Subject)).append(";")
                .append(Date).append(";")
                .append(PaymentMethod).append(";")
                .append(Status)
                .toString();
    }

}
