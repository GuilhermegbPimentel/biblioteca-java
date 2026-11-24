package br.dev.biblioteca;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class BibliotecaTest {
    private final Biblioteca biblioteca = new Biblioteca();

    @Test
    void emprestaLivroERegistraAlteracao() {
        Livro livro = biblioteca.listarDisponiveis().getFirst();
        Instant cadastro = livro.getDataCadastro();

        Emprestimo emprestimo = biblioteca.emprestar(livro.getId(), "  Bia das Flores  ");

        assertEquals(List.of(emprestimo), biblioteca.listarEmprestimos());
        assertSame(livro, emprestimo.getLivro());
        assertEquals("Bia das Flores", emprestimo.getNomeCliente());
        assertNotNull(emprestimo.getDataEmprestimo());
        assertNull(emprestimo.getDataDevolucao());
        assertFalse(livro.isDisponivel());
        assertFalse(biblioteca.listarDisponiveis().contains(livro));
        assertEquals(cadastro, livro.getDataCadastro());
        assertEquals(emprestimo.getDataEmprestimo(), livro.getDataAtualizacao());

        Emprestimo segundo = biblioteca.emprestar(biblioteca.listarDisponiveis().getFirst().getId(), "Caio dos Pinhos");
        assertTrue(emprestimo.getId() > 0);
        assertTrue(segundo.getId() > 0);
        assertNotEquals(emprestimo.getId(), segundo.getId());
    }

    @Test
    void rejeitaSegundoEmprestimoSemAlterarOPrimeiro() {
        Livro livro = biblioteca.listarDisponiveis().getFirst();
        Emprestimo primeiro = biblioteca.emprestar(livro.getId(), "Bia das Flores");
        Instant atualizacao = livro.getDataAtualizacao();

        assertThrows(IllegalArgumentException.class, () -> biblioteca.emprestar(livro.getId(), "Caio dos Pinhos"));

        assertEquals(List.of(primeiro), biblioteca.listarEmprestimos());
        assertFalse(livro.isDisponivel());
        assertEquals(atualizacao, livro.getDataAtualizacao());
    }

    @Test
    void rejeitaLivroInexistenteSemAlterarOCatalogo() {
        verificarRejeicaoSemAlteracoes(999, "Bia das Flores");
    }

    @Test
    void rejeitaNomeEmBrancoSemAlterarOCatalogo() {
        verificarRejeicaoSemAlteracoes(biblioteca.listarDisponiveis().getFirst().getId(), "   ");
    }

    @Test
    void consoleInformaQuandoNenhumLivroEstaDisponivel() {
        for (Livro livro : biblioteca.listarDisponiveis()) {
            biblioteca.emprestar(livro.getId(), "Bia das Flores");
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner entrada = new Scanner("SIM\n");
             PrintStream saida = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.executar(biblioteca, entrada, saida);
        }

        String texto = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(texto.contains("Nenhum livro disponível."));
        assertTrue(texto.stripTrailing().endsWith("Até logo!"));
        assertFalse(texto.contains("Digite o identificador"));
        assertEquals(5, biblioteca.listarEmprestimos().size());
    }

    private void verificarRejeicaoSemAlteracoes(int id, String nome) {
        List<Livro> catalogo = biblioteca.listarDisponiveis();
        List<Instant> datasCadastro = catalogo.stream().map(Livro::getDataCadastro).toList();
        List<Instant> datasAtualizacao = catalogo.stream().map(Livro::getDataAtualizacao).toList();

        assertThrows(IllegalArgumentException.class, () -> biblioteca.emprestar(id, nome));

        assertTrue(biblioteca.listarEmprestimos().isEmpty());
        assertEquals(catalogo, biblioteca.listarDisponiveis());
        assertEquals(datasCadastro, catalogo.stream().map(Livro::getDataCadastro).toList());
        assertEquals(datasAtualizacao, catalogo.stream().map(Livro::getDataAtualizacao).toList());
    }
}
