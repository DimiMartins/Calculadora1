package br.com.codificando.calc.modelo;
@FunctionalInterface
public interface MemoriaObservador {

	void valorAlterado(String novoValor);
}
