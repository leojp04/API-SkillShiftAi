## SkillShift API – Recomendações (Quarkus)

API REST simples para entregar recomendações consumidas pelo front Vite/React/TypeScript da Global Solution FIAP 2025.

### Requisitos
- JDK 17+
- Maven 3.9+
- (Opcional) Quarkus CLI

### Como rodar
```bash
./mvnw quarkus:dev
```

Rodar testes:
```bash
./mvnw test
```

### Configuração
- Porta: `8080`
- CORS dev: `http://localhost:5173`
- Banco dev: H2 em memória (modo PostgreSQL)  
  - `quarkus.datasource.db-kind=h2`  
  - `quarkus.datasource.jdbc.url=jdbc:h2:mem:skillshift;DB_CLOSE_DELAY=-1;MODE=PostgreSQL`
  - `quarkus.hibernate-orm.database.generation=update`

Para usar Postgres em produção, troque no `application.properties`:
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.jdbc.url=jdbc:postgresql://<host>:<port>/<db>
quarkus.datasource.username=<user>
quarkus.datasource.password=<pass>
```

### Rotas principais
- `GET /recomendacoes` – lista recomendações (filtros opcionais `?area=` e `?senioridade=`).
- `GET /recomendacoes/{id}` – detalhe por UUID.
- `POST /recomendacoes` – cria recomendação (JSON, retorna 201 + Location).
- `PUT /recomendacoes/{id}` – atualiza recomendação.
- `DELETE /recomendacoes/{id}` – remove recomendação (204).

### Payloads
Request (POST/PUT):
```json
{
  "titulo": "Transição para Frontend",
  "descricao": "Trilha rápida para migrar para frontend.",
  "area": "Tecnologia",
  "senioridade": "Júnior",
  "trilha": "Frontend",
  "skills": ["HTML", "CSS", "React"],
  "link": "https://example.com/curso"
}
```

Response:
```json
{
  "id": "uuid",
  "titulo": "...",
  "descricao": "...",
  "area": "...",
  "senioridade": "...",
  "trilha": "...",
  "skills": ["..."],
  "link": "https://...",
  "criadoEm": "2025-01-01T10:00:00Z",
  "atualizadoEm": "2025-01-01T10:00:00Z"
}
```

### OpenAPI / Swagger
- OpenAPI: `/q/openapi`
- Swagger UI: `/q/swagger-ui`

### Seeds
Ao subir em dev, 3 recomendações iniciais são inseridas automaticamente se a tabela estiver vazia.

### Integração com o front
- O front deve apontar `VITE_API_URL` para a URL publicada desta API.
- Todas as respostas são JSON com CORS habilitado para `http://localhost:5173` em dev. Ajuste em produção conforme necessário.
