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
public class CasillaCalle extends Casilla{
    private float importe;
    private TituloPropiedad tituloPropiedad;
    

    /**
     * Constructor con parámetros de la clase (Calle)
     * @param titulo Tirulo de propiedad correspondiente a la casilla
     */
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        tituloPropiedad = titulo;
        importe = titulo.getPrecioCompra();
    }
    
        
    /**
     * Este metodo devuelve el titulo de propiedad de la casilla
     * @return el titulo de propiedad
     */
    
    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }
    
    
    /**
     * Este metodo indica que un jugador ha caido en una casilla calle
     * @param iactual número de jugador actual
     * @param todos vector donde se encuentran los jugadores
     */
    
    
  
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            Jugador jugador = todos.get(iactual);
            
            if(!tituloPropiedad.tienePropietario()){
                jugador.puedeComprarCasilla();
            }
            else{
                tituloPropiedad.tramitarAlquiler(jugador);
            }
        }
    }
    
    @Override
    public String toString(){
        String info_casilla = "\nTipo: Calle," + tituloPropiedad.toString();

        return info_casilla;
    }
   
    
}
