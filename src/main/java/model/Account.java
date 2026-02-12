package model;

//as a record , will auto extend record class
//but after compiler will extend
//public record Account : name
//(String username, String password, String role) : Header
//{} : body
public record Account(String username, String password, String role) {

    public String toLine() {
        return username + ";" + password + ";" + role;
    }

    public Account withPassword(String newPwd) {
        return new Account(username, newPwd, role);
    }
}