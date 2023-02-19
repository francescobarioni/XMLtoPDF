import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;

public class PdfGenerator {

    public static void generatePdf(String modifiedHtml, String outputPdfFilePath) {
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            document.open();

            // converte l'HTML modificato in PDF
            HtmlConverter.convertToPdf(new ByteArrayInputStream(modifiedHtml.getBytes()), new FileOutputStream(outputPdfFilePath), new com.itextpdf.html2pdf.ConverterProperties());

            // chiudi il documento e il writer
            document.close();

            System.out.println("File PDF generato con successo!");      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

