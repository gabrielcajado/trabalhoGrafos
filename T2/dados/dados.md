# Casos de Teste
 
## Teste 1 - Exemplo do enunciado
 
**Grafo:** dois blocos, exatamente como no enunciado original do UVA 315.
 
**Objetivo:** verificar a leitura dos blocos de entrada e o formato de saída usando o exemplo oficial do problema.
 
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
 
**Saída:**
```
1
2
```
 
## Teste 2 - Rede sem pontos críticos
 
**Grafo:** ciclo fechado entre os vértices 1, 2, 3 e 4 (1-2, 2-3, 3-4, 4-1).
 
**Objetivo:** verificar que o algoritmo não aponta falsos positivos quando toda rota tem caminho alternativo (nenhum vértice é indispensável).
 
**Entrada:**
```
4
1 2 4
2 3
3 4
0
0
```
 
**Saída:**
```
0
```
 
## Teste 3 - Rede em caminho (vários pontos críticos)
 
**Grafo:** caminho simples entre os vértices 1, 2, 3, 4 e 5 (1-2, 2-3, 3-4, 4-5), sem nenhum atalho.
 
**Objetivo:** verificar o caso oposto ao Teste 2. Quando não existe nenhum ciclo, quase todo vértice intermediário é crítico.
 
**Entrada:**
```
5
1 2
2 3
3 4
4 5
0
0
```
 
**Saída:**
```
3
```
 
## Teste 4 - Múltiplos blocos no mesmo arquivo
 
**Grafo:** os dois grafos dos Testes 2 e 3 concatenados num único arquivo de entrada.
 
**Objetivo:** garantir que o programa reinicia corretamente `preorder[]`, `low[]` e `articulation[]` entre um bloco e outro, sem misturar o estado de uma rede com o de outra.
 
**Entrada:**
```
4
1 2 4
2 3
3 4
0
5
1 2
2 3
3 4
4 5
0
0
```
 
**Saída:**
```
0
3
```