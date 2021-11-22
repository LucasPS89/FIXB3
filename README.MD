# FIXB3

Exemplo de como gerar mensagens ExecutionReport do Protocolo FIX com QuickFix/J.
Programa Gera mensagens aleatórias.
O programa é composto por 3 partes:

Parte 1: Gerar mensagens randomincas do tipo ExecutionReport e Serializa lista em um arquivo;

Parte 2: Carrega mensagens geradas na parte 1 para memória e grava em dois arquivos (AllMsgs.CSV e FullFill.TXT);

Parte 3: Agrupa os dados dos dois arquivos da parte anterior e compara verifica se os dados estão consistentes; 

### Requisitos
Certifique-se de ter o Maven adicionado à sua variável de ambiente MAVEN_HOME
e o java adicionado à variavel de ambiente JAVA_HOME.
Caso tenha dúvidas, acesse o seguinte site para ter um tutorial: https://mkyong.com/maven/how-to-install-maven-in-windows/


### Compilação

Abra o Command dentro da raiz do projeto e execute o seguinte comando:

```bash
mvn install
```
O Maven ira criar o build dentro do diretório  "target".

### Execução
Com o projeto compilado, navegue para o diretório target através do comando:

```bash
cd target
```

Uma vez dentro do diretório, execute o projeto através do comando:

```bash
java -jar FIXB3.jar
```

#### Parâmetros de execução
Por padrão o programa executará todos os passos de uma só vez.
Caso deseje executar apenas um ou mais partes do projeto, basta passar como parametro o número das partes que deseja executar.

Por exemplo, caso queira executar apenas os passos 1 e 2:

```bash
java -jar FIXB3.jar 1 2 
```

### Execução sem compilação
Caso queira executar o código sem compilar, diretamente pela IDE, execute arquivo FIXB3.java diretamente pela IDE.
É recomendado fazer a compilação através do Maven e executar o arquivo Jar ao invés de executar pela IDE.

O programa foi desenvolvido utilizando a IDE IntelliJ. Caso use NetBeans ou Eclipe, podem ocorrer problemas de compatilidade.

## Pontos que não foram alcaçados da forma prevista
- Item 1.2 e 1.3: De acordo com o documento da B3, o campo AvgPx sempre tem valor fixo 0 (zero). Foi utilizado o campo LastPx no lugar;
- Item 2.1 e 2.2: tag 1010 e 1011 não foram encontradas na lista de Tags do FIX Protocol. Esse tratamento foi ignorado.
- Item 2.3: Por falta de especificação, arquivo FullFill.txt foi escrito no mesmo **formato** do arquivo AllMsgs.CSV 
