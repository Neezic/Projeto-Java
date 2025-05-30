// pacote onde essa classe está armazenada
package main.java.dao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;  // importa a classe ArrayList para armazenar listas de funcionários
import java.util.List;    // importa a classe HashMap para armazenar funcionários em pares (ID, Funcionario)
import java.util.Map;       // importa a interface List para criar listas dinâmicas
import main.java.modelo.Funcionario;        // importa a interface Map para armazenar dados como chave-valor
import main.java.modelo.Gerente;
// classe FuncionarioDao - Responsável por gerenciar os funcionários em memória (como um banco de dados temporário)
public class FuncionarioDao {
    
    // estrutura de dados que armazena os funcionários cadastrados
    // a chave (Integer) representa o ID do funcionário e o valor (Funcionario) é o objeto correspondente
    private static Map<String, Funcionario> funcionarios = new HashMap<>();
    private static Map<String, Gerente> gerentes = new HashMap<>();

    private static final String FUNC_FILE_NAME = "func.dat";
    private static final String GER_FILE_NAME = "funcgerente.dat";

    static {
        carregarMapas();
    }
     @SuppressWarnings("unchecked")
    private static synchronized void carregarMapas() {
        // Carregar Funcionarios
        try (ObjectInputStream oisFunc = new ObjectInputStream(new FileInputStream(FUNC_FILE_NAME))) {
            Object obj = oisFunc.readObject();
            if (obj instanceof Map) {
                funcionarios = (Map<String, Funcionario>) obj;
                System.out.println("Funcionários carregados de " + FUNC_FILE_NAME);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo " + FUNC_FILE_NAME + " não encontrado. Iniciando com mapa de funcionários vazio.");
            funcionarios = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar funcionários de " + FUNC_FILE_NAME + ": " + e.getMessage());
            funcionarios = new HashMap<>();
        }

        // Carregar Gerentes
        try (ObjectInputStream oisGer = new ObjectInputStream(new FileInputStream(GER_FILE_NAME))) {
            Object obj = oisGer.readObject();
            if (obj instanceof Map) {
                gerentes = (Map<String, Gerente>) obj;
                System.out.println("Gerentes carregados de " + GER_FILE_NAME);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo " + GER_FILE_NAME + " não encontrado. Iniciando com mapa de gerentes vazio.");
            gerentes = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar gerentes de " + GER_FILE_NAME + ": " + e.getMessage());
            gerentes = new HashMap<>();
        }
    }

    private static synchronized void salvarFuncionariosMap() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FUNC_FILE_NAME))) {
            oos.writeObject(funcionarios);
        } catch (IOException e) {
            System.err.println("Erro ao salvar funcionários em " + FUNC_FILE_NAME + ": " + e.getMessage());
        }
    }

    private static synchronized void salvarGerentesMap() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GER_FILE_NAME))) {
            oos.writeObject(gerentes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar gerentes em " + GER_FILE_NAME + ": " + e.getMessage());
        }
    }
    
    
    // método para salvar um funcionário no sistema
    public void salvar(Funcionario funcionario) {
        // adiciona ou atualiza o funcionário no mapa de dados
        funcionarios.put(funcionario.getcpf(), funcionario);
        salvarFuncionariosMap();
    }
    public void salvar(Gerente gerente) {
        // adiciona ou atualiza o funcionário no mapa de dados
        gerentes.put(gerente.getcpf(), gerente);
        salvarGerentesMap();
    }
    public Gerente buscarGerentePorCPF(String cpf) {
        // Simplificado, pois agora o mapa de gerentes é a fonte primária
       return gerentes.get(cpf);
    }

    // método para buscar um funcionário pelo CPF
    public Funcionario buscarPorCPF(String cpf) {
       //simplificado pois agora funcionario é fonte primaria..
       //primeiro busca em gerente, Pois gerentes também são funcionarios.
       Funcionario f = gerentes.get(cpf);
       if ( f != null){
        return f;
       }
       return funcionarios.get(cpf);
    }

    // método para listar todos os funcionários cadastrados no sistema
    public List<Funcionario> listarTodos() {
        // Combina as duas listas, evitando duplicatas se um gerente também estiver em funcionarios.
        // No entanto, com a lógica de salvar separada, eles não deveriam estar duplicados por CPF.
        Map<String, Funcionario> todos = new HashMap<>(funcionarios);
        todos.putAll(gerentes); // Gerentes sobrescrevem funcionários com mesmo CPF se houver
        return new ArrayList<>(todos.values()); // Retorna uma lista contendo todos os funcionários e gerentes.
    }

    // método para excluir um funcionário pelo ID informado
    public void excluir(String cpf) {
        boolean removido = false;
        if (gerentes.remove(cpf) != null){
            salvarGerentesMap();
            removido = true;
        }
        if (funcionarios.remove(cpf) != null){
            salvarFuncionariosMap();
            removido = true;
        }
        //se removido, logar ou tratar.
    }
}
