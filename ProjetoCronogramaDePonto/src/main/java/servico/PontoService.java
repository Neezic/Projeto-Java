package servico;

import dao.RegistraPontoDAO;
import modelo.Funcionario;
import modelo.RegistraPonto;
import java.time.LocalDateTime;
import java.util.List;

public class PontoService{
    // instanciando o objeto que vai manipular os registros de ponto no banco de dados
    private RegistraPontoDAO registroDAO = new RegistraPontoDAO();

    // método para registrar a entrada do funcionário
    public void registrarEntrada(Funcionario funcionario) throws IllegalStateException{
        // verifica se o funcionário é nulo, caso seja, lança uma exceção
        if (funcionario == null){
            throw new IllegalArgumentException("Funcionario não pode ser nulo");
        }
        // verifica se já existe um registro de entrada aberto para o funcionário
        RegistraPonto registroAberto = registroDAO.buscarRegistroAberto(funcionario);
        if(registroAberto != null){
            throw new IllegalStateException("Funcionario já possui um registro de entrada");
        }
        // cria um novo registro de entrada para o funcionário e o salva
        RegistraPonto novoRegistro = new RegistraPonto(funcionario, LocalDateTime.now());
        registroDAO.salvar(novoRegistro);
    }

    // método para registrar a saída do funcionário
    public void registrarSaida(Funcionario funcionario){
        // verifica se o funcionário é nulo, caso seja, lança uma exceção
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        // busca o registro de entrada que está aberto para o funcionário
        RegistraPonto registro = registroDAO.buscarRegistroAberto(funcionario);

        // verifica se não existe um registro de entrada aberto
        if (registro == null) {
            throw new IllegalArgumentException("Não existe registro de entrada aberto para este funcionário");
        }
        
        // se o registro de entrada existir, registra a saída e atualiza o registro
        if (registro != null){
            registro.setSaida(LocalDateTime.now());
            registroDAO.atualizar(registro);
        }
    }
    
    // método para listar os registros de ponto do funcionário
    public List <RegistraPonto> listarRegistros(Funcionario funcionario){
        return registroDAO.listarPorFuncionario(funcionario);
    }
}
