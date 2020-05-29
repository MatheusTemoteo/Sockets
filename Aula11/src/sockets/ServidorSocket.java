
package sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JTextArea;

public class ServidorSocket {

    ServerSocket servidor;
    Socket cliente;
    
    public void iniciar(String porta, JTextArea campoMensagens) throws IOException {
        servidor = new ServerSocket(Integer.parseInt(porta));

        cliente = servidor.accept();
        try (Scanner scanner = new Scanner(cliente.getInputStream())) {
			while (true) {
			    while (scanner.hasNextLine()) {
			        campoMensagens.append("[CLIENTE] " + scanner.nextLine() + "\n");
			    }
			}
		}

    }
    
    public void enviarMensagem(String mensagem) throws IOException {
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        saida.println(mensagem);
    }


}
