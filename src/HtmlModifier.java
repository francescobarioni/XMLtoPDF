import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlModifier {

    public void modify(String inputHtmlFilePath, String outputHtmlFilePath) throws Exception {

        // leggi il file HTML in una stringa
        String html = new String(Files.readAllBytes(Paths.get(inputHtmlFilePath)));

        // verifica la corretta formattazione dell'HTML
        try {
            HtmlConverter.convertToElements(html);
        } catch (RuntimeWorkerException e) {
            System.err.println("HTML non correttamente formattato.");
            // non fare nulla se l'HTML non è ben formato
        }

        //--------------------------------------------------------------------------------
        // AGGIUNTA DEL ATTRIBUTO SCOPE CON VALORE COL AL TAG TH E UPDATE HTML
        //--------------------------------------------------------------------------------

        Document doc = Jsoup.parse(html);

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

        // converti il documento Jsoup in una stringa HTML
        String modifiedHtml = doc.outerHtml();

        // sovrascrivi il file di output HTML con il nuovo HTML
        try (FileWriter fileWriter = new FileWriter(outputHtmlFilePath)) {
            fileWriter.write(modifiedHtml);
        }

        //--------------------------------------------------------------------------------
        // FINE UPDATE DOCUMENTO HTML
        //--------------------------------------------------------------------------------

    }
}
