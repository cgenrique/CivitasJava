/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
//import civitas.Respuestas;
import civitas.GestionesInmobiliarias;
import civitas.SalidasCarcel;

public class Controlador {
    private CivitasJuego juego;
    private CivitasView vista;
    
    Controlador(CivitasJuego juego, CivitasView vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        //boolean fin = vista.juegoModel.finalDelJuego();
        
        while(juego.finalDelJuego() == false){
            vista.actualizarVista();
            //vista.pausa();
            
            OperacionesJuego siguiente = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(siguiente);
            
            if(siguiente != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            
            boolean fin = juego.finalDelJuego();
            
            if(!fin){
                switch (siguiente) {
                    case COMPRAR:
                        Respuestas respuesta = vista.comprar();
                        if(respuesta == Respuestas.SI){
                            juego.comprar(); 
                        }
                         juego.siguientePasoCompletado(siguiente);
                         break;
                    case GESTIONAR:
                        vista.gestionar(juego.getJugadorActual());
                        int indice_gestion = vista.getGestionElegida();
                        int indice_propiedad = vista.getPropiedadElegida();
                               
                        GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[indice_gestion];
                        OperacionInmobiliaria operacion = new OperacionInmobiliaria(gestion ,indice_propiedad);

                       // int num_propiedad = vista.getPropiedadElegida();
                        switch (operacion.getGestion()) {
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(indice_propiedad);
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(indice_propiedad);
                                break;
                            case CONSTUIR_HOTEL:
                               juego.construirHotel(indice_propiedad);
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(indice_propiedad);
                                break;
                            case VENDER:
                                juego.vender(indice_propiedad);
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(siguiente);
                                break;
                            default:
                                break;
                        }   break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida = vista.salirCarcel();
                        if(salida == SalidasCarcel.PAGANDO){
                            juego.salirCarcelPagando();
                        }else{
                            juego.salirCarcelTirando();
                        }   
                        juego.siguientePasoCompletado(siguiente);
                        vista.mostrarEventos();
                        break;
                }
                
            }   
        }  
        for(int i = 0; i < juego.ranking().size(); i++){
            System.out.println(i + "-" + juego.ranking().get(i));
        }
    }
}
