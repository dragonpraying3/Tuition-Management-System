import controller.Registration;
import model.Admin;
import model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static controller.Registration.AccountFile;
import static controller.Registration.AdminFile;

//public class test extends JFrame {
//    public static void main(String[] args) {
//
//    }
//}


//List<String> adminFile= Registration.readFile(AdminFile);
//List<String> accountFile=Registration.readFile(AccountFile);
//List<String> updateAdminLine=new ArrayList<>();
//List<String> updateUserLine=new ArrayList<>();
//
////found tally admin
//Admin foundAdmin=null;
//List<Admin> admins=Registration.getAllAdmin();
//        for (Admin admin:admins){
//        if (admin.getUsername().equalsIgnoreCase(AdminUsername)){
//foundAdmin=admin; //store the founded admin into foundAdmin
//                break;
//                        }
//                        }
//                        if (foundAdmin==null){
//        return "Admin not found";
//        }
//
////found tally account
//User foundAccount=null;
//List<User> users=Registration.getAllAccount();
//        for (User user:users){
//        if (user.getUsername().equalsIgnoreCase(AdminUsername)){
//foundAccount=user;
//                break;
//                        }
//                        }
//                        if (foundAccount==null){
//        return "Account not found";
//        }
//
//        //only the modified will be pass, otherwise will remain unchanged
//        if (newName!=null && !newName.isEmpty()){
//        foundAdmin.setFullName(newName);
//        }
//                if (newPassword!=null && !newPassword.isEmpty()){
//        foundAccount.setPassword(newPassword);
//        }
//                if (newContact!=null && !newContact.isEmpty()){
//        foundAdmin.setContactNumber(newContact);
//        }
//                if (newEmail!=null && !newEmail.isEmpty()){
//        foundAdmin.setEmail(newEmail);
//        }
//                if (newAddress!=null && !newAddress.isEmpty()){
//        foundAdmin.setAddress(newAddress);
//        }
//                if (newWorkStatus!=null && !newWorkStatus.isEmpty()){
//        foundAdmin.setWorkStatus(newWorkStatus);
//        }
//
//                //update to admin.txt
//                for (String line :adminFile){
//String[] parts=line.split(";");
//            if (parts[0].equalsIgnoreCase(AdminUsername)){
//        updateAdminLine.add(foundAdmin.toString());
//        }else{
//        updateAdminLine.add(line); //if no change then return original line
//            }
//                    }
//
//                    //update to user.txt
//                    for (String row:accountFile){
//String[] field=row.split(";");
//            if (field[0].equalsIgnoreCase(AdminUsername)){
//        updateUserLine.add(foundAccount.toString());
//        }else{
//        updateUserLine.add(row);
//            }
//                    }
//                    Registration.writeFile(AdminFile,updateAdminLine);
//        Registration.writeFile(AccountFile,updateUserLine);
//        return "Success";