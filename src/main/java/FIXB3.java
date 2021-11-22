import quickfix.*;
import java.io.IOException;

public class FIXB3 {
    public static void main(String[] args) throws ConfigError, IOException, ClassNotFoundException, FieldNotFound {
        //Parte1 do Teste
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Executando PARTE 1");
        long p1_start = System.currentTimeMillis();
        Parte1 p1 = new Parte1();
        p1.GenerateExecutionReports();
        long p1_total = System.currentTimeMillis() - p1_start;
        System.out.println(String.format("Concluída PARTE 1. Tempo gasto: %s (ms) | %s (s)", p1_total, p1_total/1000));

        //Parte2 do Teste
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Executando PARTE 2");
        long p2_start = System.currentTimeMillis();
        Parte2 p2 = new Parte2();
        p2.ProcessMessages();
        long p2_total = System.currentTimeMillis() - p2_start;
        System.out.println(String.format("Concluída PARTE 2. Tempo gasto: %s(ms) | %s(s)", p2_total, p2_total/1000));


        //Parte3 do Teste
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Executando PARTE 3");
        long p3_start = System.currentTimeMillis();
        Parte3 p3 = new Parte3();
        p3.CompareFiles();
        long p3_total = System.currentTimeMillis() - p3_start;
        System.out.println(String.format("Concluída PARTE 3. Tempo gasto: %s(ms) | %s(s)", p3_total, p3_total/1000));


        //Termino do processo
        long total = System.currentTimeMillis() - p1_start;
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Processo encerrado.");
        System.out.println(String.format("Tempo gasto no total: %s(ms) | %s(s)", total, total/1000));
        System.out.println("Resultado printado no arquivo ./data/Result.txt");
    }
}
