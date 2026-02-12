package controller;

import model.AssignmentModel;
import model.Tutor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TutorController {

    final static String CLASS_INFO_FILE = "src/main/resources/classInfo.txt";

    public static List<Tutor> MatchedTutor(String subject, String level) {
        List<Tutor> allTutor = Registration.getAllTutor();
        List<Tutor> matchTutor = new ArrayList<>();
        Map<String, AssignmentModel> assignmentModelMap = Assignment.loadAssignmentData();

        for (Tutor tutor : allTutor) {
            if (tutor.getSubject1().equalsIgnoreCase(subject) ||
                    tutor.getSubject2().equalsIgnoreCase(subject) ||
                    tutor.getSubject3().equalsIgnoreCase(subject)) {

                if (isTutorAbleToTeach(tutor, subject, level, assignmentModelMap)) {
                    matchTutor.add(tutor);
                }
            }
        }
        return matchTutor;
    }

    public static String assignTutor(String subject, String level) {
        if (subject == null) return null;
        List<Tutor> tutors = MatchedTutor(subject, level);
        if (tutors.isEmpty()) return null;


        Random r = new Random();
        int index = r.nextInt(tutors.size());
        Tutor t = tutors.get(index);
        return t.getUsername();

    }

    public static List<String> getLevelForTutorSubject(Tutor tutor, String subject, AssignmentModel assignment) {

        List<String> level = new ArrayList<>();

        if (tutor.getSubject1().equalsIgnoreCase(subject)) {
            //Using arrays .stream because assignment.getSubject1Levels().split(",") return a arraylist
            level = Arrays.stream(assignment.getSubject1Levels().split(","))
                    //if old jdk must write .collect(Collectors.toList());
                    .toList();
        } else if (tutor.getSubject2().equalsIgnoreCase(subject)) {
            level = Arrays.stream(assignment.getSubject2Levels().split(","))
                    .toList();
        } else if (tutor.getSubject3().equalsIgnoreCase(subject)) {
            level = Arrays.stream(assignment.getSubject3levels().split(","))
                    .toList();
        }

        return level;
    }

    public static boolean isTutorAbleToTeach(Tutor tutor, String subject, String level, Map<String, AssignmentModel> assignmentModelMap) {
        AssignmentModel assignment = assignmentModelMap.get(tutor.getUsername());
        if (assignment == null) return false;

        List<String> allowedLevels = getLevelForTutorSubject(tutor, subject, assignment);
        return allowedLevels.contains(level);
    }

    public static int getSubjectFee(String tutorUsername, String subject) {
        try (BufferedReader br = new BufferedReader(new FileReader(CLASS_INFO_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    String fileTutor = parts[0].trim();
                    String fileSubject = parts[1].trim();
                    String fee = parts[4].trim();

                    if (fileTutor.equalsIgnoreCase(tutorUsername) && fileSubject.equalsIgnoreCase(subject)) {
                        return Integer.parseInt(fee);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return default if not found
        return 0;
    }

}


