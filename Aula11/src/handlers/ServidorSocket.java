
package handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class ServidorSocket {

	private ServerSocket servidor;
	private ArrayList<ThreadC> clientes = new ArrayList<>();
	private JTextArea campoMensagens;
	private static ServidorSocket nu = null;
	
	private ServidorSocket() {
		
	}
	
	public synchronized static ServidorSocket getInstance() {
		if(nu == null) {
			nu = new ServidorSocket();
			return nu;
		}else {
			return nu;
		}
		
	}
	
	public void iniciar(String porta, JTextArea campoMensagens) throws IOException {
		this.campoMensagens = campoMensagens;
		servidor = new ServerSocket(Integer.parseInt(porta));
		String msg;


		while (true) {
			clientes.add(new ThreadC(servidor.accept(), clientes.size() + 1));
			msg = clientes.size() + " Entrou no chat" ;
			campoMensagens.append(msg + "\n");
			for(ThreadC cliente : clientes) {
				cliente.enviarMensagem(msg);
			}
			
		}

	}

	public void enviarMensagem(int remetente ,String mensagem) throws IOException {
		mensagem = remetente + ": " + mensagem;
		campoMensagens.append(mensagem + "\n");
		for(ThreadC cliente : clientes) {
			cliente.enviarMensagem(mensagem);
		}
	}

}


