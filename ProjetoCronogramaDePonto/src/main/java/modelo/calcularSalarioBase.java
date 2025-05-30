package main.java.modelo;

public class calcularSalarioBase implements  calcularSalarioStrategy {
    private static final long serialVersionUID = 1L; 
    @Override    
    public double calcularSalario(Funcionario funcionario){
            return funcionario.getCargo().getsalarioBase();
        }

}
