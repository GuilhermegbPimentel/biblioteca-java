package br.dev.biblioteca;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private final List<Autor> autores = new ArrayList<>();
    private final List<Livro> livros = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
        Autor lia = new Autor(1, "Lia Ventomar", LocalDate.of(1978, 4, 12));
        Autor teo = new Autor(2, "Teo Valeclaro", LocalDate.of(1984, 9, 23));
        Autor nina = new Autor(3, "Nina Campoluz", LocalDate.of(1990, 2, 8));
        autores.addAll(List.of(lia, teo, nina));

        Instant cadastro = Instant.now();
        livros.addAll(List.of(
                new Livro(1, "O Mapa das Marés", lia, cadastro),
                new Livro(2, "Cartas do Farol", lia, cadastro),
                new Livro(3, "A Estação de Papel", teo, cadastro),
                new Livro(4, "O Jardim das Horas", nina, cadastro),
                new Livro(5, "A Ponte de Vidro", nina, cadastro)
        ));
    }

    public synchronized List<Livro> listarDisponiveis() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                disponiveis.add(livro);
            }
        }
        return disponiveis;
    }

    public synchronized List<Emprestimo> listarEmprestimos() {
        return List.copyOf(emprestimos);
    }

    public synchronized void validarLivroDisponivel(int idLivro) {
        buscarLivroDisponivel(idLivro);
    }

    public synchronized Emprestimo emprestar(int idLivro, String nomeCliente) {
        Livro livro = buscarLivroDisponivel(idLivro);
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("Informe um nome de cliente.");
        }

        Instant momento = Instant.now();
        Emprestimo emprestimo = new Emprestimo(emprestimos.size() + 1, livro, nomeCliente.strip(), momento);
        emprestimos.add(emprestimo);
        livro.marcarComoEmprestado(momento);
        return emprestimo;
    }

    private Livro buscarLivroDisponivel(int idLivro) {
        for (Livro livro : livros) {
            if (livro.getId() == idLivro) {
                if (!livro.isDisponivel()) {
                    throw new IllegalArgumentException("Livro indisponível.");
                }
                return livro;
            }
        }
        throw new IllegalArgumentException("Livro inexistente.");
    }
}
