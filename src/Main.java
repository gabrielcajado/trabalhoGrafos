import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Queue;

/*
 * Beecrowd 1100 - Knight Moves
 *
 * Modelagem (Marco 1):
 *   - Vertices: as 64 casas do tabuleiro (pares coluna, linha).
 *   - Arestas: duas casas sao ligadas quando um cavalo realiza
 *     um unico movimento entre elas.
 *   - Grafo nao direcionado e nao ponderado -> menor caminho = BFS.
 *
 * Representacao computacional (Marco 2):
 *   - Representacao IMPLICITA: nao existe matriz nem lista de
 *     adjacencia armazenada. Os vizinhos de uma casa sao gerados
 *     sob demanda pelo metodo gerarVizinhos(), aplicando os 8
 *     deslocamentos do cavalo e descartando os que saem do tabuleiro.
 *
 * Algoritmo (Marcos 3 e 4):
 *   - BFS a partir da casa de origem, ate alcancar a casa de destino.
 *   - BFS garante distancia minima em grafo nao ponderado (diferente
 *     de uma DFS, que apenas garante alcancabilidade).
 */
public class Main {


        // Tamanho fixo do tabuleiro (8x8), conforme restricao do enunciado.
        static final int TAMANHO = 8;

        // Os 8 deslocamentos possiveis do movimento do cavalo.
        // Cada linha e um par (deltaColuna, deltaLinha).
        static final int[][] MOVIMENTOS = {
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
        };

        public static void main (String[]args){
            Scanner entrada = new Scanner(System.in);
            StringBuilder saida = new StringBuilder();

            // A entrada tem um ou mais casos de teste, um por linha,
            // ate o fim do arquivo (EOF).
            while (entrada.hasNext()) {
                String origemStr = entrada.next();
                String destinoStr = entrada.next();

                // Converte a notacao de xadrez (ex: "a1") em coordenadas
                // inteiras (coluna, linha), ambas de 0 a 7.
                int[] origem = converterCasa(origemStr);
                int[] destino = converterCasa(destinoStr);

                int movimentos = bfs(origem, destino);

                saida.append("To get from ").append(origemStr)
                        .append(" to ").append(destinoStr)
                        .append(" takes ").append(movimentos)
                        .append(" knight moves.\n");
            }

            System.out.print(saida);
            entrada.close();
        }

        /**
         * Converte uma casa no formato "a1"-"h8" para coordenadas inteiras.
         * Letra -> coluna (a=0, ..., h=7); digito -> linha (1=0, ..., 8=7).
         */
        static int[] converterCasa (String casa){
            int coluna = casa.charAt(0) - 'a';
            int linha = casa.charAt(1) - '1';
            return new int[]{coluna, linha};
        }

        /**
         * Gera os vizinhos validos de uma casa (coluna, linha), aplicando
         * os 8 deslocamentos do cavalo e descartando os que caem fora
         * do tabuleiro. Isso é a "representacao implicita": os vizinhos
         * nao estao guardados em lugar nenhum, sao calculados aqui,
         * toda vez que a BFS precisa deles.
         */
        static int[][] gerarVizinhos ( int coluna, int linha){
            int[][] candidatos = new int[8][2];
            int total = 0;

            for (int[] deslocamento : MOVIMENTOS) {
                int novaColuna = coluna + deslocamento[0];
                int novaLinha = linha + deslocamento[1];

                boolean dentroDoTabuleiro =
                        novaColuna >= 0 && novaColuna < TAMANHO &&
                                novaLinha >= 0 && novaLinha < TAMANHO;

                if (dentroDoTabuleiro) {
                    candidatos[total][0] = novaColuna;
                    candidatos[total][1] = novaLinha;
                    total++;
                }
            }

            // Retorna apenas as posicoes realmente preenchidas.
            int[][] vizinhos = new int[total][2];
            System.arraycopy(candidatos, 0, vizinhos, 0, total);
            return vizinhos;
        }

        /**
         * BFS que calcula o numero minimo de movimentos de cavalo
         * entre a casa de origem e a casa de destino.
         */
        static int bfs ( int[] origem, int[] destino){
            // dist[coluna][linha] guarda a distancia (numero de movimentos)
            // ate aquela casa. -1 significa "ainda nao visitada".
            int[][] dist = new int[TAMANHO][TAMANHO];
            for (int[] linha : dist) {
                java.util.Arrays.fill(linha, -1);
            }

            Queue<int[]> fila = new ArrayDeque<>();

            dist[origem[0]][origem[1]] = 0;
            fila.add(origem);

            while (!fila.isEmpty()) {
                int[] atual = fila.poll();

                // Parada antecipada: assim que o destino e retirado da
                // fila, sua distancia ja e a distancia minima final.
                if (atual[0] == destino[0] && atual[1] == destino[1]) {
                    return dist[atual[0]][atual[1]];
                }

                // Gera os vizinhos sob demanda (representacao implicita).
                for (int[] vizinho : gerarVizinhos(atual[0], atual[1])) {
                    int vc = vizinho[0];
                    int vl = vizinho[1];

                    if (dist[vc][vl] == -1) { // ainda nao visitado
                        dist[vc][vl] = dist[atual[0]][atual[1]] + 1;
                        fila.add(vizinho);
                    }
                }
            }

            // Nao deveria acontecer no tabuleiro 8x8 real (grafo conexo),
            // mas fica como salvaguarda.
            return dist[destino[0]][destino[1]];
        }

}