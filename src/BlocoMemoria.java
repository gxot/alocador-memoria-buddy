public class BlocoMemoria {
    public int inicio;
    public int tamanho;
    public boolean livre;
    public String rotulo;
    public int tamanhoReal;

    public BlocoMemoria() {
        this.inicio = 0;
        this.tamanho = 0;
        this.livre = true;
        this.rotulo = "";
        this.tamanhoReal = 0;
    }
}