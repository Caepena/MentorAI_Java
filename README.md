# 📘 MentorAI — Plataforma Inteligente de Requalificação Profissional
### *Global Solution — FIAP 2025/2 – ADS*

---

## 🧠 Sobre o Projeto

O **MentorAI** é uma plataforma web inteligente desenvolvida para apoiar usuários em sua jornada de **Upskilling** e **Reskilling**, oferecendo:

- Cadastro de cursos, habilidades e matrículas  
- Integração com OAuth2 (Login via GitHub)  
- Recomendação inteligente de cursos utilizando **IA Generativa (OpenAI – GPT-4o-mini)**  
- Interface em **Spring MVC + Thymeleaf + DaisyUI**

O objetivo é criar um assistente capaz de entender o objetivo profissional do usuário e recomendar cursos adequados de forma rápida e personalizada.

---

# 🚀 Tecnologias Utilizadas

### Backend
- Java 17  
- Spring Boot 3.5  
- Spring MVC  
- Spring Data JPA  
- OAuth2 Client (GitHub Login)  
- Spring AI (OpenAI)  

### Frontend
- Thymeleaf  
- DaisyUI + TailwindCSS  
- HTML5 / CSS3  

### Banco de Dados
- **Oracle Cloud** (via connection string direta)  
- Migrações realizadas **manualmente** (sem Flyway)

---

# ⚙️ Pré-requisitos

Antes de rodar o sistema, você precisa configurar **variáveis de ambiente obrigatórias**:  
🔑 **OpenAI Key**  
🔐 **GitHub OAuth2 (Client ID e Secret)**

Sem elas, partes do sistema não funcionarão corretamente.

---

# 🔑 1. Configurar OpenAI API Key

O projeto utiliza a API da OpenAI para gerar recomendações inteligentes.

O professor deve definir:

```
OPENAI_KEY="sua_chave_aqui"
```

⚠️ *Sem essa chave, o módulo de recomendação de cursos não funcionará.*

---

# 🔐 2. Configurar OAuth2 – GitHub Login

Defina também:

```
GITHUB_ID="seu_client_id"
GITHUB_SECRET="seu_client_secret"
```

---

# 🏗️ Estrutura do Projeto

```
src/
 └── main/
     ├── java/br/com/fiap/mentorai/
     │     ├── model/              (Entidades JPA)
     │     ├── repository/         (JPA Repositories)
     │     ├── controller/         (Controllers MVC)
     │     ├── service/            (Camada de lógica + IA)
     │     └── auth/               (Login via GitHub OAuth2)
     │
     └── resources/
           ├── templates/          (Views Thymeleaf)
           │     ├── skills/
           │     ├── courses/
           │     ├── enrollments/
           │     └── ai/
           └── application.properties
```

---

# 🧪 Como Executar

### 1. Clone o repositório
```bash
git clone https://github.com/seu-repo.git
```

### 2. Configure as variáveis de ambiente  
(OpenAI Key + GitHub OAuth)

### 3. Compile:
```bash
./gradlew clean build
```

### 4. Execute:
```bash
./gradlew bootRun
```

### 5. Abra no navegador:
```
http://localhost:8080
```

---

# 🤖 IA – Recomendação Inteligente de Cursos

A rota:

```
/ai/recommend
```

Permite que o usuário escreva seu objetivo profissional.  
A IA:

- Recebe o contexto dos cursos cadastrados  
- Analisa a mensagem do usuário  
- Retorna até **3 cursos recomendados**  
- Explica o motivo de cada recomendação  
- Renderiza em **cards estilizados com DaisyUI**

---

# 👥 Integrantes do Grupo

| Nome | RM |
|------|------|
| **Caetano Matos Penafiel** | **557984** |
| **Kauã Fermino Zipf** | **558957** |
| **Victor Egídio Lira** | **556653** |

---

# 📄 Licença
Projeto acadêmico — FIAP.

---

# 🎓 Conclusão

O MentorAI integra:  
- Oracle DB  
- MVC completo  
- Login via GitHub  
- IA generativa personalizada  
- Interface moderna com Tailwind + DaisyUI  

O sistema está pronto para demonstração e avaliação.
