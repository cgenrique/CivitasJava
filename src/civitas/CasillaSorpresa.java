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
public class CasillaSorpresa extends Casilla{
    private MazoSorpresas mazo;
    

    /**
     * Constructor con parámetros de la clase (Sorpresa)
     * @param mazo Mazo donde se encuentra la sorpresa de la casilla
     * @param nombre Nombre de la casilla
     */
    
    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        this.mazo = mazo;
    }

    
   
    /**
     * Este metodo indica que un jugador ha caido en una casilla de sorpresa
     * @param iactual número de jugador actual
     * @param todos vector donde se encuentran los jugadores
     */
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            Sorpresa sorpresa = mazo.siguiente();
            informe(iactual, todos);
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    
    
    /**
     * Método para expresar el estado del objeto como un String
     * @return Estado del objeto en String
     */
    
    @Override
    public String toString(){
        String info_casilla = super.toString() ;    
        info_casilla = info_casilla + ", Tipo: Sorpresa";

        return info_casilla;
    }
   

}
