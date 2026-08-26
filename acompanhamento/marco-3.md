# Marco 3 — Aplicação Básica de DFS

## 3.1 Execução manual

Foi utilizado um pequeno subgrafo extraído do grafo do problema, contendo as relações necessárias para demonstrar a execução do *DFS*.

**Instância:**

Origem: **a1**

Destino: **d4**

A execução foi realizada considerando uma ordem fixa para os movimentos possíveis do cavalo. Durante a execução, serão registrados os estados dos vértices, seus predecessores e os tempos de descoberta e término.

### Tabela de execução
| Vértice | Estado | Predecessor | d(v) | f(v) |
|---------|--------|-------------|------|------|
| a1 | Finalizado | - | 1 | 8 |
| b3 | Finalizado | a1 | 2 | 7 |
| d4 | Finalizado | b3 | 3 | 6 |
| c2 | Finalizado | d4 | 4 | 5 |

### Passo a passo da execução
1. *DFS* começa em **a1**
2. **a1** é descoberto: d(a1) = 1
3. *DFS* visita **b3**
4. **b3** é descoberto: d(b3) = 2
5. *DFS* visita **d4**
6. **d4** é descoberto: d(d4) = 3
7. **d4** não possuo novos vértices no subgrafo → f(d4) = 4
8. retorna para **b3** → f(b3) = 5
9. retorna para **a1** e visita **c2**
10. **c2** é descoberto: d(c2) = 6
11. **c2** é finalizado: f(c2) = 7
12. **a1** é finalizado: f(a1) = 8

## 3.2 Estados de visita

Durante a execução, cada vértice pode assumir três estados:

* **Não visitado**
* **Em processamento**
* **Finalizado**

A evolução dos estados será registrada durante a execução.

## 3.3 Árvore de busca

A partir da execução do *DFS*, será construída a árvore de busca, indicando o caminho pelo qual cada vértice foi descoberto.

## 3.4 Tempos de descoberta e término

Para cada vértice visitado, serão registrados:

* **d(v)** - tempo de descoberta do vértice;
* **f(v)** - tempo de término do vértice;

Esses valores permitem acompanhar a ordem de entrada e saída dos vértices na busca.

## 3.5 Alcançabilidade e Predecessores

Ao final da execução, será verificado se o destino é alcançável a partir da origem.

Os predecessores registrados permitem reconstruir o caminho encontrado pelo *DFS*.

## 3.6 Aplicabilidade ao problema

O *DFS* pode ser utilizado para verificar a existência de um caminho entre duas casas do tabuleiro.

Entretanto, ele não garante que o caminho encontrado possua a menor quantidade de movimentos. Portanto, apesar de ser aplicável ao problema para verificar alcançabilidade, não é suficiente para obter diretamente a solução ótima.

## 3.7 Adaptação parcial

Nesta etapa, será considerada uma adaptação do *DFS* para o grafo do problema (baseado em `marked[]` e `edgeTo[]`, como no `DepthFirstPaths.java` da biblioteca algs4 de Sedgewick & Wayne), utilizando a representação implícita definida anteriormente. Com isso, em vez de consultar uma lista de adjacência pronta, os vizinhos de cada casa são gerados sob demanda aplicando os 8 deslocamentos do cavalo e verificando os limites do tabuleiro. Também foram acrescentados os tempos de descoberta/término (d(v)/f(v)), que não existem na versão clássica do algs4.

Por ora a adaptação é **parcial**: para manter a escala pequena e comparável com a execução manual da seção 3.1, a busca foi restrita às mesmas quatro casas (a1, b3, c2, d4).

```java
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

    // gera os vizinhos reais da casa (representacao implícita, Marco 2)
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
```
**Saída do programa:**

| Vértice | Predecessor | d(v) | f(v) |
|---------|-------------|------|------|
| a1 | - | 1 | 8 |
| b3 | a1 | 2 | 7 |
| d4 | b3 | 3 | 6 |
| c2 | d4 | 4 | 5 |

## Conclusão

O *DFS* consegue encontrar um caminho entre duas casas, porém não garante que o caminho encontrado possua a menor quantidade de movimentos.
