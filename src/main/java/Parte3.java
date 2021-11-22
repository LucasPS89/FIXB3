import tech.tablesaw.aggregate.*;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.Destination;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.io.html.HtmlWriteOptions;
import tech.tablesaw.io.html.HtmlWriter;
import tech.tablesaw.joining.DataFrameJoiner;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import static tech.tablesaw.aggregate.AggregateFunctions.mean;

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

        //Group data in the table FullFill
        //Conta, Instrumento, Lado
        this.FullFillSummarized = this.AllMsg.summarize("PrecoExecutado", mean).by("Conta","Instrumento", "Lado");
        //this.FullFillSummarized.column("Conta").setName("TxtConta");
        //this.FullFillSummarized.column("Instrumento").setName("TxtPapel");
        //this.FullFillSummarized.column("Lado").setName("TxtLado");
        //this.FullFillSummarized.column("Mean [PrecoExecutado]").setName("TxtPrecoMedio");
        //System.out.println(this.FullFillSummarized.toString());

        //Group data in the table AllMsg
        this.AllMsgSummarized = this.AllMsg.summarize("PrecoExecutado", mean).by("Conta","Instrumento", "Lado");
        //this.AllMsgSummarized.column("Conta").setName("CsvConta");
        //this.AllMsgSummarized.column("Instrumento").setName("CsvPapel");
        //this.AllMsgSummarized.column("Lado").setName("CsvLado");
        //this.AllMsgSummarized.column("Mean [PrecoExecutado]").setName("CsvPrecoMedio");
        //System.out.println(this.AllMsgSummarized.toString());

        //Join tables into Result
        //this.Result = this.AllMsgSummarized.joinOn("TxtConta", "TxtPapel", "TxtLado").inner(this.FullFillSummarized).joinOn("CsvConta", "CsvPapel", "CsvLado");
        String[] joinColumns = new String[] {"Conta", "Instrumento", "Lado"};
        this.Result = this.AllMsgSummarized.joinOn(joinColumns).inner(this.FullFillSummarized, true, joinColumns);

        //Rename result columns
        this.Result.column("Mean [PrecoExecutado]").setName("CsvPrecoMedio");
        this.Result.column("T2.Mean [PrecoExecutado]").setName("TxtPrecoMedio");


        //Save Result to HTML
        HtmlWriteOptions.Builder builder =
                HtmlWriteOptions.builder("data/Result.html");
        HtmlWriteOptions optionsHtml = builder.build();
        HtmlWriter teste = new HtmlWriter();
        teste.write(this.Result, optionsHtml);

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
