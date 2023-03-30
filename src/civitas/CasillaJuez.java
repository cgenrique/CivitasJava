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
public class CasillaJuez extends Casilla {
    static private int carcel;


    /**
     * Constructor con parámetros de la clase (Juez)
     * @param numCasillaCarcel Número de la casilla de la cárcel
     * @param nombre Nombre de la casilla
     */
    
    CasillaJuez(int numCasillaCarcel, String nombre){
        super(nombre);
        carcel = numCasillaCarcel;
    }
    

    /**
     * Este metodo hace que un jugador vaya  a la carcel tras caer en Juez
     * @param iactual número de jugador actual
     * @param todos vector donde se encuentran los jugadores
     */
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos) == true){
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
    

    /**
     * Método para expresar el estado del objeto como un String
     * @return Estado del objeto en String
     */
    
    @Override
    public String toString(){
        String info_casilla = super.toString();
        info_casilla = info_casilla + ", Tipo: Juez,";
        
        return info_casilla;
    }
}
