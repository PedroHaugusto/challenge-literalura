# Challenge LiterAlura

## Descrição do Projeto

Este projeto é um Catálogo de Livros e Autores, desenvolvido como parte do programa ONE. Ele permite que os usuários busquem informações sobre livros e autores diretamente da API Gutendex, salvem essas informações em um banco de dados PostgreSQL e realizem diversas consultas e estatísticas.

## Funcionalidades

Busca de Livros:

    Permite buscar livros por título utilizando a API Gutendex.
    Salva os dados do livro, incluindo autor, no banco de dados.

Listagem de Livros:

    Exibe todos os livros salvos no banco de dados.
    Permite filtrar livros por idioma.

Listagem de Autores:

    Mostra todos os autores cadastrados.
    Permite listar autores vivos em um determinado ano.

Estatísticas:

    Exibe a quantidade de livros cadastrados em determinados idiomas.

## Tecnologias Utilizadas

### Linguagem: Java 17
### Framework: Spring Boot 3.2.3
### Dependências: Spring Data JPA, PostgreSQL Driver, RestTemplate (para consumir APIs externas)
### Banco de Dados: PostgreSQL