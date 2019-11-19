# **Cenário**

**Descrição**

O cenário descrito será composto por um processo mestre (Master) e três processos trabalhadores (Worker). O serviço de registro RMI executará no endereço IP 127.0.0.1 na porta 12346. Cada processo Worker ficará responsável por quebrar as senhas em um dos modos de operação: Dicionário, Incremental (Min = 0 caracteres, Max = 5 caracteres) ou Incremental (Min = 6 caracteres, Max = 6 caracteres).

**Elementos (Processos):**

- 1 Master

- 3 Workers

**Passo a passo para iniciar o sistema distribído:**

- Faça o clone do repositório na sua máquina.

- Abra 4 terminais e em todos eles entre no diretório: 2019-02-projeto-pratico-01-camillabarreto/build/libs/

- Escolha o terminal que executará o processo Master e execute o comando: java -jar std-1.0.jar 127.0.0.1 12346

- Os outros 3 terminais devem executar um processo Worker com o comando: java -cp std-1.0.jar sistem.worker.Worker 127.0.0.1 12346

- Em cada processo Worker será solicitado um nome para identificação. OBS: Os nomes atribuídos nos processos devem ser distintos.

- No processo Master será exibido um menu de iteração com o usuário onde será possível saber quem são os Workers conectados e seus respectivos status através da opção (1).

- Para enviar tarefas para todos os Workers escolha a opção (3) no menu e preencha os dados solicitados. OBS: lembre-se de escolher um modo de operação diferente para cada processo.

- Quando os processos Workers finalizarem a tarefa de quebra de senha o processo Master será notificado.

- Para interromper os processos Workers escolha a opção (4).
