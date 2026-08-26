import java.util.*;

public class DFSParcial {

    // mesma ordem de deslocamentos definida no Marco 2
    static final int[] DLINHA  = {+2, +2, -2, -2, +1, +1, -1, -1};
    static final int[] DCOLUNA = {+1, -1, +1, -1, +2, -2, +2, -2};

    // restringe a exploração ao pequeno subgrafo usado na execução manual
    static final Set<String> permitido = new HashSet<>(Arrays.asList("a1", "b3", "c2", "d4"));

    static Set<String> marcado = new LinkedHashSet<>();
    static Map<String, String> predecessor = new HashMap<>();
    static Map<String, Integer> descoberta = new HashMap<>();
    static Map<String, Integer> termino = new HashMap<>();
    static int tempo = 0;

    public static void main(String[] args) {
        dfs("a1");
        imprimirResultado();
    }

    static void dfs(String v) {
        tempo++;
        descoberta.put(v, tempo);
        marcado.add(v);

        for (String w : vizinhos(v)) {
            if (permitido.contains(w) && !marcado.contains(w)) {
                predecessor.put(w, v);
                dfs(w);
            }
        }

        tempo++;
        termino.put(v, tempo);
    }

    // gera os vizinhos reais da casa (representacao implicita, Marco 2)
    static List<String> vizinhos(String casa) {
        int coluna = casa.charAt(0) - 'a';
        int linha  = casa.charAt(1) - '1';
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nl = linha + DLINHA[i];
            int nc = coluna + DCOLUNA[i];
            if (nl >= 0 && nl < 8 && nc >= 0 && nc < 8) {
                char col = (char) ('a' + nc);
                char lin = (char) ('1' + nl);
                lista.add("" + col + lin);
            }
        }
        return lista;
    }

    static void imprimirResultado() {
        System.out.printf("%-8s %-12s %-5s %-5s%n", "Vertice", "Predecessor", "d(v)", "f(v)");
        for (String v : marcado) {
            System.out.printf("%-8s %-12s %-5d %-5d%n",
                    v, predecessor.getOrDefault(v, "-"),
                    descoberta.get(v), termino.get(v));
        }
    }
}
