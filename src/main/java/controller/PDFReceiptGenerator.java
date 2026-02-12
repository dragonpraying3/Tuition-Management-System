package controller;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import model.Receipt;

import java.io.File;
import java.util.Map;

public class PDFReceiptGenerator {

    static String TEMPLATE_PATH = "src/main/resources/TuitionCentre_PaymentReceipt_Template.pdf";
    static String RECEIPT_DIR = "src/main/resources/receipt";

    /**
     * Generates a PDF receipt based on the given receipt data.
     *
     * @param r the receipt data
     * @return {true} if the receipt was generated successfully; {false} otherwise
     */
    public static boolean generateReceipt(Receipt r) {
        try {
            // Generate new receipt number based on existing PDFs
            String receiptNo = nextReceiptNo();
            String destPath = String.format("%s/receipt_%s.pdf", RECEIPT_DIR, receiptNo);

            // Ensure output directory exists
            new File(RECEIPT_DIR).mkdirs();

            // Prepare PDF writer and reader
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(TEMPLATE_PATH), new PdfWriter(destPath));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = form.getFormFields();

            // Set bold font
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Fill form fields with Receipt data
            fields.get("receipt_no").setFont(boldFont).setFontSize(11f).setValue(receiptNo);
            fields.get("student_name").setFont(boldFont).setFontSize(11f).setValue(r.getStudentName());
            fields.get("payment_date").setFont(boldFont).setFontSize(11f).setValue(r.getDate());
            fields.get("payment_method").setFont(boldFont).setFontSize(11f).setValue(r.getPaymentMethod());
            fields.get("moe").setFont(boldFont).setFontSize(11f).setValue(r.getEnrolmentMonth());
            fields.get("student_level").setFont(boldFont).setFontSize(11f).setValue(r.getStudentLevel());

            fields.get("subject_1").setFontSize(11f).setValue(r.getSubject1());
            fields.get("subject_2").setFontSize(11f).setValue(r.getSubject2());
            fields.get("subject_3").setFontSize(11f).setValue(r.getSubject3());

            fields.get("tutor_1").setFontSize(11f).setValue(r.getTutor1());
            fields.get("tutor_2").setFontSize(11f).setValue(r.getTutor2());
            fields.get("tutor_3").setFontSize(11f).setValue(r.getTutor3());

            fields.get("subject_1_fee").setFontSize(11f).setValue(String.format("RM %s", r.getAmount1()));
            fields.get("subject_2_fee").setFontSize(11f).setValue(String.format("RM %s", r.getAmount2()));
            fields.get("subject_3_fee").setFontSize(11f).setValue(String.format("RM %s", r.getAmount3()));

            fields.get("total_amount").setFontSize(11f).setValue(String.format("RM %s", r.getTotalAmount()));
            fields.get("payment_status").setFontSize(14f).setValue("PAID");

            // Finalize the form
            form.flattenFields();
            pdfDoc.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Scans existing PDFs to find the next available receipt number


    public static String nextReceiptNo() {
        File dir = new File(RECEIPT_DIR);
        if (!dir.exists()) dir.mkdirs();

        int max = 0;
        File[] pdfs = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));

        if (pdfs != null) {
            for (File f : pdfs) {
                String filename = f.getName(); // e.g., "receipt_R008.pdf"
                int start = filename.indexOf("R");
                int end = filename.lastIndexOf(".");
                if (start != -1 && end > start) {
                    try {
                        int num = Integer.parseInt(filename.substring(start + 1, end));
                        max = Math.max(max, num);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        return String.format("R%03d", max + 1);
    }
}
