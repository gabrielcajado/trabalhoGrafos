 Beecrowd 1100 — Movimentos do Cavalo
https://judge.beecrowd.com/pt/problems/view/1100

## Marco 1 — Modelagem


 1. Enunciado
O problema consiste em determinar o menor número de movimentos que um cavalo de xadrez precisa realizar para sair de uma casa de origem e chegar a uma casa de destino em um tabuleiro de xadrez 8×8.
O cavalo possui um movimento característico: ele se desloca duas casas em uma direção e uma casa perpendicularmente. A partir de uma casa, ele pode realizar até 8 movimentos diferentes, dependendo da posição no tabuleiro.
O objetivo é encontrar a menor quantidade de movimentos necessários para sair da casa de origem e chegar à casa de destino.

 2. Entrada
A entrada contém um ou mais casos de teste.
Cada caso de teste consiste em uma linha contendo duas casas do tabuleiro, separadas por um espaço.
Cada casa é representada por:

- uma letra de `a` até `h`, representando a coluna;
- um número de `1` até `8`, representando a linha.

Exemplo de entrada
a1 h8
a1 c2
b1 c3

3. Saída
Para cada caso de teste, o programa deve informar o menor número de movimentos necessários para o cavalo sair da casa de origem e chegar à casa de destino.

4. Restrições
O tabuleiro possui tamanho fixo de 8 × 8.
O tabuleiro possui 64 casas.
As colunas são representadas pelas letras a até h.
As linhas são representadas pelos números 1 até 8.
A casa de origem e a casa de destino são diferentes.
O cavalo não pode realizar movimentos para fora dos limites do tabuleiro.
Cada movimento realizado pelo cavalo possui custo de 1 movimento.
A entrada possui um ou mais casos de teste.

5. Modelagem como Grafo
Para transformar o problema em um problema de grafos, cada casa do tabuleiro será representada por um vértice.
Os movimentos possíveis do cavalo serão representados por arestas.
Dessa forma, encontrar o menor número de movimentos entre duas casas equivale a encontrar o menor caminho entre dois vértices do grafo.

6. Vértices
Cada uma das 64 casas do tabuleiro representa um vértice.

7. Arestas
Existe uma aresta entre dois vértices quando um cavalo consegue realizar um movimento diretamente entre as duas casas.


8. Tipo do Grafo
O grafo é classificado como:
Grafo não direcionado e não ponderado.
Grafo não direcionado
O grafo é não direcionado porque o movimento do cavalo é reversível.
Se existe um movimento de a1 para c2, também existe um movimento de c2 para a1.

┌────┐      ┌────┐      ┌────┐
│ a1 │ ──── │ c2 │ ──── │ e3 │
└────┘      └────┘      └────┘
 Origem

                              │
                              │
                              ▼

                         ┌────┐      ┌────┐
                         │ f5 │ ──── │ h6 │
                         └────┘      └────┘
                                       │
                                       │
                                       ▼
                                  ┌────┐      ┌────┐
                                  │ f7 │ ──── │ h8 │
                                  └────┘      └────┘
                                                Destino



                                                ## 11. Hipótese Inicial de Solução
8. Instância Pequena

Entrada
a1 c2

Resultado Esperado
Como existe uma aresta direta entre a1 e c2, o cavalo precisa realizar apenas 1 movimento.

To get from a1 to c2 takes 1 knight moves.

9.
A hipótese inicial para resolver o problema é utilizar o algoritmo **BFS (Breadth-First Search)**, também conhecido como **Busca em Largura**.
O tabuleiro será representado como um grafo, onde cada casa será um vértice e cada movimento possível do cavalo será uma aresta.
A partir da casa de origem, a BFS irá explorar as casas por níveis:
Distância 0 → casa de origem
Distância 1 → casas alcançáveis em 1 movimento
Distância 2 → casas alcançáveis em 2 movimentos
Distância 3 → casas alcançáveis em 3 movimentos
...
