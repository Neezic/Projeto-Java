// pacote onde essa classe está armazenada
package dao;

import java.util.ArrayList;  // importa a classe ArrayList para armazenar listas de funcionários
import java.util.HashMap;    // importa a classe HashMap para armazenar funcionários em pares (ID, Funcionario)
import java.util.List;       // importa a interface List para criar listas dinâmicas
import java.util.Map;        // importa a interface Map para armazenar dados como chave-valor
import modelo.Funcionario;   // importa a classe Funcionario para ser usada nesta classe

// classe FuncionarioDao - Responsável por gerenciar os funcionários em memória (como um banco de dados temporário)
public class FuncionarioDao {
    
    // estrutura de dados que armazena os funcionários cadastrados
    // a chave (Integer) representa o ID do funcionário e o valor (Funcionario) é o objeto correspondente
    private static Map<Integer, Funcionario> funcionarios = new HashMap<>();
    
    // variável que controla o próximo ID disponível para um novo funcionário
    private static int proximoId = 1;
    
    // método para salvar um funcionário no sistema
    public void salvar(Funcionario funcionario) {
        // se o funcionário ainda não tiver um ID (valor 0), ele recebe um novo ID automaticamente
        if (funcionario.getId() == 0) {
            funcionario.setId(proximoId++); // Define o ID e incrementa o próximo disponível
        }
        // adiciona ou atualiza o funcionário no mapa de dados
        funcionarios.put(funcionario.getId(), funcionario);
    }

    // método para buscar um funcionário pelo ID informado
    public Funcionario buscarPorId(int id) {
        return funcionarios.get(id); // Retorna o funcionário correspondente ao ID ou null se não encontrar
    }

    // método para buscar um funcionário pelo CPF
    public Funcionario buscarPorCPF(String cpf) {
        // percorre todos os funcionários e retorna aquele que tem o CPF correspondente
        return funcionarios.values().stream()
            .filter(f -> f.getcpf().equals(cpf)) // filtra a lista de funcionários pelo CPF
            .findFirst() // pega o primeiro funcionário encontrado (se existir)
            .orElse(null); // retorna null caso nenhum funcionário tenha esse CPF
    }

    // método para listar todos os funcionários cadastrados no sistema
    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios.values()); // Retorna uma lista contendo todos os funcionários
    }

    // método para excluir um funcionário pelo ID informado
    public void excluir(int id) {
        funcionarios.remove(id); // remove o funcionário do armazenamento interno
    }
}
