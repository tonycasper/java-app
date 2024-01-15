# Serviço de Transações Bancárias - AgBank

Este projeto é um sistema de back-end para um serviço de transações bancárias, desenvolvido com Spring Boot. Ele permite realizar operações bancárias como transferências entre contas e agendamento de reenvio de notificações para transações.

## Funcionalidades

- Cadastro e gerenciamento de clientes e contas bancárias.
- Realização de transferências entre contas.
- Agendamento automático para reenviar notificações de transações.
- Registro de tentativas de notificação e seus resultados.

## Tecnologias Utilizadas

- **Spring Boot**: Para a criação de uma aplicação stand-alone que pode ser facilmente executada.
- **Spring Data JPA**: Para a interação com o banco de dados.
- **H2 Database**: Como banco de dados em memória para testes e desenvolvimento.
- **JUnit e Mockito**: Para testes unitários.
- **Spring Scheduler**: Para o agendamento de tarefas periódicas.

## Configuração e Execução

### Pré-requisitos

- Java JDK 11 ou superior.
- Maven para gerenciamento de dependências e build do projeto.

### Executando o Projeto

1. Clone o repositório:
   ```bash
   git clone [URL do repositório]
2. Navegue até a pasta do projeto e execute:
  mvn spring-boot:run

