package controller;
import model.Admin;
import view.Admin.AdminView;
import view.Admin.IncomeReport;

/**
 * controller.PageController.refreshIncomeReport();
 *
 */

public class PageRefresh {
    private static IncomeReport incomeReport;

    //pass the incomeReport value
    public static void setIncomeReport(IncomeReport incomeReport) {
        PageRefresh.incomeReport = incomeReport;
    }
    //call the method
    public static void refreshIncomeReport(){
        if (incomeReport !=null){
            incomeReport.refreshTable();
        }
    }
}
