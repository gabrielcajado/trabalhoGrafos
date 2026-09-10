# Problema F - Movimentos do Cavalo

## 1. Problema

O problema **Movimentos do Cavalo** consiste em determinar a menor quantidade de movimentos necessários para que um cavalo de xadrez se desloque de uma casa de origem até uma casa de destino em um tabuleiro de 8 × 8.

Cada casa do tabuleiro pode ser representada como um vértice de um grafo, enquanto cada movimento válido do cavalo representa uma aresta entre duas casas. A partir dessa modelagem, o objetivo é encontrar o menor caminho entre os vértices correspondentes às casas informadas na entrada.

## 2. Integrantes

Erick Araújo Macedo - *2518801*

Gabriel Cajado Cavalcante - *2420382*

Henrique Varela Barbosa Mouta - *2420507*

## 3. Linguagem

Java. 

## 4. Execução

Para executar a solução, é necessário possuir o **Java** instalado.

O arquivo principal da implementação está localizado em `src/Main.java`.

### Compilação
```bash
javac src/Main.java
```

### Execução
`java -cp src Main`

A entrada deve ser fornecida pela entrada padrão, seguindo o formato definido no problema.

## 5. Modelagem

O problema é modelado como um **grafo não direcionado**, no qual cada casa do tabuleiro de xadrez representa um vértice.

Como o tabuleiro possui 8 × 8 casas, o grafo possui:

* **64 Vértices**, correspondentes às casas do tabuleiro;
* **arestas entre duas casas** quando é possível realizar um movimento de cavalo entre elas.

As casas de origem e destino fornecidas pela entrada correspondem, respectivamente, aos vértices inicial e final do problema. O objetivo é encontrar o menor caminho entre esses dois vértices, considerando que cada aresta representa um movimento do cavalo.

## 6. Representação

Foi escolhida a **representação implícita do grafo**. Em vez de armazenar explicitamente todos os vértices e arestas, os possíveis vizinhos de uma casa são determinados a partir dos **oito movimentos possíveis do cavalo**.

Para uma determinada posição, cada movimento é verificado para determinar se a nova posição permanece dentro dos limites do tabuleiro. Caso seja válida, ela representa um vértice adjacente.

Essa representação foi escolhida por ser simples e adequada ao problema, já que cada casa possui no máximo oito movimentos possíveis e o tabuleiro possui tamanho fixo de 8 × 8.

## 7. Algoritmo

Foi utilizado o algoritmo **BFS (Breadth-First Search)**, também conhecido como **Busca em Largura**.

A BFS explora o grafo em níveis a partir do vértice de origem. Como cada movimento do cavalo possui custo 1, a distância de cada casa representa a quantidade mínima de movimentos necessários para alcançá-la.

Durante a execução:

1. A casa de origem recebe distância `0`;
2. A origem é inserida em uma fila;
3. O primeiro vértice da fila é retirado e seus movimentos possíveis são analisados;
4. Cada casa ainda não visitada recebe a distância do vértice atual acrescida de `1`;
5. As novas casas são inseridas na fila;
6. O processo continua até que o destino seja encontrado.

A geração dos vizinhos é realizada de forma implícita, utilizando os oito movimentos possíveis do cavalo.

## 8. Implementação de referência

Como referência para o desenvolvimento da solução, foram utilizados os materiais da biblioteca **algs4**, disponibilizada pelo professor.

Foi utilizado como referência o arquivo `Graph.java`, presente no repositório do professor, para o estudo da representação de grafos por lista de adjacência.

Também foi utilizada como referência a implementação de **BFS (Breadth-First Search)** da biblioteca `algs4`, especialmente os conceitos de:

- utilização de uma fila para realizar a busca;
- marcação dos vértices já visitados;
- armazenamento da distância a partir da origem;
- armazenamento do predecessor de cada vértice.

**Referências:**

- [Graph.java — repositório do professor](https://github.com/carubbi/RPG/blob/main/algs4-java/algs4/Graph.java)

## 9. Alterações

A implementação de referência foi adaptada para atender às características específicas do problema **Beecrowd 1100 - Movimentos do Cavalo**.

As principais alterações realizadas foram:

* substituição da representação explícita do grafo por uma **representação implícita**;
* geração dos vizinhos de cada casa a partir dos **8 movimentos possíveis do cavalo**;
* conversão das casas no formato de xadrez, como `a1` e `h8`, para posições numéricas da matriz;
* utilização de uma matriz `8 × 8` para armazenar as **distâncias**;
* adaptação da fila para as estruturas disponíveis na linguagem Java;
* adaptação da leitura dos pares de casas de origem e destino;
* adaptação da saída para o formato exigido pelo Beecrowd;
* encerramento da busca assim que o destino é encontrado, retornando sua distância.

A implementação final não utiliza diretamente a estrutura `Graph` da referência, pois o problema possui uma estrutura fixa e seus vizinhos podem ser gerados sob demanda. A BFS foi adaptada para trabalhar diretamente sobre essas posições.

## 10. Justificativas

A **BFS** foi escolhida porque o problema exige encontrar a menor quantidade de movimentos entre duas casas.

O grafo é não ponderado e cada movimento do cavalo possui custo `1`. Dessa forma, a exploração por níveis realizada pela BFS permite encontrar a menor distância entre a origem e o destino.

A **representação implícita** foi utilizada porque não é necessário armazenar todas as arestas do grafo. Os vizinhos de cada casa podem ser gerados sob demanda a partir dos oito movimentos possíveis do cavalo.

A estrutura de fila utilizada pela BFS permite processar os vértices na ordem em que foram descobertos, mantendo a exploração por níveis.

## 11. Complexidade

A BFS possui complexidade:

```
O(|V| + |E|)
```

No problema, o tabuleiro possui `64` vértices e cada vértice possui no máximo `8` movimentos possíveis.

Como os vizinhos são gerados sob demanda, cada vértice examinado realiza no máximo `8` verificações de movimentos.

Assim, para o tabuleiro fixo de `8 × 8`, o número de vértices e possíveis conexões é limitado, tornando o custo da execução pequeno.

A estrutura utilizada para armazenar as distâncias possui espaço:
```
O(|V|)
```
correspondente a matriz `8 × 8`.

## 12. Testes

Foram realizados testes com diferentes pares de casas de origem e destino para verificar a obtenção do menor número de movimentos.

### Exemplo 1

**Entrada:**
`a1 c2`
**Saída esperada:**
`To get from a1 to c2 takes 1 knight moves.`

### Exemplo 2

**Entrada:**
`a1 h8`
**Saída esperada:**
`To get from a1 to h8 takes 6 knight moves.`

Também foram realizados testes adicionais durante o desenvolvimento para verificar a leitura da entrada, a geração dos movimentos válidos e o cálculo das distâncias.

## 13. Evidência do Accepted

A implementação foi submetida ao **Beecrowd**, utilizando a linguagem Java, e obteve resultado Accepted para o problema 1100 - Movimentos do Cavalo.

### Evidência da submissão
> **Evidência:** [evidencias](https://github.com/gabrielcajado/trabalhoGrafos/tree/main/evidencias)

