# **Cenário**

**Descrição**

O cenário descrito será composto 1 processo mestre (Master) e 3 processos trabalhadores (Worker).

**Elementos (Processos):**

- 1 Master

- 3 Workers

**Passo a passo para iniciar o sistema distribído:**

- 1º : Iniciar 1 processo com o código Master. OBS: Deve ser adicionado 2 argumentos de linha que serão necessários para a criação do serviço de registro RMI, onde args[0] deve ser o endereço IP e args[1] a porta.

- 2º : Iniciar 3 processos com o código Worker. OBS: Deve ser adicionado 2 argumentos de linha que serão necessários para buscar o serviço de registro RMI, onde args[0] deve ser o endereço IP e args[1] a porta.

- 3º : Em cada processo Worker será solicitado um nome para identificação. OBS: Os nomes atribuídos nos processos devem ser distintos.

- 4º : No código Master será exibido um menu de iteração com o usuário onde será possível saber quem são os Workers conectados e seus respectivos status.

- 5º : Para enviar tarefas para todos os Workers escolha a opção (3) no menu e preencha os dados solicitados.
