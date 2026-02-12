package controller;

import model.AssignmentModel;
import model.Tutor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static controller.Registration.AssignmentFile;
import static controller.Registration.TutorFile;

public class Assignment {
    //assign level to new tutor
    public static String assignTutor(String tutorUsername, Map<String, List<String>> selectedLevels) {
        if (!HasAssignment(tutorUsername)) {
            //can assign the new tutor
            List<Tutor> allTutor=Registration.getAllTutor();
            List<String> assignmentData = Registration.readFile(AssignmentFile);
            List<String> updated = new ArrayList<>(); //the update list
            boolean found = false;

            for (Tutor tutor: allTutor) {
                if (tutor.getUsername().equals(tutorUsername)) { //use getter to loop through the method
                    String qualification = tutor.getQualification();
                    String subject1 = tutor.getSubject1();
                    String subject2 = tutor.getSubject2();
                    String subject3 = tutor.getSubject3();
                    found = true;

                    //valid the qualification can teach what form of student
                    //return a map that has the list of can teach level
                    Map<String, List<String>> validLevels = validAssignment(qualification, subject1, subject2, subject3);

                    StringBuilder errorMessage=new StringBuilder();
                    //validate:the selectedLevel cannot exclude from the validLevels
                    for (Map.Entry<String, List<String>> entry : selectedLevels.entrySet()) {
                        String subject = entry.getKey();  //take the subject name from map (key)
                        List<String> chosenForms = entry.getValue(); //take the forms name from map (value)

                        //if subject is No, tutor cannot assign to teach it
                        if (!validLevels.containsKey(subject)) {
                            errorMessage.append("-Cannot assign levels because Subject is ").append(subject).append(".\n");
                            continue; //skip checking forms since subject invalid
                        }

                        for (String form : chosenForms) {
                            if (!validLevels.get(subject).contains(form)) { //.get() is getting the [value] of the subject ,find the tally
                                errorMessage.append("-Cannot assign ").append(form).append(" for subject ").append(subject).append(" due to validation of Qualification.\n");
                            }
                        }
                    }
                    if (errorMessage.length()>0){
                        return errorMessage.toString(); //return full errorMessage
                    }

                    //join the string of the form selected after done select the level
                    String subject1Levels="";
                    if (!subject1.equals("No")){
                        if (selectedLevels.containsKey(subject1)){ // Check if subject1 has selected levels, then join them into a string like "Form 1,Form 2"
                            subject1Levels=String.join(",",selectedLevels.get(subject1));
                        }
                    }
                    String subject2Levels="";
                    if (!subject2.equals("No")){
                        if (selectedLevels.containsKey(subject2)){
                            subject2Levels=String.join(",",selectedLevels.get(subject2));
                        }
                    }
                    String subject3Levels="";
                    if (!subject3.equals("No")){
                        if(selectedLevels.containsKey(subject3)){
                            subject3Levels=String.join(",",selectedLevels.get(subject3));
                        }
                    }

                    //use to String method in assignment model
                    AssignmentModel newAssign=new AssignmentModel(tutorUsername,subject1Levels,subject2Levels,subject3Levels);
                    String updatedLine=newAssign.toString();
                    //update the assign value to file
                    boolean alreadyUpdated=false;
                    for (String lineOld : assignmentData) {
                        String[] field = lineOld.split(";");
                        if (field[0].equals(tutorUsername)) {
                            if (!alreadyUpdated){
                                updated.add(updatedLine);
                                alreadyUpdated=true;
                            }
                        } else {
                            updated.add(lineOld); //no update then remain origin
                        }
                    }
                    break;
                }
            }
            //write into file
            if (found) {
                Registration.writeFile(AssignmentFile, updated);
                return "Success";
            }
        }
        return "Fail";
    }

    //update old tutor for changing subject / level
    public static String updateTutor(String tutorUsername,Map<String,List<String>> selectedItem) {
        if (!HasAssignment(tutorUsername)) { //to ensure only can assign the old teacher
            return "Tutor No Assignment"; //cannot update the teacher because no assignment field, it must go to assign first
        }
        List<String> tutorData = Registration.readFile(TutorFile);
        List<String> assignmentData = Registration.readFile(AssignmentFile);
        List<String> updatedTutorLine = new ArrayList<>();
        List<String> updatedAssignmentLine = new ArrayList<>();

        //loop through the file to find tally username
        //this method has used before in Registration class
        List<Tutor> tutors = Registration.getAllTutor();

        Tutor foundTutor = null;
        for (Tutor tutor : tutors) {
            //if the tutor is founded ,then the data will store in the variable 'foundTutor'
            if (tutor.getUsername().equalsIgnoreCase(tutorUsername)) {
                foundTutor = tutor;
                break;
            }
        }
        if (foundTutor == null) {
            return "Tutor not found"; //tutor not found
        }

        //get the new subject
        List<String> newSubject=new ArrayList<>(selectedItem.keySet());
        String subject1="No",subject2="No",subject3="No"; //set no first then get the index of the subject and found is tally with which one
        if (newSubject.size()>=1){ //size =the elements in the list
            subject1=newSubject.get(0); //if the size is only one ,then it is get to the subject 1
        }
        if (newSubject.size()>=2){
            subject2=newSubject.get(1);
        }
        if (newSubject.size()>=3){
            subject3=newSubject.get(2);
        }

        //using setter method
        foundTutor.setSubject1(subject1); //changing value here
        foundTutor.setSubject2(subject2);
        foundTutor.setSubject3(subject3);

        //validate new assignment
        StringBuilder errorMessage=new StringBuilder();
        String qualification = foundTutor.getQualification(); //get the qualification of the tally tutor
        Map<String, List<String>> validLevels = validAssignment(qualification, subject1, subject2, subject3);
        for (Map.Entry<String, List<String>> entry : selectedItem.entrySet()) {
            String subject = entry.getKey(); //the subject key enter by user
            List<String> forms = entry.getValue();

            if (!validLevels.containsKey(subject)) {
                errorMessage.append("-Cannot assign levels because Subject is ").append(subject).append(".\n");
                continue; //skip checking forms since subject invalid
            }
            for (String form : forms) {
                //get(subject) is user subject key, contain(form) is check got any non assignable form or not
                if (!validLevels.get(subject).contains(form)) { //check the qualification can teach the form student or not
                    errorMessage.append("-Cannot assign ").append(form).append(" for subject ").append(subject).append(" due to validation of Qualification.\n");
                }
            }
        }
        if (errorMessage.length()>0){
            return errorMessage.toString(); //return full errorMessage
        }

        AssignmentModel assign=new AssignmentModel(); //because have public method so no need add 4 arguments
        assign.setUsername(tutorUsername);

        //using setter method
        //joining become string in case having more form level
        if (selectedItem.containsKey(subject1)) {
            assign.setSubject1Levels(String.join(",", selectedItem.get(subject1)));
        }
        if (selectedItem.containsKey(subject2)) {
            assign.setSubject2Levels(String.join(",",selectedItem.get(subject2)));
        }
        if (selectedItem.containsKey(subject3)) {
            assign.setSubject3levels(String.join(",",selectedItem.get(subject3)));
        }

        //update to assignment file
        boolean assignmentUpdated=false;
        for (String line:assignmentData) {
            String[] parts = line.split(";");
            if (parts[0].equalsIgnoreCase(tutorUsername)) {
                updatedAssignmentLine.add(assign.toString());
                assignmentUpdated = true; //success updated
            } else {
                updatedAssignmentLine.add(line);
            }
        }
        if(!assignmentUpdated) {
            updatedAssignmentLine.add(assign.toString());
        }

        //update tutor subjects
        for (String line:tutorData) {
            String[] parts = line.split(";");
            if (parts[0].equalsIgnoreCase(tutorUsername)) {
                updatedTutorLine.add(foundTutor.toString());
            } else {
                updatedTutorLine.add(line);
            }
        }
        Registration.writeFile(AssignmentFile,updatedAssignmentLine);
        Registration.writeFile(TutorFile,updatedTutorLine);
        return "Success";
        }

    //check whether the tutor already assign level
    public static boolean HasAssignment(String tutorUsername) {
        List<String> assignmentData = Registration.readFile(AssignmentFile);
        for (String line : assignmentData) {
            String[] parts = line.split(";");
            if (parts[0].equals(tutorUsername)) {
                String subject1Levels = "";
                String subject2levels = "";
                String subject3Levels = "";

                if (parts.length > 1) {
                    subject1Levels = parts[1].trim();
                }
                if (parts.length > 2) {
                    subject2levels = parts[2].trim();
                }
                if (parts.length > 3) {
                    subject3Levels = parts[3].trim();
                }
                //if no one is empty, mean the teacher is old teacher ,has assignment
                if (!subject1Levels.isEmpty() || !subject2levels.isEmpty() || !subject3Levels.isEmpty()){
                    return true;
                }else{
                    return false; //is new teacher
                }
            }
        }
        return false; //cannot found the tutor cannot assign
    }

    //rules to assign tutor
    public static Map<String, List<String>> validAssignment(String qualification, String subject1, String subject2, String subject3) {
        Map<String, List<String>> levels = new HashMap<>(); //key=subject , value= levels (like python dict)

        List<String> forms = new ArrayList<>();
        switch (qualification) {
            case "SPM/STPM Level":
                forms = List.of("Form 1", "Form 2", "Form 3"); //UnsupportedOperationException if modify this
                break;
            case "Bachelor's in Science/Arts":
            case "Master's Degree":
            case "PhD":
                forms = List.of("Form 4", "Form 5");
                break;
            case "Postgraduate Diploma in Education":
            case "Bachelor's in Education":
            case "Teaching Certification":
            case "TESL/TEFL Certification":
                forms = List.of("Form 1", "Form 2", "Form 3", "Form 4", "Form 5");
                break;
            default:
                break;
        }

        //if the subject is not No, then it can store the value
        if (!subject1.equals("No")) {
            levels.put(subject1, new ArrayList<>(forms)); //put subject in key place and copy the form to open a new arraylist for storing levels
        }
        if (!subject2.equals("No")) {
            levels.put(subject2, new ArrayList<>(forms));
        }
        if (!subject3.equals("No")) {
            levels.put(subject3, new ArrayList<>(forms));
        }
        return levels; //return the whole map

    }

    public static Map<String, AssignmentModel> loadAssignmentData() {
        Map<String, AssignmentModel> assignmentMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/assignment.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 4) continue;

                String username = parts[0].trim();
                String subject1Levels = parts[1].trim();
                String subject2Levels = parts[2].trim();
                String subject3Levels = parts[3].trim();

                assignmentMap.put(username, new AssignmentModel(username,subject1Levels,subject2Levels,subject3Levels));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assignmentMap;
    }
}


