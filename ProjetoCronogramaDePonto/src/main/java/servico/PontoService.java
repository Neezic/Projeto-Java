package servico;

import dao.RegistraPontoDAO;
import modelo.Funcionario;
import modelo.Gerente;
import modelo.RegistraPonto;
import modelo.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class PontoService{
    // instanciando o objeto que vai manipular os registros de ponto no banco de dados
    private RegistraPontoDAO registroDAO = new RegistraPontoDAO();

    // método para registrar a entrada do funcionário
    public void registrarEntrada(Usuario usuario) throws IllegalStateException{
        // verifica se o funcionário é nulo, caso seja, lança uma exceção
        if (usuario == null){
            throw new IllegalArgumentException("Funcionario não pode ser nulo");
        }
        if (!(usuario instanceof Funcionario)){
            throw new IllegalStateException("Apenas Funcionarios podem registrar ponto");
        }
        Funcionario funcionario = (Funcionario) usuario;

        // verifica se já existe um registro de entrada aberto para o funcionário
        RegistraPonto registroAberto = registroDAO.buscarRegistroAberto(funcionario);
        if(registroAberto != null){
            throw new IllegalStateException("Funcionario já possui um registro de entrada");
        }
        LocalDateTime agora = LocalDateTime.now();
        if (usuario instanceof Gerente){
            agora = agora.plusHours(1);
        }
        // cria um novo registro de entrada para o funcionário e o salva
        RegistraPonto novoRegistro = new RegistraPonto(funcionario, agora);
        registroDAO.salvar(novoRegistro);
        System.out.println("Entrada registrada para" + funcionario.getnome() 
                            + "(" + usuario.getClass().getSimpleName() + ")");
    }

    // método para registrar a saída do funcionário
    public void registrarSaida(Usuario usuario){
        // verifica se o funcionário é nulo, caso seja, lança uma exceção
        if (usuario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        Funcionario funcionario = (Funcionario) usuario;
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
    
    public RegistraPonto buscarRegistroAberto(Funcionario funcionario) {
        return registroDAO.listarTodos().stream()
            .filter(r -> r.getfuncionario().equals(funcionario) && r.gethoraSaida() == null)
            .findFirst()
            .orElse(null);
    }
    // método para listar os registros de ponto do funcionário
    public List <RegistraPonto> listarRegistros(Usuario usuario){
        return registroDAO.listarPorFuncionario(usuario);
    }
}
