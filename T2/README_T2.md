# Problema 315 - Network

## 1. Problema

O problema **Network** consiste em identificar, em uma rede de telefonia representada por lugares numerados de 1 a N, quais lugares são críticos: aqueles cuja falha (queda da central telefônica local) desconecta a rede, fazendo com que outros lugares deixem de conseguir se comunicar entre si.

Cada lugar pode ser representado como um vértice de um grafo, enquanto cada linha telefônica direta entre dois lugares representa uma aresta. A partir dessa modelagem, o objetivo é contar quantos vértices são **pontos de articulação** do grafo.

## 2. Integrantes

* Erick Araújo Macedo - *2518801*
* Gabriel Cajado Cavalcante - *2420382*
* Henrique Varela Barbosa Mouta - *2420507*

## 3. Linguagem

Java.

## 4. Execução

*Ainda não implementada.*

## 5. Modelagem

O problema é modelado como um **grafo simples, não direcionado, não ponderado e conexo,** no qual cada lugar da rede representa um vértice.

* **Vértices:** os lugares/centrais telefônicas, numerados de 1 a N (N < 100);
* **Arestas:** as linhas telefônicas diretas entre dois lugares, sem direção e sem peso.

A entrada fornece, para cada lugar, a lista de lugares aos quais ele se conecta diretamente; cada conexão aparece em pelo menos uma das linhas do bloco, não sendo necessário que esteja duplicada nos dois sentidos.

## 6. Representação

*A definir.*

## 7. Algoritmo

Será utilizado o algoritmo de **DFS (Depth-First Search)** para identificar **pontos de articulação** (cut vertices), baseado no algoritmo de Tarjan.

Durante a DFS, cada vértice recebe:

* `disc[u]` - o tempo de descoberta do vértice;
* `low[u]` - o menor tempo de descoberta alcançável a partir da subárvore de `u`, incluindo uma por aresta de retorno (back edge).

Um vértice `u` é ponto de articulação se:

1. `u` é a raiz da DFS e possui 2 ou mais filhos na árvore de busca; ou
2. `u` não é raiz e existe um filho `v` tal que `low[v] >= disc[u]`.

Nesse caso, a **BFS não é adequada**, pois a solução depende das arestas de retorno geradas naturalmente pela árvore de DFS, que a BFS não produz da mesma forma.

## 8. Implementação de referência

*A definir.*

## 9. Alterações

*A definir.*

## 10. Justificativas

A **DFS** foi escolhida porque o cálculo de pontos de articulação depende de identificar arestas de retorno e comparar tempos de descoberta entre um vértice e seus descendentes na árvore de busca. 

Informação que só a **DFS** fornece de forma direta.

## 11. Complexidade

A **DFS** para encontrar pontos de articulação possui complexidade:

```
O(|V| + |E|)
```

já que cada vértice e cada aresta são visitados uma única vez durante a busca.

## 12. Testes

**Exemplo 1**

**Entrada:**
```
5
5 1 2 3 4
0
6
2 1 3
5 4 6 2
0
0
```

**Saída esperada:**
```
1
2
```

*A definir, testes adicionais serão incluídos conforme a implementação avançar.*

## 13. Evidência do Accepted

*A definir, ainda não submetido ao UVA Online Judge.*