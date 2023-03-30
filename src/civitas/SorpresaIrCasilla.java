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
public class SorpresaIrCasilla extends Sorpresa {
    private int valor;
    private Tablero tablero;
    private MazoSorpresas mazo;
    
     /**
     * Constructor con parámetros de la clase
     * @param tablero Tablero donde se encuentran
     * @param valor Número de casilla a donde se envía
     * @param texto Texto de la tarjeta
     */       
    SorpresaIrCasilla(Tablero tablero, int valor, String texto){ 
        super(texto);
        this.tablero = tablero;
        this.valor = valor;
    }
    
        /**
     * Método que aplica a un jugador la sospresa de ir a otra casilla
     * @param actual Jugador actual
     * @param todos Conjunto de jugadores
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        int casilla_actual = 0;
        int tirada = 0;
        int nueva_posicion = 0;
        
        if(jugadorCorrecto(actual, todos) == true){
            informe(actual,todos);
            //Diario.getInstance().ocurreEvento(texto);
            casilla_actual = todos.get(actual).getNumCasillaActual();
            tirada = tablero.calcularTirada(casilla_actual, valor);
            nueva_posicion = tablero.nuevaPosicion(casilla_actual, tirada);
            
            todos.get(actual).moverACasilla(nueva_posicion);
            
            tablero.getCasilla(nueva_posicion).recibeJugador(actual, todos);
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
