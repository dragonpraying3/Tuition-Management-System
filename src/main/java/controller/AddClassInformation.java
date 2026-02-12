package controller;

import model.ClassInfo;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class AddClassInformation {

    static String ClassInfoFile = "src/main/resources/classinfo.txt";

    // Load all class entries
    public static List<String> loadClassesFromFile() {
        List<String> classes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ClassInfoFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!classes.contains(line)) {
                    classes.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return classes;
    }

    // Save new class
    public void saveToFile(ClassInfo ci) {
        String classLine = ci.toFileString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ClassInfoFile, true))) {
            writer.write(classLine);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Load subjects for current tutor from tutor.txt
    public List<String> loadTutorSubjects(String filename) {
        List<String> subjects = new ArrayList<>();
        String currentUser = ValidUser.getCurrentUser();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 11 && parts[0].equalsIgnoreCase(currentUser)) {
                    for (int i = 11; i < parts.length; i++) {
                        String subject = parts[i].trim();
                        if (!subject.equalsIgnoreCase("No") && !subject.isEmpty()) {
                            subjects.add(subject);
                        }
                    }
                    break; // found current user
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading tutor file: " + e.getMessage());
        }

        return subjects;
    }

    // Load form levels from assignment.txt
    public Map<String, Set<String>> loadTutorForms(String filename) {
        Map<String, Set<String>> tutorForms = new HashMap<>();
        String currentUser = ValidUser.getCurrentUser();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String tutorId = parts[0];
                Set<String> forms = new HashSet<>();

                if (!tutorId.equals(currentUser)) continue;

                for (int i = 1; i < parts.length; i++) {
                    if (!parts[i].equalsIgnoreCase("null")) {
                        for (String form : parts[i].split(" ")) {
                            forms.add(form.trim());
                        }
                    }
                }

                tutorForms.put(tutorId, forms);
            }
        } catch (IOException e) {
            System.out.println("Error reading assignment file: " + e.getMessage());
        }

        return tutorForms;
    }

    // Delete a class entry by full line match
    public void deleteClass(String className) {
        List<String> updatedClasses = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ClassInfoFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(className)) {
                    found = true;
                } else {
                    updatedClasses.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ClassInfoFile))) {
                for (String updatedLine : updatedClasses) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        } else {
            System.out.println("Class not found");
        }
    }

    // Validate 2-hour time slot
    public boolean ValidTimeRange(String timeRange) {
        String[] parts = timeRange.split("-");
        if (parts.length != 2) return false;

        try {
            String start = parts[0].trim();
            String end = parts[1].trim();

            int startMin = convertToMinutes(start);
            int endMin = convertToMinutes(end);
            int duration = endMin - startMin;

            return startMin < endMin && duration == 120;
        } catch (Exception e) {
            return false;
        }
    }

    // Convert time string to minutes
    private int convertToMinutes(String time) {
        time = time.trim().toLowerCase();
        String period = "";

        if (time.endsWith("am")) period = "am";
        else if (time.endsWith("pm")) period = "pm";

        time = time.replace("am", "").replace("pm", "").trim();
        String[] parts = time.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts.length > 1 ? parts[1].replaceAll("[^0-9]", "") : "0");

        if (period.equals("pm") && hour != 12) hour += 12;
        if (period.equals("am") && hour == 12) hour = 0;

        return hour * 60 + minute;
    }

    // Update class: find and replace matching subject + day + time
    public void updateClass(ClassInfo updatedClass) {
        String username = updatedClass.getUsername();
        String subject = updatedClass.getSubject();
        String time = updatedClass.getTime();
        String day = updatedClass.getDay();
        String toDelete = null;

        List<String> allClasses = loadClassesFromFile();
        for (String line : allClasses) {
            String[] parts = line.split(";");
            if (parts.length >= 6 &&
                    parts[0].equalsIgnoreCase(updatedClass.getUsername()) &&
                    parts[1].equalsIgnoreCase(subject)) {
                toDelete = line;

                break;
            }
        }

        if (toDelete != null) {
            deleteClass(toDelete);
        }
        saveToFile(updatedClass);
    }


    // Validate ClassInfo for Update
    public boolean validClass(ClassInfo ci) {
        String currentUser = ValidUser.getCurrentUser().trim();
        String subject = ci.getSubject().trim();
        String day = ci.getDay().trim();
        String time = ci.getTime().trim();
        String room = ci.getRoom().trim();

        // Check if tutor is assigned to subject
        List<String> tutorSubjects = loadTutorSubjects("src/main/resources/tutor.txt");
        if (!tutorSubjects.contains(subject)) {
            JOptionPane.showMessageDialog(null,
                    "You are not assigned to teach this subject.",
                    "Invalid Subject", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check for duplicates
        List<String> allClasses = loadClassesFromFile();
        for (String line : allClasses) {
            String[] parts = line.split(";");
            if (parts.length >= 6) {
                String userFromFile = parts[0].trim();
                String subjectFromFile = parts[1].trim();
                String timeFromFile = parts[2].trim(); // correct: 1:00pm - 3:00pm
                String dayFromFile = parts[3].trim();  // correct: Monday, Tuesday, etc.
                String roomFromFile = parts[5].trim();

                if (dayFromFile.equalsIgnoreCase(day) &&
                        timeFromFile.equalsIgnoreCase(time) &&
                        roomFromFile.equalsIgnoreCase(room)) {
                    return false;
                }
            }
        }

        return true;
    }

    // read assigment.txt for tutor has assigned to the forms
    public boolean isTutorAssignedForm(String filename) {
        String currentUser = ValidUser.getCurrentUser();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 1 && parts[0].equalsIgnoreCase(currentUser)) {
                    // Check if there is at least one form assigned
                    for (int i = 1; i < parts.length; i++) {
                        if (!parts[i].trim().equalsIgnoreCase("null") &&
                                !parts[i].trim().isEmpty()) {
                            return true;
                        }
                    }
                    return false; // tutor exists but no form
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading assignment.txt: " + e.getMessage());
        }

        return false; // tutor not found
    }

    public List<String> loadSubjectsFromClassInfo() {
        List<String> subjects = new ArrayList<>();
        List<String> allClasses = loadClassesFromFile();
        String currentUser = ValidUser.getCurrentUser();

        for (String line : allClasses) {
            String[] parts = line.split(";");
            if (parts[0].equalsIgnoreCase(currentUser)) {
                String subject = parts[1];
                if (!subjects.contains(subject)) {
                    subjects.add(subject);
                }
            }
        }

        return subjects;
    }

    //valid for addClass
    public static boolean checkDupe(ClassInfo ci) {
        String tutorUsername = ci.getUsername();
        String subject = ci.getSubject();
        String day = ci.getDay();
        String time = ci.getTime();
        String room = ci.getRoom();

        for (String line : loadClassesFromFile()) {
            String[] parts = line.split(";");
            if (parts.length >= 6) {
                String userFromFile = parts[0].trim();
                String subjectFromFile = parts[1].trim();
                String timeFromFile = parts[2].trim(); // correct: 1:00pm - 3:00pm
                String dayFromFile = parts[3].trim();  // correct: Monday, Tuesday, etc.
                String roomFromFile = parts[5].trim();

                if (userFromFile.equalsIgnoreCase(tutorUsername) &&
                        subjectFromFile.equalsIgnoreCase(subject)) {
                    return true; // duplicate subject for same tutor
                }

                if (userFromFile.equalsIgnoreCase(tutorUsername) &&
                        dayFromFile.equalsIgnoreCase(day) &&
                        timeFromFile.equalsIgnoreCase(time) &&
                        roomFromFile.equalsIgnoreCase(room)) {
                    return true;
                }

            }
        }
        return false;
    }
}

