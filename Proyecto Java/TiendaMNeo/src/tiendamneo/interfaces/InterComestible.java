
package tiendamneo.interfaces;

import java.time.LocalDate;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public interface InterComestible {
    void setFechaVencimienti(LocalDate fechaVencimeinto);
    LocalDate getFechaVencimiento();
    void setCalorias(Integer caloria);
    Integer getCalorias();
    void setComestible(Boolean isCom);
    Boolean getComestible();
    
}
