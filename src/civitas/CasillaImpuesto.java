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
public class CasillaImpuesto extends Casilla {
    static private int carcel;
    private float importe;

    /**
     * Constructor con parámetros de la clase (Impuesto)
     * @param cantidad Importe del impuesto a pagar
     * @param nombre Nombre de la casilla
     */
    
    CasillaImpuesto(float cantidad, String nombre){
        super(nombre);
        importe = cantidad;
    }
    

    /**
     * Este metodo hace que un jugador pague un impuesto tras caer en uno
     * @param iactual número de jugador actual
     * @param todos vector donde se encuentran los jugadores
     */
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos) == true){
            informe(iactual,todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
     
    /**
     * Método para expresar el estado del objeto como un String
     * @return Estado del objeto en String
     */
    
    @Override
    public String toString(){
        String info_casilla = super.toString();
        info_casilla = info_casilla + ", Tipo: Impuesto,"  + " Cantidad a pagar: " + importe;
 
        return info_casilla;
    }

}
