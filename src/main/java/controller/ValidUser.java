package controller;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ValidUser {
    private static int attempts = 0;
    private static long lockoutTime = 0;
    public static String currentUser;

    public static boolean isLockedOut() {
        if (attempts >= 3) {
            long now = System.currentTimeMillis();
            if (now - lockoutTime >= 10 * 1000) {
                attempts = 0;
                return false;
            }
            return true;
        }
        return false;
    }

    public static long getRemainingLockTime() {
        long now = System.currentTimeMillis();
        return (10 * 1000 - (now - lockoutTime)) / 1000;
    }


    public static User validation(String username, String password){
        String File_path = "src/main/resources/users.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(File_path))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] userInfo = line.split(";",-1);

                String fileUsername = userInfo[0].trim();
                String filePassword = userInfo[1].trim();
                String fileRole = userInfo[2].trim();

                if (fileUsername.equals(username) && filePassword.equals(password)) {
                    switch (fileRole.toLowerCase()){
                        case "admin":
                            return new Admin(username,password);
                        case "receptionist":
                            return new Receptionist(username,password);
                        case "tutor":
                            return new Tutor(username,password);
                        case "student":
                            return new Student(username,password);
                        default:
                            return null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        attempts++;
        if (attempts == 3) {
            lockoutTime = System.currentTimeMillis();
        }
        return null;
    }

    public static void setCurrentUser(String Username) {
        currentUser = Username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
    public static int getRemainingAttempts() {
        return Math.max(0, 3 - attempts);

    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidIcOrPassport(String ic) {
        if (ic == null || ic.trim().isEmpty() || !ic.matches("\\d{12}")) {
            return false;
        }

        String dob = ic.substring(0,6);
        String year = dob.substring(0, 2);
        String month = dob.substring(2, 4);
        String day = dob.substring(4, 6);

        int mm = Integer.parseInt(month);
        int dd = Integer.parseInt(day);

        if (mm < 1 || mm > 12 || dd < 1 || dd > 31) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean isValidContact(String contact) {
        //9-11 malaysia number i think
        return contact != null && contact.matches("\\d{9,11}");
    }

    //to check column empty
    public static boolean isEmpty(String information) {
        return information == null || information.trim().isEmpty();
    }

    public static boolean isValidAge(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            // 120 maximum
            return age >= 0 && age <= 120;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^\\d{4,}$");
    }
}