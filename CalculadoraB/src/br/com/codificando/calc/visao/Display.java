package br.com.codificando.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.codificando.calc.modelo.Memoria;
import br.com.codificando.calc.modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador{

	private final JLabel label; // texto que vai estar contido dentro do display
	public Display() {
		Memoria.getInstacia().adicionarObservador(this);// display esta interessado em ser notificado
		
		setBackground(new Color(49,49,49));
		label = new JLabel(Memoria.getInstacia().getTextoAtual()); // pegando o texto atual 
		label.setForeground(Color.white); //definindo cor da fonte
		label.setFont(new Font("arial",Font.PLAIN , 40));// definindo nome da fonte, tipo e tamanho
		setLayout(new FlowLayout(FlowLayout.RIGHT, 15,35));// definindo label a direita, distanciamento 
		add(label);
	}
	
	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);//sempre que ouver uma mundança na memoria essa mudança ira notificar os interessados (display)
		
	}
}
