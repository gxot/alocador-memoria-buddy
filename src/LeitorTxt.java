
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeitorTxt {

    public static class ResultadoLeitura {
        public Programa[] programas;
        public int qtd;
    }

    public ResultadoLeitura lerTxt(String caminho) {
        ArrayList<Programa> programas = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(caminho));
            String linha;
            while ((linha = br.readLine()) != null) {

                linha = linha.trim();

                if (linha.isEmpty()) continue;

                String[] partes = linha.split(" ");
                if (partes.length >= 2) {
                    String rotulo = partes[0];
                    int tamanho = Integer.parseInt(partes[1]);
                    programas.add(new Programa(rotulo, tamanho));
                }

            }
        } catch (IOException e) {
            System.out.println("Erro lendo arquivo: " + e.getMessage());
        }

        Programa[] array = new Programa[programas.size()];
        for (int i = 0; i < programas.size(); i++) array[i] = programas.get(i);

        ResultadoLeitura resultadoLeitura = new ResultadoLeitura();
        resultadoLeitura.programas = array;
        resultadoLeitura.qtd = programas.size();
        return resultadoLeitura;
    }
}
