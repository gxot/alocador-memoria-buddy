

public class AlocadorMemoriaBuddy {

    static final int MEMORIA_TOTAL = 4 * 1024 * 1024; // 4 MB
    static final int BLOCO_MIN = 1024; // 1 KB

    static BlocoMemoria[] livres = new BlocoMemoria[5000];
    static BlocoMemoria[] ocupados = new BlocoMemoria[5000];

    static int qtdLivres = 0;
    static int qtdOcupados = 0;

    public static void main(String[] args) {

        BlocoMemoria inicial = new BlocoMemoria();
        inicial.inicio = 0;
        inicial.tamanho = MEMORIA_TOTAL;
        inicial.livre = true;
        inicial.rotulo = "";
        inicial.tamanhoReal = 0;

        livres[0] = inicial;
        qtdLivres = 1;

        LeitorTxt leitor = new LeitorTxt();
        LeitorTxt.ResultadoLeitura resultado = leitor.lerTxt("src/programas.txt"); // caminho exemplo
        Programa[] programas = resultado.programas;
        int qtdProgramas = resultado.qtd;

        int indice = 0;
        while (indice < qtdProgramas) {

            Programa programa = programas[indice];

            int tamanhoSolicitado = programa.getTamanho() * 1024;
            String rotulo = programa.getNome();

            boolean memoriaLivre = alocarPorRotulo(rotulo, tamanhoSolicitado);
            if (!memoriaLivre) {
                System.out.println("Sem memória para programa " + rotulo + " (" + programa.getTamanho() + " KB)");
            }

            indice++;
        }

        imprimirResultadoFinal();
    }

    public static boolean alocarPorRotulo(String rotulo, int tamanhoReal) {
        int potencia = proximaPotencia(tamanhoReal);
        int indice = encontrarBlocoMenorQueCabe(potencia);
        if (indice == -1) return false;

        dividirBloco(indice, potencia);

        BlocoMemoria blocoMemoria = livres[indice];
        blocoMemoria.livre = false;
        blocoMemoria.rotulo = rotulo;
        blocoMemoria.tamanhoReal = tamanhoReal;

        ocupados[qtdOcupados] = blocoMemoria;
        qtdOcupados++;

        removerLivre(indice);
        return true;
    }

    public static int proximaPotencia(int tamanho) {
        int potencia = BLOCO_MIN;
        while (potencia < tamanho) potencia = potencia * 2;
        return potencia;
    }

    public static int encontrarBlocoMenorQueCabe(int potencia) {
        int i = 0;
        int melhorIndice = -1;
        int melhorTamanho = Integer.MAX_VALUE;

        while (i < qtdLivres) {
            BlocoMemoria blocoMemoria = livres[i];
            if (blocoMemoria.livre && blocoMemoria.tamanho >= potencia) {
                if (blocoMemoria.tamanho < melhorTamanho) {
                    melhorTamanho = blocoMemoria.tamanho;
                    melhorIndice = i;
                    if (blocoMemoria.tamanho == potencia) break;
                }
            }
            i++;
        }

        return melhorIndice;
    }

    public static void dividirBloco(int indice, int tamanhoFinal) {
        while (livres[indice].tamanho > tamanhoFinal) {
            int novoTamanho = livres[indice].tamanho / 2;

            BlocoMemoria blocoMemoria1 = new BlocoMemoria();
            blocoMemoria1.inicio = livres[indice].inicio;
            blocoMemoria1.tamanho = novoTamanho;
            blocoMemoria1.livre = true;
            blocoMemoria1.rotulo = "";
            blocoMemoria1.tamanhoReal = 0;

            BlocoMemoria blocoMemoria2 = new BlocoMemoria();
            blocoMemoria2.inicio = livres[indice].inicio + novoTamanho;
            blocoMemoria2.tamanho = novoTamanho;
            blocoMemoria2.livre = true;
            blocoMemoria2.rotulo = "";
            blocoMemoria2.tamanhoReal = 0;

            livres[indice] = blocoMemoria1;
            livres[qtdLivres] = blocoMemoria2;
            qtdLivres++;
        }
    }

    public static void removerLivre(int i) {

        while (i < qtdLivres - 1) {
            livres[i] = livres[i + 1];
            i++;
        }
        qtdLivres--;
    }


    public static void imprimirResultadoFinal() {
        System.out.println("\n=== BLOCOS ALOCADOS ===");
        int i = 0;
        int totalAlocado = 0;
        while (i < qtdOcupados) {
            BlocoMemoria blocoMemoria = ocupados[i];
            System.out.println("Programa " + blocoMemoria.rotulo +
                    " | tamanho real (bytes) = " + blocoMemoria.tamanhoReal +
                    " | bloco alocado (bytes) = " + blocoMemoria.tamanho +
                    " | inicio = " + blocoMemoria.inicio);
            totalAlocado += blocoMemoria.tamanho;
            i++;
        }

        int livreTotal = 0;
        int fragmentos = 0;
        System.out.println("\n=== BLOCOS LIVRES ===");
        i = 0;
        while (i < qtdLivres) {
            BlocoMemoria blocoMemoriaLivre = livres[i];
            if (blocoMemoriaLivre.livre) {
                System.out.println("Bloco livre | tamanho = " + blocoMemoriaLivre.tamanho + " | inicio = " + blocoMemoriaLivre.inicio);
                livreTotal += blocoMemoriaLivre.tamanho;
                fragmentos++;
            }
            i++;
        }

        System.out.println("\nTotal livre (bytes): " + livreTotal);
        System.out.println("Fragmentos livres: " + fragmentos);
        System.out.println("Total ocupado em blocos (bytes): " + totalAlocado);
        System.out.println("Total geral (deve ser 4MB): " + (livreTotal + totalAlocado));
    }
}
