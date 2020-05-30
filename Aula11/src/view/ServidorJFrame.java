package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import handlers.ServidorSocket;

@SuppressWarnings("serial")
public class ServidorJFrame extends JFrame implements ActionListener {
	JLabel tituloJLabel = new JLabel("Servidor");
    JLabel portaJLabel = new JLabel("Porta");
    JTextField portaJText = new JTextField("123456");
    JButton iniciarJButton = new JButton("Iniciando");
    JLabel mensagensJLabel = new JLabel("Mensagens");
    JLabel iniciadoJLabel = new JLabel("");
    JTextArea mensagensJTextArea = new JTextArea();
    JTextField mensagemJText = new JTextField();
    JButton enviarJButton = new JButton("Enviar");

    ServidorSocket socketServidor = ServidorSocket.getInstance();
    
    public ServidorJFrame() {
    	tituloJLabel.setBounds(170, 0, 60, 20);
        portaJLabel.setBounds(10, 20, 60, 20);
        portaJText.setBounds(60, 20, 60, 20);
        iniciarJButton.setBounds(130, 20, 200, 20);
        mensagensJLabel.setBounds(10, 30, 200, 80);
        iniciadoJLabel.setBounds(150, 15, 150, 80);
        iniciadoJLabel.setForeground(Color.black);
        mensagensJTextArea.setBounds(10, 90, 350, 150);
        mensagemJText.setBounds(10, 250, 250, 50);
        enviarJButton.setBounds(260, 250, 100, 50);
                
        Container caixa = getContentPane();
        caixa.setLayout(null);
        caixa.add(tituloJLabel);
        caixa.add(portaJLabel);
        caixa.add(portaJText);
        caixa.add(iniciarJButton);
        caixa.add(mensagensJLabel);
        caixa.add(iniciadoJLabel);
        caixa.add(mensagensJTextArea);
        caixa.add(mensagemJText);
        caixa.add(enviarJButton);

        iniciarJButton.addActionListener(this);
        enviarJButton.addActionListener(this);
        mensagensJTextArea.setEditable(false);
        
        setVisible(true);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(iniciarJButton)) {
            new Thread() {
                @Override
                public void run() {
                    iniciarJButton.setEnabled(false);
                    portaJText.setEditable(false);
                    iniciadoJLabel.setText("Servidor iniciado!");
                    try {
                        socketServidor.iniciar(portaJText.getText(), mensagensJTextArea);
                    } catch (IOException ex) {
                        iniciarJButton.setEnabled(true);
                        portaJText.setEditable(true);
                        iniciadoJLabel.setText("");
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
        if (e.getSource().equals(enviarJButton)) {
            try {
                socketServidor.enviarMensagem(0, mensagemJText.getText());
                mensagemJText.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
     public static void main(String[] args) {
    	    new ServidorJFrame();
    }   
}
