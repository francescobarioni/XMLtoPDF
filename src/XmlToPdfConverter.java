import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XmlToPdfConverter {

    public static void main(String[] args) throws Exception {

        // disattivo temporaneamente il logging della libreria com.itextpdf
        java.util.logging.Logger.getLogger("com.itextpdf").setLevel(java.util.logging.Level.OFF);

        // Definisci il percorso del file XML e del file XSL
        String xmlFilePath = "C:/WorkSpace/Java/XMLtoPDF/input/Petroncini.xml";
        String xslFilePath = "C:/WorkSpace/Java/XMLtoPDF/input/FoglioStileAssoSoftware.xsl";
        String outputHtmlFilePath = "C:/WorkSpace/Java/XMLtoPDF/output/output.html";
        String outputPdfFilePath = "C:/WorkSpace/Java/XMLtoPDF/output/output.pdf";

        // Crea un oggetto TransformerFactory per trasformare il file XML utilizzando lo stile XSL
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xslFilePath)));

        // Crea un oggetto StreamSource per il file XML
        StreamSource source = new StreamSource(new File(xmlFilePath));

        // Crea un oggetto StreamResult per il file HTML di output
        StreamResult result = new StreamResult(new File(outputHtmlFilePath));

        // Applica la trasformazione XSLT all'input XML e scrivi il risultato sull'output HTML
        transformer.transform(source, result);

        System.out.println("File HTML generato con successo!");

        try {
            // leggi il file HTML in una stringa
            String html = readFile(outputHtmlFilePath);

            // verifica la corretta formattazione dell'HTML
            try {
                HtmlConverter.convertToElements(html);
            } catch (RuntimeWorkerException e) {
                System.err.println("HTML non correttamente formattato.");
                return;
            }

            //-------------------------------------------------------------------------------
            // AGGIUNTA DEL ATTRIBUTO SCOPE CON VALORE COL AL TAG TH E UPDATE HTML
            //-------------------------------------------------------------------------------
            org.jsoup.nodes.Document doc = Jsoup.parse(html);

            // seleziona tutti gli elementi "th" con un attributo "scope" già presente
            Elements thElements = doc.select("th[scope]");

            // aggiungi l'attributo "scope" con valore "col" a tutti gli elementi "th" selezionati
            for (Element thElement : thElements) {
                thElement.attr("scope", "col");
            }

            // seleziona tutti gli elementi "th" senza l'attributo "scope"
            Elements thElementsWithoutScope = doc.select("th:not([scope])");

            // aggiungi l'attributo "scope" con valore "col" a tutti gli elementi "th" selezionati
            for (Element thElement : thElementsWithoutScope) {
                thElement.attr("scope", "col");
            }

            // stampa il documento html dopo la modifica
            //System.out.println("HTML dopo la modifica: " + doc.html());

            // converti il documento Jsoup in una stringa HTML
            String modifiedHtml = doc.outerHtml();

            // sovrascrivi il file di output HTML con il nuovo HTML
            try (FileWriter fileWriter = new FileWriter(outputHtmlFilePath)) {
                fileWriter.write(modifiedHtml);
            }

            // riattivo i logging della libreria com.itextpdf
            java.util.logging.Logger.getLogger("com.itextpdf").setLevel(java.util.logging.Level.OFF);

            //--------------------------------------------------------------------------------
            // FINE UPDATE DOCUMENTO HTML
            //--------------------------------------------------------------------------------

            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            document.open();

            // chiudi il documento e il writer
            document.close();

            // converte l'HTML modificato (con th modificato) in PDF
            HtmlConverter.convertToPdf(new ByteArrayInputStream(modifiedHtml.getBytes()), new FileOutputStream(outputPdfFilePath));

            System.out.println("File PDF generato con successo!");      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}

