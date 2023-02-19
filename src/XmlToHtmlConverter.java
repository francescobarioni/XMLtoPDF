import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;

public class XmlToHtmlConverter{
    
    public void transform(String xmlFilePath, String xslFilePath, String outputHtmlFilePath) throws Exception {

        // Crea un oggetto TransformerFactory per trasformare il file XML utilizzando lo stile XSL
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xslFilePath)));

        // Crea un oggetto StreamSource per il file XML
        StreamSource source = new StreamSource(new File(xmlFilePath));

        // Crea un oggetto StreamResult per il file HTML di output
        StreamResult result = new StreamResult(new FileWriter(outputHtmlFilePath));

        // Applica la trasformazione XSLT all'input XML e scrivi il risultato sull'output HTML
        transformer.transform(source, result);

        System.out.println("File HTML generato con successo!");
    }
}
