import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * UVA 315 - Network
 *
 * Problema: dada uma rede de lugares ligados por linhas telefônicas,
 * contar quantos lugares são "críticos", isto é, quantos lugares, ao
 * pararem de funcionar, deixam outros lugares sem conseguir se comunicar.
 * Em teoria dos grafos, isso é contar os PONTOS DE ARTICULAÇÃO.
 *
 * Reuso do algs4 (Sedgewick & Wayne): Bag, Graph e Biconnected.
 * As três classes foram copiadas para este arquivo porque o UVA aceita
 * apenas um arquivo, sem package, com a classe pública chamada Main.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder saida = new StringBuilder();

        String linha;

        while ((linha = proximaLinhaNaoVazia(br)) != null) {

            int n = Integer.parseInt(linha.trim());

            if (n == 0) break;

            Graph grafo = new Graph(n + 1);

            while ((linha = proximaLinhaNaoVazia(br)) != null) {
                StringTokenizer st = new StringTokenizer(linha);

                int origem = Integer.parseInt(st.nextToken());

                if (origem == 0) break;

                while (st.hasMoreTokens()) {
                    int destino = Integer.parseInt(st.nextToken());
                    grafo.addEdge(origem, destino);
                }
            }

            Biconnected bc = new Biconnected(grafo);

            int criticos = 0;
            for (int v = 1; v <= n; v++) {
                if (bc.isArticulation(v)) criticos++;
            }

            saida.append(criticos).append('\n');
        }

        System.out.print(saida);
    }

    private static String proximaLinhaNaoVazia(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return null;
    }
}

/** 
 *  algs4.Bag (reutilizada sem alteração de lógica)                    
 *  coleção simples (sem ordem) que guarda os vizinhos de cada vértice 
 */
class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() { return first == null; }

    public int size() { return n; }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Iterator<Item> iterator() { return new LinkedIterator(first); }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) { current = first; }

        public boolean hasNext() { return current != null; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("nao ha mais elementos para percorrer");
            Item item = current.item; 
            current = current.next;    
            return item;
        }
    }
}

/** 
 *  algs4.Graph (reduzida: sem In, Stack, toString e toDot)            
 *  grafo não direcionado com lista de adjacência                      
 */
class Graph {
    private final int V;          
    private int E;                
    private Bag<Integer>[] adj;   

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("o numero de vertices nao pode ser negativo");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public int V() { return V; }

    public int E() { return E; }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("o vertice " + v + " nao esta entre 0 e " + (V - 1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w); 
        adj[w].add(v); 
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
}

/** 
 *  algs4.Biconnected                                     
 *  encontra os pontos de articulacao usando DFS com preorder e low
 */
class Biconnected {
    private final int[] low;
    private final int[] preorder;     
    private final boolean[] articulation;
    private int preorderCounter;       

    public Biconnected(Graph graph) {
        if (graph == null) throw new IllegalArgumentException("o grafo informado e nulo");

        low = new int[graph.V()];
        preorder = new int[graph.V()];
        articulation = new boolean[graph.V()];

        for (int vertex = 0; vertex < graph.V(); vertex++) {
            low[vertex] = -1;
            preorder[vertex] = -1;
        }

        for (int vertex = 0; vertex < graph.V(); vertex++) {
            if (preorder[vertex] == -1) {
                dfs(graph, vertex, vertex);
            }
        }
    }

    private void dfs(Graph graph, int parent, int vertex) {
        int children = 0;

        preorder[vertex] = preorderCounter++;
        low[vertex] = preorder[vertex];

        for (int adjacent : graph.adj(vertex)) {

            if (preorder[adjacent] == -1) {
                // vizinho ainda não visitado vira filho de 'vertex' na árvore da DFS
                children++;
                dfs(graph, vertex, adjacent);

                // ao voltar da recursão, 'vertex' herda o menor low encontrado no filho
                low[vertex] = Math.min(low[vertex], low[adjacent]);

                // se 'vertex' não é raiz e o filho não consegue chegar a nenhum vértice
                // anterior a 'vertex' sem passar por ele, 'vertex' é crítico
                if (parent != vertex && low[adjacent] >= preorder[vertex]) {
                    articulation[vertex] = true;
                }
            }
            else if (adjacent != parent) {
                // vizinho já visitado que não é o pai: aresta de retorno (atalho).
                // atualiza o low de 'vertex' com a ordem de visita desse vizinho
                low[vertex] = Math.min(low[vertex], preorder[adjacent]);
            }
            // se 'adjacent' é o pai, ignora: voltar pela aresta que nos trouxe não é atalho
        }

        // crítica se tiver 2 ou mais filhos na árvore da DFS
        if (parent == vertex && children > 1) {
            articulation[vertex] = true;
        }
    }

    // retorna true se o vértice é um ponto de articulacao.
    public boolean isArticulation(int vertex) {
        validateVertex(vertex);
        return articulation[vertex];
    }

    // garante que o vértice consultado existe
    private void validateVertex(int vertex) {
        int vertices = articulation.length;
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException(
                "o vertice " + vertex + " nao esta entre 0 e " + (vertices - 1));
        }
    }
}