package controller;

import model.Account;
import model.Admin;
import model.Receptionist;
import model.Student;
import model.Tutor;

import java.util.ArrayList;
import java.util.List;

import static controller.Registration.*;


public class Profile {

    public static String updateAdminProfile(String AdminUsername, String newName, String newPassword, String newContact, String newEmail, String newAddress, String newWorkStatus) {
        //1. load admin and account
        List<String> adminLines = Registration.readFile(AdminFile);
        List<String> accountLines = Registration.readFile(AccountFile);

        //2. found tally admin and account
        Admin foundAdmin = Registration.getAllAdmin().stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(AdminUsername))
                .findFirst()
                .orElse(null);

        if (foundAdmin == null) return "Admin not found";

        Account foundAccount = Registration.getAllAccount().stream()
                .filter(ac -> ac.username().equalsIgnoreCase(AdminUsername))
                .findFirst()
                .orElse(null);

        if (foundAccount == null) return "Account not found";

        //3. only the modified will be pass, otherwise will remain unchanged
        if (newName != null && !newName.isEmpty()) {
            foundAdmin.setFullName(newName);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            foundAccount = foundAccount.withPassword(newPassword);
        }
        if (newContact != null && !newContact.isEmpty()) {
            foundAdmin.setContactNumber(newContact);
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            foundAdmin.setEmail(newEmail);
        }
        if (newAddress != null && !newAddress.isEmpty()) {
            foundAdmin.setAddress(newAddress);
        }
        if (newWorkStatus != null && !newWorkStatus.isEmpty()) {
            foundAdmin.setWorkStatus(newWorkStatus);
        }

        //4. update to admin.txt
        List<String> newAdminLines = new ArrayList<>();
        for (String line : adminLines) {
            String[] parts = line.split(";", -1); //-1 will not remove the empty field ""
            if (parts.length > 0 && parts[0].equalsIgnoreCase(AdminUsername)) {
                newAdminLines.add(foundAdmin.toString());
            } else {
                newAdminLines.add(line);
            }
        }

        //5. update to user.txt
        List<String> newAccountLines = new ArrayList<>();
        for (String row : accountLines) {
            String[] parts = row.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(AdminUsername)) {
                newAccountLines.add(foundAccount.toLine()); // username;password;role
            } else {
                newAccountLines.add(row);
            }
        }

        Registration.writeFile(AdminFile, newAdminLines);
        Registration.writeFile(AccountFile, newAccountLines);
        return "Success";
    }

    /**
     * Updates the profile information of a receptionist and their associated account.
     *
     * @param ReceptionistUsername the username of the receptionist to be updated
     * @param newName              the new name
     * @param newPassword          the new password for the account
     * @param newContact           the new contact number
     * @param newEmail             the new email address
     * @param newAddress           the new home address
     * @param newWorkStatus        the new work status
     * @return "Success" if the update is successful, or an error message if the receptionist/account is not found
     */
    public static String updateReceptionistProfile(String ReceptionistUsername, String newName, String newPassword, String newContact, String newEmail, String newAddress, String newWorkStatus) {
        //1.load receptionist and account
        List<String> receptionistLines = Registration.readFile(ReceptionistFile);
        List<String> accountLines = Registration.readFile(AccountFile);

        //2.found tally receptionist & account
        Receptionist foundReceptionist = Registration.getAllReceptionist()
                .stream()
                .filter(r -> r.getUsername().equalsIgnoreCase(ReceptionistUsername))
                .findFirst()
                .orElse(null);

        if (foundReceptionist == null) return "Receptionist not found";

        Account foundAccount = Registration.getAllAccount()
                .stream()
                .filter(a -> a.username().equalsIgnoreCase(ReceptionistUsername))
                .findFirst()
                .orElse(null);

        if (foundAccount == null) return "Account not found";

        //3.only the modified will be pass, otherwise will remain unchanged
        if (newName != null && !newName.isEmpty()) {
            foundReceptionist.setFullName(newName);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            //must give new variable , because at account model is record class , Immutable
            foundAccount = foundAccount.withPassword(newPassword);
        }
        if (newContact != null && !newContact.isEmpty()) {
            foundReceptionist.setContactNumber(newContact);
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            foundReceptionist.setEmail(newEmail);
        }
        if (newAddress != null && !newAddress.isEmpty()) {
            foundReceptionist.setAddress(newAddress);
        }
        if (newWorkStatus != null && !newWorkStatus.isEmpty()) {
            foundReceptionist.setWorkStatus(newWorkStatus);
        }

        //4.update to receptionist.txt
        List<String> newReceptionistLines = new ArrayList<>();
        for (String line : receptionistLines) {
            String[] parts = line.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(ReceptionistUsername)) {
                newReceptionistLines.add(foundReceptionist.toString());
            } else {
                newReceptionistLines.add(line);
            }
        }

        //5.update to user.txt
        List<String> newAccountLines = new ArrayList<>();
        for (String row : accountLines) {
            String[] parts = row.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(ReceptionistUsername)) {
                newAccountLines.add(foundAccount.toLine());
            } else {
                newAccountLines.add(row);
            }
        }
        Registration.writeFile(ReceptionistFile, newReceptionistLines);
        Registration.writeFile(AccountFile, newAccountLines);
        return "Success";
    }

    public static String updateStudentProfile(String studentUsername, String newPassword, String newFullName, String newContact, String newEmail, String newAddress) {
        // 1. load student and account
        List<String> studentLines = Registration.readFile(StudentFile);
        List<String> accountLines = Registration.readFile(AccountFile);

        // 2. find matching student and account
        Student foundStudent = Registration.getAllStudent().stream()
                .filter(s -> s.getUsername().equalsIgnoreCase(studentUsername))
                .findFirst()
                .orElse(null);
        if (foundStudent == null) return "Student not found";

        Account foundAccount = Registration.getAllAccount().stream()
                .filter(ac -> ac.username().equalsIgnoreCase(studentUsername))
                .findFirst()
                .orElse(null);
        if (foundAccount == null) return "Account not found";

        // 3. update fields if provided
        if (newFullName != null && !newFullName.isEmpty()) {
            foundStudent.setName(newFullName);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            foundAccount = foundAccount.withPassword(newPassword);
        }
        if (newContact != null && !newContact.isEmpty()) {
            foundStudent.setContact(newContact);
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            foundStudent.setEmail(newEmail);
        }
        if (newAddress != null && !newAddress.isEmpty()) {
            foundStudent.setAddress(newAddress);
        }

        // 4. update student.txt
        List<String> newStudentLines = new ArrayList<>();
        for (String line : studentLines) {
            String[] parts = line.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(studentUsername)) {
                newStudentLines.add(foundStudent.toFileString());
            } else {
                newStudentLines.add(line);
            }
        }

        // 5. update user.txt
        List<String> newAccountLines = new ArrayList<>();
        for (String line : accountLines) {
            String[] parts = line.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(studentUsername)) {
                newAccountLines.add(foundAccount.toLine());
            } else {
                newAccountLines.add(line);
            }
        }

        Registration.writeFile(StudentFile, newStudentLines);
        Registration.writeFile(AccountFile, newAccountLines);

        return "Success";
    }

    public static String[] getStudentProfile(String studentUsername) {
        List<String> studentLines = Registration.readFile(StudentFile);

        for (String line : studentLines) {
            String[] parts = line.split(";", -1);
            if (parts.length > 1 && parts[0].equalsIgnoreCase(studentUsername)) {
                return parts; // assumes parts[2] = NRIC, parts[4] = DOB etc.
            }
        }

        return null;
    }


    public static String updateTutorProfile(
            String tutorUsername,
            String newName,
            String newPassword,
            String newPhone,
            String newEmail,
            String newDob,
            String newAge,
            String newGender,
            String newRace,
            String newIC,
            String newQualification,
            String newAddress
    ) {
        // 1. Load current files
        List<String> tutorLines = Registration.readFile(Registration.TutorFile);
        List<String> accountLines = Registration.readFile(Registration.AccountFile);

        // 2. Find tutor and account
        Tutor foundTutor = Registration.getAllTutor().stream()
                .filter(t -> t.getUsername().equalsIgnoreCase(tutorUsername))
                .findFirst()
                .orElse(null);

        if (foundTutor == null) return "Tutor not found";

        Account foundAccount = Registration.getAllAccount().stream()
                .filter(ac -> ac.username().equalsIgnoreCase(tutorUsername))
                .findFirst()
                .orElse(null);

        if (foundAccount == null) return "Account not found";

        // 3. Update fields
        if (!newName.isEmpty()) foundTutor.setFullName(newName);
        if (!newPhone.isEmpty()) foundTutor.setContactNumber(newPhone);
        if (!newEmail.isEmpty()) foundTutor.setEmail(newEmail);
        if (!newDob.isEmpty()) foundTutor.setDateOfBirth(newDob);
        if (!newAge.isEmpty()) foundTutor.setAge(newAge);
        if (!newGender.isEmpty()) foundTutor.setGender(newGender);
        if (!newRace.isEmpty()) foundTutor.setRace(newRace);
        if (!newIC.isEmpty()) foundTutor.setIC(newIC);
        if (!newQualification.isEmpty()) foundTutor.setQualification(newQualification);
        if (!newAddress.isEmpty()) foundTutor.setAddress(newAddress);
        if (!newPassword.isEmpty()) foundAccount = foundAccount.withPassword(newPassword);

        // 4. Update tutor.txt
        List<String> updatedTutorLines = new ArrayList<>();
        for (String line : tutorLines) {
            String[] parts = line.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(tutorUsername)) {
                updatedTutorLines.add(foundTutor.toString());
            } else {
                updatedTutorLines.add(line);
            }
        }

        // 5. Update account.txt
        List<String> updatedAccountLines = new ArrayList<>();
        for (String line : accountLines) {
            String[] parts = line.split(";", -1);
            if (parts.length > 0 && parts[0].equalsIgnoreCase(tutorUsername)) {
                updatedAccountLines.add(foundAccount.toLine());
            } else {
                updatedAccountLines.add(line);
            }
        }

        // 6. Save back
        Registration.writeFile(Registration.TutorFile, updatedTutorLines);
        Registration.writeFile(Registration.AccountFile, updatedAccountLines);

        return "Success";
    }
}
