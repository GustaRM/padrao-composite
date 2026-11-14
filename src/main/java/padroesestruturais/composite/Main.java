package padroesestruturais.composite;

public class Main {

    public static void main(String[] args) {
        Componente hospital = Hospital.montar();

        System.out.print(hospital.exibir(0));
        System.out.println();
        System.out.println("Folha total: " + Formato.moeda(hospital.calcularFolhaPagamento()));
        System.out.println("Funcionários: " + hospital.contarFuncionarios());
    }
}
