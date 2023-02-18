## Per partire

- Programma di conversione da file XML a file PDF passando per un passaggio intermedio che è un file HTML

## Struttura del progetto
Sono contenute diverse cartelle importanti:

- `src`: che contiene il codice java principale;
- `lib`: dove sono contenuti tutti i jar collegati;
- `input`: dove sono contenut tutti file di input (quello XML & XSL)
- `output`: dove sono presenti i file di output (quello HTML & PDF);

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Librerie principalmente utilizzata

- `com.itextpdf.text`: una libreria per la creazione di documenti PDF. In particolare, viene utilizzata la classe Document per creare un nuovo documento PDF e la classe PageSize per specificare le dimensioni del foglio.

- `com.itextpdf.html2pdf`: una libreria per convertire il contenuto HTML in PDF. In particolare, viene utilizzata la classe HtmlConverter per convertire il contenuto HTML in PDF.

- `org.jsoup`: una libreria per analizzare e manipolare il contenuto HTML. In particolare, viene utilizzata la classe Jsoup per analizzare il file HTML generato dalla trasformazione XSLT e per modificare gli elementi HTML.

## Operazioni svolte

- La classe TransformerFactory viene utilizzata per creare un oggetto Transformer che applica il foglio di stile XSLT al file XML di input. La trasformazione XSLT produce un file HTML di output.

- Il file HTML di output viene letto in una stringa, verificato per la corretta formattazione HTML e successivamente elaborato con la libreria Jsoup per aggiungere l'attributo scope con valore col ai tag th presenti.

- Il documento HTML viene convertito in PDF utilizzando la classe HtmlConverter della libreria com.itextpdf.html2pdf.

- Il documento PDF viene salvato in un file di output.
