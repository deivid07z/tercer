package util;

public class CaException  {
      private Exception lastError;
      private String descripcion;


            
      public String getDescripcion(){
        return descripcion;
      }

    /**
     * Metodo constructor que recoge la descripci�n del
     * error que genero la excepci�n.
     *
     * @param error
     *        cadena que contiene la descripci�n del error.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDetalle(Exception lastError) {
        this.lastError = lastError;
    }
      
      public Exception getDetalle(){
        return lastError;
      }

    public String getLastException() {
        return "Error: "+lastError+", Mensaje: "+lastError.getMessage();
    }
      
      
      private static CaException instance = null;
      
      public static CaException getInstance() {
            if (instance == null) {
                    try {
                            instance = new CaException();
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
            }
		return instance;
	}
      
    private CaException() {

      }

 }

