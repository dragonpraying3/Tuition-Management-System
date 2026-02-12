package main;

import view.Tutor.AddClass;
import view.Tutor.DeleteClass;
import view.Tutor.UpdateClass;
import view.Tutor.EnrolledStudents;

public class TuitionApp {
    public static void main(String[] args) {

        new view.LoginPage().setVisible(true);
        new AddClass().setVisible(true);
        new UpdateClass().setVisible(true);
        new DeleteClass().setVisible(true);
        new EnrolledStudents().setVisible(true);
    }
}

