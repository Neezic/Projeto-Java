package main.java.dao;

import java.util.ArrayList;  // importa a classe ArrayList para armazenar listas de funcionários
import java.util.List;       // importa a interface List para criar listas dinâmicas
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.*;
import main.java.modelo.Cargo;

public class CargoDAO {
    private Map<String, Cargo> cargos = new TreeMap<>();
    
    public void salvar (Cargo cargo){
        cargos.put(cargo.getNome(),cargo);
    }

    public List<Cargo> listarTodos(){
        return new ArrayList<>(cargos.values());
    }

    public Cargo buscarPorNome(String nome){
        return cargos.get(nome);
    }

    public List<Cargo> filtrarPorSalarioMinimo(double salarioMinimo){
        return cargos.values().stream()
        .filter(c -> c.getsalarioBase() >= salarioMinimo)
        .collect(Collectors.toList());
        }
}
