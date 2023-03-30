package civitas;

import java.util.ArrayList;

/**
 *
 * @author enriq
 */
public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;

    /**
     * Constructor con parámetros de la clase
     * @param indice indice donde se encuentra la casilla de la cárcel
     * @return Nuevo objeto de la clase
     */
    Tablero (int indice){
        if(indice >= 1)
            numCasillaCarcel = indice;
        else
            numCasillaCarcel = 1;
        
        casillas = new ArrayList<>();
        Casilla salida = new Casilla("Salida");
        casillas.add(salida);
        
        porSalida = 0;
        tieneJuez = false;
    }
    
    /**
     * Método que indica si un tablero es correcto
     * @return true si el tablero es correcto
     */
    private boolean correcto(){
        boolean correcto = false;
        
        if( casillas.size() > numCasillaCarcel && tieneJuez ==  true)
            correcto = true;
        
        return correcto;
    }
    
    /**
     * Método que indica si una casilla es correcta
     * @return true si es correcta
     */
    private boolean correcto (int numCasilla){
        boolean indice_correcto = false;
        if (correcto() == true){
            if(numCasilla < casillas.size() && numCasilla > -1)
                indice_correcto = true;
        }
        
        return indice_correcto;
    }
    
    /**
     * Método consultor del número de casilla donde está la carcel
     * @return indice de la casilla de cárcel
     */
    int getCarcel(){
        return numCasillaCarcel;
    }
    
      
    /**
     * Método consultor del importe que se gana al pasar por la casilla de salida
     * @return importe por pasar por la salida
     */
    int getPorSalida(){
        int valor  = porSalida;
        if(porSalida > 0)
            porSalida -= 1;
        
        return valor;
    }

      
    /**
     * Método que añade una casilla al tablero
     * @param casilla Casilla a añadir
     */
    void añadeCasilla(Casilla casilla){
        Casilla carcel = new Casilla("CÁRCEL");
        if(casillas.size() == numCasillaCarcel)
           casillas.add(carcel);
        
        casillas.add(casilla);
        
        if(casillas.size() == numCasillaCarcel)
            casillas.add(carcel);
    }
    
      
    /**
     * Método que añade una casilla de tipo juez al tablero
     */
    void añadeJuez(){
        if(tieneJuez == false){
            CasillaJuez juez = new CasillaJuez(numCasillaCarcel,"JUEZ");
            casillas.add(juez);
            tieneJuez = true;
        }
    }
    
    /**
     * Método que devuelve una casilla al tablero
     * @param numCasilla Casilla devolver
     */
    Casilla getCasilla(int numCasilla){
        Casilla casilla;
        
        if(correcto(numCasilla)){
            casilla = casillas.get(numCasilla);
        }
        else
            casilla = null;
        
        return casilla;
    }
    
    /**
     * Método que calcula la nueva posición en el tablero tras una tirada del dado
     * @param actual Posicion actual
     * @param tirada Tirada del dado
     * @return Nueva posición en el tablero
     */
    int nuevaPosicion (int actual, int tirada){
        int nueva_posicion = -1;
        
        if(correcto()){
            nueva_posicion = actual + tirada;
            if(nueva_posicion >= casillas.size()){
                nueva_posicion = nueva_posicion % casillas.size();
                porSalida++;
            }
        }
        
        return nueva_posicion;
    }
    
    /**
     * Método que calcula una tirada con el dado para llegar a una casilla
     * @param origen Casilla de la que parto
     * @param destino Casilla a la que quiero llegar
     * @return Número de casillas necesarias
     */
    int calcularTirada(int origen, int destino){
        int avance = destino - origen;
        
        if(avance < 0)
            avance = avance + casillas.size();
        
        return avance;
    }
}

