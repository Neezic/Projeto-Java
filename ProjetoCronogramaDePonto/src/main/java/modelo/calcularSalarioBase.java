package main.java.modelo;

public class calcularSalarioBase implements  calcularSalarioStrategy {
        public double calcularSalario(Funcionario funcionario){
            return funcionario.getCargo().getsalarioBase();
        }

}
