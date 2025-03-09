package br.com.juliana.calculadora.bin.modelo;

@FunctionalInterface
public interface MemoriaObservador {
   void valorAlterado(String var1);
}