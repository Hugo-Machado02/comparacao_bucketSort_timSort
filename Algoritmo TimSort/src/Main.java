import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        int[] array = lerArquivo("src/numeros_500_mil.txt");

        if(array != null){
            long opInicio, opFim;
            int[] arrayAux = new int[array.length];
            int[] movimentos = {0};
            int[] comparacoes = {0};

            opInicio = System.currentTimeMillis();

            TimSort(array, arrayAux, array.length, movimentos, comparacoes);
            opFim = System.currentTimeMillis();

            String tempo_validado = calcTempo(opFim - opInicio);

            System.out.println("Array ordenado: " + Arrays.toString(array));
            System.out.println("Aluno: Hugo Machado Mendes da Silva");
            System.out.println("Algoritmo: TimSort");
            System.out.println("Tempo de Execucao: " + tempo_validado);
            System.out.println("Quantidade de comparações: " + comparacoes[0]);
            System.out.println("Quantidade de movimentos: " + movimentos[0]);

        }
        else{
            System.out.println("Array Não encontrado");
        }
    }

    public static void TimSort(int[] array, int[] arrayAux, int tamanho, int[] movimentos, int[] comparacoes){
        int tamMinimo = tamanoMinimo(tamanho);

        for(int i = 0; i < tamanho; i += tamMinimo) {
            int delimitador = Math.min(i + tamMinimo - 1, tamanho - 1);
            insertionSort(array, i, delimitador, movimentos, comparacoes);
        }

        for (int i = tamMinimo; i < tamanho; i *= 2) {
            for (int esquerda = 0; esquerda < tamanho; esquerda += 2 * i) {
                int mid = esquerda + i - 1;
                int direira = Math.min((esquerda + 2 * i - 1), (tamanho - 1));
                merge(array, arrayAux, esquerda, mid, direira, movimentos, comparacoes);
            }
        }
    }

    private static void insertionSort(int[] array, int valsEsquerda, int valsDireita, int[] movimentos, int[] comparacoes) {
        for (int i = valsEsquerda + 1; i <= valsDireita; i++) {
            int aux = array[i];
            int j = i - 1;
            while (j >= valsEsquerda && array[j] > aux) {
                array[j + 1] = array[j];
                j--;
                movimentos[0]++;
                comparacoes[0]++;
            }
            if (j >= valsEsquerda) {
                comparacoes[0]++; // Conta a comparação de saída do loop
            }
            array[j + 1] = aux;
        }
    }

    private static void merge(int[] array, int[] arrayAux, int indexIni, int indexMeio, int indexFim, int[] movimentos, int[] comparacoes) {
        for(int i = indexIni; i <= indexFim; i++) {
            arrayAux[i] = array[i];
        }

        int inicio = indexIni;
        int meio = indexMeio + 1;

        for(int i = indexIni; i <= indexFim; i++){
            if(inicio > indexMeio){
                array[i] = arrayAux[meio++];
            }
            else if(meio > indexFim){
                array[i] = arrayAux[inicio++];
            }
            else if(arrayAux[inicio] < arrayAux[meio]){
                array[i] = arrayAux[inicio++];
            }
            else{
                array[i] = arrayAux[meio++];
                movimentos[0]++; // Incrementa o contador de movimentos
            }
            comparacoes[0]++; // Incrementa a contagem de comparações
        }
    }

    private static int tamanoMinimo(int tamanho) {
        int aux = 0;
        while (tamanho >= 20) {
            aux |= (tamanho & 1);
            tamanho >>= 1;
        }
        return tamanho + aux;
    }

    public static int[] lerArquivo(String url) throws IOException {
        Path arquivo = Path.of(url);

        if(Files.notExists(arquivo)){
            return null;
        }
        else {
            String valoresString = Files.readString(arquivo);
            String formataValor = valoresString.replace("[", "").replace("]", "").replace(" ", "");

            String[] arrayString = formataValor.split(",");

            int[] arrayInt = new int[arrayString.length];

            for(int i = 0; i < arrayString.length; i++){
                arrayInt[i] = Integer.valueOf(arrayString[i]);
            }

            return arrayInt;
        }
    }

    public static String calcTempo(long total){
        long opHr, opMin, opSeg, opMils;

        opMils = total % 1000;
        total /= 1000;
        opHr = total / 3600;
        total %= 3600;
        opMin = total / 60;
        opSeg = total % 60;

        return opHr + ":" + opMin + ":" + opSeg + ":"+opMils;
    }


}
