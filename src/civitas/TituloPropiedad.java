/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author enriq
 */
public class TituloPropiedad {
    private float alquilerBase;
    static final private float factorInteresesHipoteca = 1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
   
    /**
     * Constructor con parámetros de la clase
     * @param nom Nombre de la propiedad
     * @param ab Alguiler de base
     * @param fr Factor de revalorizacion
     * @param hb Hipoteca de base
     * @param pc Precio de Compra
     * @param pe Precio por edificar
     * @return Nuevo objeto de la clase
     */
    
    TituloPropiedad(String nom, float ab, float fr,
                    float hb, float pc, float pe){
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        numCasas = 0;
        numHoteles = 0;
        hipotecado = false;
        propietario = null;
    }
    
    /**
     * Método que indica si la propiedad tiene propietario
     * @return false si el propietario es nulo
     */
    
    boolean tienePropietario(){
        return(propietario != null);
    }
    
    /**
     * Método para expresar el estado del objeto como un String
     * @return Estado del objeto en String
     */
    
    @Override
    public String toString(){
        String estado;
        String prop = "";
        
        if(tienePropietario() == false){
            prop = "No tiene propietario";
        }
        else{
            prop = propietario.getNombre();
        }
        
        estado = "Nombre: " + nombre + ", Propietario: " + prop
                + ", Precio de compra = " + precioCompra
                + ", Precio por edificar = " + precioEdificar 
                + "\nAlquiler de base: " + alquilerBase
                + ", Hipoteca de base: " + hipotecaBase
                + ", Factor de revalorizacion: " + factorRevalorizacion
                + "\nFactor de interes de la hipoteca: " + factorInteresesHipoteca
                + ", Casas: " + numCasas
                + ", Hoteles: " + numHoteles + ", Hipotecado: " + hipotecado;
        
        return estado;
    }
    
    /**
     * Consultor del precio del alquiler de la propiedad
     * @return Precio del alquiler
     */
    
    private float getPrecioAlquiler(){
        float precioAlquiler;
        
        precioAlquiler = alquilerBase * (1 + (numCasas * 0.5f) + (numHoteles * 2.5f));
        
        return precioAlquiler;
    }
    
    /**
     * Método que indica el coste de la cancelación de la hipoteca de la propiedad
     * @return Precio de la cancelacion de la hipoteca
     */
    
    float getImporteCancelarHipoteca(){
        float costeCancelar;
        
        costeCancelar = (hipotecaBase * (1 + (numCasas * 0.5f) + (numHoteles * 2.5f))) * factorInteresesHipoteca;
        
        return costeCancelar;
    }
    
    /**
     * Consultor del precio del venta de la propiedad
     * @return Precio del alquiler
     */
    private float getPrecioVenta(){
        float precio;
        
        precio = precioCompra + ((numCasas + numHoteles) * precioEdificar) * factorRevalorizacion;
        
        return precio;
    }
    
    /**
     * Consultor del atributo propietario
     * @return Estado de propietario
     */
    
    Jugador getPropietario(){
        return propietario;
    }
    
    /**
     * Consultor del precio de edificación
     * @return Precio por edificar
     */
    
    float getPrecioEficicar(){
        return precioEdificar;
    }
    
    /**
     * Consultor del precio de compra
     * @return Precio por comprar
     */
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    /**
     * Consultor del número de hoteles de la propiedad
     * @return Número de hoteles
     */
    
    public int getNumHoteles(){
        return numHoteles;
    }
    
    /**
     * Consultor del número de casas de la propiedad
     * @return Número de casas
     */
    
    public int getNumCasas(){
        return numCasas;
    }
    
    /**
     * Consultor del nombre de la propiedad
     * @return Nombre de la propiedad
     */
    
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Consultor del importe de la hipoteca
     * @return Importe de la hipoteca
     */
    
    private float getImporteHipoteca(){
        return hipotecaBase;
    }
    
    /**
     * Método que indica si la propiedad está hipotecada o no
     * @return true si la propiedad está hipotecada
     */
    
    public boolean getHipotecado(){
        return hipotecado;
    }
    
    /**
     * Método para saber si un jugador es el propietario del título de propiedad
     * @param jugador jugador a comprobar
     * @return true si jugador es el propietario del título de propiedad
     */
    
    private boolean esEsteElPropietario(Jugador jugador){
        boolean es_propietario = false;
        
        if(jugador == propietario){
            es_propietario = true;
        }
        
        return es_propietario;
    }
    
    /**
     * Método que efectua el pago del alquiler sobre un jugador
     * @param jugador jugador que paga el alquiler al propietario
     */
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario()){
            if(!esEsteElPropietario(jugador) && !hipotecado){
                Diario.getInstance().ocurreEvento(jugador.getNombre() + " le paga el alquiler a " + propietario.getNombre());
                float precio = getPrecioAlquiler();
                jugador.pagaAlquiler(precio);
                propietario.recibe(precio);
            }
        }
    }
    
    /**
     * Método que indica si el propietario de la propiedad está encarcelado
     * @return true si el propietario está en la cárcel
     */
    
    private boolean propietarioEncarcelado(){
        boolean encarcelado = false;
        
        if(tienePropietario() && propietario.encarcelado == true){
            encarcelado = true;
        }
        
        return encarcelado;
    }
    
    /**
     * Método que indica el número total de casas y hoteles
     * @return número de edificaciones
     */
    
    
    int cantidadCasaHoteles(){
        return(numCasas + numHoteles);
    }
    
    /**
     * Método que indica el número total de casas y hoteles
     * @param n número de casas a demoler
     * @param jugador jugador que pide la demolición
     * @return true si la demolición se lleva a cabo
     */
    
    boolean derruirCasas(int n, Jugador jugador){
        boolean demolicion = false;
        
        if(esEsteElPropietario(jugador) && numCasas >= n){
            numCasas = numCasas - n;
            demolicion = true;
        }
        
        return demolicion;
    }
    
    /**
     * Método que vende la propiedad
     * @param jugador jugador que intenta vender la propiedad
     * @return true si la venta se lleva a cabo
     */
    
    
    boolean vender(Jugador jugador){
        boolean vendido = false;
        
        if(esEsteElPropietario(jugador) && hipotecado == false){
            propietario.recibe(getPrecioVenta());
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            vendido = true;
        }
        
        return vendido;
    }
    
    /**
     * Método que cambia el propietario de la propiedad por el introducido por parámetro
     * @param jugador jugador que será el próximo propietario
     */
    
    protected void actualizarPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    /**
     * Método que hace que un jugador cancele una hipoteca
     * @param jugador jugador que será el próximo propietario
     * @return true si la operación se realiza con éxito
     */
    boolean cancelarHipoteca(Jugador jugador){
        boolean result = false;
        
        if(hipotecado){
            if(esEsteElPropietario(jugador)){
                propietario.paga(getImporteCancelarHipoteca());
                hipotecado = false;
                result = true;
            }
        }
        return result;
    }
    
    /**
     * Método que hace que un jugador hipoteque una propiedad
     * @param jugador jugador que hipoteca
     * @return true si la operación se realiza con éxito
     */
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        
        if(!hipotecado && esEsteElPropietario(jugador)){
            propietario.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        
        return salida;
    }
    
    /**
     * Método que hace que un jugador compre una propiedad
     * @param jugador jugador que compra la propiedad
     * @return true si la operación se realiza con éxito
     */
    boolean comprar(Jugador jugador){
        boolean result = false;
        
        if(!tienePropietario()){
            propietario = jugador;
            result = true;
            propietario.paga(getPrecioCompra());
        }
        
        return result;
    }
    
    /**
     * Método que hace que un jugador construya una casa
     * @param jugador jugador que construye una casa
     * @return true si la operación se realiza con éxito
     */
    boolean construirCasa(Jugador jugador){
        boolean result = false;
        
        if(esEsteElPropietario(jugador)){
            propietario.paga(precioEdificar);
            numCasas = numCasas + 1;
            result = true;
        }
        
        return result;
    }
    
    /**
     * Método que hace que un jugador construya un hotel
     * @param jugador jugador que construye un hotel
     * @return true si la operación se realiza con éxito
     */
    boolean construirHotel(Jugador jugador){
        boolean result = false;
        
        if(esEsteElPropietario(jugador)){
            propietario.paga(precioEdificar);
            numHoteles = numHoteles + 1;
            result = true;
        }
        
        return result;
    }
}



