package jframe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ServidorJFrame extends Thread {
	private static ArrayList<BufferedWriter>clientes;           
	private static ServerSocket server; 
	private String nome;
	private Socket socket;
	private InputStream input;  
	private InputStreamReader inputS;  
	private BufferedReader bfr;

	public ServidorJFrame(Socket socket){
		this.socket = socket;
		try {
			input  = socket.getInputStream();
			inputS = new InputStreamReader(input);
			bfr = new BufferedReader(inputS);
		} catch (IOException e) {
			e.printStackTrace();
		}                          
	}

	public void run(){

		try{

			String msg;
			OutputStream ou =  this.socket.getOutputStream();
			Writer ouw = new OutputStreamWriter(ou);
			BufferedWriter bfw = new BufferedWriter(ouw); 
			clientes.add(bfw);
			nome = msg = bfr.readLine();

			while(!"Sair".equalsIgnoreCase(msg) && msg != null)
			{           
				msg = bfr.readLine();
				sendToAll(bfw, msg);
				System.out.println(msg);                                              
			}

		}catch (Exception e) {
			e.printStackTrace();

		}                       
	}
	public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException 
	{
		BufferedWriter bwS;

		for(BufferedWriter bw : clientes){
			bwS = (BufferedWriter)bw;
			if(!(bwSaida == bwS)){
				bw.write(nome + " -> " + msg+"\r\n");
				bw.flush(); 
			}
		}          
	}
	public static void main(String []args) {

		try{
			JLabel lblMessage = new JLabel("Porta do Servidor:");
			JTextField txtPorta = new JTextField("12345");
			Object[] texts = {lblMessage, txtPorta };  
			JOptionPane.showMessageDialog(null, texts);
			server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
			clientes = new ArrayList<BufferedWriter>();
			JOptionPane.showMessageDialog(null,"Servidor ativo na porta: "+         
					txtPorta.getText());

			while(true){
				System.out.println("Conectando");
				Socket socket = server.accept();
				System.out.println("Conectado");
				Thread t = new ServidorJFrame(socket);
				t.start();   
			}

		}catch (Exception e) {

			e.printStackTrace();
		}                       
	}                   
}