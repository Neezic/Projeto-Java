package main.java.dao;

import main.java.modelo.RegistraPonto;
import main.java.modelo.Funcionario;
import java.util.ArrayList;
import java.util.List;


public class RegistraPontoDAO {
    private static List<RegistroPonto> registros = new ArrayList<>();
    private static int proximoId = 1;
    
    public void salvar(RegistroPonto registro){
        if(registro.getId() == 0 ){
            registro.setId(proximoId++);
        }
        registro.add(registro);
    }
    public RegistroPonto buscarRegistroAberto(Funcionario funcionario){
        return registros.stream()
        .filter( r -> r.getfuncionario().equals(funcionario) && r.getHoraSaida() == null)
        .findFirst()
        .orElse(null);
    }
    public List<RegistroPonto> listarPorFuncionario(Funcionario funcionario){
        List<RegistroPonto> resultado = new ArrayList<>();
        for(RegistroPonto r : registros){
            if(r.getfuncionario().equals(funcionario)){
                resultado.add(r);
            }
        }
        return resultado;
    }
    
}
