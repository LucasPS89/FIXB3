import quickfix.*;
import java.io.IOException;

public class FIXB3 {
    public static void main(String[] args) throws ConfigError, IOException, ClassNotFoundException, FieldNotFound {
        //Parte1 do Teste
        System.out.println("Executando PARTE 1");
        long p1_start = System.currentTimeMillis();
        Parte1 p1 = new Parte1();
        p1.GenerateExecutionReports();
        long p1_total = System.currentTimeMillis() - p1_start;
        System.out.println(String.format("Concluída PARTE 1. Tempo gasto (ms): %s", p1_total));

        //Parte2 do Teste
        System.out.println("Executando PARTE 2");
        long p2_start = System.currentTimeMillis();
        Parte2 p2 = new Parte2();
        p2.ProcessMessages();
        long p2_total = System.currentTimeMillis() - p2_start;
        System.out.println(String.format("Concluída PARTE 2. Tempo gasto (ms): %s", p2_total));


        //Parte3 do Teste
        System.out.println("Executando PARTE 3");
        long p3_start = System.currentTimeMillis();
        Parte3 p3 = new Parte3();
        p3.CompareFiles();
        long p3_total = System.currentTimeMillis() - p3_start;
        System.out.println(String.format("Concluída PARTE 3. Tempo gasto (ms): %s", p3_total));


        //Termino do processo
        System.out.println("Processo encerrado");


    }
}
