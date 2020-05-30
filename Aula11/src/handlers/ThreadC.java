package handlers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ThreadC extends Thread {

	private Socket cliente;
	private int index;

	public ThreadC(Socket cliente, int index) {
		this.cliente = cliente;	
		this.index = index;
		start();

	}

	@SuppressWarnings("resource")
	@Override
	public void run(){
		Scanner scanner;
		String mensagem;
		ServidorSocket ss = ServidorSocket.getInstance();
		try {
			scanner = new Scanner(cliente.getInputStream());
		} catch (IOException e) {
			return;
		}

		while (true) {

			if(scanner.hasNextLine()) {
				mensagem = scanner.nextLine();
				try {					
					ss.enviarMensagem(index, mensagem);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

		}
	}

	public void enviarMensagem(String mensagem) {
			try {
				PrintStream saida = new PrintStream(cliente.getOutputStream());
				saida.println(mensagem);
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}

}
