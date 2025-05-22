package main.java.modelo;
import java.util.ArrayList;
import java.util.List;
public class Gerente extends Funcionario {

    private List<Departamento> departamentosGerenciados;

    public Gerente(String nome, String cpf, Departamento departamento, Cargo cargo){
        super(nome,cpf,departamento,cargo);
        this.departamentosGerenciados = new ArrayList<>();
    }
    public List<Departamento> getDepartamentosGerenciados(){
        return departamentosGerenciados;
    }

    public void setDepartementosGerenciados(List<Departamento> departamentosGerenciados){
        this.departamentosGerenciados = departamentosGerenciados;
    }

    @Override
    public boolean autenticar(String senha){
        return senha != null && senha.length() >= 10 && contemCaracterEspecial(senha);
    }

    public void aprovarRegistroPonto(RegistraPonto registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro não pode ser nulo");
        }
        
        if (registro.gethoraSaida() == null) {
            registro.setAprovado(true);
            System.out.println("Registro de " + registro.getfuncionario().getnome() + " aprovado!");
        } else {
            System.out.println("Apenas registros pendentes podem ser aprovados");
        }
    }
    private boolean contemCaracterEspecial(String senha){
        return senha.matches(".*[!@#$%^&*].");
    }

}
