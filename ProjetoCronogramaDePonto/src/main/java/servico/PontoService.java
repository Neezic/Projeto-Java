package servico;

import dao.RegistraPontoDAO;
import modelo.Funcionario;
import modelo.RegistraPonto;
import java.time.LocalDateTime;
import java.util.List;

public class PontoService{
    private RegistraPontoDAO registroDAO = new RegistraPontoDAO();

    public void registrarEntrada(Funcionario funcionario) throws IllegalStateException{
        if (funcionario == null){
            throw new IllegalArgumentException("Funcionario não pode ser nulo");
        }
        RegistraPonto registroAberto = registroDAO.buscarRegistroAberto(funcionario);
        if(registroAberto != null){
            throw new IllegalStateException("Funcionario já possui um registro de entrada");
        }
        RegistraPonto novoRegistro = new RegistraPonto(funcionario, LocalDateTime.now());
        registroDAO.salvar(novoRegistro);
    }

    public void registrarSaida(Funcionario funcionario){
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        RegistraPonto registro = registroDAO.buscarRegistroAberto(funcionario);

        if (registro == null) {
            throw new IllegalArgumentException("Não existe registro de entrada aberto para este funcionário");
        }
        
        if (registro != null){
            registro.setSaida(LocalDateTime.now());
            registroDAO.atualizar(registro);
        }
    }
    public List <RegistraPonto> listarRegistros(Funcionario funcionario){
        return registroDAO.listarPorFuncionario(funcionario);
    }
}