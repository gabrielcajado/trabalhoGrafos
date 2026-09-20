# Marco 4 — Aplicação básica de BFS e conclusão

## 4.1 - Execução manual

Foi utilizada uma instância pequena de uma matriz 3 × 3 para demonstrar a aplicação da **BFS (Breadth-First Search)**.

A matriz foi representada como um grafo, no qual cada posição corresponde a um vértice e as conexões entre posições representam as arestas.

A partir da lista de adjacência construída para a instância, foi realizada a execução manual da BFS.

### Instância utilizada

```text
1 2 3
4 5 6
7 8 9
```

### Lista de adjacência
```
1 → 6, 8
2 → 9, 7
3 → 8, 4
4 → 9, 3
5 → 
6 → 7, 1
7 → 6, 2
8 → 3, 1
9 → 4, 2
```

### Execução da BFS
A busca foi iniciada pelo vértice escolhido como origem e realizada seguindo a ordem dos vizinhos definida na lista de adjacência.

Durante a execução foram registrados os níveis, as distâncias e os predecessores dos vértices visitados.

## 4.2 - Níveis, distâncias e predecessores

Na *BFS*, os vértices são explorados por níveis de distância a partir da origem.

Considerando o vértice **1** como origem, os resultados obtidos durante a execução são:

| Vértice | Nível | Distância | Predecessor |
|--------:|------:|----------:|------------:|
| 1       | 0     | 0         | —           |
| 6       | 1     | 1         | 1           |
| 8       | 1     | 1         | 1           |
| 7       | 2     | 2         | 6           |
| 3       | 2     | 2         | 8           |
| 2       | 3     | 3         | 7           |
| 4       | 3     | 3         | 3           |
| 9       | 4     | 4         | 2           |
| 5       | —     | ∞         | —           |

O nível 0 corresponde ao vértice de origem. A cada novo nível, são considerados os vértices que podem ser alcançados com um movimento adicional.

O vértice **5** não é alcançável a partir do vértice 1, pois não possui arestas conectadas a outros vértices.

## 4.3 - Comparação entre DFS e BFS

Os dois algoritmos foram analisados considerando a aplicação ao problema.

### DFS

O DFS realiza a exploração aprofundando-se em um caminho antes de retornar e analisar outras possibilidades.

No Marco 3, foi observado que o DFS consegue verificar a alcançabilidade e construir um caminho, mas não garante que o caminho encontrado possua a menor quantidade de movimentos.

### BFS

A BFS explora os vértices por níveis de distância a partir da origem.

Como cada movimento possui o mesmo custo, a BFS garante que um vértice seja alcançado pela menor quantidade de arestas possível.

### Comparação
Característica | DFS | BFS
---------------|-----|-----
Exploração | Em profundidade | Por níveis
Verifica alcançabilidade | Sim | Sim 
Encontra menor caminho em grafo não ponderado | Não garante | Sim
Aplicabilidade ao problema | Parcial | Adequada

## 4.4 - Escolha do algoritmo

A *BFS* foi escolhida para a solução do problema porque o objetivo é encontrar o menor número de movimentos entre a casa de origem e a casa de destino.

Como cada movimento do cavalo possui custo 1, o grafo é não ponderado. Dessa forma, a exploração por níveis realizada pela *BFS* permite determinar a menor distância entre os vértices.

## 4.5 - Adaptação e integração

A solução foi adaptada para utilizar a modelagem do problema apresentada nos marcos anteriores.

A representação do grafo foi realizada por meio de uma lista de adjacência, permitindo armazenar os vértices e suas respectivas conexões.

A *BFS* foi integrada a essa representação para realizar a busca do menor caminho entre a origem e o destino.

## 4.6 - Complexidade

A BFS possui complexidade:

```
O(|V| + |E|)
```

onde: 

* ```|V|``` representa o número de vértices;
* ```|E|``` representa o número de arestas.

Para a instância analisada, o algoritmo percorre os vértices e as arestas da lista de adjacência durante a busca.

## Conclusão

A partir da modelagem do problema como um grafo e da análise realizada nos marcos anteriores, foi possível concluir que a *BFS* é o algoritmo mais adequado para a solução do problema.

Enquanto o *DFS* é capaz de verificar a existência de um caminho, a *BFS* explora o grafo por níveis e garante o menor número de movimentos em um grafo não ponderado, correspondendo ao objetivo do problema.