package br.dev.biblioteca;

import java.io.Console;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Locale PORTUGUES = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter
            .ofPattern("dd/MM/uuuu 'às' HH:mm:ss", PORTUGUES)
            .withZone(ZoneId.of("America/Sao_Paulo"));

    public static void main(String[] args) {
        Console console = System.console();
        try (Scanner entrada = console == null
                ? new Scanner(System.in, StandardCharsets.UTF_8)
                : new Scanner(console.reader())) {
            executar(new Biblioteca(), entrada, System.out);
        }
    }

    static void executar(Biblioteca biblioteca, Scanner entrada, PrintStream saida) {
        if (desejaConsultar(entrada, saida)) {
            List<Livro> disponiveis = biblioteca.listarDisponiveis();
            if (disponiveis.isEmpty()) {
                saida.println("Nenhum livro disponível.");
            } else {
                for (Livro livro : disponiveis) {
                    saida.printf("%d - %s - %s%n", livro.getId(), livro.getTitulo(), livro.getAutor().getNome());
                }
                solicitarEmprestimo(biblioteca, entrada, saida);
            }
        }
        saida.println("Até logo!");
    }

    private static boolean desejaConsultar(Scanner entrada, PrintStream saida) {
        while (true) {
            saida.println("Deseja consultar os livros disponíveis? (SIM/NAO)");
            if (!entrada.hasNextLine()) {
                return false;
            }
            String resposta = entrada.nextLine().strip().toUpperCase(PORTUGUES);
            switch (resposta) {
                case "SIM":
                    return true;
                case "NAO", "NÃO":
                    return false;
                default:
                    saida.println("Resposta inválida. Digite SIM, NAO ou NÃO.");
            }
        }
    }

    private static void solicitarEmprestimo(Biblioteca biblioteca, Scanner entrada, PrintStream saida) {
        while (true) {
            saida.println("Digite o identificador do livro:");
            if (!entrada.hasNextLine()) {
                return;
            }

            int idLivro;
            try {
                idLivro = Integer.parseInt(entrada.nextLine().strip());
                biblioteca.validarLivroDisponivel(idLivro);
            } catch (NumberFormatException erro) {
                saida.println("Identificador inválido. Digite um número inteiro.");
                continue;
            } catch (IllegalArgumentException erro) {
                saida.println(erro.getMessage());
                continue;
            }

            while (true) {
                saida.println("Digite o nome do cliente:");
                if (!entrada.hasNextLine()) {
                    return;
                }
                String nomeCliente = entrada.nextLine();
                if (nomeCliente.isBlank()) {
                    saida.println("Informe um nome de cliente.");
                    continue;
                }

                Emprestimo emprestimo;
                try {
                    emprestimo = biblioteca.emprestar(idLivro, nomeCliente);
                } catch (IllegalArgumentException erro) {
                    saida.println(erro.getMessage());
                    break;
                }
                saida.printf("Empréstimo confirmado para %s em %s.%n",
                        emprestimo.getNomeCliente(), FORMATO_DATA.format(emprestimo.getDataEmprestimo()));
                return;
            }
        }
    }
}
