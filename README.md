# BibliotecaJava 0.1

Aplicação de console baseada no desafio da Rocketseat. O catálogo inicia em memória com três autores e cinco livros fictícios. Responda `SIM` para ver os livros disponíveis e fazer um empréstimo ou `NÃO` para sair. O programa encerra após confirmar o empréstimo. Uma nova execução restaura o catálogo inicial.

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
New-Item -ItemType Directory -Force target/classes | Out-Null
$fontes = @(Get-ChildItem src/main/java -Recurse -Filter *.java | ForEach-Object FullName)
& "$jdk\bin\javac.exe" --release 21 -encoding UTF-8 -d target/classes $fontes
& "$jdk\bin\jar.exe" --create --file target/biblioteca-java-0.1.0.jar --main-class br.dev.biblioteca.Main -C target/classes .
java -jar target/biblioteca-java-0.1.0.jar
```

Para reproduzir dois fluxos por entrada redirecionada no PowerShell:

```powershell
$OutputEncoding = [Console]::OutputEncoding = [Text.UTF8Encoding]::new($false)
'  nÃo  ' | java '-Dstdout.encoding=UTF-8' -jar target/biblioteca-java-0.1.0.jar
'talvez', ' sim ', 'abc', '999', '1', '   ', 'Bia das Flores' |
    java '-Dstdout.encoding=UTF-8' -jar target/biblioteca-java-0.1.0.jar
```

O primeiro fluxo termina com uma despedida. O segundo orienta a corrigir a resposta, o identificador e o nome antes de confirmar o empréstimo.

## Verificação local

- Java e `javac` 21.0.10, Git 2.54.0.windows.1 e Maven 3.9.11 incluído no IntelliJ. O comando `mvn` está fora do PATH desta máquina.
- A compilação com `javac --release 21` e a geração do JAR com `jar` passaram. Oito roteiros de terminal passaram, cobrindo recusa, variações de SIM, correção de entradas, encerramento após um empréstimo e fim de entrada.
- Uma verificação com asserções do JDK confirmou o registro do empréstimo, as datas, a filtragem dos disponíveis, a preservação de estado nas rejeições e o encerramento com o catálogo esgotado.
- Os comandos Maven `test` e `package` pararam ao resolver `maven-resources-plugin:3.3.1`. O ambiente bloqueou o acesso ao Maven Central com `Permission denied: getsockopt`. A execução da suíte JUnit Jupiter pelo Maven está pendente.
