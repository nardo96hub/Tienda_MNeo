
package tiendamneo.interfaces;

import tiendamneo.entidades.Tienda;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public interface InterTienda {
      
    Tienda crearTienda();   
    void productosCreados();
    void ventaPro();
    void compraPro(); 
    void actualizarPro();
    void listarStock();
    void listarStockNoDisponible();
    void listarStockDisponible();
    void obtenerComestiblesConMenorDescuento(Double porcentaje_descuento);
    void listarProductosConUtilidadesInferiores(Double porcentaje_utilidad);
  
}
