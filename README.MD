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
java -jar FIXB31.0-shapshot.jar
```

#### Parâmetros de execução
Caso deseje executar apenas um ou mais partes do projeto, basta passar como parametro o número das partes que deseja executar.
Por exemplo:

```bash
java -jar FIXB31.0-shapshot.jar 1 2 
```