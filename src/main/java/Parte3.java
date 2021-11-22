import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import java.io.IOException;
import java.io.PrintWriter;
import static tech.tablesaw.aggregate.AggregateFunctions.mean;

/**
 * Parte 3 do Teste. Compara arquivos de OUTPUT e exibe resultado
 */
public class Parte3 {
    private Table FullFill;
    private Table FullFillSummarized;
    private Table AllMsg;
    private Table AllMsgSummarized;
    private Table Result;

    public void CompareFiles() throws IOException {
        //Load Files into memory
        this.loadTXT();
        this.loadCSV();

        //Group data in the table FullFill and ALlMsg
        this.FullFillSummarized = this.AllMsg.summarize("PrecoExecutado", mean).by("Conta","Instrumento", "Lado");
        this.AllMsgSummarized = this.AllMsg.summarize("PrecoExecutado", mean).by("Conta","Instrumento", "Lado");

        //Join tables into Result
        String[] joinColumns = new String[] {"Conta", "Instrumento", "Lado"};
        this.Result = this.AllMsgSummarized.joinOn(joinColumns).inner(this.FullFillSummarized, true, joinColumns);

        //Rename result columns
        this.Result.column("Mean [PrecoExecutado]").setName("CsvPrecoMedio");
        this.Result.column("T2.Mean [PrecoExecutado]").setName("TxtPrecoMedio");

        //Save Result to TXT File
        try(PrintWriter wTXT = new PrintWriter("data/Result.txt")){
            wTXT.write(this.Result.toString());
            wTXT.close();
        }
    }

    /**
     * Load file FullFill.TXT into memory
     * @throws IOException
     */
    public void loadTXT() throws IOException {
        CsvReadOptions.Builder builder =
                CsvReadOptions.builder("data/FullFill.txt")
                        .separator(';')
                        .header(true)
                        .dateFormat("yyyy-MM-dd");
        CsvReadOptions options = builder.build();
        this.FullFill = Table.read().usingOptions(options);
        System.out.println(this.FullFill.toString());
    }

    /**
     * Load file AllMsgs.CSV into memory
     * @throws IOException
     */
    public void loadCSV() throws IOException {
        CsvReadOptions.Builder builder =
                CsvReadOptions.builder("data/AllMsgs.csv")
                        .separator(';')
                        .header(true)
                        .dateFormat("yyyy-MM-dd");
        CsvReadOptions options = builder.build();
        this.AllMsg = Table.read().usingOptions(options);
        System.out.println(this.AllMsg.toString());
    }
}
