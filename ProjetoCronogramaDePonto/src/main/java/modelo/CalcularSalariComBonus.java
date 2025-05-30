package main.java.modelo;

public class CalcularSalariComBonus implements calcularSalarioStrategy {
    private static final long serialVersionUID = 1L; 
    @Override
    public double calcularSalario(Funcionario funcionario){
        double salarioBase = funcionario.getCargo().getsalarioBase();
        return salarioBase + (salarioBase *0.1);
    }

}
