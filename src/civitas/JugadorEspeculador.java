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
public class JugadorEspeculador extends Jugador {
    private static int FactorEspeculador = 2; 
    private int fianza;
    
    JugadorEspeculador(Jugador jug, int f){
        super(jug);
        fianza = f;
        
        for(int i = 0; i < super.getPropiedades().size(); i++){
            super.getPropiedades().get(i).actualizarPropietarioPorConversion(this);
        }
    }
    
    @Override
     boolean encarcelar (int numCasillaCarcel){
         boolean entra = false;
         
         if(!encarcelado){
             if(!tieneSalvoconducto()){
                 if(fianza < getSaldo()){
                     super.paga(fianza);
                     Diario.getInstance().ocurreEvento("El jugador " + getNombre() 
                             + ", se libra de la cárcel pagando la fianza" );
                 }
                 else{
                     entra = true;
                 }
             }
             else{
                 perderSalvoconducto();
                 Diario.getInstance().ocurreEvento("El jugador " + getNombre() 
                             + ", se libra de la cárcel usando su salvoconducto " );
             }
         }
        return entra;
     }
     
    @Override
    public int getCasasMax(){
        return(CasaMax * FactorEspeculador);
    }
     
    @Override
    public int getHotelesMax(){
        return(HotelesMax * FactorEspeculador);
    }
     
    @Override
    boolean pagaImpuesto(float cantidad){
        boolean pagado = false;
        
        if(!encarcelado){
            Diario.getInstance().ocurreEvento(getNombre() + " paga un impuesto");
            pagado = paga(cantidad/2);
        }
        
        return pagado;
    }
    
    @Override
    public String toString(){
        String estado;
        
        estado = super.toString();
        estado = estado +  "Se trata de un jugador especulador -> " +"Factor de especulación:" + FactorEspeculador + ", " + "Fianza: " + fianza + "\n";
        
        return estado;
    }
}
