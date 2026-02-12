package controller;

import model.Request;
import model.Student;

import java.util.ArrayList;
import java.util.List;

public class RequestController {

    final static String REQUEST_FILE = "src/main/resources/request.txt";

    public static List<Request> getAllRequest(){
        List<String> RequestLine = Registration.readFile(REQUEST_FILE);
        List<Request> allRequest = new ArrayList<>();
        for (String line : RequestLine) {
            String[] parts = line.split(";");
            if (parts.length >= 6) {
                String UserName = parts[0];
                String Rid = parts[1];
                String CurrentSubject = parts[2];
                String RequestSubject = parts[3];
                String ReceptionistName = parts[4];
                String status = parts[5];
                allRequest.add(new Request(UserName,Rid,CurrentSubject,RequestSubject,ReceptionistName,status));
            }
        }
        return allRequest;
    }

    public static String checkRequest(String subject, Student matchStudent) {
        List<Request> allRequest = getAllRequest();

        for (Request r : allRequest) {
            if (r.getUsername().equalsIgnoreCase(matchStudent.getUsername()) &&
                    r.getOldSubject().equalsIgnoreCase(subject)) {
                return r.getNewSubject();
            }
        }

        return null;
    }

}
