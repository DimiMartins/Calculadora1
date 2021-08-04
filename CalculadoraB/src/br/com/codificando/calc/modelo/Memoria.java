package br.com.codificando.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	private enum TipoComando {
		NUMERO,ZERAR,PORCENTAGEM, APAGAR, QUADRADRO, CUBO, RAIZQ, RAIZC, DIV, MULT, SOMA, SUB, TROCAR_SINAL, VIRGULA, IGUAL
	}
	private static final Memoria instacia = new Memoria();
	private final List<MemoriaObservador>observadores = new ArrayList<>();
	private TipoComando ultimaOperacao = null;// vai armazenar a ultima operacao clicada na calculadora
	private boolean substituir = false;
	private String textoAtual = ""; // texto Atual da calculadora
	private String textoBuffer = ""; // vai armazenar o texto atual
	
	private Memoria() {
		
	}
	
	//get texto atual
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0": textoAtual;//sempre que estiver vazio no display vai mostrar 0
	}

	public static Memoria getInstacia() {
		return instacia;
	}
	
	//adicionar os observadores, quem esta interessado, sempre que tiver uma mudança no texto eu vou querer ser notificado 
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	//resonsavel por processar um novo texto ou caractere
	public void processarComando(String texto) {
		//quando for passado um novo comando para processar ele acrescenta e notifica
		//para saber o tipo de comando que foi passado
		TipoComando tipoComando = detectarTipoComando(texto);
		//apagar amanha colocar comandos aq
		if (tipoComando == null) {
			return;//caso tenha clicado em zerar
		}else if (tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if (tipoComando == TipoComando.TROCAR_SINAL) {
			textoAtual = textoAtual.contains("-") ? textoAtual.substring(1) : "-" + textoAtual;
		}else if (tipoComando == TipoComando.NUMERO ||tipoComando == TipoComando.VIRGULA) {
			// se substituir for verdadeiro, textoatual vai receber o valor de texto
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
		}else {//se não for zerar, não for um numero e nem virgula vamos ter uma operação
			substituir = true;// usado para validar e substituir no display quando clicado em alguma operação
			textoAtual = obterResultadoOperacao();
			//quando clicar em uma operação eu preciso armazenar
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}
	private String obterResultadoOperacao() {
		if (ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", ".")); //veio a partir do texto buffer
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		
		double resultado = 0;
		// se a ultima operação for ...
		if (ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		}else if (ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		}else if (ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		}else if (ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		}else if (ultimaOperacao == TipoComando.PORCENTAGEM) {
			resultado = numeroBuffer / 100;
		}else if (ultimaOperacao == TipoComando.QUADRADRO) {
			resultado = Math.pow(numeroBuffer, 2);
		}else if (ultimaOperacao == TipoComando.CUBO) {
			resultado = Math.pow(numeroBuffer, 3);
		}else if (ultimaOperacao == TipoComando.RAIZQ) {
			resultado = Math.sqrt(numeroBuffer);
		}else if (ultimaOperacao == TipoComando.RAIZC) {
			resultado = Math.cbrt(numeroBuffer);
		}
		// precido retornar um valor do tipo String
		String texto = Double.toString(resultado).replace(".", ","); //resultadoString == texto
		// para não retornar um double caso não for necessário
		boolean inteiro = texto.endsWith(",0");// se terminar com ,0 significa que é inteiro
		return inteiro ? texto.replace(",0", ""): texto;
	}

	//recebe os eventos e cadegoriza os eventos em numero, virgula, etc ...
	//categorizar cada um dos eventos que pode acontecer na calculadora

	//ira identificar e retornar o tipo de comando // o texto passado é o label do botao
	private TipoComando detectarTipoComando(String texto) {
		//teste para previnir que haja 0 a esquerda
		if (textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		try {
			//vereficar se o texto digitado pode ser convertido para um numero inteiro
			Integer.parseInt(texto); // se converter na sequencia vou retornar um numero
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			//O number formatExceptio significa que não é um numero
			//quando não for numero
			
			if ("CE".equals(texto)) {
				return TipoComando.ZERAR;
			}else if ("÷".equals(texto)) {
				return TipoComando.DIV;
			}else if ("x".equals(texto)) {
				return TipoComando.MULT;
			}else if ("+".equals(texto)) {
				return TipoComando.SOMA;
			}else if ("-".equals(texto)) {
				return TipoComando.SUB;
			}else if ("%".equals(texto)) {
				return TipoComando.PORCENTAGEM;
			}else if ("⌫".equals(texto)) {
				return TipoComando.APAGAR;
			}else if ("x²".equals(texto)) {
				return TipoComando.QUADRADRO;
			}else if ("x³".equals(texto)) {
				return TipoComando.CUBO;
			}else if ("²√".equals(texto)) {
				return TipoComando.RAIZQ;
			}else if ("³√".equals(texto)) {
				return TipoComando.RAIZC;
			}else if ("+/-".equals(texto)) {
				return TipoComando.TROCAR_SINAL;
			}else if ("=".equals(texto)) {
				return TipoComando.IGUAL;
			}else if (",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
		}
		return null;
	}
}
