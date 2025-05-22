// pacote onde essa classe está armazenada
package main.java.dao;

import java.util.ArrayList;  // importa a classe ArrayList para armazenar listas de funcionários
import java.util.HashMap;    // importa a classe HashMap para armazenar funcionários em pares (ID, Funcionario)
import java.util.List;       // importa a interface List para criar listas dinâmicas
import java.util.Map;        // importa a interface Map para armazenar dados como chave-valor
import main.java.modelo.Funcionario;   // importa a classe Funcionario para ser usada nesta classe
import main.java.modelo.Gerente;
// classe FuncionarioDao - Responsável por gerenciar os funcionários em memória (como um banco de dados temporário)
public class FuncionarioDao {
    
    // estrutura de dados que armazena os funcionários cadastrados
    // a chave (Integer) representa o ID do funcionário e o valor (Funcionario) é o objeto correspondente
    private static Map<String, Funcionario> funcionarios = new HashMap<>();
    private static Map<String, Gerente> gerentes = new HashMap<>();
    
    // método para salvar um funcionário no sistema
    public void salvar(Funcionario funcionario) {
        // adiciona ou atualiza o funcionário no mapa de dados
        funcionarios.put(funcionario.getcpf(), funcionario);
    }
    public void salvar(Gerente gerente) {
        // adiciona ou atualiza o funcionário no mapa de dados
        gerentes.put(gerente.getcpf(), gerente);
    }
    public Gerente buscarGerentePorCPF(String cpf) {
        // percorre todos os funcionários e retorna aquele que tem o CPF correspondente
        return gerentes.values().stream()
            .filter(f -> f.getcpf().equals(cpf)) // filtra a lista de funcionários pelo CPF
            .findFirst() // pega o primeiro funcionário encontrado (se existir)
            .orElse(null); // retorna null caso nenhum funcionário tenha esse CPF
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
    public void excluir(String cpf) {
        funcionarios.remove(cpf); // remove o funcionário do armazenamento interno
    }
}
