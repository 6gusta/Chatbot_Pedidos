# Chatbot de Restaurante com Typebot e API Java



## Descrição do Projeto
Este é um chatbot para restaurantes criado com [Typebot](https://typebot.io/), integrado a uma API em Java para registrar pedidos e gerenciar o sistema de vendas. O projeto conta com uma interface para clientes, vendedores e administradores.

## Tecnologias Utilizadas
- **Typebot**: Para criação do chatbot interativo
- **Java (Spring Boot)**: API backend
- **JPA (Java Persistence API)**: Gerenciamento de banco de dados
- **Spring Security**: Autenticação e segurança
- **MySQL**: Banco de dados
- **Ngrok**: Exposição da API local para o chatbot

### **Cliente**
- **Faz pedidos via Chatbot**: O cliente pode interagir com o chatbot para fazer pedidos de forma rápida e prática.
- ![Chatbot de Restaurante](133006.png)

### **Vendedor**
- **Gerencia pedidos**: O vendedor tem a capacidade de gerenciar os pedidos realizados pelos clientes, podendo verificar o status e as informações de cada pedido.
- ![Chatbot de Restaurante](003429.png)
- **Gerencia margem de lucro em tempo real**: O vendedor pode monitorar as margens de lucro das vendas em tempo real, ajudando na gestão financeira.
-  ![Chatbot de Restaurante]( 003815.png)
- **Controle de estoque**: O vendedor consegue acompanhar o estoque dos produtos em tempo real, podendo fazer ajustes ou atualizações quando necessário.
- **Disparo automático de status do pedido**: O sistema envia automaticamente as atualizações de status do pedido para o cliente (exemplo: "Pedido em preparação", "Pedido pronto para entrega", etc.).


## Como Executar
### Backend (API Java)
1. Clone o repositório:
   ```sh
   git clone https://github.com/6gusta/Chatnot_Pedidos.git
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd 6gusta
   ```
3. Configure o banco de dados MySQL no **application.properties**:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```
4. Execute a aplicação:
   ```sh
   mvn spring-boot:run
   ```
5. Utilize o **Ngrok** para expor a API:
   ```sh
   ngrok http 8080
   ```

### Chatbot (Typebot)
1. Acesse [Typebot.io](https://typebot.io/) e crie um novo bot
2. Configure as requisições HTTP para chamar a API usando a URL do **Ngrok**
3. Publique e teste o chatbot



## Contribuição
Contribuições são bem-vindas! Fique à vontade para abrir issues e pull requests.

## Autor
Desenvolvido por [Luiz Gustavo[(https://github.com/6gusta/Chatnot_Pedidos.git).

