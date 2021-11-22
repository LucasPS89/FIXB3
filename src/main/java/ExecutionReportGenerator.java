import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

public class ExecutionReportGenerator {

    /**
     * Generate a new ExecutionReport
     * @return
     */
    public ExecutionReport GenerateExecutionReport(boolean totalExecution, Account accounts){
        Random rand = new Random();

        //New Execution Report
        ExecutionReport er = new ExecutionReport();
        int messageSeqNumber = 1;

        //Set Instrument
        Instrument instrument = new Instrument();
        String instrCode = instrument.GetRandom();
        String instrISIN = instrument.GetISINCode(instrCode);
        er.getHeader().setField(new Symbol(instrCode));
        er.getHeader().setField(new SecurityID(instrISIN));

        //Sender, Target and Date/Time
        er.getHeader().setField(new SenderCompID("SISTEMA"));
        er.getHeader().setField(new TargetCompID("DROPCOPY"));
        er.getHeader().setField(new DeliverToCompID("GATEWAY"));
        er.getHeader().setField(new SendingTime(LocalDateTime.now(ZoneId.of("UTC"))));
        er.getHeader().setField(new TransactTime(LocalDateTime.now(ZoneId.of("UTC"))));

        //Buy or Sell
        Side side = new Side(Side.BUY);
        if (rand.nextInt(2) == 0){
            side = new Side(Side.SELL);
        }
        er.getHeader().setField(side);

        //Type of Order
        er.getHeader().setField(new ExecType(ExecType.TRADE));
        er.getHeader().setField(new MsgSeqNum(messageSeqNumber));
        er.getHeader().setField(new OrdType(OrdType.MARKET));
        er.getHeader().setField(new TimeInForce(TimeInForce.DAY));

        //Order Qty and Execution Status
        int orderQty, cumQty, lastQtyFilled = 0;
        if (totalExecution){
            er.getHeader().setField(new OrdStatus(OrdStatus.FILLED));
            orderQty = 1+rand.nextInt(10);
            lastQtyFilled = orderQty;
            cumQty = orderQty;
        }else{
            er.getHeader().setField(new OrdStatus(OrdStatus.PARTIALLY_FILLED));
            orderQty = 1+(10 - rand.nextInt(9));
            lastQtyFilled = 1+rand.nextInt(orderQty);
            cumQty = lastQtyFilled;
        }
        int leavesQty = orderQty - cumQty;
        er.set(new OrderQty(orderQty));
        er.set(new LastQty(lastQtyFilled));
        er.set(new CumQty(cumQty));
        er.set(new LeavesQty(leavesQty));

        //Client Accounts
        Integer enteringTrader = accounts.GetRandom();
        er.getHeader().setField(new NoPartyIDs(4));
        er.getHeader().setField(new PartyID(enteringTrader.toString()));
        er.getHeader().setField(new PartyIDSource(PartyIDSource.PROPRIETARY_CUSTOM_CODE));
        er.getHeader().setField(new PartyRole(PartyRole.EXECUTING_TRADER));

        //Order Number (keeping the same of the client for simplicity)
        Integer orderId = 1+rand.nextInt(10^12);
        Integer clientOrderId = orderId;
        Integer execId = 1+rand.nextInt(10^12);
        er.set(new ClOrdID(clientOrderId.toString()));
        er.set(new OrderID(orderId.toString()));
        er.set(new ExecID(execId.toString()));

        //Order Prices
        double lastFillPrice = 1+rand.nextDouble();
        double price = lastFillPrice;
        er.set(new AvgPx(0));
        er.set(new LastPx(lastFillPrice));
        er.set(new Price(price));

        er.set(new quickfix.field.Account(enteringTrader.toString()));
        er.set(new SecurityIDSource("8"));

        return er;
    }
}
