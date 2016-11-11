# BAgent
Monitoramento de fila de atendimento em agências bancárias

Haverá 3 agentes

- Agente Gerente
- Agente Atendente
- Agente Cliente


## Agente Gerente
O Agente gerente é encarregado de monitorar a fila de atendimento, alocando mais Atendentes conforme a necessidade.


## Agente Atendente
O Agente atendente se desloca até o ponto de atendimento quando convocado pelo Gerente. Este solicita ao cliente, os titulos que deseja pagar. É desalocado pelo Gerente conforme a baixa demanda de atendimento.

## Agente Cliente
O Agente cliente ao chegar a agencia solicita o atendimento de prioridade correspondente, e se encaminha a fila de atendimento. Ao ser atendinto este apresenta seus titulos ao Atendente sempre que solicitado.


## Fluxo

### 1 - Ao entrar na agência, o cliente solicita ao gerente uma senha para fila de atendimentos.

### 2 - O Gerente fornece a senha e solicita ao cliente que se acomode para aguardar o atendimento.

### 3 - O Cliente verifica qual cadeira está disponível e se desloca até a posição. ####Quando o cliente é criado na janela de simulação, é atribuido uma referência para uma cadeira na lista, de forma randomica, antes de se alocar o cliente verifica se a propriedade de visibilidade da Label está true, caso sim tenta outra cadeira.

### 4 - O Gerente verifica a fila de atendimento e a quantidade de atendentes, caso necessário aloca mais um atendente.
  






