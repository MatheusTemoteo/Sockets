
package handlers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JTextArea;

public class ClienteSocket {
    
    Socket conexao;
    
    public void conectar(String endereco, String porta) throws IOException {
        conexao = new Socket(endereco, Integer.parseInt(porta));
    }
    
    public void enviarMensagem(String mensagem) throws IOException {
        PrintStream saida = new PrintStream(conexao.getOutputStream());
        saida.println(mensagem);
    }

    public void ativarRecebimentoMensagens(JTextArea campoParaAdicionar) throws IOException {
        try (Scanner scanner = new Scanner(conexao.getInputStream())) {
			while (true) {
			    while (scanner.hasNextLine()) {
			        campoParaAdicionar.append("[SERVIDOR] " + scanner.nextLine() + "\n");
			    }

				}
		}
		}

    }
