package dao;

import java.util.ArrayList;  // importa a classe ArrayList para armazenar listas de funcionários
import java.util.HashMap;    // importa a classe HashMap para armazenar funcionários em pares (ID, Funcionario)
import java.util.List;       // importa a interface List para criar listas dinâmicas
import java.util.Map;
import java.util.stream.*;
public class CargoDAO {
    private Map<String, Cargo> cargos = new TreeMap<>();
    
    public void salvar (Cargo cargo){
        cargos.put(cargo.getnome(),cargo);
    }

    public List<Cargo> listarTodos(){
        return new ArrayList<>(cargos.values());
    }

    public Cargo buscarPorNome(String nome){
        return cargos.get(nome);
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
