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
 * apenas um arquivo, sem package, com a classe publica chamada Main.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder saida = new StringBuilder();

        String linha;

        // cada iteração deste laço processa um bloco (uma rede completa)
        while ((linha = proximaLinhaNaoVazia(br)) != null) {

            // quantidade de lugares
            int n = Integer.parseInt(linha.trim());

            // não há mais redes para processar
            if (n == 0) break;

            // cria o grafo com N+1 vértices: os lugares são numerados de 1 a N,
            // então o índice 0 fica sem uso (isolado) e será ignorado na contagem
            Graph grafo = new Graph(n + 1);

            while ((linha = proximaLinhaNaoVazia(br)) != null) {
                StringTokenizer st = new StringTokenizer(linha);

                // lugar de origem
                int origem = Integer.parseInt(st.nextToken());

                // termina a descrição desta rede
                if (origem == 0) break;

                // demais números da linha: lugares ligados diretamente a origem.
                // addEdge já cria a ligação nos dois sentidos (grafo não direcionado)
                while (st.hasMoreTokens()) {
                    int destino = Integer.parseInt(st.nextToken());
                    grafo.addEdge(origem, destino);
                }
            }

            // roda a DFS com preorder[]/low[] e marca os pontos de articulação
            Biconnected bc = new Biconnected(grafo);

            // conta quantos lugares de 1 a N foram marcados como críticos
            int criticos = 0;
            for (int v = 1; v <= n; v++) {
                if (bc.isArticulation(v)) criticos++;
            }

            // guarda a resposta deste bloco (uma linha por rede)
            saida.append(criticos).append('\n');
        }

        // imprime todas as respostas de uma vez
        System.out.print(saida);
    }

    // lê a próxima linha que tenha algum conteúdo, pulando linhas em branco. 
    // retorna null no fim da entrada.
    private static String proximaLinhaNaoVazia(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return null;
    }
}

/** 
 *  algs4.Bag (reutilizada sem alteracao de lógica)                    
 *  coleção simples (sem ordem) que guarda os vizinhos de cada vértice 
 */
class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int n;

    // nó da lista encadeada: um item e o ponteiro para o próximo nó
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // cria uma coleção vazia.
    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() { return first == null; }

    public int size() { return n; }

    // insere um item no início da lista (operacao O(1)).
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    // permite percorrer a colecao com for-each.
    public Iterator<Item> iterator() { return new LinkedIterator(first); }

    // iterador que anda do primeiro ao último nó
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

    // cria um grafo com V vértices (numerados de 0 a V-1) e nenhuma aresta.
    @SuppressWarnings("unchecked")
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("o numero de vertices nao pode ser negativo");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        // cada vértice comeca com uma lista de vizinhos vazia
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public int V() { return V; }

    public int E() { return E; }

    // garante que o vértice existe (0 <= v < V);
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("o vertice " + v + " nao esta entre 0 e " + (V - 1));
    }

    // adiciona a aresta v-w, registrando um como vizinho do outro.
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w); 
        adj[w].add(v); 
    }

    // retorna os vizinhos do vértice v.
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    // retorna o grau (quantidade de vizinhos) do vértice v.
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

        // marca todos os vértices como "nao visitados"
        for (int vertex = 0; vertex < graph.V(); vertex++) {
            low[vertex] = -1;
            preorder[vertex] = -1;
        }

        // roda uma DFS em cada vértice ainda não visitado
        // (cobre tambem grafos desconexos, como o vertice 0 isolado do nosso Main)
        for (int vertex = 0; vertex < graph.V(); vertex++) {
            if (preorder[vertex] == -1) {
                dfs(graph, vertex, vertex); // a raiz e pai de si mesma
            }
        }
    }

    // DFS recursiva: visita 'vertex' vindo de 'parent'
    private void dfs(Graph graph, int parent, int vertex) {
        int children = 0;

        preorder[vertex] = preorderCounter++;
        low[vertex] = preorder[vertex];

        for (int adjacent : graph.adj(vertex)) {

            if (preorder[adjacent] == -1) {
                // vizinho ainda nao visitado vira filho de 'vertex' na árvore da DFS
                children++;
                dfs(graph, vertex, adjacent);

                // ao voltar da recursão, 'vertex' herda o menor low encontrado no filho
                low[vertex] = Math.min(low[vertex], low[adjacent]);

                // se não é raiz e o filho não consegue chegar a nenhum vértice
                // anterior a 'vertex' sem passar por ele, 'vertex' e critico
                if (parent != vertex && low[adjacent] >= preorder[vertex]) {
                    articulation[vertex] = true;
                }
            }
            else if (adjacent != parent) {
                // vizinho ja visitado que não é o pai: aresta de retorno (atalho).
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