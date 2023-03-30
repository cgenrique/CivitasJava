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
public class SorpresaConvertirJugador extends Sorpresa{
    private int fianza;
    
    SorpresaConvertirJugador(int valor){
        super("Te conviertes en un jugador especulador");
        fianza = valor;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            JugadorEspeculador especulador = new JugadorEspeculador(todos.get(actual), fianza);
            todos.set(actual, especulador);
        }
    }
}
