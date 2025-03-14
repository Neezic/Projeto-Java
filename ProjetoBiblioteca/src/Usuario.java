import java.util.ArrayList;
public class Usuario {
    String nome;
    int idUsuario;
    double[] notas;
    double media;
    
    ArrayList<String> emprestimo = new ArrayList<String>();
    String[] livros;

    void nota(){
        for (int i = 0; i < notas.length; i++){
            media += notas[i];
        }
        media = media / notas.length;
    }

    void addNota(double n){
    }

}
