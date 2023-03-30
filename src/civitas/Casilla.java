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
public class Casilla {
    private String nombre;

    
    /**
     * Constructor con parámetros de la clase (Descanso)
     * @param nombre Nombre de la casilla
     */
    Casilla(String nombre){
        this.nombre = nombre;
    }


    /**
     * Este metodo devuelve el nombre de la casilla
     * @return el nombre de la casilla
     */
    public String Getnombre (){
        return nombre;
    }
    

    /**
     * Este metodo informa al diario de que un Jugador ha caído en una casilla
     * @param actual número del jugador actual
     * @param todos  vector donde se encuentran los jugadores
     */
    
    protected void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(actual).getNombre() +
                " ha caído en la casilla " + nombre);
    }
    
    /**
     * Este metodo indica si un Jugador es correcto
     * @param actual número del jugador actual
     * @param todos  vector donde se encuentran los jugadores
     * @return true si el indice del jugador es válido
     */
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        boolean correcto = false;
        
        if(actual < todos.size()){
            correcto = true;
        }
        
        return correcto;
    }


     /**
     * Método que indica la casilla donde ha caido un jugador y su efecto
     * @param iactual número de jugador actual
     * @param todos vector donde se encuentran los jugadores
     */
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        informe(iactual, todos);
    }
    
    /**
     * Método para expresar el estado del objeto como un String
     * @return Estado del objeto en String
     */
    
    @Override
    public String toString(){
        String info_casilla = "\nNombre: " + nombre;
 
        return info_casilla;
    }
}
