package br.com.codificando.calc.visao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Botao extends JButton{
	public Botao(String TextoDoBot�o, Color corDoBotao) {
		setText(TextoDoBot�o);
		setOpaque(true);
		setBackground(corDoBotao);
		setForeground(Color.white);// cor do numero do botao 
		setFont(new Font("arial",Font.PLAIN , 25));//nome da fonte tamanho tipo 
		setBorder(BorderFactory.createLineBorder(Color.black));//defini cor da borda
	}
}
