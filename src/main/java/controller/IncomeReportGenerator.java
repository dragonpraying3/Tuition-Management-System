package controller;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeReportGenerator {

    //import the file path
    static String TemplatePath="src/main/resources/IncomeReportTemplate.pdf";
    static String reportDIR="src/main/resources/Report";

    public static boolean generateReport(List<String[]> datalist){

        try{
            //ensure the report directory exists
            new File(reportDIR).mkdirs();

            //auto generate report file name
            String reportNo=nextReportNo();
            String fileName=String.format("Income_%s.pdf",reportNo);
            //safe because can use in linux or window
            String destPath = Paths.get(reportDIR, fileName).toString(); //save the generated file (src/main/resources/Report/fileName)

            //prepare reader and writer
            PdfDocument pdfDoc=new PdfDocument(new PdfReader(TemplatePath),new PdfWriter(destPath)); //read the template file and writer file
            PdfAcroForm form=PdfAcroForm.getAcroForm(pdfDoc,true); //read the form fields (the blue block)
            //String is the name of the block ,PdfFormField is the object that can set the value
            Map<String,PdfFormField> fields=form.getFormFields(); //get the defined field name and can set value later
            PdfFont boldFont=PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD); //set the field font

            //field name mapping
            String[] incomeFields={"jan1","feb1","mar1","april1","may1","jun1","july1","au1","sep1","oc1","nov1","dec1"};
            String[] studentFields={"jan2","feb2","mar2","april2","may2","jun2","july2","au2","sep2","oc2","nov2","dec2"};

            //the temporarily income and student
            Map<Integer,Integer> incomeMonth=new HashMap<>(); //put the month and income
            Map<Integer,Integer> countMonth=new HashMap<>(); //put the month and count

            //process data row
            for (String[] column:datalist){
                try{
                    String date=column[4];
                    int amount=Integer.parseInt(column[5]);
                    int month=Integer.parseInt(date.split("-")[1]); //take the month box

                    incomeMonth.put(month,incomeMonth.getOrDefault(month,0)+amount); //get the value of income fo the current month and add the amount
                    countMonth.put(month,countMonth.getOrDefault(month,0)+1); //get the value of the people of that month, and add by 1 if have that month student
                }catch(Exception ignored){}
            }

            int totalIncome=0;
            int totalStudent=0;

            //fill month income table
            for (int m=1;m<=12;m++){ //load from 12 months
                int income=incomeMonth.getOrDefault(m,0);
                int count=countMonth.getOrDefault(m,0);

                totalIncome+=income;
                totalStudent+=count;

                String incomeField=incomeFields[m-1]; //the map
                String studentField=studentFields[m-1]; //the map

                fields.get(incomeField).setFont(boldFont).setFontSize(11f).setValue("RM "+income);
                fields.get(studentField).setFont(boldFont).setFontSize(11f).setValue(String.valueOf(count));
            }

            //fill total field
            fields.get("totalIncome").setFont(boldFont).setFontSize(11f).setValue("RM "+totalIncome);
            fields.get("StudentsTotal").setFont(boldFont).setFontSize(11f).setValue(String.valueOf(totalStudent));

            //fill in field
            fields.get("reportNum").setFont(boldFont).setFontSize(12f).setValue("IC"+reportNo);
            fields.get("date").setFont(boldFont).setFontSize(12f).setValue(java.time.LocalDate.now().toString()); //get the generated report date

            form.flattenFields(); //make the form is read only
            pdfDoc.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //generate next report number
    private static String nextReportNo(){
        File dir =new File(reportDIR); //find the report directory only
        if (!dir.exists()) dir.mkdirs();  //if file not exist will make a dir

        int max=0; //define as 0 at first
        File[] pdfs=dir.listFiles((d,name)->name.toLowerCase().endsWith(".pdf")); //find all .pdf file

        if (pdfs !=null){ //if the content is not null
            for (File f:pdfs){
                String filename=f.getName(); //get the file name
                int start =filename.indexOf("_"); //start from the underscore
                int end=filename.indexOf("."); //end at the dot

                if (start !=-1 && end>start){ //check the start is larger than 0 and the sequence is correctly
                    try{
                        int num=Integer.parseInt(filename.substring(start+1,end)); //take the number 005 and convert to 5
                        max=Math.max(max,num); //compare and redefine the larger number
                    }catch (NumberFormatException ignored){}
                }
            }
        }
        return String.format("%03d",max+1); //return number in 00(x) format
    }
}

/**
 * the warning is because the program is using iText (inside system) not safe method ,and this method may be removed in the future JDK
 * student project can be ignored if not a permanent and formal project
 */