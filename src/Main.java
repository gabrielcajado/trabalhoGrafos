import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * beecrowd 1100 - Movimentos do Cavalo
 *
 * Para cada par de casas (origem, destino), calcula o menor número de
 * movimentos que um cavalo de xadrez precisa para ir de uma até a outra,
 * usando BFS (Busca em Largura) sobre a representação implícita do grafo
 * do problema: cada casa é um vértice, e os vizinhos de uma casa são
 * gerados sob demanda aplicando os 8 deslocamentos possíveis do cavalo.
 *
 * BFS é o algoritmo certo aqui (e não DFS) porque ele explora o grafo
 * "em camadas" a partir da origem: primeiro todas as casas alcançaveis
 * em 1 movimento, depois em 2, e assim por diante. Isso garante que,
 * quando o destino é encontrado, o número de movimentos usado até ali
 * é, por construção, o menor possível.
 */
public class Main {

    static final int N = 8;

    static final int[] DLINHA  = {+2, +2, -2, -2, +1, +1, -1, -1};
    static final int[] DCOLUNA = {+1, -1, +1, -1, +2, -2, +2, -2};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String origem = sc.next();
            String destino = sc.next();

            int movimentos = bfs(origem, destino);

            System.out.println("To get from " + origem + " to " + destino
                    + " takes " + movimentos + " knight moves.");
        }
    }

    static int bfs(String origem, String destino) {

        int[][] distancia = new int[N][N];
        for (int[] linha : distancia) {
            java.util.Arrays.fill(linha, -1);
        }

        int[] ini = paraPosicao(origem);
        int[] fim = paraPosicao(destino);

        if (ini[0] == fim[0] && ini[1] == fim[1]) {
            return 0;
        }

        Queue<int[]> fila = new LinkedList<>();
        distancia[ini[0]][ini[1]] = 0;
        fila.add(ini);

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int linha  = atual[0];
            int coluna = atual[1];

            for (int i = 0; i < 8; i++) {
                int novaLinha  = linha  + DLINHA[i];
                int novaColuna = coluna + DCOLUNA[i];

                boolean dentroDoTabuleiro =
                        novaLinha >= 0 && novaLinha < N &&
                        novaColuna >= 0 && novaColuna < N;

                if (dentroDoTabuleiro && distancia[novaLinha][novaColuna] == -1) {
                    distancia[novaLinha][novaColuna] = distancia[linha][coluna] + 1;

                    if (novaLinha == fim[0] && novaColuna == fim[1]) {
                        return distancia[novaLinha][novaColuna];
                    }

                    fila.add(new int[]{novaLinha, novaColuna});
                }
            }
        }

        return distancia[fim[0]][fim[1]];
    }

    static int[] paraPosicao(String casa) {
        int coluna = casa.charAt(0) - 'a';
        int linha  = casa.charAt(1) - '1';
        return new int[]{linha, coluna};
    }
}
