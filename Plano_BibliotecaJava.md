# BibliotecaJava — plano de desenvolvimento e uso do Codex

Preparado em 24/11/2026.

Objetivo: transformar o desafio de biblioteca da Rocketseat em um projeto de portfólio, com entregas executáveis e estudo do código depois de cada implementação.

## 1. Decisões do projeto

- Pasta e nome de repositório sugeridos: biblioteca-java.
- Primeiro produto: aplicação de console que atende ao enunciado original.
- Produto de portfólio: API de biblioteca com catálogo, exemplares, clientes, empréstimos, devoluções, banco de dados, autorização e testes.
- Organização: uma aplicação, com separação progressiva entre domínio, casos de uso, interface e persistência.
- Linguagem de trabalho: Java; nomes de domínio em português, sem acentos nos identificadores.
- Java alvo: 21 LTS, sem recursos de preview. Um JDK mais novo compatível pode compilar com release 21.
- Build: Maven, com versões fixadas no pom.xml e Maven Wrapper quando disponível.
- Testes: JUnit/Jupiter; na etapa de banco, testes de integração com PostgreSQL real de teste.
- A implementação precede a explicação detalhada, conforme preferência do estudante.
- Cada pedido ao Codex deve indicar uma entrega. O roteiro completo não é autorização para implementar todas as versões em uma única execução.

As funcionalidades das etapas 2 em diante são ampliações propostas para este projeto. Elas não devem ser confundidas com exigências da Rocketseat.

## 2. Primeira entrega: versão 0.1, desafio original

### 2.1 Comportamento obrigatório

1. Iniciar com autores e livros de demonstração cadastrados em memória.
2. Perguntar ao usuário se deseja consultar os livros disponíveis.
3. Interpretar SIM e NAO/NÃO, desconsiderando espaços externos e diferenças entre maiúsculas e minúsculas.
4. Em caso de NÃO, apresentar despedida e finalizar.
5. Em caso de SIM, listar apenas os livros disponíveis, mostrando identificador, título e autor.
6. Solicitar o identificador de um livro e o nome do cliente.
7. Validar o livro e o nome antes de modificar qualquer estado.
8. Criar o registro de empréstimo, atualizar o livro para indisponível e atualizar sua data de alteração.
9. Confirmar a operação e finalizar com despedida.

O laço inicial permite repetir perguntas inválidas. Após um empréstimo concluído, esta versão encerra a execução, como pede o enunciado.

### 2.2 Comportamentos de entrada definidos para o projeto

- Resposta inicial inválida: explicar as opções aceitas e repetir a pergunta.
- Identificador não numérico: explicar o erro e solicitar novamente.
- Identificador inexistente ou livro indisponível: explicar o erro e solicitar novamente.
- Nome vazio ou composto apenas por espaços: solicitar novamente.
- Nenhum livro disponível: informar a situação, despedir-se e encerrar.
- Fim da entrada do terminal: encerrar de forma controlada.
- Uma operação rejeitada não cria empréstimo nem altera disponibilidade ou datas.

### 2.3 Classes da primeira versão

| Classe | Campos | Responsabilidade |
|---|---|---|
| Autor | id, nome, dataNascimento | Representar o autor. |
| Livro | id, titulo, autor, disponivel, dataCadastro, dataAtualizacao | Representar uma unidade emprestável, conforme o exercício. |
| Emprestimo | id, livro, nomeCliente, dataEmprestimo, dataDevolucao | Representar o empréstimo; dataDevolucao começa nula. |
| Biblioteca | livros, autores, emprestimos | Manter as listas e coordenar a listagem e o empréstimo. |
| Main | ponto de entrada e interação | Ler o terminal, apresentar mensagens e chamar Biblioteca. |

Manter as três listas de Biblioteca como List com implementação ArrayList, conforme a proposta do curso.

Convenção de datas:

- dataNascimento: LocalDate.
- Eventos de cadastro, atualização, empréstimo e devolução: Instant.
- Datas devem ser formatadas de modo legível na interface; a zona da biblioteca será America/Sao_Paulo.
- dataCadastro não muda ao emprestar um livro.
- dataAtualizacao é alterada quando o estado do livro muda.

Encapsular os campos. A operação de empréstimo deve passar por Biblioteca para validar as regras e manter as listas consistentes.

### 2.4 Estrutura inicial

~~~text
pom.xml
README.md
AGENTS.md
Plano_BibliotecaJava.md
.gitignore
src/main/java/br/dev/biblioteca/
    Main.java
    Autor.java
    Livro.java
    Emprestimo.java
    Biblioteca.java
src/test/java/br/dev/biblioteca/
    BibliotecaTest.java
~~~

Usar Java, listas, métodos, classes e controle de fluxo nesta entrega. Getters e construtores explícitos ajudam a leitura do código introdutório.

### 2.5 Dados iniciais

- Três autores fictícios e cinco livros de demonstração.
- Identificadores positivos e únicos.
- Livros disponíveis no início da execução.
- Novos processos iniciam com o catálogo inicial novamente: a persistência entra em uma etapa posterior.
- Dados usados em testes e demonstrações devem ser fictícios.

### 2.6 Critérios de conclusão e validação

| ID | Cenário | Resultado esperado |
|---|---|---|
| C01 | Iniciar e responder NÃO | Despedida, sem empréstimos, processo encerrado. |
| C02 | Responder SIM | Exibir somente livros disponíveis, com id, título e autor. |
| C03 | Informar livro válido e nome válido | Criar um empréstimo associado ao livro e ao nome informado. |
| C04 | Concluir empréstimo | Livro indisponível, atualização registrada, confirmação e encerramento. |
| C05 | Consultar disponíveis após empréstimo em teste de Biblioteca | Livro emprestado ausente da lista. |
| C06 | Tentar emprestar novamente o mesmo livro na mesma instância | Rejeitar; continuar existindo apenas o primeiro empréstimo. |
| C07 | Informar livro inexistente | Rejeitar, preservando o estado anterior. |
| C08 | Informar nome em branco | Rejeitar, preservando o estado anterior. |
| C09 | Digitar resposta ou id inválido no console | Explicar e permitir correção, sem encerrar por exceção. |
| C10 | Informar sim, Sim ou SIM com espaços externos | Reconhecer a resposta. |
| C11 | Lista de livros disponíveis vazia | Informar a situação e encerrar de forma controlada. |
| C12 | Inspecionar o empréstimo criado | Id único, dataEmprestimo preenchida e dataDevolucao nula. |

Automatizar os cenários essenciais de negócio, especialmente C03, C05, C06, C07 e C08. Verificar os fluxos de terminal com roteiros de entrada. Os testes devem comprovar comportamento; não criar testes apenas para repetir getters e setters.

O README deve conter os comandos exatos para compilar, testar e executar no Windows. Validar esses comandos no ambiente disponível, registrando qualquer impedimento real.

## 3. Etapa 0: preparar a execução local

Antes de implementar:

1. Confirmar a pasta do projeto e examinar arquivos existentes.
2. Verificar java -version, javac -version, mvn -version e git --version.
3. Registrar as versões efetivamente encontradas.
4. Preparar o projeto Maven para release 21.
5. Criar AGENTS.md curto a partir da seção 12 e README inicial.
6. Preparar .gitignore para saídas de build, arquivos do editor e segredos locais.
7. Inicializar Git local se estiver disponível e a pasta ainda não for um repositório.

Se um pré-requisito estiver ausente, informar exatamente qual é. Continuar com a organização e os arquivos que independem dele; diferenciar verificações executadas de verificações pendentes. Não alterar configurações globais do computador como parte silenciosa da implementação.

## 4. Roadmap das entregas

| Etapa | Versão sugerida | Entrega | Evidência de conclusão |
|---|---|---|---|
| 0 | preparação | Ambiente, build, orientações e repositório local | Versões registradas e comandos documentados. |
| 1 | v0.1.0 | Console original da Rocketseat | Critérios C01–C12 conferidos. |
| 2 | v0.2.0 | Menu, cadastros, devolução, buscas e clientes | Operações completas durante a mesma execução. |
| 3 | v0.3.0 | Exemplares, políticas de empréstimo e testes de domínio | Casos de negócio e limites comprovados. |
| 4 | v0.4.0 | PostgreSQL e migrações | Dados sobrevivem ao reinício; concorrência testada. |
| 5 | v0.5.0 | API REST com Spring Boot | Contratos, validação, erros e testes HTTP. |
| 6 | v0.6.0 | Autenticação, autorização e proteção de dados | Testes de acesso e isolamento entre leitores. |
| 7 | v1.0.0 | Demonstração de portfólio | Execução reproduzível, CI, documentação e roteiro de demonstração. |

Preservar a versão do curso por commit/tag depois de validada. A branch principal pode evoluir ao longo das demais versões.

## 5. Etapa 2: biblioteca operável pelo console

Ampliar a interface para um menu que continua aberto até a opção Sair:

- Listar livros disponíveis.
- Cadastrar autor e livro.
- Cadastrar e listar clientes.
- Buscar livros por título ou autor.
- Filtrar por gênero e período de cadastro.
- Registrar empréstimo.
- Registrar devolução.
- Consultar empréstimos em aberto e histórico de um cliente.

Criar Cliente com id, nome e email. Associar os novos empréstimos a Cliente, substituindo a string isolada na versão evoluída.

Regras:

- Título, autor e nome do cliente obrigatórios.
- Referências a autores, clientes e livros devem existir.
- Email de cliente com formato válido e unicidade sem diferenciar maiúsculas e minúsculas.
- Busca textual sem diferenciar maiúsculas e minúsculas.
- Devolução encerra o empréstimo e torna a unidade novamente disponível.
- Repetir uma devolução não deve alterar a data original nem criar registros.
- Alterações cadastrais não apagam o histórico.

Critério de conclusão: executar cadastro, busca, empréstimo, devolução e nova listagem sem reiniciar o programa.

## 6. Etapa 3: domínio realista e qualidade

### 6.1 Separar Livro e Exemplar

Na primeira versão, Livro representa uma unidade emprestável. Na versão de portfólio:

- Livro representa o título bibliográfico, seu autor e gênero.
- Exemplar representa uma cópia física, com identificador e código de inventário únicos.
- Emprestimo passa a referenciar Exemplar.
- Um Livro pode ter vários exemplares.
- Um Exemplar pode ter muitos empréstimos históricos, mas no máximo um empréstimo em aberto.

Situação cadastral de exemplar: ATIVO, EM_MANUTENCAO ou BAIXADO. A disponibilidade para empréstimo é derivada da situação ATIVO e da ausência de empréstimo em aberto.

O campo disponivel exigido pelo curso pertence à primeira versão; na evolução, a API pode apresentar disponibilidade calculada, evitando armazenar duas informações contraditórias.

### 6.2 Políticas propostas

Parâmetros iniciais para o nosso projeto, configuráveis:

- Prazo: 14 dias.
- Limite por cliente: três empréstimos em aberto.
- Cliente com empréstimo atrasado não inicia outro empréstimo.
- Atraso significa que a data atual da biblioteca ultrapassou a data prevista e o empréstimo continua aberto.
- Manter o histórico depois da devolução.

Registrar dataPrevistaDevolucao separadamente de dataDevolucao. A primeira é o prazo; a segunda é o momento real em que a devolução ocorreu.

Usar Clock para testar regras de tempo sem depender da data real da máquina.

### 6.3 Organização progressiva

Concentrar regras em classes de domínio e serviços de aplicação. A interface do console deve cuidar de entrada e saída; a persistência deve cuidar do armazenamento.

Introduzir interfaces de repositório quando elas permitirem trocar armazenamento em memória por banco e testar casos de uso. Manter cada abstração ligada a uma necessidade verificável.

Testes essenciais:

- Dois exemplares do mesmo livro podem ser emprestados a clientes diferentes.
- O mesmo exemplar não admite dois empréstimos simultâneos.
- Manutenção e baixa impedem novo empréstimo.
- O quarto empréstimo aberto é rejeitado.
- A devolução libera o limite do cliente.
- O prazo de 14 dias é calculado de maneira determinística.
- Atraso bloqueia novo empréstimo; a devolução encerra o atraso aberto.
- Falhas preservam o estado anterior.

## 7. Etapa 4: banco de dados e integridade

Tecnologias previstas: PostgreSQL, migrations com Flyway e Spring Data JPA na evolução para o banco.

Nesta etapa, Spring Boot pode entrar como infraestrutura da aplicação, mantendo ainda a interface de console. A API HTTP é a entrega seguinte.

Decisões:

- Modelo relacional com autores, livros, exemplares, clientes e empréstimos.
- Chaves estrangeiras e restrições de unicidade.
- Código de inventário único; email normalizado/único.
- Índice único parcial impedindo mais de um empréstimo aberto por exemplar.
- Operações de empréstimo e devolução transacionais.
- Migrações versionadas; schema validado na execução.
- Banco de teste isolado dos dados de desenvolvimento.
- Configurações por ambiente, com exemplo de variáveis sem credenciais reais.

Testes de integração:

1. Criar dados, reiniciar a aplicação e confirmar sua permanência.
2. Executar duas solicitações concorrentes para o mesmo exemplar: exatamente uma deve criar o empréstimo.
3. Confirmar que uma falha desfaz toda a operação.
4. Aplicar as migrações em um banco vazio.
5. Comprovar as restrições usando PostgreSQL, inclusive no cenário concorrente.

Usar Testcontainers quando Docker estiver disponível para os testes de integração. Registrar os requisitos necessários e os comandos separados para testes rápidos e de integração.

## 8. Etapa 5: API REST

Java 21 permanece como alvo. Referência verificada em 24/11/2026: Spring Boot 4.2.0 aceita Java 21. Ao iniciar esta etapa, confirmar a versão estável e compatível e fixar o patch escolhido no projeto.

Recursos previstos:

| Recurso | Operações principais |
|---|---|
| /api/v1/autores | Cadastrar, consultar e atualizar autores. |
| /api/v1/livros | Cadastrar, consultar, atualizar, buscar e filtrar títulos. |
| /api/v1/exemplares | Cadastrar cópias e atualizar sua situação cadastral. |
| /api/v1/clientes | Cadastrar e consultar clientes, respeitando as permissões. |
| /api/v1/emprestimos | Registrar empréstimo e consultar registros autorizados. |
| /api/v1/emprestimos/{id}/devolucao | Registrar a devolução. |
| /api/v1/me/emprestimos | Consultar os próprios empréstimos na etapa de segurança. |

Definir verbos HTTP e DTOs no início desta etapa, preservando as regras já testadas.

Padrões:

- DTOs de entrada e saída, sem expor entidades de persistência diretamente.
- Validação de entrada com Jakarta Validation.
- Paginação com tamanho máximo de 100 itens.
- Respostas de erro estruturadas no formato Problem Details.
- 400 para entrada inválida; 404 para recurso inexistente; 409 para conflito de negócio, inclusive disputa pelo mesmo exemplar.
- Depois de ativar segurança: 401 para acesso sem autenticação e 403 para operação proibida.
- Ausência de stack traces, SQL ou credenciais nas respostas.
- Contrato OpenAPI e exemplos de requisições reproduzíveis.

A fase sem autenticação deve ser executada como desenvolvimento local. A demonstração compartilhada da aplicação entra após a etapa 6.

## 9. Etapa 6: segurança verificável

Usar Spring Security da linha compatível com o Spring Boot escolhido.

Modelo inicial: autenticação com sessão HTTP, adequada à demonstração via navegador e a uma futura interface da mesma aplicação.

- PasswordEncoder com algoritmo adaptativo e parâmetros apropriados; não armazenar senhas em texto.
- Sessão com proteção contra fixação e logout.
- Cookies HttpOnly; Secure no ambiente HTTPS; SameSite conforme o cenário.
- Proteção CSRF mantida nas operações de escrita baseadas em sessão.
- Política CORS explícita quando houver cliente de outra origem.
- Configurações e segredos fora do código versionado.
- Logs sem senhas, cookies, tokens ou conteúdo pessoal desnecessário.
- Dados fictícios no ambiente público de demonstração.

### 9.1 Perfis

| Perfil | Acesso |
|---|---|
| ADMIN | Administrar contas e perfis, além das operações de biblioteca. |
| BIBLIOTECARIO | Gerenciar catálogo, clientes, empréstimos e devoluções. |
| LEITOR | Consultar catálogo e seus próprios empréstimos. |

Para operações do LEITOR, a identidade do cliente deve vir da autenticação. Não confiar em um clienteId informado para acessar os dados de outra pessoa.

Criar Usuario como conceito de autenticação separado de Cliente. O perfil LEITOR é associado a um Cliente.

### 9.2 Testes que devem demonstrar a proteção

- Requisição protegida sem autenticação é rejeitada.
- LEITOR não altera catálogo nem perfis.
- LEITOR A não lê empréstimos do LEITOR B, mesmo alterando um id na URL.
- Operação de escrita com sessão e sem CSRF válido é rejeitada.
- Login e logout são verificados.
- Senha e dados internos não aparecem nos DTOs e logs testados.
- Perfis de desenvolvimento, teste e demonstração ficam documentados.

Usar requisitos selecionados do OWASP ASVS como referência, identificando o que foi implementado e testado. A entrega deve descrever as evidências sem se apresentar como certificação do padrão.

## 10. Etapa 7: entrega de portfólio

Produzir:

- README com problema, funcionalidades, tecnologias, decisões e execução.
- Sumário em inglês como complemento.
- Comandos de build e testes reproduzíveis.
- Dockerfile e Docker Compose para a aplicação e o PostgreSQL.
- Verificação de saúde e configuração de demonstração.
- Pipeline no GitHub Actions com build, testes unitários e testes de integração.
- Documentação dos endpoints e exemplos de requisições.
- Diagrama de dados e uma decisão arquitetural explicando o modelo Livro/Exemplar e a organização do projeto.
- Roteiro de demonstração de três a cinco minutos.
- Dados iniciais fictícios e orientações para acessar o ambiente de demonstração.
- Registro de limitações conhecidas e próximas melhorias.

Definição de conclusão:

Outra pessoa consegue obter o projeto, iniciar a aplicação pelas instruções, executar os testes e demonstrar cadastro, empréstimo, devolução, autorização e rejeição de empréstimos concorrentes para o mesmo exemplar.

Publicação de uma demonstração online é um passo separado, após revisar hospedagem, custos e credenciais. Não executar deploy apenas porque ele aparece no roadmap.

### Melhorias opcionais depois da v1.0

- Interface web para leitores e bibliotecários.
- Reservas e fila de espera.
- Notificações de prazo e atraso.
- Relatórios de circulação.
- Login por provedor OIDC.

## 11. Como desenvolver e depois estudar

Para cada entrega:

1. Ler o objetivo, os critérios e os arquivos relevantes.
2. Implementar uma alteração com escopo pequeno.
3. Executar as verificações pertinentes e corrigir os problemas encontrados.
4. Apresentar arquivos alterados, comandos executados e resultado.
5. Explicar o fluxo real do código e as decisões tomadas.
6. Propor duas pequenas alterações para o estudante prever o resultado, executar e conferir.
7. Registrar a entrega no Git quando houver um conjunto coerente validado.

Manter a explicação ligada ao que foi implementado. No primeiro estudo, acompanhar Main, Biblioteca, Livro, Autor e Emprestimo na ordem em que participam do empréstimo.

Referências de estudo:

- Curso Rocketseat: fundamentos da linguagem e enunciado da versão 0.1.
- Engenharia de Software Moderna: princípios de projeto, testes e refatoração.
- Refatoração, segunda edição: melhoria estrutural preservando comportamento; adaptar exemplos para Java.
- The Clean Coder: profissionalismo, prática e responsabilidade; é distinto de Clean Code.
- Jakarta EE Tutorial: consultas pontuais de conceitos de REST, validação, persistência e segurança, distinguindo os exemplos do ecossistema Spring.

## 12. Modelo curto de AGENTS.md

Criar o arquivo AGENTS.md na raiz do projeto com orientações como estas, ajustando os comandos à implementação real:

~~~markdown
# BibliotecaJava
- Projeto Java de estudo com evolução para portfólio.
- Implementar somente a entrega solicitada; consultar a seção correspondente de Plano_BibliotecaJava.md.
- Java alvo 21, Maven e JUnit/Jupiter; sem recursos de preview.
- Preservar o fluxo original da Rocketseat na versão 0.1.
- Implementar primeiro; depois explicar os arquivos, o fluxo e as decisões em português.
- Validar regras de negócio com testes pertinentes e executar os comandos de build documentados.
- Relatar verificações executadas e impedimentos reais.
- Manter alterações focadas e a resposta final concisa.
- Preservar arquivos e alterações existentes.
- Usar somente dados fictícios nos exemplos e demonstrações.
- Não versionar credenciais, senhas, tokens ou arquivos locais de ambiente.
- O roadmap descreve evolução futura; não constitui pedido de deploy ou de publicação.
~~~

## 13. Primeiro pedido ao Codex: preparação e versão do curso

Copiar o pedido abaixo dentro do Codex aberto na pasta biblioteca-java, depois de colocar este documento nessa pasta:

~~~text
Estamos iniciando o projeto BibliotecaJava, baseado no desafio da Rocketseat.

Leia as seções 1, 2 e 3 de Plano_BibliotecaJava.md e use a seção 12 para criar um AGENTS.md curto.

Primeiro, examine a pasta e verifique java, javac, Maven e Git. Informe as versões e preserve os arquivos existentes.

Prepare o projeto Maven e implemente somente a primeira entrega: a versão 0.1 de console. Ela deve cumprir a seção 2, inclusive encerrar após um empréstimo bem-sucedido. Use as classes Autor, Livro, Emprestimo, Biblioteca e Main, com listas em memória e dados fictícios.

Implemente os testes essenciais de negócio e verifique os fluxos de entrada. Execute build e testes, corrigindo os problemas encontrados. Documente os comandos reais para Windows no README.

Se faltar um pré-requisito, informe exatamente o impedimento e continue com a organização e os arquivos que não dependem dele. Não altere configurações globais do computador silenciosamente.

Ao terminar, apresente uma síntese dos arquivos, comandos executados e verificações. Depois explique o fluxo do empréstimo de maneira didática. Aguarde um novo pedido para a versão 0.2.
~~~

## 14. Pedidos reutilizáveis

### Estudar uma entrega pronta

~~~text
Vamos estudar a entrega que acabou de ser implementada. Use o código atual como referência.
Explique o caminho percorrido desde a entrada do usuário até a alteração do estado dos objetos.
Mostre trechos pequenos, relacione-os a classes, métodos, listas e condições, e proponha dois experimentos simples.
~~~

### Implementar a próxima entrega

~~~text
Implemente somente a etapa [número] de Plano_BibliotecaJava.md.
Leia os critérios dessa etapa e os arquivos envolvidos.
Preserve o comportamento que deve continuar funcionando, adicione os testes pertinentes, execute as verificações e corrija as falhas.
Ao final, informe o resultado e explique as decisões principais. Não avance para a etapa seguinte.
~~~

### Revisar uma tentativa do estudante

~~~text
Revise minha alteração no arquivo [caminho].
O comportamento esperado é [descrição] e o resultado observado foi [descrição].
Identifique a causa, faça a menor correção necessária e explique por que ela resolve o problema.
Execute a verificação pertinente e informe o resultado.
~~~

## 15. Fontes técnicas verificadas

- Enunciado e checklist Rocketseat fornecidos pelo estudante nesta conversa.
- Java LTS: https://www.oracle.com/java/technologies/java-se-support-roadmap.html
- Requisitos do Spring Boot: https://docs.spring.io/spring-boot/system-requirements.html
- Spring Security: https://docs.spring.io/spring-security/reference/
- PasswordEncoder: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/password-encoder.html
- Testes de CSRF: https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html
- OWASP ASVS: https://asvs.owasp.org/
- Java: https://dev.java/learn/
- Engenharia de Software Moderna: https://engsoftmoderna.info/

As versões das bibliotecas e ferramentas devem ser registradas na implementação. Ao mudar de etapa, verificar compatibilidade antes de atualizar dependências.
