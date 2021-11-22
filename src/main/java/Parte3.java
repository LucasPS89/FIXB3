import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static tech.tablesaw.aggregate.AggregateFunctions.*;

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
        //Check if Parte 2 was executed
        String file_path = "data/AllMsgs.csv";
        File f = new File(file_path);
        if(!f.exists()) {
            System.out.println("Parte 2 do teste não foi executada. Não é possível prosseguir com a parte 3");
            return;
        }

        //Load Files into memory
        this.loadTXT();
        this.loadCSV();

        //Group data in the table AllMsg
        this.AllMsgSummarized = this.AllMsg.summarize("QtdExecucaoAcumulada", "NotionalExecucaoAcumulada", sum).by("Conta","Instrumento", "Lado");
        this.AllMsgSummarized.addColumns(DoubleColumn.create("PrecoMedio", this.AllMsgSummarized.stream().mapToDouble(row -> {
            return row.getDouble("Sum [NotionalExecucaoAcumulada]") / row.getDouble("Sum [QtdExecucaoAcumulada]");
        })));
        this.AllMsgSummarized.removeColumns(new String[]{"Sum [NotionalExecucaoAcumulada]", "Sum [QtdExecucaoAcumulada]"});

        //Group data in the table FullFill
        this.FullFillSummarized = this.FullFill.summarize("QtdExecucaoAcumulada", "NotionalExecucaoAcumulada", sum).by("Conta","Instrumento", "Lado");
        this.FullFillSummarized.addColumns(DoubleColumn.create("PrecoMedio", this.FullFillSummarized.stream().mapToDouble(row -> {
            return row.getDouble("Sum [NotionalExecucaoAcumulada]") / row.getDouble("Sum [QtdExecucaoAcumulada]");
        })));
        this.FullFillSummarized.removeColumns(new String[]{"Sum [NotionalExecucaoAcumulada]", "Sum [QtdExecucaoAcumulada]"});

        //Join tables into Result
        String[] joinColumns = new String[] {"Conta", "Instrumento", "Lado"};
        this.Result = this.AllMsgSummarized.joinOn(joinColumns).inner(this.FullFillSummarized, true, joinColumns);

        //Save Result to TXT File
        try(PrintWriter wTXT = new PrintWriter("data/Result.txt")){
            wTXT.write(this.Result.setName("Results").printAll());
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
                        .header(true);
        CsvReadOptions options = builder.build();
        this.FullFill = Table.read().usingOptions(options);
    }

    /**
     * Load file AllMsgs.CSV into memory
     * @throws IOException
     */
    public void loadCSV() throws IOException {
        CsvReadOptions.Builder builder =
                CsvReadOptions.builder("data/AllMsgs.csv")
                        .separator(';')
                        .header(true);
        CsvReadOptions options = builder.build();
        this.AllMsg = Table.read().usingOptions(options);

        //Filter only FILLED orders
        this.AllMsg = this.AllMsg.where(this.AllMsg.intColumn("QtdExecucaoAcumulada").isEqualTo(this.AllMsg.intColumn("QtdOrdem")));
    }
}
