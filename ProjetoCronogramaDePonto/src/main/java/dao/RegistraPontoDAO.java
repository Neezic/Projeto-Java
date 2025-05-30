// pacote onde essa classe esta armazenada
package main.java.dao;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import main.java.modelo.Funcionario;
import main.java.modelo.RegistraPonto;
import main.java.modelo.Usuario;
// classe RegistraPontoDAO - responsável por gerenciar os registros de ponto dos funcionários
public class RegistraPontoDAO {

    // lista que armazena os registros de ponto
    private static List<RegistraPonto> registros = new ArrayList<>();
    
    // variável para controlar o próximo ID disponível para um novo registro de ponto
    private static int proximoId = 1;

    private static final String FILE_NAME = "registros_ponto.dat";
    static {
        carregarRegistros();
    }


    @SuppressWarnings("unchecked") // Para o cast de ois.readObject()
    private static synchronized void carregarRegistros() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                registros = (List<RegistraPonto>) obj;
                // Recalcular proximoId baseado nos dados carregados
                if (!registros.isEmpty()) {
                    Optional<Integer> maxId = registros.stream()
                                                     .map(RegistraPonto::getId)
                                                     .max(Integer::compareTo);
                    proximoId = maxId.orElse(0) + 1;
                } else {
                    proximoId = 1;
                }
                System.out.println("Registros de ponto carregados de " + FILE_NAME);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo " + FILE_NAME + " não encontrado. Iniciando com lista de registros vazia.");
            registros = new ArrayList<>();
            proximoId = 1;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar registros de ponto de " + FILE_NAME + ": " + e.getMessage());
            // Em caso de erro, iniciar com uma lista vazia para evitar falhas na aplicação
            registros = new ArrayList<>();
            proximoId = 1;
        }
    }

    private static synchronized void salvarTodosRegistros() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(registros);
            // System.out.println("Registros de ponto salvos em " + FILE_NAME); // Opcional: log de salvamento
        } catch (IOException e) {
            System.err.println("Erro ao salvar registros de ponto em " + FILE_NAME + ": " + e.getMessage());
        }
    }

    // método para atualizar um registro de ponto
    public void atualizar(RegistraPonto registroAtualizado) {
        // percorre a lista de registros para encontrar o que corresponde ao ID informado
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId() == registroAtualizado.getId()) {
                registros.set(i, registroAtualizado);  // substitui o registro antigo pelo novo
                salvarTodosRegistros(); //Salvar após atualizar.
                return;  // finaliza a execução após a atualização
            }
        }
        // caso o registro não seja encontrado, lança uma exceção
        throw new NoSuchElementException("Registro não encontrado para atualização");
    }

    // método para salvar um novo registro de ponto
    public void salvar(RegistraPonto registro) {
        boolean existe = false;
        // se o registro ainda não tem ID, cria um novo ID e adiciona à lista
        if (registro.getId() != 0) {
            for (int i = 0; i < registros.size(); i++) {
                if (registros.get(i).getId() == registro.getId()) {
                    registros.set(i, registro);  // atualiza o registro existente
                    existe = true;
                    break;  // finaliza a execução após a atualização
                }
            }
            
        } 
        if (!existe){
            if (registro.getId() == 0){
                registro.setId(proximoId);
            } else {
                if (registro.getId() >= proximoId){
                    proximoId = registro.getId() + 1;
                }
            }
            registros.add(registro);
        }
        salvarTodosRegistros(); //Salvar após adicionar ou atualizar. 
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

