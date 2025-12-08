# 📦 Inventory Dashboard — API

API REST para gerenciamento de estoque, construída com Java, Spring Boot, Spring Security (JWT) e banco de dados MongoDB Atlas.

🚀 Tecnologias Utilizadas

Java 

Spring Boot 

Spring Web

Spring Security (JWT)

MongoDB Atlas

Spring Validation

JUnit + Mockito (testes)

Dockerfile presente

Render para hospedagem da API

🔗 Hospedagem

Backend: hospedado no Render

MongoDB: MongoDB Atlas
A API é consumida pelo frontend hospedado na Vercel → https://front-inventory-dashboard.vercel.app/.

⚙️ Funcionalidades da API
🔐 Autenticação

Login com JWT

Filtro de autenticação personalizado

Rotas públicas:

/auth/login

/auth/register

📦 Produtos

Criar produto

Listar todos

Buscar por ID

Atualizar

Excluir

✔️ Validações aplicadas

Campos obrigatórios

Validação de preço e quantidade

DTOs separados para requests/responses

🧪 Testes

O projeto contém testes automatizados:

AuthControllerTest

ProductControllerTest

Integration tests

Service tests

Validation tests

📦 Build / Deploy

O projeto contém:

Dockerfile

Configuração compatível com Render Deploy
