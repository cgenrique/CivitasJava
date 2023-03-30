/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import GUI.Dado;
import static java.lang.Float.compare;
import java.util.ArrayList;

/**
 *
 * @author enriq
 */
public class Jugador implements Comparable<Jugador> {
    static public final int CasaMax = 4;
    static public final int CasasPorHotel = 4;
    protected boolean encarcelado;
    static protected final int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    static protected final float PasoPorSalida = 1000f;
    static protected final float PrecioLibertad = 200f;
    private boolean puedeComprar;
    private float saldo;
    static private final float SaldoInicial = 7500f;
    private ArrayList<TituloPropiedad> propiedades;
    private SorpresaSalirCarcel salvoconducto;
    
    /**
     * Constructor con parámetros de la clase
     * @param nombre Nombre del jugador
     * @return Nuevo objeto de la clase
     */
    Jugador (String nombre){
        this.nombre = nombre;
        encarcelado = false;
        numCasillaActual = 0;
        puedeComprar = true;
        saldo = SaldoInicial;
        propiedades = new ArrayList<>();
        salvoconducto = null;
    }
    
    /**
     * Constructor de copia de la clase
     * @param otro jugador a copiar
     * @return Nuevo objeto de la clase
     */
    protected Jugador (Jugador otro){
        this.propiedades = new ArrayList<>(otro.propiedades);
        this.salvoconducto = otro.salvoconducto;
        this.nombre = otro.nombre;
        this.encarcelado = otro.encarcelado;
        this.numCasillaActual = otro.numCasillaActual;
        this.puedeComprar = otro.puedeComprar;
        this.saldo = otro.saldo;
        
    }
    
    /**
     * Metodo que indica si un jugador tiene un salvoconducto que lo exime de la cárcel
     * @return true si el jugador se puede librar de la carcel
     */
    boolean tieneSalvoconducto(){
       boolean tiene = false;
       
       if(salvoconducto != null){
           tiene = true;
       }
       
       return tiene;
    }
    
    /**
     * Metodo que  le proporciona al jugador un salvoconducto que lo exime de la cárcel
     * @return si la operación se realiza correctamente
     */
    boolean obtenerSalvoconducto(SorpresaSalirCarcel s){
        boolean obtieneSalvoconducto = false;
        
        if(!encarcelado){
            this.salvoconducto = s;
            obtieneSalvoconducto = true;
        }
        
        return obtieneSalvoconducto;
    }
        
    /**
     * Metodo que que le quita al jugador su salvoconducto, lo usa
     */
    protected void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto = null;
    }
    
        
    /**
     * Metodo que determina si el jugador debe ser encarcelado
     * @return true si el jugador debe ir a la carcel
     */
    protected boolean debeSerEncarcelado(){
        boolean aLaCarcel = false;
        
        if(!encarcelado){
            if(tieneSalvoconducto() == true){
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento(nombre + " se libra de la cárcel");
            }
            else{
                aLaCarcel = true;
                Diario.getInstance().ocurreEvento(nombre + " debe ir a la cárcel");
                encarcelado = true;
            }
        }
        
        return aLaCarcel;
    } 
    
        
    /**
     * Metodo que mueve el jugador a una determinada casilla
     * @param numCasilla Casilla donde se ha de mover el jugador
     * @return true si la operación se realiza correctamente
     */
    boolean moverACasilla (int numCasilla){
        boolean seMueve = false;
        
        if(!encarcelado){
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento(nombre + " se mueve a la casilla " + numCasillaActual);
            seMueve = true;
        }
        return seMueve;
    }
    
    /**
     * Metodo que encarcela al jugador
     * @param numCasillaCarcek Casilla donde está la carcel
     * @return true si la operación se realiza correctamente
     */
    boolean encarcelar (int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento(nombre + " entra en la cárcel");
        }
        
        return encarcelado;
    }
    
    /**
     * Metodo que indica si un jugador puede comprar una casilla
     * @return true si se puede comprar la casilla
     */
    boolean puedeComprarCasilla(){
        puedeComprar = !encarcelado;
        
        return puedeComprar;
    }
    
    /**
     * Metodo que modifica el sueldo del jugador
     * @param cantidad Cantidad que se añade o sustrae al sueldo del jugador
     * @return true
     */
    boolean modificarSaldo (float cantidad){
        saldo = saldo + cantidad;
        Diario.getInstance().ocurreEvento("Se modifica el saldo de " + nombre);
        
        return true;
    }
    
    /**
     * Metodo que hace que el jugador pague una determinada cantidad
     * @param cantidad Cantidad que el jugador debe pagar
     * @return true si la operación se realiza con éxito
     */
    boolean paga (float cantidad){
        boolean pagado;
        pagado = modificarSaldo(cantidad * -1);
        Diario.getInstance().ocurreEvento(nombre + " paga " + cantidad);
        
        return pagado;
    }
    
    /**
     * Metodo que hace que el jugador pague un impuesto
     * @param cantidad Cantidad que el jugador debe pagar
     * @return true si la operación se realiza con éxito
     */
    boolean pagaImpuesto (float cantidad){
        boolean pagado = false;
        
        if(!encarcelado){
            Diario.getInstance().ocurreEvento(nombre + " paga un impuesto");
            pagado = paga(cantidad);
            //puedeComprar = false;
        }
        
        return pagado;
    }
    
    /**
     * Metodo que hace que el jugador pague el alquiler de una propiedad
     * @param cantidad Cantidad que el jugador debe pagar
     * @return true si la operación se realiza con éxito
     */
    boolean pagaAlquiler (float cantidad){
        boolean pagado = false;
        
        if(!encarcelado){
            pagado = paga(cantidad);
            //puedeComprar = false;
        }
        
        return pagado;
    }
    
    /**
     * Metodo que hace que el jugador reciba una determinada cantidad
     * @param cantidad Cantidad que el jugador debe reibir
     * @return true si la operación se realiza con éxito
     */ 
    
    boolean recibe (float cantidad){
        boolean recibido = false;
        
        if(!encarcelado){
           recibido = modificarSaldo(cantidad);
           Diario.getInstance().ocurreEvento(nombre + " recibe " + cantidad);
        }
        
        return recibido;
    }
    
    /**
     * Metodo que indica si el jugador puede gastarse una determinada cantidad
     * @param precio Cantidad que el jugador pretende gastarse
     * @return true si el jugador puede permitirse el gasto
     */
    private boolean puedoGastar(float precio){
        boolean puedo = false;
        
        if(!encarcelado){
           if(saldo >= precio){
               puedo = true;
           }
        }
        
        return puedo;
    }
    
    /**
     * Metodo consultor del nombre del jugador
     * @return nombre del jugador
     */
    public String getNombre(){
        return(nombre);
    }
    
    /**
     * Metodo que consultor del numero de edificaciones que posee el jugador
     * @return número de casas y hoteles que posee el jugador
     */
    
    int cantidadCasasHoteles(){
        int num_edificaciones = 0;
        
        for(int i = 0; i < propiedades.size(); i++){
            num_edificaciones += propiedades.get(i).getNumHoteles();
            num_edificaciones += propiedades.get(i).getNumCasas();
        }
        
        return num_edificaciones;
    }
    
    /**
     * Metodo  consultor del de la casilla donde se encuentra el jugador
     * @return casilla actual del jugador
     */
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    /**
     * Metodo que indica si existe una determinada propiedad entre las que posee el jugador
     * @param ip identidicador de la propiedad
     * @return si el jugador posee la propiedad
     */
    private boolean existeLaPropiedad(int ip){
        boolean existe = false;
        
        if(ip >= 0 && ip < propiedades.size()){
            existe = true;
        }
        
        return existe;
    }
    
    /**
     * Metodo que hace que el jugador venda una de sus propiedades
     * @param ip identidicador de la propiedad
     * @return si el jugador vende la propiedad correctamente
     */
    boolean vender (int ip){
        boolean vendo = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                vendo = propiedad.vender(this);
                Diario.getInstance().ocurreEvento(nombre + " ha vendido la propiedad " +
                        propiedades.get(ip).getNombre());
                propiedades.remove(ip);
            }
        }
        return vendo;
    }
    
    /**
     * Metodo que indica si el jugador posee alguna propiedad que gestionar
     * @return si el jugador posee alguna propiedad
     */
    boolean tieneAlgoQueGestionar(){
        boolean tiene = false;
        
        if(propiedades.size() > 0){
            tiene = true;
        }
        
        return tiene;
    }
    
    /**
     * Metodo que indica si el jugador puede salir de la cárcel pagando una fianza
     * @return si el jugador puede salir de la cárcel pagando
     */
    private boolean puedeSalirCarcelPagando(){
        boolean puede_salir = false;
        
        if(saldo > PrecioLibertad){
            puede_salir = true;
        }
        
        return puede_salir;
    }
    
    /**
     * Metodo que indica si el jugador puede cosntruir una casa en una propiedad
     * @return si el jugador puede constuir una casa
     */
     private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puedoEdificarCasa = false;
        float precio = propiedad.getPrecioEficicar();
        
        if(puedoGastar(precio)){
            if(propiedad.getNumCasas() < getCasasMax()){
                puedoEdificarCasa = true;
            }
        }
        
        return puedoEdificarCasa;
    }
    
    /**
     * Metodo que indica si el jugador puede cosntruir un hotel en una propiedad
     * @return si el jugador puede constuir un hotel
     */
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean puedoEdificarHotel = false;
        float precio = propiedad.getPrecioEficicar();
        
        if(puedoGastar(precio)){
            if(propiedad.getNumHoteles() < getHotelesMax()){
                if(propiedad.getNumCasas() >= getCasasPorHotel()){
                    puedoEdificarHotel = true;
                }
            }
        }
        return puedoEdificarHotel;
    }
    
    /**
     * Metodo que hace que el jugador salga de la cárcel pagando una fianza
     * @return si el jugador ha salido de la cárcel
     */
    boolean salirCarcelPagando(){
        boolean ha_salido = false;
        
       if(encarcelado && puedeSalirCarcelPagando()){
           paga(PrecioLibertad);
           encarcelado = false;
           Diario.getInstance().ocurreEvento(nombre + " ha pagado para salir de la cárcel");
           ha_salido = true;
       } 
       
       return ha_salido;
    }
    
    /**
     * Metodo que hace que el jugador intente salir de la cárcel tirando el dado
     * @return si el jugador ha salido de la cárcel
     */
    boolean salirCarcelTirando(){
        boolean ha_salido = false;
        
        if(Dado.getInstance().salgoDeLaCarcel()){
            encarcelado = false;
            Diario.getInstance().ocurreEvento(nombre + " ha tirado y ha salido de la cárcel");
            ha_salido = true;
        }
        else{
            Diario.getInstance().ocurreEvento(nombre + " ha tirado y no ha conseguido salir de la cárcel");
        }
        
        return ha_salido;
    }
    
    /**
     * Metodo que indica si el jugador pasa por la casilla de salida
     * @return si el jugador pasa por la casilla de salida
     */
    boolean pasaPorSalida(){
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " + nombre +" ha pasado por la salida");
        
        return true;
    }
    
    /**
     * Metodo que compara el sueldo de dos jugadores
     * @return 1 si el primero tiene más dinero, 0 si tienen el mismo y -1 si el segundo tiene más
     */
    @Override
    public int compareTo(Jugador otro){
        return compare(saldo, otro.getSaldo());
    }
    
    /**
     * Metodo consultor del número máximo de casas que se pueden edificar en la propiedad
     * @return número máximo de casas
     */
    public int getCasasMax(){
        return CasaMax;
    }
    
    /**
     * Metodo consultor del número  de casas que se deben derruir para construir un hotel
     * @return número de casas a las que equivale un hotel
     */
    public int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    /**
     * Metodo consultor del número máximo de hoteles que se pueden edificar en la propiedad
     * @return número máximo de hoteles
     */
    int getHotelesMax(){
        return HotelesMax;
    }
    
    /**
     * Metodo consultor del precio que debe pagar un jugador pra salir de la cárcel
     * @return fianza para salir de la cárcel
     */
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    /**
     * Metodo consultor de la cantidad que se cobra al pasar por la casilla de salida
     * @return cantidad que se obtiene al pasar por salida
     */
    private float getPremioPasoSalida(){
        return PasoPorSalida;
    }
    
    /**
     * Metodo consultor del vector de propiedades del jugador
     * @return propiedades del jugador
     */
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    /**
     * Metodo que indica si un jugador puede comprar una propiedad
     * @return true si puede comprar
     */
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    /**
     * Metodo consultor del saldo del jugador
     * @return saldo del jugador
     */
    public float getSaldo(){
        return saldo;
    }
    
    /**
     * Metodo consultor, indica si el jugador está en bancarrota
     * @return true si el jugador está arruinado
     */
    boolean enBancarrota(){
        boolean arruinado = false;
        
        if(saldo < 0){
            arruinado = true;
        }
        
        return arruinado;
    }
    
    /**
     * Metodo consultor, indica si el jugador está en la cárcel
     * @return true si está en la cárcel
     */
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    /**
     * Metodo que devuelve el estado del jugador en un string
     * @return estado actual del jugador
     */
    @Override
    public String toString(){
        String estado = "\nInformacion sobre el jugador: ";
        String todas = "";
        String propiedad;
        
        for(int i = 0; i < propiedades.size() ; i++){
            propiedad = propiedades.get(i).getNombre();
            todas = todas + propiedad + ",";
            todas = todas; //+ ", ";
        }
        
        estado = estado + "\nNombre: " + getNombre() + ", Encarcelado: " + encarcelado +
                ", Puede comprar: " + puedeComprar + ", Saldo: " + saldo + ", Número casilla actual: " +
                numCasillaActual + ", Número propiedades: " + propiedades.size() + "\nPropiedades: " +
                todas + "\n";
        
        return estado;
    }
    
    /**
     * Metodo que hace que un jugador anule la hipoteca de una propiedad
     * @param ip identificador de la propiedad
     * @return true si la operación se realiza con éxito
     */
    boolean cancelarHipoteca(int ip){
        boolean result = false;
        
        if(encarcelado) return result;
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = this.puedoGastar(cantidad);
            
            if(puedoGastar){
                result = propiedad.cancelarHipoteca(this);
                
                if(result){
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " cancela la hipoteca de la propiedad " + ip);
                }
            }
        }
        return result;
    }
    
    /**
     * Metodo que  hace que un jugador compre una propiedad
     * @param titulo propiedad a comprar
     * @return true si la operación se realiza con éxito
     */
    boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        
        if(encarcelado)return result;
        
        if(puedeComprar){
            float precio = titulo.getPrecioCompra();
            
            if(puedoGastar(precio)){
                result = titulo.comprar(this);
                
                if(result){
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " compra la propiedad " + titulo.getNombre());
                }
                puedeComprar = false;
            }
        }
        
        return result;
    }
    
    /**
     * Metodo que hace que un jugador construya un hotel
     * @param ip identificador de la propiedad
     * @return true si la operación se realiza con éxito
     */
    boolean construirHotel(int ip){
        boolean result = false;
        
        if(encarcelado) return result;
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
            
            if(puedoEdificarHotel){
                result = propiedad.construirHotel(this);
                int casasPorHotel = getCasasPorHotel();
                propiedad.derruirCasas(casasPorHotel, this);
                
                Diario.getInstance().ocurreEvento("El jugador " + nombre + " cosntruye un hotel en la propiedad " + ip );
            } 
        }
        return result;
    }
    
    /**
     * Metodo que hace que un jugador construya una casa
     * @param ip identificador de la propiedad
     * @return true si la operación se realiza con éxito
     */
    boolean construirCasa(int ip){
        boolean result = false;
        
        if(encarcelado) return result;
        
        if(!encarcelado){
            boolean existe = existeLaPropiedad(ip);
            
            if(existe){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
                
                if(puedoEdificarCasa){
                    result = propiedad.construirCasa(this);
                    
                    if(result){
                        Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye casa en la propiedad " + ip );
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Metodo que hace que un jugador hipoteque una propiedad
     * @param ip identificador de la propiedad
     * @return true si la operación se realiza con éxito
     */
    boolean hipotecar(int ip){
        boolean result = false;
        
        if(encarcelado) return result;
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }
        
        if(result){
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " hipoteca la propiedad " + ip);
        }
        
        return result;
    }
}
