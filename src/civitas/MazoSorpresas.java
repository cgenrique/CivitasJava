package civitas;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author enriq
 */
public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    /**
     * Método que inicializa los atributos de la clase
     */
    private void init(){
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }
    
    /**
     * Constructor con parámetros de la clase
     * @param b debu del dado
     * @return Nuevo objeto de la clase
     */
    MazoSorpresas(boolean b){
        debug = b;
        init();
        
        if(debug == true){
            Diario.getInstance().ocurreEvento("El dado se pone en modo debug");
        }
    }
    
    /**
     * Constructor sin parámetros de la clase
     * @return Nuevo objeto de la clase
     */
    MazoSorpresas(){
        init();
        debug = false;
    }
    
    /**
     * Método que añade una sorpresa al mazo
     * @param s Sorpresa a añadir
     */
    void alMazo (Sorpresa s){
        if(barajada == false){
           sorpresas.add(s);
        }
    }
    
    /**
     * Método que baraja y devuelve la última sorpresa del mazo
     * @return Última sorpresa
     */
    Sorpresa siguiente(){
        if(barajada == false || usadas == sorpresas.size()){
           if(debug == false){
            Collections.shuffle(sorpresas); 
           }
           
           usadas = 0;
           barajada = true;
        }
        
        usadas++;
        
        Sorpresa a_mover = sorpresas.get(0);
        sorpresas.add(sorpresas.size() - 1, a_mover);
        sorpresas.remove(0);
        
        ultimaSorpresa = a_mover;
        
        return ultimaSorpresa;
    }
    
    /**
     * Método que inhabilita una carta especial
     * @param sospresa Carta a inhabilitar
     */
    void inhabilitarCartaEspecial (Sorpresa sorpresa){
        Sorpresa a_mover;
        
        for(int i = 0; i < sorpresas.size(); i++){
            if(sorpresas.get(i) == sorpresa){
                a_mover = sorpresas.get(i);
                sorpresas.remove(i);
                 
                cartasEspeciales.add(a_mover); 
                
                Diario.getInstance().ocurreEvento("Se ha movido una carta de sorpresa a especiales");

            }
        }
    }
    
    /**
     * Método que habilita una carta especial
     * @param sospresa Carta a habilitar
     */
    void habilitarCartaEspecial (Sorpresa sorpresa){
        Sorpresa a_mover;
        for(int i = 0; i < cartasEspeciales.size(); i++){
            if(cartasEspeciales.get(i) == sorpresa){
                a_mover = cartasEspeciales.get(i);
                cartasEspeciales.remove(i);
                 
                sorpresas.add(a_mover); 
                
                Diario.getInstance().ocurreEvento("Se ha movido una carta de especiales a sorpresas");

            }
        }
    }
}
