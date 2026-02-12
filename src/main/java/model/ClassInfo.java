package model;

public class ClassInfo {
    private String Username;
    private String Subject;
    private String Time;
    private String Day;
    private String Charge;
    private String Room;

    public ClassInfo() {
    }

    public ClassInfo(String username, String subject, String time,String day, String charge, String room) {
        this.Username = username;
        this.Subject = subject;
        this.Time = time;
        this.Day = day;
        this.Charge = charge;
        this.Room = room;
    }

    public String getUsername() {
        return Username;
    }

    public String getSubject() {
        return Subject;
    }

    public String getDay() {
        return Day;
    }

    public String getTime() {
        return Time;
    }

    public String getCharge() {
        return Charge;
    }

    public String getRoom() {
        return Room;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setDay(String day) {
        Day = day;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String toFileString(){
        return String.join(";",
                Username,
                Subject,
                Time,
                Day,
                Charge,
                Room

        );
    }
}
