# BibliotecaJava 0.1

BibliotecaJava é um sistema de empréstimos de livros pelo terminal, desenvolvido como exercício da Rocketseat. O objetivo é praticar orientação a objetos em Java ao modelar um catálogo e aplicar as regras de um empréstimo.

## Funcionalidades

- Inicia com um catálogo de demonstração com quatro autores e cinco livros.
- Aceita `SIM` para listar os livros disponíveis com identificador, título e autor. Aceita `NÃO` ou `NAO` para sair.
- Solicita o identificador do livro e o nome do cliente. Em caso de entrada inválida, pede a correção antes de registrar o empréstimo.
- Registra o empréstimo, marca o livro como indisponível, atualiza a data de alteração do livro e encerra após confirmar a operação.

O catálogo e os empréstimos ficam em memória. A cada execução, o catálogo volta ao estado inicial e a lista de empréstimos começa vazia.

## Tecnologias

- **Java 21** para a aplicação de console.
- **Maven** para compilar, testar e empacotar o JAR executável.
- **JUnit Jupiter** para testar as regras de empréstimo.

## Organização do projeto

- `src/main/java/br/dev/biblioteca/` contém a aplicação. `Main` cuida da interação com o terminal e `Biblioteca` mantém o catálogo e aplica as regras de empréstimo. `Autor`, `Livro` e `Emprestimo` representam os dados do sistema.
- `src/test/java/br/dev/biblioteca/` contém os testes de `BibliotecaTest`.
- `pom.xml` configura a compilação, os testes e o empacotamento.

## Compilar, testar e executar no Windows

No PowerShell, a partir da raiz do projeto, com JDK 21 e Maven 3.9 disponíveis no PATH:

```powershell
java -version
javac -version
mvn -version
mvn compile
mvn test
mvn package
java -jar target/biblioteca-java-0.1.0.jar
```

Se usar o Maven incluído no IntelliJ IDEA 2026.1.1, execute o binário diretamente. Ajuste o caminho se a instalação estiver em outra pasta.

```powershell
$maven = 'C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.1\plugins\maven\lib\maven3\bin\mvn.cmd'

& $maven -version
& $maven '-Dmaven.repo.local=target/maven-repository' -B test
& $maven '-Dmaven.repo.local=target/maven-repository' -B package
```

## Compilar e executar só com o JDK

Use o caminho do seu JDK 21. Estes comandos geram o JAR diretamente a partir do código da aplicação.

```powershell
$jdk = 'C:\Program Files\Java\jdk-21.0.10'

$fontes = @(Get-ChildItem src/main/java -Recurse -Filter *.java | ForEach-Object FullName)

New-Item -ItemType Directory -Force target/classes | Out-Null
& "$jdk\bin\javac.exe" --release 21 -encoding UTF-8 -d target/classes $fontes
& "$jdk\bin\jar.exe" --create --file target/biblioteca-java-0.1.0.jar --main-class br.dev.biblioteca.Main -C target/classes .
java -jar target/biblioteca-java-0.1.0.jar
```

Para reproduzir dois fluxos por entrada redirecionada no PowerShell:

```powershell
$OutputEncoding = [Console]::OutputEncoding = [Text.UTF8Encoding]::new($false)

'  nÃo  ' | java '-Dstdout.encoding=UTF-8' -jar target/biblioteca-java-0.1.0.jar
'talvez', ' sim ', 'abc', '999', '1', '   ', 'Bia das Flores' | java '-Dstdout.encoding=UTF-8' -jar target/biblioteca-java-0.1.0.jar
```

O primeiro fluxo termina com uma despedida. O segundo orienta a corrigir a resposta, o identificador e o nome antes de confirmar o empréstimo.
