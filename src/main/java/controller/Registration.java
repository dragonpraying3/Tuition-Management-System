package controller;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Registration {

    public static String AdminFile = "src/main/resources/admin.txt";
    public static String StudentFile = "src/main/resources/student.txt";
    public static String TutorFile = "src/main/resources/tutor.txt";
    public static String ReceptionistFile = "src/main/resources/receptionist.txt";
    public static String AccountFile = "src/main/resources/users.txt";
    public static String AssignmentFile = "src/main/resources/assignment.txt";
    static String PaymentFile = "src/main/resources/payment.txt";
    static String ClassInfoFile = "src/main/resources/classInfo.txt";
    static String EnrolmentFile = "src/main/resources/enrolment.txt";

    public static List<String> readFile(String FilePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public static void writeFile(String filename, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static char[] PasswordGenerator() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String num = "0123456789";
        String combination = upper + lower + num;
        int len = 8;
        char[] password = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            password[i] = combination.charAt(r.nextInt(combination.length()));
        }
        return password;
    }

    public static String RegisterUser(String username, String password, String role, String file1, String file2, String UserData) {
        List<String> accountData = readFile(AccountFile);

        for (String line : accountData) {
            String[] parts = line.split(";");
            if (parts.length > 0 && parts[0].equalsIgnoreCase(username)) {
                return "Error: Username already exists.";
            }
        }
        try (BufferedWriter UserWrite = new BufferedWriter(new FileWriter(file1, true))) {
            UserWrite.write(UserData);
            UserWrite.newLine();
        } catch (IOException e) {
            return "Error writing to user data file.";
        }
        try (BufferedWriter AccountWrite = new BufferedWriter(new FileWriter(file2, true))) {
            AccountWrite.write(username + ";" + password + ";" + role);
            AccountWrite.newLine();
        } catch (IOException e) {
            return "Error writing to user file.";
        }
        return "Success";
    }

    public static String RegisterTutor(Tutor tutor) {
        String passwordStr = tutor.getPassword(); //use the password that generated

        String UserData = tutor.getUsername() + ";" + tutor.getFullName() + ";" + tutor.getDateOfBirth() + ";" + tutor.getAge()
                + ";" + tutor.getGender() + ";" + tutor.getContactNumber() + ";" + tutor.getEmail() + ";" +
                tutor.getRace() + ";" + tutor.getIC() + ";" + tutor.getAddress() + ";" +
                tutor.getQualification() + ";" + tutor.getSubject1() + ";" + tutor.getSubject2() + ";" + tutor.getSubject3();
        return RegisterUser(tutor.getUsername(), passwordStr, "tutor", TutorFile, AccountFile, UserData);
    }

    //copy the tutor username to assignmentFile
    public static String CopyTutorUsername(String username) {
        try (BufferedWriter CopyUsername = new BufferedWriter(new FileWriter(AssignmentFile, true))) {
            CopyUsername.write(username + ";");
            CopyUsername.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Success";
    }

    public static String RegisterReceptionist(Receptionist receptionist) {
        String passwordStr = receptionist.getPassword();

        String UserData = receptionist.getUsername() + ";" + receptionist.getFullName() + ";" + receptionist.getDateOfBirth() +
                ";" + receptionist.getAge() + ";" + receptionist.getGender() + ";" + receptionist.getContactNumber() +
                ";" + receptionist.getEmail() + ";" + receptionist.getRace() + ";" + receptionist.getIC() +
                ";" + receptionist.getAddress() + ";" + receptionist.getSalary() + ";" + receptionist.getWorkingShift() + ";"
                + receptionist.getWorkStatus();

        return RegisterUser(receptionist.getUsername(), passwordStr, "receptionist", ReceptionistFile, AccountFile, UserData);
    }

    public static String DeleteUser(String usernameDelete, String accountFile, String userFile) {
        boolean found = false;

        //delete from account file
        List<String> accountData = readFile(accountFile);
        List<String> updateAccount = new ArrayList<>();
        for (String line : accountData) {
            String[] parts = line.split(";");
            if (!parts[0].equalsIgnoreCase(usernameDelete)) {
                updateAccount.add(line);
            } else {
                found = true;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountFile))) {
            for (String line : updateAccount) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            return "Error writing account file.";
        }

        //delete from user file
        List<String> UserData = readFile(userFile);
        List<String> updateUser = new ArrayList<>();
        for (String line : UserData) {
            String[] parts = line.split(";");
            if (!parts[0].equalsIgnoreCase(usernameDelete)) {
                updateUser.add(line);
            } else {
                found = true;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (String line : updateUser) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            return "Error writing User File.";
        }
        if (found) {
            return "Success";
        } else {
            return "User not found";
        }
    }

    public static String CopyDeleting(String DeleteUsername) {
        boolean found = false;
        List<String> AssData = readFile(AssignmentFile);
        List<String> updateAss = new ArrayList<>();
        for (String line : AssData) {
            String[] parts = line.split(";");
            if (!parts[0].equalsIgnoreCase(DeleteUsername)) {
                updateAss.add(line);
            } else {
                found = true;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AssignmentFile))) {
            for (String line : updateAss) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            return "Error writing User File.";
        }
        if (found) {
            return "Success";
        } else {
            return "User not found";
        }
    }

    public static String DeleteTutor(String username) {
        return DeleteUser(username, AccountFile, TutorFile);
    }

    public static String DeleteReceptionist(String username) {
        return DeleteUser(username, AccountFile, ReceptionistFile);
    }

    //this method is to get tutor info to have update after register +loop through
    public static List<Tutor> getAllTutor() {
        List<Tutor> tutors = new ArrayList<>(); //the hidden list that combine username,password,info of tutor
        List<String> lines = readFile(TutorFile);
        List<String> userLines = readFile(AccountFile);
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length >= 14) {
                String username = parts[0];

                //find the tally password
                String password = "";
                for (String userLine : userLines) {
                    String[] userParts = userLine.split(";");
                    if (userParts.length >= 2 && userParts[0].equals(username) && userParts[2].equalsIgnoreCase("tutor")) {
                        password = userParts[1];
                        break;
                    }
                }
                Tutor tutor = new Tutor(username, password, parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9],
                        parts[10], parts[11], parts[12], parts[13]);
                tutors.add(tutor);
            }
        }
        return tutors;
    }

    public static List<Receptionist> getAllReceptionist() {
        List<Receptionist> receptionists = new ArrayList<>();
        List<String> lines = readFile(ReceptionistFile);
        List<String> userLines = readFile(AccountFile);
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length >= 13) {
                String username = parts[0];

                //find the tally password
                String password = "";
                for (String userLine : userLines) {
                    String[] userParts = userLine.split(";");
                    if (userParts.length >= 2 && userParts[0].equals(username) && userParts[2].equalsIgnoreCase("receptionist")) {
                        password = userParts[1];
                        break;
                    }
                }

                Receptionist receptionist = new Receptionist(username, password, parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9],
                        parts[10], parts[11], parts[12]);
                receptionists.add(receptionist);
            }
        }
        return receptionists;
    }

    public static String RegisterStudent(Student s) {
        String stuInfo = s.toFileString();
        return RegisterUser(s.getUsername(), s.getPassword(), "student", StudentFile, AccountFile, stuInfo);
    }

    public static boolean saveEnrolment(Enrolment enrolment) {
        String studentEnrolment = enrolment.toFileString();
        try (BufferedWriter UserWrite = new BufferedWriter(new FileWriter(EnrolmentFile, true))) {
            UserWrite.write(studentEnrolment);
            UserWrite.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static List<Enrolment> loadEnrolment() {
        List<String> allEnrolment = readFile(EnrolmentFile);
        List<Enrolment> enrolments = new ArrayList<>();
        for (String line : allEnrolment) {
            String[] stuPart = line.split(";", -1);
            if (stuPart.length >= 8) {
                String username = stuPart[0];
                String month = stuPart[1];
                String subject1 = stuPart[2].equalsIgnoreCase("null") || ValidUser.isEmpty(stuPart[2]) ? null : stuPart[2];
                String subject1Tutor = stuPart[3].equalsIgnoreCase("null") || ValidUser.isEmpty(stuPart[3]) ? null : stuPart[3];
                String subject2 = stuPart[4].equalsIgnoreCase("null") || ValidUser.isEmpty(stuPart[4]) ? null : stuPart[4];
                String subject2Tutor = stuPart[5].equalsIgnoreCase("null") || ValidUser.isEmpty(stuPart[5]) ? null : stuPart[5];
                String subject3 = stuPart[6].equalsIgnoreCase("null") || ValidUser.isEmpty(stuPart[6]) ? null : stuPart[6];
                String subject3Tutor = stuPart[7].equalsIgnoreCase("null") || ValidUser.isEmpty(stuPart[7]) ? null : stuPart[7];

                Enrolment e = new Enrolment(username, month, subject1, subject1Tutor, subject2, subject2Tutor, subject3, subject3Tutor);
                enrolments.add(e);
            }
        }
        return enrolments;
    }

    public static boolean UpdateStudentEnrollmentAndStatus(Student s, Enrolment enrol) {
        List<String> lines = readFile(Registration.StudentFile);
        List<String> enrolLines = readFile(Registration.EnrolmentFile);

        // this is the line will write in to file
        List<String> updatedLines = new ArrayList<>();
        List<String> updatedEnrolLines = new ArrayList<>();

        // student file
        for (String line : lines) {
            String[] parts = line.split(";", -1);
            if (parts.length == 12 && parts[1].equalsIgnoreCase(s.getName())) {
                String newLine = s.toFileString();
                updatedLines.add(newLine);
            } else {
                //keep default line
                updatedLines.add(line);
            }
        }

        //enrolment file
        for (String line : enrolLines) {
            String[] parts = line.split(";", -1);
            if (parts.length >= 8 && parts[0].equalsIgnoreCase(enrol.getUsername())) {
                String newLine = enrol.toFileString();
                updatedEnrolLines.add(newLine);
            } else {
                //keep default line
                updatedEnrolLines.add(line);
            }
        }

        // write student file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Registration.StudentFile))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write enrolment file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Registration.EnrolmentFile))) {
            for (String newline : updatedEnrolLines) {
                bw.write(newline);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    public static List<Student> loadStudentFromFile() {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Registration.StudentFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] stuPart = line.split(";", -1);
                if (stuPart.length == 12) {
                    String username = stuPart[0];
                    String name = stuPart[1];
                    String ic = stuPart[2];
                    String age = stuPart[3];
                    String dob = stuPart[4];
                    String email = stuPart[5];
                    String contact = stuPart[6];
                    String address = stuPart[7];
                    String gender = stuPart[8];
                    String race = stuPart[9];
                    String level = stuPart[10];
                    String studyStat = stuPart[11];

                    Student s = new Student(username, name, ic, age, dob, email, contact, address, gender, race, level, studyStat);
                    students.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static String DeleteStudent(String username) {
        String result = DeleteUser(username, AccountFile, StudentFile);
        if (result.equals("Success")) {
            deleteEnrolmentByStudent(username);
            deletePaymentByStudent(username);
        }
        return result;
    }

    public static void deleteEnrolmentByStudent(String username) {
        List<String> enrolments = readFile(EnrolmentFile);
        List<String> updated = new ArrayList<>();
        for (String line : enrolments) {
            String[] parts = line.split(";");
            if (!parts[0].equalsIgnoreCase(username)) {
                updated.add(line);
            }
        }
        writeFile(EnrolmentFile, updated);
    }

    public static void deletePaymentByStudent(String username) {
        List<String> payments = readFile(PaymentFile);
        List<String> updated = new ArrayList<>();
        for (String line : payments) {
            String[] parts = line.split(";");
            if (!parts[0].equalsIgnoreCase(username)) {
                updated.add(line);
            }
        }
        writeFile(PaymentFile, updated);
    }

    public static List<Admin> getAllAdmin() {
        List<Admin> admins = new ArrayList<>();
        List<String> lines = readFile(AdminFile);
        List<String> userLines = readFile(AccountFile);
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length >= 12) {
                String username = parts[0];

                String password = "";
                for (String info : userLines) {
                    String[] field = info.split(";");
                    if (field.length >= 3 && field[0].equals(username) && field[2].equalsIgnoreCase("admin")) {
                        password = field[1];
                        break;
                    }
                }
                Admin admin = new Admin(username, password, parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9],
                        parts[10], parts[11], parts[12]);
                admins.add(admin);
            }
        }
        return admins;
    }

    public static List<Account> getAllAccount() {

        List<Account> accounts = new ArrayList<>();
        List<String> userLines = readFile(AccountFile);

        String username = "";
        String password = "";
        String role = "";
        for (String line : userLines) {
            String[] parts = line.split(";");
            if (parts.length >= 3) {
                username = parts[0];
                password = parts[1];
                role = parts[2];
                Account account = new Account(username, password, role);
                accounts.add(account);
            }
        }
        return accounts;
    }

    public static List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        List<String> lines = readFile(StudentFile);
        List<String> userLines = readFile(AccountFile);

        for (String line : lines) {
            String[] parts = line.split(";", -1);
            if (parts.length == 12) {
                String username = parts[0];

                // Find matching password from users.txt
                String password = "";
                for (String userLine : userLines) {
                    String[] userParts = userLine.split(";");
                    if (userParts.length >= 3 && userParts[0].equalsIgnoreCase(username) && userParts[2].equalsIgnoreCase("student")) {
                        password = userParts[1];
                        break;
                    }
                }

                Student student = new Student(username, password,
                        parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7], parts[8],
                        parts[9], parts[10], parts[11]
                );
                students.add(student);
            }
        }

        return students;
    }
}