import quickfix.FieldNotFound;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Parte 2 do Teste. Carrega para memória o resultado da Parte 1 e gera arquivo CSV e TXT
 */

public class Parte2 {
    List<ExecutionReport> executionReportList = new ArrayList<ExecutionReport>();

    /**
     * Load serialized messages into memory and export them to CSV/TXT files in a user friendly format
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws FieldNotFound
     */
    public boolean ProcessMessages() throws IOException, ClassNotFoundException, FieldNotFound {
        if (!this.loadExecutionReportList()){
            System.out.println("Parte 1 do teste não foi executada. Não é possível prosseguir com a parte 2");
            return false;
        }

        //Gerarate CSV (AllMsgs.csv) and TXT (FullFill.txt)
        this.generateCSVandTXT();

        return true;
    }

    /**
     * Load to memory serialized ExecutionReport List
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private boolean loadExecutionReportList() throws IOException, ClassNotFoundException {
        String file_path = "data/ExecutionReport.ser";
        //Check if file exists
        File f = new File(file_path);
        if(!f.exists()) {
            return false;
        }

        FileInputStream fis = new FileInputStream(file_path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        this.executionReportList = (ArrayList<ExecutionReport>) ois.readObject();

        return true;
    }

    /**
     * Generate CSV file with columns from Execution Data
     * @throws FieldNotFound
     */
    private void generateCSVandTXT() throws FieldNotFound, FileNotFoundException {
        //Create files
        PrintWriter wCSV = new PrintWriter("data/AllMsgs.csv");
        PrintWriter wTXT = new PrintWriter("data/FullFill.txt");

        //Write Header
        String header = "Horário;Conta;Instrumento;Lado;QtdOrdem;QtdExecucaoAtual;QtdExecucaoAcumulada;PrecoExecutado;NotionalOrdem;NotionalExecucaoAtual;NotionalExecucaoAcumulada;EnteringTrader\n";
        wCSV.write(header);
        wTXT.write(header);

        //Write each Message to CSV File (//TODO: Migrate do data dictionary if possible)
        for (ExecutionReport er : this.executionReportList){
            //Standard FIX TAGS
            String symbol = er.getHeader().getField(new Symbol()).getValue();
            String securityID = er.getHeader().getField(new SecurityID()).getValue();
            String senderCompID = er.getHeader().getField(new SenderCompID()).getValue();
            String targetCompID = er.getHeader().getField(new TargetCompID()).getValue();
            String deliverToCompID = er.getHeader().getField(new DeliverToCompID()).getValue();
            LocalDateTime sendingTime = er.getHeader().getField(new SendingTime()).getValue();
            LocalDateTime transactTime = er.getHeader().getField(new TransactTime()).getValue();
            char side = er.getHeader().getField(new Side()).getValue();
            if (side == Side.BUY) {side='C';} else {side='V';}
            char execType = er.getHeader().getField(new ExecType()).getValue();
            int msgSeqNum = er.getHeader().getField(new MsgSeqNum()).getValue();
            char ordType = er.getHeader().getField(new OrdType()).getValue();
            char timeInForce = er.getHeader().getField(new TimeInForce()).getValue();
            char ordStatus = er.getHeader().getField(new OrdStatus()).getValue();
            int noPartyIDs = er.getHeader().getField(new NoPartyIDs()).getValue();
            String partyID = er.getHeader().getField(new PartyID()).getValue().toString();
            char partyIDSource = er.getHeader().getField(new PartyIDSource()).getValue();
            int partyRole = er.getHeader().getField(new PartyRole()).getValue();
            double orderQty = er.getField(new quickfix.field.OrderQty()).getValue();
            double lastQty = er.getField(new quickfix.field.LastQty()).getValue();
            double cumQty = er.getField(new quickfix.field.CumQty()).getValue();
            double leavesQty = er.getField(new quickfix.field.LeavesQty()).getValue();
            String clOrdID = er.getField(new quickfix.field.ClOrdID()).getValue();
            String orderID = er.getField(new quickfix.field.OrderID()).getValue();
            String execID = er.getField(new quickfix.field.ExecID()).getValue();
            double avgPx = er.getField(new quickfix.field.AvgPx()).getValue();
            double lastPx = er.getField(new LastPx()).getValue();
            double price = er.getField(new quickfix.field.Price()).getValue();
            String account = er.getField(new quickfix.field.Account()).getValue();
            String securityIDSource = er.getField(new quickfix.field.SecurityIDSource()).getValue();

            //Calculate Derived Fields
            double notionalOrder = price * cumQty;
            double notionalLastExecution = lastPx * lastQty;  //TODO: De acordo com o doct da B3, AvgPx tem o valor fixo = 0. Ao invés de AvgPx estou usando LastPX.
            double notionalAccExecution = lastPx * cumQty;    //TODO: De acordo com o doct da B3, AvgPx tem o valor fixo = 0. Ao invés de AvgPx estou usando LastPX.

            //write line
            StringBuilder sb = new StringBuilder();
            sb.append(transactTime);                sb.append(";");
            sb.append(account);                     sb.append(";");
            sb.append(symbol);                      sb.append(";");
            sb.append(side);                        sb.append(";");
            sb.append(orderQty);                    sb.append(";");
            sb.append(lastQty);                     sb.append(";");
            sb.append(cumQty);                      sb.append(";");
            sb.append(lastPx);                      sb.append(";");
            sb.append(notionalOrder);              sb.append(";");
            sb.append(notionalLastExecution);     sb.append(";");
            sb.append(notionalAccExecution);      sb.append(";");
            sb.append(partyID);                     sb.append('\n');

            wCSV.write(sb.toString());

            //If message is fullfiled, write to FullFill.txt
            if (ordStatus == OrdStatus.FILLED){
                wTXT.write(sb.toString());
            }
        }

        wCSV.close();
        wTXT.close();
    }

}
