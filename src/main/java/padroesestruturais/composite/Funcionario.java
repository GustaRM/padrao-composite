package padroesestruturais.composite;

/** Leaf: elemento simples da hierarquia, não possui filhos. */
public class Funcionario implements Componente {

    private final String nome;
    private final String cargo;
    private final double salario;

    public Funcionario(String nome, String cargo, double salario) {
        if (salario < 0) {
            throw new IllegalArgumentException("Salário não pode ser negativo");
        }
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public double calcularFolhaPagamento() {
        return salario;
    }

    @Override
    public int contarFuncionarios() {
        return 1;
    }

    @Override
    public String exibir(int nivel) {
        return Formato.recuo(nivel) + "- " + nome + " (" + cargo + ") " + Formato.moeda(salario) + "\n";
    }
}
