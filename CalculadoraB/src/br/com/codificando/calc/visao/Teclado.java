package br.com.codificando.calc.visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.codificando.calc.modelo.Memoria;
//para o display cvomeçar a escutar os eventos do teclado display implements actionListener 
@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener {

	private final Color COR_PRETO = new Color(28, 28, 28);
	private final Color COR_LARANJA = new Color(255, 200, 11);

	public Teclado() {

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		c.fill = GridBagConstraints.BOTH;// preenche os espaçoes em branco entre os botoes
		c.weightx = 1;// todos os elementos tem o mesmo peso
		c.weighty = 1;// todos os elementos tem o mesmo peso
		// c.gridx = 0 //c.gridy = 0
		// add(new Botao("AC", COR_CINZA_CLARO), c);
		// c.gridx = 1 //c.gridy = 1

		// linha 1
		c.gridwidth = 3;
		adicionarBotao("CE", COR_LARANJA, c, 0, 0);
		c.gridwidth = 1;
		//adicionarBotao("MR", COR_LARANJA, c, 1, 0);
		//adicionarBotao("M--", COR_LARANJA, c, 2, 0);
		adicionarBotao("%", COR_LARANJA, c, 3, 0);
		c.gridwidth = 2;
		adicionarBotao("⌫", COR_LARANJA, c, 4, 0);
		c.gridwidth = 1;

		// linha 2
		adicionarBotao("7", COR_PRETO, c, 0, 1);
		adicionarBotao("8", COR_PRETO, c, 1, 1);
		adicionarBotao("9", COR_PRETO, c, 2, 1);
		adicionarBotao("x²", COR_LARANJA, c, 3, 1);
		adicionarBotao("²√", COR_LARANJA, c, 4, 1);
		adicionarBotao("³√", COR_LARANJA, c, 5, 1);
		// linha 3
		adicionarBotao("4", COR_PRETO, c, 0, 2);
		adicionarBotao("5", COR_PRETO, c, 1, 2);
		adicionarBotao("6", COR_PRETO, c, 2, 2);
		adicionarBotao("x³", COR_LARANJA, c, 3, 2);
		adicionarBotao("x", COR_LARANJA, c, 4, 2);
		adicionarBotao("÷", COR_LARANJA, c, 5, 2);
		// linha 4
		adicionarBotao("1", COR_PRETO, c, 0, 3);
		adicionarBotao("2", COR_PRETO, c, 1, 3);
		adicionarBotao("3", COR_PRETO, c, 2, 3);
		adicionarBotao("+/-", COR_LARANJA, c, 3, 3);
		adicionarBotao("+", COR_LARANJA, c, 4, 3);
		adicionarBotao("-", COR_LARANJA, c, 5, 3);
		// linha 5
		adicionarBotao("0", COR_PRETO, c, 0, 4);
		c.gridwidth = 2;
		adicionarBotao("00", COR_PRETO, c, 1, 4);
		c.gridwidth = 1;
		adicionarBotao(",", COR_LARANJA, c, 3, 4);
		c.gridwidth = 2;
		adicionarBotao("=", COR_LARANJA, c, 4, 4);
		c.gridwidth = 1;
	}

	// metodo responsavel por adicionar um botao dentro do teclado
	private void adicionarBotao(String texto, Color cor, GridBagConstraints c, int x, int y) {
		Botao botao = new Botao(texto, cor);
		//sempre vai mostrar o valor que foi digitado
		botao.addActionListener(this);//para cada novo botao que eu for criar
		c.gridx = x;
		c.gridy = y;
		add(botao, c);
	}
	
	@Override// a ação foi performada
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton botao = (JButton) e.getSource();
			Memoria.getInstacia().processarComando(botao.getText());// sempre que um botao for precionado eu vou pegar o label (texto) do botao 
		}
	}
}
