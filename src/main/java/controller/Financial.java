package controller;

//got time can do generating monthly income report

import model.Payment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controller.Registration.*;

public class Financial {

    public List<String[]> getPaidStudent() {
        //list<string[]> is store as [[],[],[]] method
        List<String[]> paidStudent=new ArrayList<>(); //store the JTable information

        List<String> studentData = Registration.readFile(StudentFile);
        List<String> paymentData=Registration.readFile(PaymentFile);
        List<String> chargesData=Registration.readFile(ClassInfoFile);
        List<String> enrollData=Registration.readFile(EnrolmentFile);

        for (String line : paymentData) {
            String[] parts = line.split(";");
            if (parts.length>=5 && parts[4].equalsIgnoreCase("paid")){
                String username=parts[0];
                String subject=parts[1];
                String date=parts[2];
                String payMethod=parts[3];
                String payStatus=parts[4];

                payStatus="Paid";

                String level="";
                String name="";
                for (String field:studentData){
                    String[] info=field.split(";");
                    if (info.length>=12 && info[0].equals(username)){
                        name=info[1];
                        level=info[10];

                        String tutorName="";
                        for (String column:enrollData){
                            String[] number=column.split(";");
                            if (number.length>=8 &&number[0].equals(username)){
                                for (int i=2;i<number.length-1; i+=2){
                                    if (number[i].equals(subject)){ //check the index field is equal to subject or not
                                        tutorName=number[i+1]; //if yes, then the tutor name index is i+1
                                        break;
                                    }
                                }
                            }
                        }

                        String charges="";
                        for (String data:chargesData){
                            String[] num=data.split(";");
                            if (num.length>=6 && num[0].equals(tutorName) && num[1].equals(subject)){
                                charges=num[4];
                            }
                        }
                        paidStudent.add(new String[]{username,name,subject,level,date,charges,payMethod,payStatus});
                    }
                }
            }

        }
        return paidStudent;
    }

    public int calcIncome(List<String[]> paidStudent){
        int total=0;
        for (String[] row:paidStudent){
            String charges=row[5];
            try{
                total+=Integer.parseInt(charges);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return total;
    }

    public int calcPeople(List<String[]> paidStudent){
        int human=0;
        for (String[] line:paidStudent){
            human+=1;
        }
        return human;
    }

    public static void updatePaymentStatus(Payment p) {
        List<String> paymentLines = readFile(PaymentFile);
        List<String> updatedLines = new ArrayList<>();

        for (String line : paymentLines) {
            String[] parts = line.split(";", -1);
            if (parts.length >= 5 &&
                    parts[0].equalsIgnoreCase(p.getUsername()) &&
                    parts[1].equalsIgnoreCase(p.getSubjects())){
                parts[4] = p.getStatus();
                String newLine = String.join(";", parts);
                updatedLines.add(newLine);
            } else {
                //keep default line
                updatedLines.add(line);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PaymentFile))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Payment> getAllPayment() {
        List<Payment> payments = new ArrayList<>();
        List<String> allPayment = Registration.readFile(PaymentFile);
        for (String line : allPayment) {
            String[] payPart = line.split(";", -1);
            if (payPart.length >= 5) {
                payments.add(new Payment(payPart[0],payPart[1],payPart[2],payPart[3],payPart[4]));
            }
        }
        return payments;
    }
}