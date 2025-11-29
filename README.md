# Alocador Memória Buddy

## Como compilar Com Terminal
- Localize a pasta src
- Abra o terminal nesta pasta e digite: javac *.java
- Para executar digite: java AlocadorMemoriaBuddy

## Como compilar Com IntelliJ
- Abra o projeto na sua IDE
- Localize o arquivo AlocadorMemoriaBuddy dentro da pasta src
- Aperte o botão verdinho no canto superior direito para rodar a aplicação

## Regras de Formatação do arquivo .txt de entrada  
- Um "programa" por linha
- O "programa" deve seguir o seguinte padrão: {nome_do_programa} {tamanho_em_kb}
- Em caso de dúvida consultar o exemplo localizado no projeto (programas.txt)

## Ideia do alocador buddy

O Buddy System é um algoritmo de gerenciamento de memória que organiza a memória em blocos de tamanhos que são sempre potências de 2.
Ele é eficiente, rápido, reduz fragmentação e permite que a memória seja dividida e fundida dinamicamente.

### Ideia Geral

- A memória total (por exemplo, 4 MB) é tratada como um grande bloco inicial.
Quando um programa precisa ser alocado, o sistema:

- Encontra o menor bloco livre que possa armazenar o programa
(por exemplo, se o programa tem 3 KB → precisa de um bloco de 4 KB).

- Divide esse bloco ao meio repetidas vezes, até alcançar o tamanho ideal.

- Cada divisão gera dois novos blocos chamados buddies.

Quando um programa é removido, o sistema tenta recombinar (coalescer) o bloco com seu buddy se ambos estiverem livres.

### Estrutura de dados usada

Os blocos de memória foram representados pela classe:

public class BlocoMemoria {
    public int inicio;
    public int tamanho;
    public boolean livre;
    public String rotulo;
    public int tamanhoReal;
}

Cada bloco contém:

inicio → posição inicial na memória
tamanho → tamanho do bloco (sempre potência de 2)
livre → identifica se o bloco está disponível
rotulo → nome do programa
tamanhoReal → tamanho solicitado pelo programa 

Os programas foram representados pela classe:

public class Programa {
    private String nome;
    private int tamanho;
}

Cada programa contém:

rotulo → nome do programa
tamanho → tamanho exigido pelo programa

## Estratégia de alocação

O projeto usa Best Fit, que funciona assim:

- Verifica cada bloco livre.
- Seleciona o menor bloco cujo tamanho é suficiente para comportar o programa.
- Se o bloco for maior que necessário, aplica-se o Buddy System dividindo ao meio repetidamente até atingir o bloco ideal (potência de 2 mais próxima).
- Isso minimiza fragmentação interna e aproveita melhor o espaço
