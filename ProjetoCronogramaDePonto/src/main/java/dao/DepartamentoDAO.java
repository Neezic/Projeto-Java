package dao;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import modelo.Departamento;

public class DepartamentoDAO {
    private Map<String,Departamento> departamentos = new LinkedHashMap<>();

    public void salvar(Departamento departamento ){
        departamento.put(departamento.getNome(), departamento);
    }
    public Map<Departamento, set<Funcionario>> getDeparmtentocomFuncionarios(){
        return departamentos.values().stream.collect(Collectors.toMap(d -> d, Deparmento : : getFuncionarios));

    }
    public Map<Departameto, double> mediaSalarialPorDepartamento(){
        return departamentos.values().stream().collect(Collectors.groupingBy(d -> d, Collectors.averagingDouble(d -> d.getFuncionarios().stream()
        .mapToDouble(f -> f.getcargo().getsalarioBase().average().orElse(0))

        )));
    }
}
