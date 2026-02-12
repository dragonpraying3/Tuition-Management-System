package model;

public class Request {
    private String Username;
    private String requestId;
    private String oldSubject;
    private String newSubject;
    private String newReceptionist;
    private String Status;

    public Request(String Username, String requestId, String oldSubject, String newSubject, String newReceptionist, String status) {
        this.Username = Username;
        this.requestId = requestId;
        this.oldSubject = oldSubject;
        this.newSubject = newSubject;
        this.newReceptionist = newReceptionist;
        this.Status = status;
    }

    public String getUsername() { return Username; }
    public String getRequestId() { return requestId; }
    public String getOldSubject() { return oldSubject; }
    public String getNewSubject() { return newSubject; }
    public String getNewReceptionist() { return newReceptionist; }
    public String getStatus() { return Status; }

    public void setStatus(String status) { this.Status = status; }

    @Override
    public String toString() {
        return Username + ";" + requestId + ";" + oldSubject + ";" + newSubject + ";" + newReceptionist + ";" + Status;
    }
}
