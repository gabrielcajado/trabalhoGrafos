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
| b3 | Finalizado | a1 | 2 | 5 |
| c2 | Finalizado | b3 | 3 | 4 |
| d4 | Finalizado | a1 | 6 | 7 |

### Árvore de busca
```
a1
├── b3
│   └── d4
└── c2
```

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

Nesta etapa, será considerada uma adaptação do *DFS* para o grafo do problema, utilizando a representação implícita definida anteriormente. A implementação será realizada posteriormente, após a compreensão dos conceitos apresentados neste marco.

## Conclusão

O *DFS* consegue encontrar um caminho entre duas casas, porém não garante que o caminho encontrado possua a menor quantidade de movimentos.