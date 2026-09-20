import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * beecrowd 1100 - Movimentos do Cavalo
 *
 * Mesma solucao (BFS) do Marco 3/4, porem agora usando a MESMA estrutura
 * das classes Graph.java e BreadthFirstPaths.java da biblioteca algs4
 * (Sedgewick & Wayne), que e a biblioteca usada em aula. Os metodos
 * publicos (V(), addEdge, adj(v), hasPathTo(v), distTo(v)) tem o mesmo
 * nome e o mesmo comportamento das classes originais.
 *
 * A unica diferenca e que aqui elas foram colocadas como classes
 * internas (static) dentro de Main, e a fila interna usa
 * java.util.ArrayDeque em vez da classe Queue.java do algs4 - isso foi
 * necessario porque o beecrowd so aceita UM arquivo .java por envio,
 * entao nao e possivel importar algs4.jar ou os demais arquivos
 * (Bag.java, Queue.java, In.java...) separadamente. A logica do BFS
 * em si e identica a original.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Monta o grafo do problema UMA UNICA VEZ: 64 vertices (casas do
        // tabuleiro) e uma aresta para cada movimento de cavalo valido.
        Graph tabuleiro = construirGrafoDoCavalo();

        // A entrada contem um ou mais casos de teste, cada um com duas casas
        while (sc.hasNext()) {
            String origem = sc.next();
            String destino = sc.next();

            int s = paraVertice(origem);
            int t = paraVertice(destino);

            // Igual ao "main" de exemplo do BreadthFirstPaths.java:
            // roda o BFS a partir de "s" e depois consulta distTo(t).
            BreadthFirstPaths bfs = new BreadthFirstPaths(tabuleiro, s);
            int movimentos = bfs.distTo(t);

            System.out.println("To get from " + origem + " to " + destino
                    + " takes " + movimentos + " knight moves.");
        }
    }

    /**
     * Constroi o grafo do problema (representacao explicita, como pede a
     * classe Graph do algs4): 64 vertices numerados de 0 a 63 (linha*8 +
     * coluna), e uma aresta v-w para cada movimento de cavalo valido.
     * Os deslocamentos usados sao os mesmos do Marco 2.
     */
    static Graph construirGrafoDoCavalo() {
        final int N = 8;
        final int[] DLINHA  = {+2, +2, -2, -2, +1, +1, -1, -1};
        final int[] DCOLUNA = {+1, -1, +1, -1, +2, -2, +2, -2};

        Graph grafo = new Graph(N * N);

        for (int linha = 0; linha < N; linha++) {
            for (int coluna = 0; coluna < N; coluna++) {
                int v = linha * N + coluna;
                for (int i = 0; i < 8; i++) {
                    int novaLinha  = linha  + DLINHA[i];
                    int novaColuna = coluna + DCOLUNA[i];
                    if (novaLinha >= 0 && novaLinha < N && novaColuna >= 0 && novaColuna < N) {
                        int w = novaLinha * N + novaColuna;
                        // so adiciona uma vez por par (v < w evita aresta duplicada)
                        if (v < w) {
                            grafo.addEdge(v, w);
                        }
                    }
                }
            }
        }
        return grafo;
    }

    /** Converte uma casa como "a1" no numero do vertice (0 a 63). */
    static int paraVertice(String casa) {
        int coluna = casa.charAt(0) - 'a';
        int linha  = casa.charAt(1) - '1';
        return linha * 8 + coluna;
    }

    // ---------------------------------------------------------------
    // A partir daqui: mesma API de Graph.java e BreadthFirstPaths.java
    // do algs4 (Sedgewick & Wayne), reunidas neste unico arquivo.
    // ---------------------------------------------------------------

    /** Equivalente a algs4.Graph: grafo nao direcionado por lista de adjacencia. */
    static class Graph {
        private final int V;
        private int E;
        private final List<Integer>[] adj;

        @SuppressWarnings("unchecked")
        Graph(int V) {
            this.V = V;
            this.E = 0;
            adj = new List[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<>();
            }
        }

        int V() { return V; }

        int E() { return E; }

        void addEdge(int v, int w) {
            E++;
            adj[v].add(w);
            adj[w].add(v);
        }

        Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    /** Equivalente a algs4.BreadthFirstPaths: BFS a partir de um vertice fonte s. */
    static class BreadthFirstPaths {
        private static final int INFINITO = Integer.MAX_VALUE;
        private final boolean[] marked; // marked[v] = existe caminho s-v?
        private final int[] edgeTo;     // edgeTo[v] = vertice anterior no caminho minimo
        private final int[] distTo;     // distTo[v] = numero de arestas no caminho minimo

        BreadthFirstPaths(Graph graph, int s) {
            marked = new boolean[graph.V()];
            distTo = new int[graph.V()];
            edgeTo = new int[graph.V()];
            bfs(graph, s);
        }

        private void bfs(Graph graph, int s) {
            Queue<Integer> fila = new ArrayDeque<>();
            for (int v = 0; v < graph.V(); v++) {
                distTo[v] = INFINITO;
            }
            distTo[s] = 0;
            marked[s] = true;
            fila.add(s);

            while (!fila.isEmpty()) {
                int v = fila.poll();
                for (int w : graph.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        fila.add(w);
                    }
                }
            }
        }

        boolean hasPathTo(int v) {
            return marked[v];
        }

        int distTo(int v) {
            return distTo[v];
        }
    }
}
