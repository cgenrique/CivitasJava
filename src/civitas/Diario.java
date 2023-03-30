package civitas;



import java.util.ArrayList;

public class Diario {
  static final private Diario instance = new Diario();
  
  private ArrayList<String> eventos;
  
  /**
    * Método que devuelve una referencia (singleton) de un objeto de la clase
    * @return referencia al singleton de la clase diario
    */
  static public Diario getInstance() {
    return instance;
  }
  
  /**
    * Constructor sin parámetros de la clase
    * @return Nuevo objeto de la clase
    */
  private Diario () {
    eventos = new ArrayList<>();
  }
  
  /**
    * Método que escribe un evento en el diario de la partida
    */
  void ocurreEvento (String e) {
    eventos.add (e);
  }
  
  /**
    * Método que nos indica si hay eventos pendientes o no
    * @return true si hay eventos pendientes
    */
  public boolean eventosPendientes () {
    return !eventos.isEmpty();
  }
  
  /**
    * Método que lee y devuelve un evento del diario
    * @return evento del diario
    */
  public String leerEvento () {
    String salida = "";
    if (!eventos.isEmpty()) {
      salida = eventos.remove(0);
    }
    return salida;
  }
}
