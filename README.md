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
## 8. Implementação de referência
## 9. Alterações
## 10. Justificativas
## 11. Complexidade
## 12. Testes
## 13. Evidência do Accepted
