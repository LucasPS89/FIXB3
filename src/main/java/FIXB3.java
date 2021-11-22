import quickfix.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main Class of the project
 */

public class FIXB3 {
    public static void main(String[] args) throws ConfigError, IOException, ClassNotFoundException, FieldNotFound {
        //Trata argumentos caso o usuário queira executar apenas uma parte por execução
        Boolean exec1, exec2, exec3 ;
        exec1 = exec2 = exec3 = true;
        if (args.length > 0){
            if (Arrays.stream(args).anyMatch("1"::equals)) {exec1 = true;} else {exec1 = false;}
            if (Arrays.stream(args).anyMatch("2"::equals)) {exec2 = true;} else {exec2 = false;}
            if (Arrays.stream(args).anyMatch("3"::equals)) {exec3 = true;} else {exec3 = false;}
        }
        long start = System.currentTimeMillis();

        //Parte1 do Teste
        if (exec1) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Executando PARTE 1");
            Parte1 p1 = new Parte1();
            p1.GenerateExecutionReports();
            long p1_total = System.currentTimeMillis() - start;
            System.out.println(String.format("Concluida PARTE 1. Tempo gasto: %s(ms) | %s(s)", p1_total, p1_total / 1000));
        }

        //Parte2 do Teste
        if (exec2) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Executando PARTE 2");
            long p2_start = System.currentTimeMillis();
            Parte2 p2 = new Parte2();
            p2.ProcessMessages();
            long p2_total = System.currentTimeMillis() - p2_start;
            System.out.println(String.format("Concluida PARTE 2. Tempo gasto: %s(ms) | %s(s)", p2_total, p2_total / 1000));
        }

        //Parte3 do Teste
        if (exec3) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Executando PARTE 3");
            long p3_start = System.currentTimeMillis();
            Parte3 p3 = new Parte3();
            p3.CompareFiles();
            long p3_total = System.currentTimeMillis() - p3_start;
            System.out.println(String.format("Concluida PARTE 3. Tempo gasto: %s(ms) | %s(s)", p3_total, p3_total / 1000));
        }

        //Termino do processo
        long total = System.currentTimeMillis() - start;
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Processo encerrado.");
        System.out.println(String.format("Tempo gasto no total: %s(ms) | %s(s)", total, total/1000));
        if (exec3) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Resultado salvo no arquivo data/Result.txt");
            System.out.println("-------------------------------------------------------------------");
        }
    }
}
