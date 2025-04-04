// pacote onde essa classe esta armazenada
package dao;

import java.util.ArrayList;   // importa a classe ArrayList para armazenar registros de ponto
import java.util.List;        // importa a interface List para retornar listas de registros
import java.util.NoSuchElementException; // importa exceção para quando não encontrar um registro
import modelo.Funcionario;   // importa a classe Funcionario para ser usada neste DAO
import modelo.RegistraPonto; // importa a classe RegistraPonto para ser usada neste DAO
import modelo.Usuario;        
// classe RegistraPontoDAO - responsável por gerenciar os registros de ponto dos funcionários
public class RegistraPontoDAO {

    // lista que armazena os registros de ponto
    private static List<RegistraPonto> registros = new ArrayList<>();
    
    // variável para controlar o próximo ID disponível para um novo registro de ponto
    private static int proximoId = 1;

    // método para atualizar um registro de ponto
    public void atualizar(RegistraPonto registroAtualizado) {
        // percorre a lista de registros para encontrar o que corresponde ao ID informado
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId() == registroAtualizado.getId()) {
                registros.set(i, registroAtualizado);  // substitui o registro antigo pelo novo
                return;  // finaliza a execução após a atualização
            }
        }
        // caso o registro não seja encontrado, lança uma exceção
        throw new NoSuchElementException("Registro não encontrado para atualização");
    }

    // método para salvar um novo registro de ponto
    public void salvar(RegistraPonto registro) {
        // se o registro ainda não tem ID, cria um novo ID e adiciona à lista
        if (registro.getId() == 0) {
            registro.setId(proximoId++);
            registros.add(registro);  // adiciona o novo registro à lista
        } else {
            // caso o registro já tenha ID, procura se o ID já existe na lista
            for (int i = 0; i < registros.size(); i++) {
                if (registros.get(i).getId() == registro.getId()) {
                    registros.set(i, registro);  // atualiza o registro existente
                    return;  // finaliza a execução após a atualização
                }
            }
        }
    }

    // método para buscar o registro de ponto aberto de um funcionário (sem hora de saída)
    public RegistraPonto buscarRegistroAberto(Funcionario funcionario) {
        // filtra os registros para encontrar um que tenha o mesmo funcionário e hora de saída nula
        return registros.stream()
            .filter(r -> r.getfuncionario().equals(funcionario) && r.gethoraSaida() == null)
            .findFirst()  // retorna o primeiro registro encontrado (se houver)
            .orElse(null);  // retorna null caso não encontre nenhum
    }

    // método para listar todos os registros de ponto de um determinado funcionário
    public List<RegistraPonto> listarPorFuncionario(Usuario usuario) {
        List<RegistraPonto> resultado = new ArrayList<>();  // lista para armazenar os resultados
        // percorre todos os registros e adiciona os que pertencem ao funcionário
        for (RegistraPonto r : registros) {
            if (r.getfuncionario().equals(usuario)) {
                resultado.add(r);  // adiciona o registro à lista de resultados
            }
        }
        return resultado;  // retorna a lista de registros encontrados
    }
    public List<RegistraPonto> listarTodos() {
        return new ArrayList<>(registros); // Retorna uma lista contendo todos os funcionários
    }
}

