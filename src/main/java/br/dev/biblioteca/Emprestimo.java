package br.dev.biblioteca;

import java.time.Instant;

public class Emprestimo {
    private final int id;
    private final Livro livro;
    private final String nomeCliente;
    private final Instant dataEmprestimo;
    private final Instant dataDevolucao;

    public Emprestimo(int id, Livro livro, String nomeCliente, Instant dataEmprestimo) {
        this.id = id;
        this.livro = livro;
        this.nomeCliente = nomeCliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = null;
    }

    public int getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public Instant getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Instant getDataDevolucao() {
        return dataDevolucao;
    }
}
