## 1. Resumo de entrada, saída e restrições

**Entrada:** vários blocos, um por rede. Cada bloco começa com N (número de lugares, N < 100). 

As linhas seguintes trazem um lugar seguido dos lugares aos quais ele se conecta diretamente (a lista de adjacência "crua"); cada conexão aparece em pelo menos uma dessas linhas, então não precisa estar duplicada nos dois sentidos. 

O bloco termina com uma linha "0". O arquivo inteiro termina quando aparece um bloco com N = 0.

**Saída:** para cada bloco (exceto o último, que só sinaliza fim de arquivo), uma linha com o número de **lugares críticos**.

**Restrições:** N < 100; o grafo de cada bloco é conexo (de qualquer lugar dá pra chegar em qualquer outro); as arestas são bidirecionais (linha telefônica não tem "sentido").

## 2. Modelagem de vértices e arestas

* **Vértice** = um lugar/central telefônica (números de 1 a N).
* **Aresta** = uma linha telefônica direta entre dois lugares — não direcionada, sem peso (o problema não fala de custo/distância).
* **Estutura natural:** lista de adjacência, `adj[u]` contendo `v` ligados diretamente a `u`.

## 3. Classificação do grafo

Grafo **simples, não direcionado, não ponderado e conexo**. 

Pode conter ciclos (é justamente a existência de ciclos que garante que a queda de um lugar não isole os demais - é isso que o problema está testando).

## 4. Resultado de aprendizagem aferido

A competência avaliada aqui é a de **modelar um problema do mundo real como grafo e usar busca em profundidade para analisar conectividade/robustez da rede.** 

Especificamente, identificar **pontos de articulação:** vértices cuja remoção desconecta o grafo. 

É a aplicação prática de DFS além de "só visitar todo mundo": usá-la para extrair uma propriedade estrutural do grafo.

## 5. Como DFS/BFS participa da solução

A solução clássica (algoritmo de Tarjan) usa **DFS**, não BFS, porque precisa das chamadas **arestas de retorno (back edges)** que só a DFS gera naturalmente:

* Durante a DFS, cada vértice recebe um tempo de descoberta `disc[u]`.
* Calcula-se também `low[u]` = o menor `disc` alcançável a partir da subárvore de `u`, incluindo por uma aresta de retorno.
* Um vértice `u` é ponto de articulação se: 
    * `u`é a raiz da DFS e tem **2 ou mais filhos** na árvore, ou
    * `u` não é raiz e existe um filho `v` tal que `low[v] >= disc[u]` (ou seja, a subárvore de `v` não consegue "escapar" para cima passando por `u`).

BFS não serve bem aqui porque a árvore de BFS não distingue arestas de retorno da mesma forma.

Sem essa informação não dá pra calcular o `low[u]`. 

Complexidade: O(V + E), igual a uma DFS comum.

## 6. Instância pequena

Um exemplo simples e fácil de verificar na mão, um caminho:

```
 1 - 2 - 3
```

Adjacência: `1: [2]`, `2: [1, 3]`, `3: [2]`.

Se o lugar **2** falhar, 1 e 3 ficam isolados um do outro → **2 é crítico.**

Se o 1 ou 3 falhar, o resto continua conectado → não são críticos.

Resultado esperado apenas para essa instância: **1 ponto crítico.**