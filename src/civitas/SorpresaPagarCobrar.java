/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author enriq
 */
public class SorpresaPagarCobrar extends Sorpresa {
    private int valor;
    //private Tablero tablero;
    //private MazoSorpresas mazo;

    /**
     * Constructor con parámetros de la clase 
     * @param valor Número de casilla a donde se envía
     * @param texto Texto de la tarjeta
     */
    
    SorpresaPagarCobrar(int valor, String texto){ 
        super(texto);
        this.valor = valor;
    }
    
    
    /**
     * Método que aplica a un jugador la sospresa de cobrar o pagar una cantidad
     * @param actual Jugador actual
     * @param todos Conjunto de jugadores
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos) == true){
            informe(actual, todos);
            //Diario.getInstance().ocurreEvento(texto);
            todos.get(actual).modificarSaldo(valor);
        }
    }
    

    /**
     * Método que indica el estado (nombre) de una sorpresa
     * @return Estado de una sorpresa
     */
    @Override
    public String toString(){
        return super.toString();
    }
}
