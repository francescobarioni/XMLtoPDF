## Per partire

- Programma di conversione da file XML a file PDF passando per un passaggio intermedio che è un file HTML

## Struttura del progetto
Sono contenute diverse cartelle importanti:

- `src`: che contiene il codice java principale;
- `lib`: dove sono contenuti tutti i jar collegati;
- `input`: dove sono contenut tutti file di input (quello XML & XSL)
- `output`: dove sono presenti i file di output (quello HTML & PDF);

## Librerie principalmente utilizzata

- `com.itextpdf.text`: una libreria per la creazione di documenti PDF. In particolare, viene utilizzata la classe Document per creare un nuovo documento PDF e la classe PageSize per specificare le dimensioni del foglio.

- `com.itextpdf.html2pdf`: una libreria per convertire il contenuto HTML in PDF. In particolare, viene utilizzata la classe HtmlConverter per convertire il contenuto HTML in PDF.

- `org.jsoup`: una libreria per analizzare e manipolare il contenuto HTML. In particolare, viene utilizzata la classe Jsoup per analizzare il file HTML generato dalla trasformazione XSLT e per modificare gli elementi HTML.

## Operazioni svolte

- `MainProgram`: 
    Questa classe Java è il programma principale di un'applicazione che converte un file XML in un file PDF.
    All'inizio del programma vengono impostati i percorsi di input e output fissi per i file XML, XSL, HTML e PDF.
    Il programma poi disattiva temporaneamente il logging della libreria itextpdf, che viene utilizzata per generare il file PDF, per evitare di stampare inutilmente messaggi di log sulla console.
    Viene quindi definito il percorso dei file XML e XSL di input, nonché il percorso dei file HTML e PDF di output.
    Successivamente, il programma esegue i seguenti passaggi:
    Crea il file HTML a partire dai file XML e XSL, utilizzando un'istanza della classe XmlToHtmlConverter.
    Modifica il file HTML utilizzando un'istanza della classe HtmlModifier.
    Riattiva il logging della libreria itextpdf.
    Genera il file PDF dal file HTML modificato, utilizzando un'istanza della classe PdfGenerator.
    Stampa a console un messaggio di completamento.
    In caso di eccezioni, viene stampato l'output dell'eccezione sulla console.

- `XmlToHtmlConverter`:
    La classe `XmlToHtmlConverter` è utilizzata per convertire un file XML in un file HTML utilizzando un file di stile XSL (Extensible Stylesheet Language). La conversione avviene utilizzando la libreria Java per la trasformazione XML (javax.xml.transform), che consente di applicare una trasformazione XSLT (Extensible Stylesheet Language Transformations) a un file XML e produrre un output in un altro formato, in questo caso HTML.
    Il metodo transform della classe accetta tre parametri:
    - `xmlFilePath`: il percorso del file XML di input
    - `xslFilePath`: il percorso del file XSL da utilizzare per la trasformazione
    - `outputHtmlFilePath`: il percorso del file HTML di output che verrà generato
    Il metodo utilizza un oggetto `TransformerFactory` per creare un oggetto `Transformer` che applica la trasformazione XSLT specificata dal file XSL al file XML specificato dal percorso del file di input. Il risultato della trasformazione viene quindi scritto su un file HTML utilizzando un oggetto `StreamResult` e un `FileWriter`. Alla fine, il metodo stampa un messaggio a console per indicare che il file HTML è stato generato con successo.

- `HtmlModifier`:
    La classe `HtmlModifier` legge un file HTML di input, verifica se la formattazione dell'HTML è corretta, modifica il contenuto HTML aggiungendo l'attributo "scope" con valore "col" ai tag <th> che non lo hanno, quindi sovrascrive il file di output con il nuovo contenuto HTML modificato.
    La classe utilizza la libreria `JSoup` per effettuare l'analisi e la modifica dell'HTML. Viene inoltre utilizzata la libreria `iText` per convertire l'HTML in un file PDF nel codice del programma principale.

- `PdfGenerator`:
    La classe `PdfGenerator` contiene un metodo statico generatePdf che prende come argomenti una stringa contenente HTML modificato e un percorso di file di output in cui scrivere il file PDF risultante. Il metodo utilizza la libreria `iText` per convertire l'HTML in un documento PDF. In particolare, crea un oggetto Document di iText con dimensioni di pagina A4 e margini di 36 punti, lo apre e imposta la codifica del testo a UTF-8, quindi converte l'HTML in PDF tramite il metodo `HtmlConverter.convertToPdf`, che scrive il documento PDF nel file di output specificato. Infine, chiude il documento e stampa un messaggio di conferma a console. In caso di eccezione, viene stampata una traccia dello stack.
