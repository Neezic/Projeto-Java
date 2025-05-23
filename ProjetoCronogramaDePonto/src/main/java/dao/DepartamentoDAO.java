package main.java.dao;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import main.java.modelo.Departamento;
import main.java.modelo.Funcionario;

public class DepartamentoDAO {
    private Map<String,Departamento> departamentos = new ConcurrentHashMap<>();

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
    public List<Departamento>buscarPorPrefixo(String prefixo){
        return departamentos.entrySet().stream()
        .filter(e -> e.getKey().startsWith(prefixo))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
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
