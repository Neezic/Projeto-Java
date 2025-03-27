package main.java.dao;

import main.java.modelo.Funcionario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioDao {
    private static Map<Integer,Funcionario> funcionarios = new HashMap<>();
    private static int proximoId = 1;
    
    public void salvar(Funcionario funcionario){
        if (funcionario.getId() == 0){
            funcionario.setId(proximoId++);
        }
        funcionarios.put(funcionario.getId(),funcionario);
    }

    public Funcionario buscarPorId(int id){
        return funcionarios.get(id);
    }
    public Funcionario buscarPorCPF(String cpf){
        return funcionarios.values().stream()
        .filter(f -> f.getcpf().equals(cpf))
        .findFirst()
        .orElse(null);
    }

    public List<Funcionario> listarTodos(){
        return new ArrayList<>(funcionarios.values());
    }
    public void excluir(int id){
        funcionarios.remove(id);
    }
    
}
