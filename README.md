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



## Marco 2 - Representação computacional

### 2.1 Representação do grafo
Escolhemos apresentar o problema por uma **representação implícita do grafo**, nesse caso, uma lista de adjacência seria uma eventual alternativa, mas não é necessária para resolver o problema em questão.

**Por que representação ímplicita?**
Cada vértice corresponde a uma posição *(linha, coluna)* do tabuleiro. A partir de uma posição, basta aplicar os 8 possíveis deslocamentos do cavalo: 

* (+2, +1)
* (+2, -1)
* (-2, +1)
* (-2, -1)
* (+1, +2)
* (+1, -2)
* (-1, +2)
* (-1, -2)

Um movimento é considerado aresta somente quando a nova posição continua dentro do tabuleiro, assim, não precisamos armazenar todas as arestas antecipadamente.

Para esse problema dos movimentos do cavalo, a representação implícita é particularmente conveniente porque **todo vértice possui no máximo 8 vizinhos**, e os movimentos possíveis são sempre os mesmos.

### 2.2 Leitura da entrada
A entrada do problema é composta por um ou mais casos de teste.

Cada caso contém duas casas do tabuleiro, por exemplo: 
* a1 h8 

Onde a primeira casa representa o vértice de origem e a segunda representa o vértice de destino.

#### **Código exemplo**

~~~java
Scanner sc = new Scanner(System.in);

while (sc.hasNext()) {
    String origem = sc.next();
    String destino = sc.next();
}
~~~

A entrada é lida em pares de strings, representando respectivamente a casa de origem e a casa de detino. Como podem existir vários casos de teste, a leitura continua enquanto houver dados na entrada.

Por exemplo, para:
* a1 h8
* b2 c4

a leitura será:
* origem = "a1"
* destino = "h8"

* origem = "b2"
* destino = "c4"

### 2.3 Construção do grafo
O grafo não precisa ser construído explicitamente antes da execução do algoritmo.

Cada posição do tabuleiro representa um vértice:
* V = 8 x 8 = 64

Para gerar as arestas incidentes a um vértice, utilizam-se os oito movimentos possíveis do cavalo.

Considere uma posição **(l, c)**. Para cada deslocamento **(dl, dc)**, calculamos:
* nova_linha = l + dl
* nova_coluna = c + dc

A posição resultante será um vizinho válido quando: 
* 0 ≤ nova_linha < 8
* 0 ≤ nova_coluna < 8

Por exemplo: 
* Origem: d4 = (3,3)

Então, aplicando os 8 movimentos:
* (3+2, 3+1) → f6
* (3+2, 3-1) → b6
* (3-2, 3+1) → f2
* (3-2, 3-1) → b2
* (3+1, 3+2) → e5
* (3+1, 3-2) → c5
* (3-1, 3+2) → e3
* (3-1, 3-2) → c3

Todos estão dentro do tabuleiro, então **d4** possui grau 8.

### 2.4 Medidas estruturais

Medida	Valor	Observação

Ordem |V|:	64	uma por casa do tabuleiro
Tamanho |E|:	168	soma dos graus / 2
Grau mínimo: 2	casas de canto (a1, a8, h1, h8)
Grau máximo: 8 casas centrais
Regularidade: não regular	grau varia conforme a posição
Bipartição:	sim	todo movimento troca a cor da casa
Conectividade:	conexo	existe caminho entre qualquer par de casas

### 2.5 Validação da representação com a instância pequena
Tomamos de exemplo a verificação da casa **a1**.

Representação:
* a1 = (0,0)

Aplicando os 8 movimentos:
* (+2,+1) → (2,1) = b3
* (+2,-1) → inválido
* (-2,+1) → inválido
* (-2,-1) → inválido
* (+1,+2) → (1,2) = c2
* (+1,-2) → inválido
* (-1,+2) → inválido
* (-1,-2) → inválido

Logo: 
* Adj(a1) = {b3, c2}

Portanto:
* grau(a1) = 2

Agora podemos verificar uma casa central, por exemplo **d4**:
* Adj(d4) =
* {b2, b6, c3, c5, e3, e5, f2, f6}

Logo:
* grau(d4) = 8

Isso, valida a regra utilizada pela representação implícita: **os vizinhos gerados pelo programa são exatamente as casas que podem ser alcançadas por um único movimento de cavalo**.
