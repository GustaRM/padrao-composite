package padroesestruturais.composite;

/** Monta a estrutura organizacional de exemplo usada na demonstração e nos testes. */
public class Hospital {

    public static Departamento montar() {
        Departamento uti = new Departamento("UTI")
                .adicionar(new Funcionario("Dra. Helena", "Intensivista", 5000))
                .adicionar(new Funcionario("Marcos", "Técnico de Enfermagem", 3000));

        Departamento enfermagem = new Departamento("Enfermagem")
                .adicionar(new Funcionario("Paula", "Enfermeira", 7000))
                .adicionar(uti);

        Departamento diretoria = new Departamento("Diretoria")
                .adicionar(new Funcionario("Dr. Roberto", "Diretor Clínico", 20000));

        Departamento administracao = new Departamento("Administração")
                .adicionar(new Funcionario("Ana", "Analista Administrativa", 4500));

        return new Departamento("Hospital Central")
                .adicionar(diretoria)
                .adicionar(enfermagem)
                .adicionar(administracao);
    }
}
