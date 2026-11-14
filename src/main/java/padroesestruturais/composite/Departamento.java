package padroesestruturais.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Composite: contém outros componentes (funcionários ou departamentos) e delega as operações a eles. */
public class Departamento implements Componente {

    private final String nome;
    private final List<Componente> filhos = new ArrayList<>();

    public Departamento(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public Departamento adicionar(Componente componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente não pode ser nulo");
        }
        if (componente == this) {
            throw new IllegalArgumentException("Um departamento não pode conter a si mesmo");
        }
        filhos.add(componente);
        return this;
    }

    public boolean remover(Componente componente) {
        return filhos.remove(componente);
    }

    public List<Componente> getFilhos() {
        return Collections.unmodifiableList(filhos);
    }

    @Override
    public double calcularFolhaPagamento() {
        double total = 0;
        for (Componente filho : filhos) {
            total += filho.calcularFolhaPagamento();
        }
        return total;
    }

    @Override
    public int contarFuncionarios() {
        int total = 0;
        for (Componente filho : filhos) {
            total += filho.contarFuncionarios();
        }
        return total;
    }

    @Override
    public String exibir(int nivel) {
        StringBuilder sb = new StringBuilder();
        sb.append(Formato.recuo(nivel)).append("+ ").append(nome)
          .append(" [").append(contarFuncionarios()).append(" funcionário(s), ")
          .append(Formato.moeda(calcularFolhaPagamento())).append("]\n");
        for (Componente filho : filhos) {
            sb.append(filho.exibir(nivel + 1));
        }
        return sb.toString();
    }
}
