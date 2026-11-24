package br.dev.biblioteca;

import java.time.Instant;

public class Livro {
    private final int id;
    private final String titulo;
    private final Autor autor;
    private boolean disponivel;
    private final Instant dataCadastro;
    private Instant dataAtualizacao;

    public Livro(int id, String titulo, Autor autor, Instant dataCadastro) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataCadastro;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public Instant getDataAtualizacao() {
        return dataAtualizacao;
    }

    void marcarComoEmprestado(Instant momento) {
        disponivel = false;
        dataAtualizacao = momento;
    }
}
