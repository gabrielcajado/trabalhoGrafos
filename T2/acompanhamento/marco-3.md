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

## 3. Instância pequena e rastreamento manual

Mesma instância do Marco 2: vértices {1..6}, arestas {1-2, 2-3, 3-4, 4-5, 5-6, 2-5}, DFS iniciada na raiz 1 (`dfs(graph, 1, 1)`, já que `Biconnected` sinaliza raiz com `parent == vertex`).

Vizinhos examinados em ordem crescente, conforme `adj[]`:

| vértice | vizinhos |
|---|---|
| 1 | 2 |
| 2 | 1, 3, 5 |
| 3 | 2, 4 |
| 4 | 3, 5 |
| 5 | 2, 4, 6 |
| 6 | 5 |

| Passo | Ação | Estruturas atualizadas | Decisão |
|---|---|---|---|
| 1 | Iniciar DFS em 1 (raiz) | preorder[1]=0, low[1]=0 | `dfs(graph, 1, 1)`; examinar vizinho 2. |
| 2 | Avançar de 1 para 2 | preorder[2]=1, low[2]=1 | 2 é filho de 1; examinar vizinhos de 2. |
| 3 | Vizinho 1 de 2 | sem alteração | 1 é o pai de 2, ignorar; examinar 3. |
| 4 | Avançar de 2 para 3 | preorder[3]=2, low[3]=2 | 3 é filho de 2; examinar vizinhos de 3. |
| 5 | Vizinho 2 de 3 | sem alteração | 2 é o pai de 3, ignorar; examinar 4. |
| 6 | Avançar de 3 para 4 | preorder[4]=3, low[4]=3 | 4 é filho de 3; examinar vizinhos de 4. |
| 7 | Vizinho 3 de 4 | sem alteração | 3 é o pai de 4, ignorar; examinar 5. |
| 8 | Avançar de 4 para 5 | preorder[5]=4, low[5]=4 | 5 é filho de 4; examinar vizinhos de 5. |
| 9 | Vizinho 4 de 5 | sem alteração | 4 é o pai de 5, ignorar; examinar 6. |
| 10 | Avançar de 5 para 6 | preorder[6]=5, low[6]=5 | 6 é filho de 5; examinar vizinhos de 6. |
| 11 | Vizinho 5 de 6 | sem alteração | 5 é o pai de 6, ignorar. Sem mais vizinhos: `dfs(6)` retorna. |
| 12 | Retornar de 6 para 5 | low[5] = min(4, low[6]=5) = 4 | `low[6]=5 >= preorder[5]=4` → **5 é marcado crítico**. |
| 13 | Vizinho 2 de 5 | low[5] = min(4, preorder[2]=1) = 1 | 2 já visitado e não é o pai de 5: aresta de retorno. Sem mais vizinhos: `dfs(5)` retorna. |
| 14 | Retornar de 5 para 4 | low[4] = min(3, low[5]=1) = 1 | `low[5]=1 >= preorder[4]=3`? Não → 4 não é crítico. Sem mais vizinhos: `dfs(4)` retorna. |
| 15 | Retornar de 4 para 3 | low[3] = min(2, low[4]=1) = 1 | `low[4]=1 >= preorder[3]=2`? Não → 3 não é crítico. Sem mais vizinhos: `dfs(3)` retorna. |
| 16 | Retornar de 3 para 2 | low[2] = min(1, low[3]=1) = 1 | `low[3]=1 >= preorder[2]=1` → **2 é marcado crítico**. |
| 17 | Vizinho 5 de 2 | low[2] = min(1, preorder[5]=4) = 1 (sem mudança) | 5 já visitado e não é o pai de 2: aresta de retorno. Sem mais vizinhos: `dfs(2)` retorna. |
| 18 | Retornar de 2 para 1 | low[1] = min(0, low[2]=1) = 0 | 1 é raiz e teve apenas 1 filho (não > 1) → 1 não é crítico. DFS encerra: todos os 6 vértices visitados. |

**Resultado:** `articulation[] = {1: false, 2: true, 3: false, 4: false, 5: true, 6: false}` → pontos críticos = {2, 5} → resposta do bloco = **2**.

## 4. Complexidade de tempo e memória

**Tempo:** O(V + E) - cada vértice é visitado uma única vez pela DFS, e cada aresta é examinada no máximo duas vezes (uma por extremidade, via `adj(v)`). As comparações de `low[]`/`preorder[]` são O(1) por chamada e não mudam a ordem de grandeza.

**Memória**, distinguindo representação do grafo da memória auxiliar do algoritmo:

- **Representação do grafo (`Graph`):** O(V + E) - a lista de adjacência guardando a topologia da rede lida na entrada, fixa para cada bloco.
- **Memória auxiliar (`Biconnected`):** O(V) - os arrays `low[]`, `preorder[]`, `articulation[]`, mais a pilha de recursão da DFS, que no pior caso (grafo em formato de caminho, como nossa instância) também chega a O(V).

Total: O(V + E), dominado pela representação do grafo quando a rede tem muitas arestas, e pela pilha de recursão quando o grafo é "esticado" (poucas arestas, porém profundo).
