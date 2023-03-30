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
public class SorpresaIrCarcel extends Sorpresa {

    private Tablero tablero;
    
    /**
     * Constructor con parámetros de la clase
     * @param tablero Tablero donde se encuentran
     */
    SorpresaIrCarcel(Tablero tablero){
        super("Ve a la cárcel");
        this.tablero = tablero;
    }
    

    /**
     * Método que aplica a un jugador la sospresa de ir a la cárcel
     * @param actual Jugador actual
     * @param todos Conjunto de jugadores
     */
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual,todos);
           //Diario.getInstance().ocurreEvento("Ve a la cárcel");
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
