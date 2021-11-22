import quickfix.fix44.ExecutionReport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Parte1 {
    public void GenerateExecutionReports() throws IOException {
        //Generate 5000 ExecutionReport
        List<ExecutionReport> executionReportList = new ArrayList<ExecutionReport>();
        ExecutionReportGenerator erGenerator = new ExecutionReportGenerator();
        Account accounts = new Account();
        for (int i=1;i<=5000; i++){
            boolean totalExecution = false;
            if (i % 2 == 0){
                totalExecution = true;
            }
            ExecutionReport er = erGenerator.GenerateExecutionReport(totalExecution, accounts);
            executionReportList.add(er);
        }

        //Export LIST to FILE
        FileOutputStream fos = new FileOutputStream("data/ExecutionReport.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(executionReportList);
        oos.close();
    }
}
