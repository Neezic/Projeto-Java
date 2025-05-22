package main.java.dao;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Set;
import main.java.modelo.Departamento;
import main.java.modelo.Funcionario;

public class DepartamentoDAO {
    private Map<String,Departamento> departamentos = new HashMap<>();

    public void salvar(Departamento departamento){
        if (departamento == null || departamento.getNome() == null) {
            throw new IllegalArgumentException("Departamento inválido");
        }
        
        departamentos.put(departamento.getNome(), departamento);
    }
    public Map<Departamento, Set<Funcionario>> getDeparmtentocomFuncionarios(){
        return departamentos.values().stream()
        .collect(Collectors.toMap(
            d -> d, 
            Departamento::getFuncionarios
            ));

    }
   
    public Map<Departamento, Double> mediaSalarialPorDepartamento() {
    return departamentos.values().stream()
        .collect(Collectors.groupingBy(
            d -> d,
            Collectors.averagingDouble(d -> 
                d.getFuncionarios().stream()
                    .mapToDouble(f -> f.getCargo().getsalarioBase())
                    .average()
                    .orElse(0.0) // Usando 0.0 (double) em vez de 0 (int)
            )
        ));
}
}
