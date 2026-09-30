# Marco 2 — Componentes conexas

Aplicação dos tópicos do Marco 2 ao problema **315 - Network (UVA)**, usando uma instância reduzida do grafo (V ≤ 6, E ≤ 6), simples e não dirigido.

## 1. Caso particular do problema (V=6, E=6)

- **Vértices:** {1, 2, 3, 4, 5, 6}
- **Arestas:** {1-2, 2-3, 3-4, 4-5, 5-6, 2-5}

O grafo é um caminho principal (1 → 6) com um atalho extra entre 2 e 5, formando um ciclo (2-3-4-5-2). Isso representa uma rede com uma rota redundante: nenhum vértice do ciclo sozinho consegue interromper a comunicação entre os demais.

## 2. Lista de adjacência

```
adj[1] = [2]
adj[2] = [1, 3, 5]
adj[3] = [2, 4]
adj[4] = [3, 5]
adj[5] = [4, 6, 2]
adj[6] = [5]
```

## 3. Excentricidades, raio, diâmetro e centro

Distâncias (nº de arestas) calculadas por BFS a partir de cada vértice:

| vértice | d(·,1) | d(·,2) | d(·,3) | d(·,4) | d(·,5) | d(·,6) | excentricidade |
|---|---|---|---|---|---|---|---|
| 1 | - | 1 | 2 | 3 | 2 | 3 | **3** |
| 2 | 1 | - | 1 | 2 | 1 | 2 | **2** |
| 3 | 2 | 1 | - | 1 | 2 | 3 | **3** |
| 4 | 3 | 2 | 1 | - | 1 | 2 | **3** |
| 5 | 2 | 1 | 2 | 1 | - | 1 | **2** |
| 6 | 3 | 2 | 3 | 2 | 1 | - | **3** |

Como o grafo é conexo (uma única componente), esses valores valem para o grafo inteiro:

- **Raio** = 2 (menor excentricidade, atingida em 2 e 5)
- **Diâmetro** = 3 (maior excentricidade — ex.: entre 1 e 4, ou entre 1 e 6)
- **Vértices centrais / centro** = {2, 5}

## 4. Rastreamento manual do algoritmo de componentes conexas (DFS recursiva)

**Estruturas de dados:**
- `visited[1..6]` — booleano, marca se o vértice já foi visitado
- `comp[1..6]` — id da componente à qual o vértice pertence
- pilha implícita da recursão (call stack)

**Lógica:** para cada vértice não visitado no laço externo, inicia uma nova componente e dispara uma DFS que marca todos os vértices alcançáveis a partir dele com o mesmo id.

```
v = 1, não visitado → nova componente (1). DFS(1):
  visita 1, comp[1] = 1
  → vizinho 2 (não visitado) → DFS(2):
      visita 2, comp[2] = 1
      → vizinho 1 (visitado, ignora)
      → vizinho 3 (não visitado) → DFS(3):
          visita 3, comp[3] = 1
          → vizinho 2 (visitado, ignora)
          → vizinho 4 (não visitado) → DFS(4):
              visita 4, comp[4] = 1
              → vizinho 3 (visitado, ignora)
              → vizinho 5 (não visitado) → DFS(5):
                  visita 5, comp[5] = 1
                  → vizinho 4 (visitado, ignora)
                  → vizinho 6 (não visitado) → DFS(6):
                      visita 6, comp[6] = 1
                      → vizinho 5 (visitado, ignora) → retorna
                  → vizinho 2 (visitado, ignora) → retorna
              → retorna
          → retorna
      → vizinho 5 (já visitado durante a recursão, ignora) → retorna
  → retorna

v = 2, 3, 4, 5, 6 → todos já visitados, laço externo termina
```

**Resultado:** uma única componente, contendo todos os 6 vértices — condiz com a premissa do problema real, em que a rede sempre chega conectada na entrada.

## 5. Complexidade e custo das consultas de conectividade

- **Tempo:** O(V + E) — cada vértice é visitado uma única vez (`visited[]`), e cada aresta é examinada no máximo duas vezes (uma por extremidade, já que a lista de adjacência guarda a ligação nos dois sentidos).
- **Espaço:** O(V) — `visited[]`/`comp[]` ocupam O(V); a pilha de recursão, no pior caso (grafo em formato de caminho), chega a O(V);
- **Consulta de conectividade:** depois de uma única DFS preenchendo `comp[]`, qualquer consulta "u e v estão conectados?" vira uma comparação `comp[u] == comp[v]`, custando **O(1)**. O custo caro é apenas o pré-processamento (a DFS em si, executada uma única vez).
