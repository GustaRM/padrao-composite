package padroesestruturais.composite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponenteTest {

    private Departamento hospital;

    @BeforeEach
    void montarHospital() {
        hospital = Hospital.montar();
    }

    private Departamento filho(Departamento pai, String nome) {
        for (Componente c : pai.getFilhos()) {
            if (c.getNome().equals(nome)) {
                return (Departamento) c;
            }
        }
        throw new AssertionError("Departamento não encontrado: " + nome);
    }

    @Test
    void funcionarioDeveRetornarProprioSalarioEContarUm() {
        Funcionario f = new Funcionario("Ana", "Analista", 4500);
        assertEquals(4500.0, f.calcularFolhaPagamento(), 0.001);
        assertEquals(1, f.contarFuncionarios());
    }

    @Test
    void funcionarioNaoPodeTerSalarioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Funcionario("X", "Y", -1));
    }

    @Test
    void departamentoVazioDeveTerFolhaZero() {
        Departamento vazio = new Departamento("Vazio");
        assertEquals(0.0, vazio.calcularFolhaPagamento(), 0.001);
        assertEquals(0, vazio.contarFuncionarios());
    }

    @Test
    void deveCalcularFolhaTotalDoHospital() {
        assertEquals(39500.0, hospital.calcularFolhaPagamento(), 0.001);
        assertEquals(5, hospital.contarFuncionarios());
    }

    @Test
    void deveCalcularFolhaDeSubdepartamentos() {
        Departamento enfermagem = filho(hospital, "Enfermagem");
        assertEquals(15000.0, enfermagem.calcularFolhaPagamento(), 0.001);
        assertEquals(3, enfermagem.contarFuncionarios());

        Departamento uti = filho(enfermagem, "UTI");
        assertEquals(8000.0, uti.calcularFolhaPagamento(), 0.001);
        assertEquals(2, uti.contarFuncionarios());

        assertEquals(20000.0, filho(hospital, "Diretoria").calcularFolhaPagamento(), 0.001);
        assertEquals(4500.0, filho(hospital, "Administração").calcularFolhaPagamento(), 0.001);
    }

    @Test
    void deveTratarFolhaEDepartamentoUniformementePelaInterface() {
        Componente simples = new Funcionario("Ana", "Analista", 4500);
        Componente composto = filho(filho(hospital, "Enfermagem"), "UTI");
        assertEquals(4500.0, simples.calcularFolhaPagamento(), 0.001);
        assertEquals(8000.0, composto.calcularFolhaPagamento(), 0.001);
    }

    @Test
    void deveExibirEstruturaHierarquica() {
        String esperado =
                "+ Hospital Central [5 funcionário(s), R$ 39500.00]\n" +
                "  + Diretoria [1 funcionário(s), R$ 20000.00]\n" +
                "    - Dr. Roberto (Diretor Clínico) R$ 20000.00\n" +
                "  + Enfermagem [3 funcionário(s), R$ 15000.00]\n" +
                "    - Paula (Enfermeira) R$ 7000.00\n" +
                "    + UTI [2 funcionário(s), R$ 8000.00]\n" +
                "      - Dra. Helena (Intensivista) R$ 5000.00\n" +
                "      - Marcos (Técnico de Enfermagem) R$ 3000.00\n" +
                "  + Administração [1 funcionário(s), R$ 4500.00]\n" +
                "    - Ana (Analista Administrativa) R$ 4500.00\n";
        assertEquals(esperado, hospital.exibir(0));
    }

    @Test
    void deveAtualizarTotaisAoAdicionarFuncionario() {
        Departamento uti = filho(filho(hospital, "Enfermagem"), "UTI");
        uti.adicionar(new Funcionario("Carla", "Fisioterapeuta", 4000));
        assertEquals(12000.0, uti.calcularFolhaPagamento(), 0.001);
        assertEquals(43500.0, hospital.calcularFolhaPagamento(), 0.001);
        assertEquals(6, hospital.contarFuncionarios());
    }

    @Test
    void deveAtualizarTotaisAoRemoverComponente() {
        Departamento enfermagem = filho(hospital, "Enfermagem");
        Departamento uti = filho(enfermagem, "UTI");
        assertTrue(enfermagem.remover(uti));
        assertEquals(7000.0, enfermagem.calcularFolhaPagamento(), 0.001);
        assertEquals(31500.0, hospital.calcularFolhaPagamento(), 0.001);
        assertFalse(enfermagem.remover(uti));
    }

    @Test
    void naoDevePermitirAdicionarNuloNemASiMesmo() {
        Departamento d = new Departamento("D");
        assertThrows(IllegalArgumentException.class, () -> d.adicionar(null));
        assertThrows(IllegalArgumentException.class, () -> d.adicionar(d));
    }

    @Test
    void listaDeFilhosDeveSerSomenteLeitura() {
        assertThrows(UnsupportedOperationException.class,
                () -> hospital.getFilhos().add(new Funcionario("X", "Y", 1)));
    }
}
