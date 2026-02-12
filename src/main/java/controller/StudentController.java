package controller;//package controller;

import model.Request;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StudentController {


    public static List<String[]> getScheduleData(String Username) {
        List<String[]> scheduleList = new ArrayList<>();

        String enrolmentPath = "src/main/resources/enrolment.txt";
        String classinfoPath = "src/main/resources/classinfo.txt";
        String tutorPath = "src/main/resources/tutor.txt";

        HashMap<String, String> subjectTutorMap = new HashMap<>();
        HashMap<String, String> tutorFullNameMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(tutorPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    tutorFullNameMap.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            return scheduleList;
        } catch (IOException e) {
            return scheduleList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(enrolmentPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].trim().equalsIgnoreCase(Username)) {
                    for (int i = 2; i < parts.length - 1; i += 2) {
                        String subject = parts[i];
                        String tutor = parts[i + 1];
                        if (!subject.equalsIgnoreCase("null") && !subject.isEmpty()) {
                            subjectTutorMap.put(subject, tutor);
                        }
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            return scheduleList;
        } catch (IOException e) {
            return scheduleList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(classinfoPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    String tutor = parts[0];
                    String subject = parts[1];
                    String time = parts[2];
                    String day = parts[3];
                    String charges = parts[4];
                    String room = parts[5];

                    if (subjectTutorMap.containsKey(subject)) {
                        String expectedTutor = subjectTutorMap.get(subject);
                        //expectedTutor.equalsIgnoreCase("null") ||
                        if (expectedTutor.equalsIgnoreCase(tutor)) {
                            String fullName = tutorFullNameMap.getOrDefault(tutor, tutor);
                            scheduleList.add(new String[]{subject, fullName, day, time, room});
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return scheduleList;
        } catch (IOException e) {
        }

        return scheduleList;
    }

    public static final String FILE_PATH = "src/main/resources/request.txt";

    public static boolean sendChangeRequest(String Username, String requestId, String oldSubject, String newSubject, String receptionist) {
        String Status = "pending";

        //find existing Request prevent double request
        ArrayList<String[]> existingRequests = getUserRequests(Username);
        for (String[] req : existingRequests) {
            String reqOldSubject = req[2];
            String reqNewSubject = req[3];

            if (reqOldSubject.equalsIgnoreCase(oldSubject)) {
                JOptionPane.showMessageDialog(null, "You already have a pending request to change from " + oldSubject + " to " + reqNewSubject + "\n"
                        + " Remove old request before submit a new request.");
                return false;
            }
        }

        Request request = new Request(Username, requestId, oldSubject, newSubject, receptionist, Status);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(request.toString());
            writer.newLine();
        } catch (IOException e) {
        }
        return true;
    }

    public static String generateRequestId() {
        boolean[] usedIds = new boolean[1000];
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/request.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2 && parts[1].startsWith("R")) {
                    try {
                        int id = Integer.parseInt(parts[1].substring(1));
                        if (id >= 1 && id < usedIds.length) {
                            usedIds[id] = true;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
        }

        for (int i = 1; i < usedIds.length; i++) {
            if (!usedIds[i]) {
                return "R" + String.format("%03d", i);
            }
        }

        return "R999";
    }

    public static ArrayList<String[]> getUserRequests(String Username) {
        ArrayList<String[]> requests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/request.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6 && parts[0].equalsIgnoreCase(Username)) {
                    requests.add(parts);
                }
            }

            requests.sort((a, b) -> {
                int idA = Integer.parseInt(a[1].substring(1));
                int idB = Integer.parseInt(b[1].substring(1));
                return Integer.compare(idA, idB);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static void deletePendingRequest(String username, String requestId) {
        File inputFile = new File("src/main/resources/request.txt");
        File tempFile = new File("src/main/resources/request_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 6) continue;

                String fileUsername = parts[0].trim();
                String fileRequestId = parts[1].trim();
                String status = parts[5].trim();

                if (fileUsername.equals(username) &&
                        fileRequestId.equals(requestId) &&
                        status.equalsIgnoreCase("pending")) {
                    continue;
                }

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
        }

        if (!inputFile.delete()) {
        } else if (!tempFile.renameTo(inputFile)) {
        }
    }

    public static ArrayList<String> getEnrolledSubjects(String Username) {
        ArrayList<String> subjects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/enrolment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 && parts[0].equalsIgnoreCase(Username.trim())) {
                    for (int i = 2; i < parts.length; i += 2) {
                        String subject = parts[i].trim();
                        if (!subject.equalsIgnoreCase("null") && !subject.isEmpty()) {
                            subjects.add(subject);
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
        }
        return subjects;
    }

    public static ArrayList<String> getAllSubjects(String Username) {
        ArrayList<String> subjects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/enrolment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                for (int i = 2; i < parts.length; i += 2) {
                    String subject = parts[i].trim();
                    if (!subject.equalsIgnoreCase("null") && !subject.isEmpty() && !subjects.contains(subject)) {
                        subjects.add(subject);
                    }
                }
            }
        } catch (IOException e) {
        }
        return subjects;
    }

    public static ArrayList<String> getUserSubjects(String username) {
        ArrayList<String> subjects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/enrolment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equalsIgnoreCase(username)) {
                    for (int i = 2; i < parts.length; i += 2) {
                        if (i < parts.length && parts[i] != null && !parts[i].equalsIgnoreCase("null") && !parts[i].trim().isEmpty()) {
                            subjects.add(parts[i].trim());
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
        }

        return subjects;
    }

    public static void makePayment(String username, String subject, String paymentMethod) {
        String classInfoPath = "src/main/resources/classinfo.txt";
        String paymentPath = "src/main/resources/payment.txt";
        String tempPath = "src/main/resources/payment_temp.txt";

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String status = "Pending";
        String charge = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(classInfoPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5 && parts[1].equalsIgnoreCase(subject)) {
                    charge = parts[4];
                    break;
                }
            }
        } catch (IOException e) {
        }


        //add a jOptionPane if no found classInfo then popup
        if (charge.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No charge defined in class info.\nPlease wait for tutor update class information.\nPayment cannot proceed.",
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!charge.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(paymentPath));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == 5) {
                        if (!(parts[0].equalsIgnoreCase(username) && parts[1].equalsIgnoreCase(subject))) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                }

                writer.write(username + ";" + subject + ";" + date + ";" + paymentMethod + ";" + status);
                writer.newLine();

            } catch (IOException e) {
            }

            File original = new File(paymentPath);
            File temp = new File(tempPath);
            if (!original.delete()) {
            }
            if (!temp.renameTo(original)) {
            }
        }
    }

    public static ArrayList<String[]> getUserPayments(String username) {
        ArrayList<String[]> result = new ArrayList<>();

        ArrayList<String> subjects = getUserSubjects(username);
        HashMap<String, String[]> paymentMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/payment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equalsIgnoreCase(username)) {
                    String subject = parts[1];
                    String date = parts[2];
                    String method = parts[3];
                    String status = parts[4];
                    paymentMap.put(subject, new String[]{date, method, status});
                }
            }
        } catch (IOException e) {
        }

        for (String subject : subjects) {
            double charge = getChargeForSubject(subject);
            if (paymentMap.containsKey(subject)) {
                String[] paymentDetails = paymentMap.get(subject);
                result.add(new String[]{subject, String.valueOf(charge), paymentDetails[0], paymentDetails[1], paymentDetails[2]});
            } else {
                result.add(new String[]{subject, String.valueOf(charge), "", "", ""});
            }
        }

        return result;
    }

    public static double getChargeForSubject(String subjectName) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/classinfo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5 && parts[1].equalsIgnoreCase(subjectName)) {
                    return Double.parseDouble(parts[4]);
                }
            }
        } catch (IOException | NumberFormatException e) {
        }
        return 0.0;
    }

    public static String viewTotalBalance(String Username) {
        ArrayList<String[]> payments = getUserPayments(Username);

        double totalPaid = 0;
        double totalPending = 0;

        for (String[] p : payments) {
            double charge = 0;
            try {
                charge = Double.parseDouble(p[1]);
            } catch (NumberFormatException e) {
                charge = 0;
            }

            if (p[4].equalsIgnoreCase("Paid")) {
                totalPaid += charge;
            } else if (p[4].equalsIgnoreCase("Pending")) {
                totalPending += charge;
            }
        }

        return String.format("Total Paid: RM %.2f\nTotal Pending: RM %.2f", totalPaid, totalPending);
    }
}








