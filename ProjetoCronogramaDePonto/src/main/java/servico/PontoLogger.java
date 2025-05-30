package main.java.servico;

import main.java.modelo.PontoObserver;
import main.java.modelo.RegistraPonto;

public class PontoLogger implements PontoObserver {

    @Override
    public void notificarRegistro(RegistraPonto registro, String mensagem) {
        System.out.printf("[LOG] %s - Funcionário: %s, Horário: %s%n",
            mensagem,
            registro.getfuncionario().getnome(),
            registro.gethoraEntrada());
    }
}
