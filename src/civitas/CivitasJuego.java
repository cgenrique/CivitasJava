/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import GUI.Dado;
import java.util.ArrayList;
import java.util.Collections;

public class CivitasJuego {
    private int indiceJugadorActual;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private Tablero tablero;
    private MazoSorpresas mazo;
    private ArrayList<Jugador> jugadores;
    
    /**
     * Constructor con parámetros de la clase
     * @param nombres Nombres de los jugadores de la partida
     * @return Nuevo objeto de la clase
     */
    public CivitasJuego(ArrayList<String> nombres){
        jugadores = new ArrayList<>();
        
        for(int i = 0; i < nombres.size(); i++){
            Jugador jugador = new Jugador(nombres.get(i));
            jugadores.add(jugador);
        }
        
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        mazo = new MazoSorpresas();
        inicializaMazoSorpresas(tablero);
        inicializaTablero(mazo);
    }
    
    /**
     * Metodo que crea un tablero con valores concretos
     * @param mazo Mazo de sorpresas de la partida
     */
    
    private void inicializaTablero (MazoSorpresas mazo){
       tablero = new Tablero(6);
       
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Calle Alhamar", 100, 1.1f, 100, 300, 100)));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Calle San Antón", 125, 1.1f, 130, 325, 125)));
       tablero.añadeCasilla(new CasillaImpuesto(250f, "Impuesto sobre el capital"));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Calle Mesones", 120, 1.1f, 200, 400, 140)));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Calle Recogidas", 200, 1.1f, 500, 600, 200)));
       tablero.añadeJuez();
       tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Calle Puentezuelas", 300, 1.1f, 400, 500, 250)));
       tablero.añadeCasilla(new Casilla("Parking gratuito"));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Camino de Ronda", 260, 1.1f, 200, 250, 180)));
       tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Avenida Dilar", 100, 1.1f, 100, 120, 190)));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Calle Periodista Daniel Saucedo Aranda", 400, 1.1f, 300, 550, 280)));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Plaza de Europa", 370, 1.1f, 270, 390, 220)));
       tablero.añadeCasilla(new CasillaImpuesto(350f, "Impuesto de circulación"));
       tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Gran Vía de Colón", 400, 1.1f, 400, 1100, 400)));
       tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad ("Paseo de los tristes", 500, 1.1f, 500, 1200, 500)));
    }
    
    /**
     * Metodo que crea un mazo de sorpresas con valores concretos
     * @param tablero Tablero de la partida
     */
       
    private void inicializaMazoSorpresas(Tablero tablero){
        mazo.alMazo(new SorpresaIrCasilla (tablero, 1,"Ve a la Calle Alhamar" ));
        mazo.alMazo(new SorpresaIrCarcel (tablero));
        mazo.alMazo(new SorpresaPagarCobrar (200, "Enhorabuena, recibes 200"));
        mazo.alMazo(new SorpresaIrCasilla (tablero, 13,"Ve a la Avenida Dílar" ));
        mazo.alMazo(new SorpresaPorCasaHotel (100, "Paga 100 por cada casa y hotel que tengas"));
        mazo.alMazo(new SorpresaPagarCobrar (200*(jugadores.size()-1), "Recibes 200 de cada jugador"));
        mazo.alMazo(new SorpresaSalirCarcel (mazo));
        mazo.alMazo(new SorpresaPagarCobrar (300, "Paga 300"));
    }
    
    /**
     * Metodo que cuenta el numero de veces que un jugador pasa por la salida
     * @param jugadorActual Jugador sobre el que contamos los pasos por salida
     */
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    /**
     * Metodo que pasa un turno
     */
    
    private void pasarTurno(){
       if(indiceJugadorActual < jugadores.size() - 1){
           indiceJugadorActual ++;
       }
       else{
           indiceJugadorActual = 0;
       }
    }
   
    /**
     * Metodo que actualiza el estado del juego
     * @param operacion Operacion que actualiza el estado del juego
     */
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
    }
   
    /**
     * Metodo que construye una casa
     * @param ip Identificador de la propiedad
     * @return true si la casa se construye correctamente
     */
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    /**
     * Metodo que construye un hotel
     * @param ip Identificador de la propiedad
     * @return true si el hotel se construye correctamente
     */
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    /**
     * Metodo que hace que un jugador venda una propiedad
     * @param ip Identificador de la propiedad
     * @return true si la propiedad se vende correctamente
     */
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    /**
     * Metodo que hace que un jugador hipoteque una propiedad
     * @param ip Identificador de la propiedad
     * @return true si la propiedad se hipoteca correctamente
     */

    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    /**
     * Metodo que hace que un jugador cancele una hipoteca de una propiedad
     * @param ip Identificador de la propiedad
     * @return true si la propiedad se cancela la hipoteca correctamente
     */
    
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    /**
     * Metodo que hace que un jugador salga de la carcel pagando
     * @return true si el jugador sale de la carcel correctamente
     */
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    /**
     * Metodo que hace que un jugador salga de la carcel tirando
     * @return true si el jugador sale de la carcel correctamente
     */
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    /**
     * Metodo que nos indica si es el momento de terminar el juego
     * @return true si el juego debe terminar
     */
    
    public boolean finalDelJuego(){
        boolean fin = false;
        for(int i = 0; i < jugadores.size(); i++){
            if(jugadores.get(i).enBancarrota()){
                fin = true;
            }
        }
        
        return fin;
    }
    
    
    /**
     * Metodo que devuelve un ranking de los jugadores en funcion de su saldo
     * @return ranking de jugadores por saldo
     */
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> ranking = new ArrayList<>();
        
        for(int i = 0; i < jugadores.size(); i++){
            ranking.add(jugadores.get(i));
        }
        
        Collections.sort(ranking, Jugador::compareTo);
        
        return ranking;
    }
    
    /**
     * Metodo consultor de la casilla actual
     * @return casilla actual
     */
    
    public Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }
    
    /**
     * Metodo consultor del jugador actual
     * @return jugador actual
     */
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    /**
     * Metodo que proporciona informacion del jugador actual
     * @return informacion del estado del jugador actual
     */
    
    public String infoJugadorTexto(){
        String info;
        
        info = jugadores.get(indiceJugadorActual).toString() + 
                "\nInformación sobre la casilla actual: " +
                getCasillaActual().toString() + "\n";
        
        return info;
    }
    
    /**
     * Metodo que hace que un jugador avance un número de casillas
     */
    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadorActual);
    }
    
    /**
     * Metodo que realiza una operacion con el jugador
     * @return Operación que se realiza
     */
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion =  gestorEstados.operacionesPermitidas(jugadorActual, estado);
        
        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        
        return operacion;
    }
    
    /**
     * Metodo que efectua la compra de una propiedad por un jugador
     * @return true si la operación se realiza con éxito
     */
    
    public boolean comprar(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        CasillaCalle casilla = (CasillaCalle) tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        
        return res;
    }
}
