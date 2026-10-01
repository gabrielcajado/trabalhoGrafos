# Marco 3 - Estratégia algorítmica

Aplicação dos tópicos do Marco 3 ao problema **315 - Network (UVA)**, usando a mesma instância reduzida do Marco 2 (V ≤ 6, E ≤ 6, conexa).

## 1. Propriedade estrutural central

**Propriedade central:** ponto de articulação (*cut vertex*), um vértice cuja remoção aumenta o número de componentes conexas do grafo. No problema, é exatamente a definição de "lugar crítico".

**Critério de reconhecimento:** durante uma DFS, cada vértice `u` recebe `preorder[u]` (ordem de descoberta) e `low[u]` (o menor `preorder` alcançável a partir da subárvore de `u`, incluindo por uma aresta de retorno). `u` é ponto de articulação se:

- `u` é a **raiz** da DFS e tem **2 ou mais filhos**; ou
- `u` **não é raiz** e existe um filho `v` tal que `low[v] >= preorder[u]` (a subárvore de `v` não consegue "escapar" para cima de `u` por nenhum ciclo/aresta de retorno).

**Como obter a resposta exigida pelo problema:** o UVA 315 TLC pede apenas a **contagem** de lugares críticos. Após a DFS, percorre-se o array `articulation[]` (booleano, um por vértice) e soma-se quantos estão marcados como `true`. Esse total é a resposta impressa para o bloco.

## 2. Implementações de referência do algs4

O repositório algs4, disponibilizado pelo professor, contém uma classe pronta exatamente para esse problema:

- **`Biconnected.java`** - implementa a detecção de pontos de articulação em um grafo simples não dirigido, usando DFS com `preorder[]`/`low[]`. O construtor `Biconnected(Graph graph)` já roda a DFS completa e preenche o array interno `articulation[]`; o método `isArticulation(v)` consulta o resultado. É a referência **principal**, usada quase sem alteração.
- **`Graph.java`** - fornece a representação do grafo por lista de adjacência (`adj(v)`, `addEdge(v, w)`, `V()`), que é o tipo de entrada exigido pelo construtor de `Biconnected`. Usada para montar o grafo a partir da entrada do problema.

**Papel de cada uma:**
- `Graph` monta a topologia da rede lida da entrada.
- `Biconnected` recebe esse `Graph` pronto e calcula, internamente, quais vértices são críticos.

**Adaptações previstas** (sem implementar ainda):
- Escrever o parser da entrada do 315 (blocos terminados em `0`, arquivo terminado em `N = 0`) para popular um `Graph` novo a cada bloco - `Graph`/`Biconnected` não sabem nada sobre esse formato.
- Como o problema numera os lugares de `1` a `N`, criar o `Graph` com `N + 1` vértices e ignorar o índice `0`, evitando remapear os números de entrada para base 0.
- Depois de `new Biconnected(graph)`, iterar `v` de `1` a `N` chamando `isArticulation(v)` e somar os `true` para obter a contagem pedida pelo problema.
- Repetir a construção de um novo `Graph`/`Biconnected` para cada bloco de entrada, já que cada bloco é uma rede independente.

## 3. Instância pequena e rastreamento manual

Mesma instância do Marco 2: vértices {1..6}, arestas {1-2, 2-3, 3-4, 4-5, 5-6, 2-5}, DFS iniciada na raiz 1 (`dfs(graph, 1, 1)`, já que `Biconnected` sinaliza raiz com `parent == vertex`).

### 3.1 Tabela marked / edgeTo (DFS genérica)

Rastreamento no estilo clássico de `DepthFirstPaths.java`/`DepthFirstSearch.java` do algs4, mostrando a árvore de busca formada:

| vértice | marked | edgeTo |
|---|---|---|
| 1 | true | - (raiz) |
| 2 | true | 1 |
| 3 | true | 2 |
| 4 | true | 3 |
| 5 | true | 4 |
| 6 | true | 5 |

Árvore de DFS resultante: `1 → 2 → 3 → 4 → 5 → 6` (um único caminho, sem ramificação).

### 3.2 Tabela preorder / low / articulation (Biconnected)

`Biconnected.java` não usa `marked[]`/`edgeTo[]` - usa `preorder[]`, `low[]` e `articulation[]`, com o "pai" passado como parâmetro da recursão (`dfs(graph, parent, vertex)`) em vez de armazenado em array:

| vertex | preorder | low | é raiz com 2+ filhos? | articulation? |
|---|---|---|---|---|
| 1 | 0 | 0 | não (1 filho só) | não |
| 2 | 1 | 1 | — | **sim** - filho 3: `low[3]=1 >= preorder[2]=1` |
| 3 | 2 | 1 | — | não - filho 4: `low[4]=1 >= preorder[3]=2`? não |
| 4 | 3 | 1 | — | não - filho 5: `low[5]=1 >= preorder[4]=3`? não |
| 5 | 4 | 1 | — | **sim** - filho 6: `low[6]=5 >= preorder[5]=4` |
| 6 | 5 | 5 | folha, sem filhos | não |

## 4. Complexidade de tempo e memória

**Tempo:** O(V + E) - cada vértice é visitado uma única vez pela DFS, e cada aresta é examinada no máximo duas vezes (uma por extremidade, via `adj(v)`). As comparações de `low[]`/`preorder[]` são O(1) por chamada e não mudam a ordem de grandeza.

**Memória**, distinguindo representação do grafo da memória auxiliar do algoritmo:

- **Representação do grafo (`Graph`):** O(V + E) - a lista de adjacência guardando a topologia da rede lida na entrada, fixa para cada bloco.
- **Memória auxiliar (`Biconnected`):** O(V) - os arrays `low[]`, `preorder[]`, `articulation[]`, mais a pilha de recursão da DFS, que no pior caso (grafo em formato de caminho, como nossa instância) também chega a O(V).

Total: O(V + E), dominado pela representação do grafo quando a rede tem muitas arestas, e pela pilha de recursão quando o grafo é "esticado" (poucas arestas, porém profundo).
