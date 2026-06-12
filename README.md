# BFF Agendador de Tarefas
 
![Java](https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?style=flat&logo=springboot)
![Spring Cloud OpenFeign](https://img.shields.io/badge/OpenFeign-5.0.1-brightgreen?style=flat&logo=spring)
![Maven](https://img.shields.io/badge/Maven-3.8-C71A36?style=flat&logo=apachemaven)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?style=flat&logo=docker)
 
Microsserviço BFF (Backend for Frontend) do sistema **Agendador de Tarefas**. Atua como ponto de entrada único da aplicação, roteando as requisições para os serviços internos de usuário, tarefas e notificação via OpenFeign. Também possui um job agendado que verifica tarefas pendentes a cada 5 minutos e dispara notificações por e-mail.
 
---
 
## Funcionalidades
 
- Cadastro, autenticação e gerenciamento de usuários
- Cadastro e gerenciamento de tarefas agendadas
- Busca de tarefas por período e por e-mail do usuário
- Alteração de status de tarefas
- Job agendado (cron) que verifica tarefas da próxima hora e envia notificações
- Tratamento global de exceções
- Documentação automática via Swagger (SpringDoc OpenAPI)
---
 
## Estrutura do Projeto
 
```
src/
└── main/
    └── java/
        └── com.morcineck.bffagendador/
            ├── business/         # Serviços e DTOs
            ├── controller/       # Endpoints REST e tratamento de exceções
            ├── infastructure/    # Segurança, Feign clients e exceções
            └── BffAgendadorApplication.java
```
 
---
 
## Endpoints — Usuário
 
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/usuario` | Cadastra um novo usuário | Não |
| POST | `/usuario/login` | Autentica e retorna token JWT | Não |
| GET | `/usuario?email=` | Busca usuário por e-mail | Token JWT |
| DELETE | `/usuario/{email}` | Remove usuário por e-mail | Token JWT |
| PUT | `/usuario` | Atualiza dados do usuário | Token JWT |
| POST | `/usuario/endereco` | Cadastra endereço do usuário | Token JWT |
| PUT | `/usuario/endereco?id=` | Atualiza endereço por ID | Token JWT |
| POST | `/usuario/telefone` | Cadastra telefone do usuário | Token JWT |
| PUT | `/usuario/telefone` | Atualiza telefone por ID | Token JWT |
 
---
 
## Endpoints — Tarefas
 
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/tarefas` | Cadastra uma nova tarefa | Token JWT |
| GET | `/tarefas` | Lista tarefas do usuário autenticado | Token JWT |
| GET | `/tarefas/eventos?dataInicial=&dataFinal=` | Busca tarefas por período | Token JWT |
| PUT | `/tarefas?id=` | Atualiza dados de uma tarefa | Token JWT |
| PATCH | `/tarefas?status=&id=` | Altera status de uma tarefa | Token JWT |
| DELETE | `/tarefas?id=` | Remove uma tarefa por ID | Token JWT |
 
---
 
## Documentação da API
 
Com o sistema rodando via Docker Compose, acesse o Swagger em:
 
```
http://localhost:8083/swagger-ui.html
```
 
---
 
## Variáveis de Ambiente
 
| Variável | Descrição |
|----------|-----------|
| `USUARIO_URL` | URL do serviço de usuário |
| `AGENDADOR_TAREFAS_URL` | URL do serviço de tarefas |
| `NOTIFICACAO_URL` | URL do serviço de notificação |
 
---
 
## Como Executar
 
Este repositório contém o `docker-compose.yml` que sobe todos os microsserviços:
 
 
```bash
docker-compose up --build
```
 
---
 
## Relacionamento com outros serviços
 
```
        ┌──────────────────────────┐
        │  bff-agendador-tarefas   │
        └────────────┬─────────────┘
                     │
       ┌─────────────┼─────────────┐
       ▼             ▼             ▼
   usuario     agendador-     notificacao
               tarefas
```
