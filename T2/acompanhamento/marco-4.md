# Marco 4 - Implementação final e conclusão

**Problema:** UVA 315 - Network (pontos de articulação)

**Linguagem:** Java

**Arquivo da solução:** `src/Main.java`


## 1. Classes do algs4 utilizadas

| Classe | Situação | Papel na solução |
|---|---|---|
| `Biconnected` | **Reutilizada** (lógica intacta) | Detecta os pontos de articulação via DFS com `preorder[]` e `low[]` |
| `Graph` | **Modificada (reduzida)** | Lista de adjacência do grafo não direcionado |
| `Bag` | **Reutilizada** (sem alteração) | Estrutura de dados usada por `Graph` nas listas de adjacência |

### Classes do algs4 que não foram necessárias
- `CC`: só identifica componentes conexas, não vértices críticos.
- `Bridge`: detecta arestas críticas (pontes), não vértices.
- `DepthFirstPaths`: DFS genérica sem `low[]`; serviu só como apoio conceitual.

## 2. Adaptações e justificativas

| Alteração | Classe | Justificativa |
|---|---|---|
| Classes copiadas para um único arquivo, sem `package algs4` | `Bag`, `Graph`, `Biconnected` | O UVA aceita um só arquivo, sem package, com a classe pública `Main` |
| Removido o construtor `Graph(In in)` | `Graph` | O formato do UVA 315 não é o do `tinyG.txt`; a leitura é feita no `Main`. Elimina a dependência de `In` |
| Removido o construtor de cópia `Graph(Graph)` | `Graph` | Não usado; eliminava a dependência de `Stack` |
| Removidos `toString()` e `toDot()` | `Graph` | Só serviam para depuração |
| Removido o `main` de demonstração | `Biconnected` | Dependia de `GraphGenerator` e `StdOut`, e não faz parte do algoritmo |
| Parser novo (`BufferedReader` + `StringTokenizer`) | `Main` | Entrada em blocos terminados em `0`, com linhas de tamanho variável |
| `Graph(n + 1)`, ignorando o índice 0 | `Main` | Lugares numerados de 1 a N; o vértice 0 fica isolado e não é contado |
| Contagem de `isArticulation(v)` de 1 a N | `Main` | Saída exigida: quantidade de lugares críticos por bloco |

A lógica de `dfs` em `Biconnected` não foi alterada.

## 3. Testes executados

| # | Entrada | Esperado | Obtido | Resultado |
|---|---|---|---|---|
| 1 | Exemplo do enunciado (blocos N=5 e N=6) | `1` e `2` | `1` e `2` | OK |
| 2 | Instância do Marco 2/3 (V=6, E=6) | `2` | `2` | OK |
| 3 | 200 arquivos aleatórios (30 redes cada, N de 1 a 12), comparados com força bruta (remove cada vértice e verifica a conectividade por BFS) | saídas idênticas | saídas idênticas | OK |

## 4. Evidência do Accepted

> **Pendente:** site para submissão temporariamente indisponível. 

## 5. Complexidade

- Tempo: O(V + E) por bloco
- Memória: O(V + E) para o grafo, mais O(V) auxiliar no algoritmo
