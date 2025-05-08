// polimorfismo inicio e exceção de erros no final 
package modelo;
import dao.FuncionarioDao;
import java.time.*;
import java.time.format.*;
import java.util.*;
import servico.PontoService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PontoService service = new PontoService();
    private static FuncionarioDao funcionarioDAO = new FuncionarioDao();
    private static Usuario usuarioAtual = null;

    public static void main(String[] args) {
        // Inicializa dados de teste
        Departamento ti = new Departamento("TI");
        Cargo dev = new Cargo("Desenvolvedor", 5000.00);
        Cargo gerenteCargo = new Cargo("Gerente", 8000.00);

        // Cadastro de Usuario e POLIMORFISMO classe Gerente herda de Funcionario.
        // O Gerente pode ser tratado como um Funcionario devido à herança. dsd compartilhem a mesma classe base
        Funcionario funcionario = new Funcionario("João", "12345678900", ti, dev);
        Gerente gerente = new Gerente("Maria", "98765432100", ti, gerenteCargo);
        
        funcionarioDAO.salvar(funcionario);
        funcionarioDAO.salvar(gerente); // Gerente é um Funcionario
        
        // Loop principal
        while (true) {
            if (usuarioAtual == null) {
                exibirMenuLogin();
            } else {
                if (usuarioAtual instanceof Gerente) {
                    exibirMenuGerente();
                } else {
                    exibirMenuFuncionario();
                }
            }
        }
    }
<<<<<<< Updated upstream

  
=======
    
    // criando dois funcionários de exemplo
    private static void cadastrarFuncionariosExemplo() {
        Departamento ti = new Departamento("TI"); // Associação estabelecida.
        Funcionario joao = new Funcionario("João Silva", "12345678900",ti,LocalDateTime.now());
        Departamento rh = new Departamento("RH");
        Funcionario maria = new Funcionario("Maria Souza", "98765432100",rh, LocalDateTime.now());
        rh.adicionarFuncionario(maria); // Agregação, Maria pode existir sem o departamento.
        funcionarioDAO.salvar(joao);
        funcionarioDAO.salvar(maria);
    }
>>>>>>> Stashed changes

    // MENU DE LOGIN (igual ao seu original)
    private static void exibirMenuLogin() {
        System.out.println("\n=== SISTEMA DE PONTO ===");
        System.out.println("1. Login");
        System.out.println("2. Sair");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> fazerLogin();
            case 2 -> {
                System.out.println("Saindo do sistema...");
                System.exit(0);
            }
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void fazerLogin() {
        System.out.print("\nCPF (digite 123 ou 456): ");
        String cpf = scanner.nextLine();

        usuarioAtual = funcionarioDAO.buscarGerentePorCPF(cpf);
        if (usuarioAtual == null){
            usuarioAtual = funcionarioDAO.buscarPorCPF(cpf);
        } 
        
        if (usuarioAtual == null) {
            System.out.println("Usuário não encontrado!");
        } else {
            System.out.println("\nBem-vindo, " + usuarioAtual.getnome() + "!");
        }
    }

    // MENU DE FUNCIONÁRIO (similar ao seu original)
    private static void exibirMenuFuncionario() {
        System.out.println("\n=== MENU FUNCIONÁRIO ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Meus Registros");
        System.out.println("4. Logout");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
<<<<<<< Updated upstream
        scanner.nextLine();
=======
        scanner.nextLine(); // limpar buffer

        // controlando a escolha do usuário no menu
        switch (opcao) {
            case 1:
                registrarEntrada();
                break;
            case 2:
                registrarSaida();
                break;
            case 3:
                consultarRegistros();
                break;
            case 4:
                funcionarioAtual = null;
                System.out.println("Logout realizado com sucesso!");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    // registra a entrada do funcionário
    private static void registrarEntrada() {
        try {
            service.registrarEntrada(funcionarioAtual,registroDAO);
            System.out.println("\nEntrada registrada com sucesso em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // registra a saída do funcionário
    private static void registrarSaida() {
        try {
            service.registrarSaida(funcionarioAtual);
            System.out.println("\nSaída registrada com sucesso em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        } catch (IllegalStateException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // consulta os registros de entrada e saída do funcionário
    private static void consultarRegistros() {
        System.out.println("\n=== MEUS REGISTROS ===");
        var registros = service.listarRegistros(funcionarioAtual);
>>>>>>> Stashed changes
        
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 -> consultarRegistros();
            case 4 -> usuarioAtual = null;
            default -> System.out.println("Opção inválida!");
        }
    }

    // NOVO MENU ESPECÍFICO PARA GERENTES
    private static void exibirMenuGerente() {
        System.out.println("\n=== MENU GERENTE ===");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Saída");
        System.out.println("3. Consultar Registros da Equipe");
        System.out.println("4. Aprovar Registros Pendentes");
        System.out.println("5. Logout");
        System.out.print("Escolha: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1 -> registrarEntrada();
            case 2 -> registrarSaida();
            case 3 ->aprovarRegistroPendente();
            case 4 -> usuarioAtual = null;
            default -> System.out.println("Opção inválida!");
        }
        

    }
    private static void aprovarRegistroPendente() {
        // Verifica se o usuário atual é um Gerente
        if (!(usuarioAtual instanceof Gerente)) {
            System.out.println("Apenas gerentes podem aprovar registros!");
            return;
        }
        
        // Cast seguro para Gerente
        Gerente gerente = (Gerente) usuarioAtual;
        
        // Solicita CPF
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        
        // Busca funcionário
        Funcionario funcionario = funcionarioDAO.buscarPorCPF(cpf);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }
        
        // Busca e aprova registro
        RegistraPonto registro = pontoService.buscarRegistroAberto(funcionario);
        if (registro != null) {
            gerente.aprovarRegistroPonto(registro);
        } else {
            System.out.println("Nenhum registro pendente encontrado para " + funcionario.getnome());
        }
    }

    // TRY CATCH EXCEÇÃO ERROS quando o usuário tenta registrar a entrada ou a saída, 
    // pode dar algum erro e a gente trata isso com o catch, pra não quebrar o programa e mostrar uma mensagem certinha pro usuário.
    private static void registrarEntrada() {
        try {
            pontoService.registrarEntrada((Funcionario)usuarioAtual);
            System.out.println("✅ Entrada registrada: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))); 
            // o sistema lança uma exceção IllegalStateException
        } catch (IllegalStateException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
   // Isso ajuda a deixar o sistema mais estável e com uma resposta mais amigável, mesmo quando dá erro
    private static void registrarSaida() {
        try {
            pontoService.registrarSaida((Funcionario)usuarioAtual);
            System.out.println("✅ Saída registrada: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } catch (IllegalStateException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private static void consultarRegistros() {
        List<RegistraPonto> registros = pontoService.listarRegistros((Funcionario)usuarioAtual);
        exibirRegistros(registros, "SEUS REGISTROS");
    }

    private static void exibirRegistros(List<RegistraPonto> registros, String titulo) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        System.out.println("\n📋 " + titulo + ":");
        System.out.println("----------------------------------------");
        
        registros.forEach(reg -> {
            System.out.printf("%s | %s | Entrada: %s | Saída: %s | Horas: %d\n",
                reg.getfuncionario().getnome(),
                reg.gethoraEntrada().toLocalDate(),
                reg.gethoraEntrada().toLocalTime(),
                reg.gethoraSaida() != null ? reg.gethoraSaida().toLocalTime() : "---",
                reg.calcularHorasTrabalhadas());
        });
    }
}
