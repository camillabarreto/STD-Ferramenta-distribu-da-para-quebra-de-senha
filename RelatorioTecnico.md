# **Relatório técnico**

**Tecnologia**

Foi escolhido o RMI (Remote Method Invocation) pois esse serviço proporciona a execução de um procedimento em outra máquina de forma transparente, além de explorar os paradigmas de orientação a objetos de forma a abstrair o uso de sockets.

**Mecanismo**

O mecanismo para que os processos se encontrem funciona da seguinte maneira:

- 1º : O processo Master fica responsável por criar o serviço de registro RMI (endereço IP e a porta devem ser previamente conhecido pelos elementos do sistema).

- 2º : Em seguida o Master deve exportar o objeto distribuído Notification (nome de registro deve ser previamente conhecido pelos elementos do sistema) e aguardar que os processos tralhadores encontrem este objeto e se comuniquem através dos seus métodos.

- 3º : O processo Worker quando iniciado deve procurar o serviço de registro RMI criado pelo Master.

- 4º : Em seguida o processo Worker deve exportar o objeto distribuído Service.

- 5º : O processo Worker deve procurar o objeto Notification ofertado pelo Master e utilizar o método activate deste objeto para informar ao Master o nome de registro do objeto Service que foi ofertado.

- 6º : Conhecendo o nome de registro do objeto Service ofertado pelo Worker, o Master pode fazer a busca por ele no serviço de registro RMI e assim ter disponível seus métodos.
