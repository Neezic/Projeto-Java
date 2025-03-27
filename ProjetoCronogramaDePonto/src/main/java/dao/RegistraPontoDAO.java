package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import modelo.Funcionario;
import modelo.RegistraPonto;


public class RegistraPontoDAO {
    private static List<RegistraPonto> registros = new ArrayList<>();
    private static int proximoId = 1;

    public void atualizar(RegistraPonto registroAtualizado){
        for (int i = 0; i < registros.size(); i++){
            if (registros.get(i).getId() == registroAtualizado.getId()){
                registros.set(i, registroAtualizado);
                return;
            }
        }
        throw new NoSuchElementException("Registro não encontrado para atualização");
    }
    public void salvar(RegistraPonto registro){
        if(registro.getId() == 0 ){
            registro.setId(proximoId++);
            registros.add(registro);
        } else {
            for (int i = 0; i < registros.size();i++){
                if (registros.get(i).getId() == registro.getId()){
                    registros.set(i,registro);
                    return;
                }
            }
        }
        
    }
    public RegistraPonto buscarRegistroAberto(Funcionario funcionario){
        return registros.stream()
        .filter( r -> r.getfuncionario().equals(funcionario) && r.gethoraSaida() == null)
        .findFirst()
        .orElse(null);
    }
    public List<RegistraPonto> listarPorFuncionario(Funcionario funcionario){
        List<RegistraPonto> resultado = new ArrayList<>();
        for(RegistraPonto r : registros){
            if(r.getfuncionario().equals(funcionario)){
                resultado.add(r);
            }
        }
        return resultado;
    }
    
}
