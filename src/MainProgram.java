import java.nio.file.Files;
import java.nio.file.Paths;

public class MainProgram {
    // path di input e di output fissi per i file
    public static String absolutePathInput = "C:/WorkSpace/Java/XMLtoPDF/input/";
    public static String absolutePathOutput = "C:/WorkSpace/Java/XMLtoPDF/output/";

    public static void main(String[] args) {

        // disattivo temporaneamente il logging della libreria com.itextpdf
        java.util.logging.Logger.getLogger("com.itextpdf").setLevel(java.util.logging.Level.OFF);

        // Definisci il percorso del file XML e del file XSL
        String xmlFilePath = absolutePathInput + "esempio.xml";
        String xslFilePath = absolutePathInput + "esempio.xsl";
        String outputHtmlFilePath = absolutePathOutput + "output.html";
        String outputPdfFilePath = absolutePathOutput + "output.pdf";

        try {
            // Creazione del file HTML dal file XML e XSL
            XmlToHtmlConverter converter = new XmlToHtmlConverter();
            converter.transform(xmlFilePath, xslFilePath, outputHtmlFilePath);

            // Modifica del file HTML
            HtmlModifier modifier = new HtmlModifier();
            modifier.modify(outputHtmlFilePath, outputHtmlFilePath);

            // Creazione del file PDF dal file HTML modificato
            String html = new String(Files.readAllBytes(Paths.get(outputHtmlFilePath)));
            PdfGenerator.generatePdf(html, outputPdfFilePath);

            System.out.println("Operazione completata con successo!");

            // disattivo temporaneamente il logging della libreria com.itextpdf
            java.util.logging.Logger.getLogger("com.itextpdf").setLevel(java.util.logging.Level.ALL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
