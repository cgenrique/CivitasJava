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
public abstract class Sorpresa {
    private String texto;


    Sorpresa(String texto){ 
        this.texto = texto;
    }
    
    
    /**
     * Método que inidca si un jugador es correcto
     * @param actual Jugador a evaluar
     * @param todos Conjunto de jugadores
     * @return true si el jugador es correcto
     */
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        boolean valido = false;
        
        if(actual >= 0 && actual < todos.size()){
            valido = true;
        } 
        
        return valido;
    }
    
    /**
     * Método que realiza un informe al diario sobre la aplicación de una sorpresa
     * @param actual Jugador actual
     * @param todos Conjunto de jugadores
     */
    protected void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se aplica una sorpresa a " + todos.get(actual).getNombre() + ". " + this.toString());
    }
    
    /**
     * Método que aplicar una sorpresa a un jugador
     * @param actual Jugador actual
     * @param todos Conjunto de jugadores
     */
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    

    /**
     * Método que indica el estado (nombre) de una sorpresa
     * @return Estado de una sorpresa
     */
    @Override
    public String toString(){
        return texto;
    }
}
