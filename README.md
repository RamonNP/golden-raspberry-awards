# Golden Raspberry Awards API

## Descrição

Este projeto é uma API RESTful que permite a leitura da lista de indicados e vencedores da categoria "Pior Filme" do Golden Raspberry Awards. A aplicação carrega os dados de um arquivo CSV na inicialização e fornece endpoints para consultar informações sobre os produtores que tiveram o maior intervalo entre dois prêmios consecutivos e aqueles que obtiveram dois prêmios mais rapidamente.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- JPA com banco de dados H2 em memória
- Apache Commons CSV para leitura do arquivo CSV
- Lombok para geração de código boilerplate
- JUnit 5 para testes
- Mockito para mockar dependências nos testes
- Maven para gerenciamento de dependências

## Pré-requisitos

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)

## Estrutura do Projeto

- `com.prova.golden.raspberry.awards.controller`: Contém os controladores REST.
- `com.prova.golden.raspberry.awards.service`: Lógica de negócio.
- `com.prova.golden.raspberry.awards.model`: Modelos de entidade JPA.
- `com.prova.golden.raspberry.awards.repository`: Repositórios JPA.
- `com.prova.golden.raspberry.awards.dto`: Classes de transferência de dados (DTOs).
- `resources`: Contém o arquivo `movielist.csv` com os dados dos filmes.
- `application.properties`: Configurações da aplicação.

## Configuração do Arquivo CSV

O arquivo CSV contendo os dados dos filmes está localizado em `resources/movielist.csv`. 

year;title;studios;producers;winner 
1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes


## Instruções para Executar a Aplicação

1. Clone o repositório:

   ```bash
   git clone https://github.com/RamonNP/golden-raspberry-awards.git
   cd golden-raspberry-awards


Compile e construa o projeto usando Maven:

mvn clean install

Execute a aplicação:

mvn spring-boot:run

A aplicação estará disponível em: http://localhost:8080/producers/intervals

Endpoints
Obter Intervalos dos Produtores
GET /producers/intervals: Retorna os produtores com maior intervalo entre dois prêmios consecutivos e aqueles que obtiveram dois prêmios mais rapidamente.
Exemplo de Resposta




 
`{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}`

Instruções para Executar os Testes de Integração
Os testes de integração garantem que a API está retornando os dados conforme esperado. Para executá-los:

Certifique-se de que a aplicação não esteja rodando em outra porta (ela será executada em uma porta aleatória para os testes).

Execute os testes usando Maven:

bash

mvn test

Isso executará todos os testes, incluindo os testes de integração localizados na classe ProducerControllerIntegrationTest.

Verifique os resultados dos testes no console. Eles devem validar que os dados retornados pelo endpoint /producers/intervals estão de acordo com os dados de entrada.

Considerações Finais
O banco de dados utilizado é o H2, que roda em memória e não requer instalação ou configuração adicional.
Para qualquer dúvida ou problema, entre em contato pelo e-mail ramon.pereira3156@gmail.com.
Autor
Ramon do Nascimento Pereira
LinkedIn
GitHub