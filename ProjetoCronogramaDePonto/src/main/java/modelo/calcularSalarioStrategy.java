package main.java.modelo;
import java.io.Serializable;

public interface calcularSalarioStrategy extends Serializable {
    double calcularSalario(Funcionario funcionario);

}

