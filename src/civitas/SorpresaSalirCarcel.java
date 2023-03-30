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
public class SorpresaSalirCarcel extends Sorpresa {
    private MazoSorpresas mazo;

   
    /**
     * Constructor con parámetros de la clase
     * @param mazo Mazo donde se encuentra la tarjeta
     */
    
    SorpresaSalirCarcel(MazoSorpresas mazo){
        super("Obtienes un salvoconducto para evitar la carcel");
        this.mazo = mazo;
    }
    

    /**
     * Método que aplica a un jugador la sospresa de salir a la cárcel
     * @param actual Jugador actual
     * @param todos Conjunto de jugadores
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        int cuenta_salvoconductos = 0;
        
        if(jugadorCorrecto(actual, todos) == true){
            informe(actual,todos);
            //Diario.getInstance().ocurreEvento(texto);
            
            for(int i = 0; i < todos.size(); i++){
                if(todos.get(i).tieneSalvoconducto()){
                    cuenta_salvoconductos++;
                }
            }
            
            if(cuenta_salvoconductos == 0){
                SorpresaSalirCarcel salvoconducto = new SorpresaSalirCarcel(mazo);
                todos.get(actual).obtenerSalvoconducto(salvoconducto);
                salvoconducto.salirDelMazo();
            }
            
        }
    }
    
    /**
     * Método que elimina la carta que exime de la cárcel del mazo
     */
    void salirDelMazo(){
        SorpresaSalirCarcel salvoconducto = new SorpresaSalirCarcel(mazo);
        mazo.inhabilitarCartaEspecial(salvoconducto);
    }
    
    /**
     * Método que indica que se ha usado la sospresa de salir de la carcel
     */
    void usada(){   
        SorpresaSalirCarcel salvoconducto = new SorpresaSalirCarcel(mazo);    
        mazo.habilitarCartaEspecial(salvoconducto);
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
