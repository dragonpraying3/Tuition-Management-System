package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EnrolledStudents {

    public static List<String[]> getEnrolledData() {
        List<String[]> enrolledList = new ArrayList<>();
        String tutor = ValidUser.getCurrentUser();
        String enrolmentPath = "src/main/resources/Enrolment.txt";
        String studentPath = "src/main/resources/Student.txt";

        // username fullName form
        Map<String, String[]> studentMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(studentPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 11) {
                    String username = parts[0].trim();
                    String fullName = parts[1].trim();
                    String form = parts[10].trim();
                    studentMap.put(username, new String[]{fullName, form});
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read enrolment.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(enrolmentPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 4) continue;

                String username = parts[0].trim();

                for (int i = 2; i < parts.length - 1; i += 2) {
                    String subject = parts[i].trim();
                    String assignedTutor = parts[i + 1].trim();

                    if (assignedTutor.equals(tutor)) {
                        String[] details = studentMap.get(username);
                        if (details != null) {
                            String fullName =  details[0];
                            String form = details[1];
                            enrolledList.add(new String[]{fullName, subject, form});
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return enrolledList;
    }
}
