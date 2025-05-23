package main.java.modelo;

public class CalcularSalariComBonus implements calcularSalarioStrategy {
    public double calcularSalario(Funcionario funcionario){
        double salarioBase = funcionario.getCargo().getsalarioBase();
        return salarioBase + (salarioBase *0.1);
    }

}
