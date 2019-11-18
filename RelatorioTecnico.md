# **Relatório técnico**

**Tecnologia**

Foi escolhido o RMI (Remote Method Invocation) pois esse serviço proporciona a execução de um procedimento em outra máquina de forma transparente, além de explorar os paradigmas de orientação a objetos.

**Mecanismo**

O mecanismo para que os processos se encontrem funciona da seguinte maneira:

- 1º : O processo Master fica responsável por criar o serviço de registro RMI (endereço IP e a porta devem ser previamente conhecido pelos elementos do sistema) e exportar o objeto distribuido Notification (nome de registro deve ser previamente conhecido pelos elementos do sistema) e aguardar que os processos tralhadores encontrem este objeto w se comuniquem através dos seus métodos.

- 2º : O processo Worker quando iniciado deve procurar o serviço de registro RMI criado pelo Master e exportar o objeto distribuído Service.

- 3º : Em seguida o processo Worker deve procurar o objeto Notification ofertado pelo Master e utilizar o método activate deste objeto para informar ao Master o nome de registro do objeto Service que foi ofertado.

- 4º : Conhecendo o nome de registro do objeto Service distribuído pelo Worker, o Master pode fazer a busca por ele no serviço de registro RMI e assim ter disponível seus métodos.
