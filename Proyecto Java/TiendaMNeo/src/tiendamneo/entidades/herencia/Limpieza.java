package tiendamneo.entidades.herencia;

import tiendamneo.entidades.Producto;
import tiendamneo.enumeracion.TipoAplicacion;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public class Limpieza extends Producto{
    private TipoAplicacion aplicacion;

    
    //  Getters and Setters / toString
    public Limpieza(){
      super(); 
    }

    public Limpieza(String iden,String des,Integer cantS,Double precioU,Double costoU,Boolean dis,TipoAplicacion aplicacion) {
        super(iden, des, cantS, precioU, costoU, dis);
        this.aplicacion = aplicacion;
    }
    public Limpieza(String iden,String des,Integer cantS,Double precioU,Double costoU,Boolean dis,Double descuento,TipoAplicacion aplicacion) {
        super(iden, des, cantS, precioU, costoU, dis,descuento);
        this.aplicacion = aplicacion;
    }
    
    public TipoAplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(TipoAplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    @Override
    public String toString() {
        return "Limpieza{id:"+getIdentificador()+" - nombre: "+getDescripcion()+" - cantStock:"+getCantidad()+" - precioUnidad:$"+getPrecioUnidad()+" - costoUnidad: "+getCostoUnidad()+" - disponibleVenta: "+getDisponibilidad() + " - aplicacion:" + aplicacion.toString() + '}';
    }
    
    
    
    
}
