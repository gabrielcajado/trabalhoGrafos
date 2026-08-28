# Marco 2 - Representação computacional

### 1 Representação do grafo
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

### 2 Leitura da entrada
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

### 3 Construção do grafo
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

### 4 Medidas estruturais

Medida, Valor e Observação.

**Ordem |V|**:	64,	uma por casa do tabuleiro;

**Tamanho |E|**:	168, soma dos graus / 2;

**Grau mínimo**: 2	casas de canto (a1, a8, h1, h8);

**Grau máximo**: 8 casas centrais;

**Regularidade**: não regular,	grau varia conforme a posição;

**Bipartição**:	sim, todo movimento troca a cor da casa;

**Conectividade**:	conexo,	existe caminho entre qualquer par de casas.

### 5 Validação da representação com a instância pequena
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
