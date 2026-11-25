# BibliotecaJava

Projeto desenvolvido durante meus estudos de Java, a partir de um desafio da Rocketseat.

A proposta é criar um programa de terminal para consultar livros disponíveis e registrar um empréstimo.

## O que o programa faz

- Mostra um catálogo inicial com cinco livros de programação.
- Permite escolher um livro pelo identificador.
- Solicita o nome de quem está pegando o livro.
- Registra o empréstimo e marca o livro como indisponível.
- Solicita a correção de entradas inválidas.

O programa encerra após confirmar o empréstimo ou quando o usuário responde NÃO à consulta inicial.

Os dados ficam apenas na memória: ao executar novamente, todos os livros voltam a estar disponíveis.

## Conceitos praticados

- Classes, objetos e construtores.
- Encapsulamento.
- Listas com ArrayList.
- Condicionais e laços de repetição.
- Leitura de dados pelo terminal.
- Testes automatizados com JUnit.

## Como executar

É necessário ter o JDK 21 e o Maven disponíveis no terminal.

Na pasta do projeto, execute:

```powershell
mvn clean verify
```

Esse comando compila o projeto, executa os testes e gera o JAR.

Depois, inicie o programa:

```powershell
java -jar .\target\biblioteca-java-0.1.0.jar
```

Responda SIM para consultar os livros e siga as instruções do terminal.

## Sobre o desafio

O exercício original foi proposto pela Rocketseat para praticar os fundamentos de Java e orientação a objetos.
