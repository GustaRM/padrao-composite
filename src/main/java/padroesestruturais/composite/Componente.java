package padroesestruturais.composite;

/**
 * Component: interface comum a elementos simples (Funcionario) e compostos (Departamento).
 * O cliente trata ambos de forma uniforme.
 */
public interface Componente {

    String getNome();

    /** Soma dos salários do componente (para um departamento, de toda a sua subárvore). */
    double calcularFolhaPagamento();

    /** Quantidade de funcionários do componente (para um departamento, de toda a sua subárvore). */
    int contarFuncionarios();

    /** Representação hierárquica em texto, com recuo de 2 espaços por nível. */
    String exibir(int nivel);
}
