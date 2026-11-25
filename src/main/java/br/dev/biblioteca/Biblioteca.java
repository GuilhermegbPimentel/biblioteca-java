package br.dev.biblioteca;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private final List<Autor> autores = new ArrayList<>();
    private final List<Livro> livros = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
        Autor robertMartin = new Autor(1, "Robert C. Martin", null);
        Autor joshuaBloch = new Autor(2, "Joshua Bloch", null);
        Autor martinFowler = new Autor(3, "Martin Fowler", null);
        Autor kentBeck = new Autor(4, "Kent Beck", null);
        autores.addAll(List.of(robertMartin, joshuaBloch, martinFowler, kentBeck));

        Instant cadastro = Instant.now();
        livros.addAll(List.of(
                new Livro(1, "Clean Code", robertMartin, cadastro),
                new Livro(2, "Test Driven Development: By Example", kentBeck, cadastro),
                new Livro(3, "Effective Java", joshuaBloch, cadastro),
                new Livro(4, "Refactoring: Improving the Design of Existing Code", martinFowler, cadastro),
                new Livro(5, "UML Distilled", martinFowler, cadastro)
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
