# Anime Voting

## Descrição
O Anime Voting é um sistema desenvolvido em Spring Boot que permite aos usuários votar nos animes da temporada. A aplicação integra-se com uma API de animes para exibir as informações dos animes e permite que cada IP vote apenas uma vez. Os votos são salvos em um MongoDB Atlas e são contabilizados semanalmente até as 22 horas (UTC-3) de todo domingo.

## Funcionalidades
- Exibição de animes da temporada atual.
- Votação nos animes favoritos pelos usuários.
- Controle de votação por IP para evitar múltiplos votos.
- Integração com o bot do Discord para facilitar a interação dos usuários.
- Contabilização dos votos semanalmente.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Thymeleaf** para renderização do frontend
- **MongoDB Atlas** para armazenamento dos votos
- **API de animes** para obter as informações dos animes
- **Bootstrap** para design responsivo

## Configuração do Ambiente

### Pré-requisitos
- Java 17
- Maven
- MongoDB Atlas

### Passos para Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/rodrigues159/anime-voting.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd anime-voting
   ```
3. Configure as variáveis de ambiente para conexão com o MongoDB Atlas.
4. Compile e rode o projeto utilizando Maven:
   ```bash
   mvn spring-boot:run
   ```
5. Acesse a aplicação no seu navegador:
   ```bash
   http://localhost:8080
   ```

## Estrutura do Projeto
- `src/main/java`: Contém o código fonte do backend.
- `src/main/resources/templates`: Contém os templates Thymeleaf utilizados no frontend.
- `src/main/resources/static`: Contém arquivos estáticos como CSS e JavaScript.

## Endpoints
- `/vote`: Página principal para votação nos animes.
- `/vote-name`: Página para inserir o nome do votante.
- `/vote-success`: Página de confirmação de voto bem-sucedido.
- `/vote-failure`: Página de falha na votação (quando o usuário já votou).

## Contribuição
1. Faça um fork do projeto.
2. Crie uma branch para sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Commit suas mudanças:
   ```bash
   git commit -m 'Minha nova feature'
   ```
4. Faça um push para a branch:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

## Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## Contato
Para mais informações, entre em contato:
- **Autor**: rodrigues159
- **Repositório**: [Anime Voting](https://github.com/rodrigues159/anime-voting)
```
