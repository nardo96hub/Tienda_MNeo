package tiendamneo.entidades.herencia;

import tiendamneo.entidades.Producto;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public class Bebida extends Producto{
    private Boolean isAlcolica;
    private Integer graduacionAlcolica;
    private Boolean isImportado;

    
   
    public Bebida() {
        super();
    }

   
    public Bebida(String iden,String des,Integer cantS,Double precioU,Double costoU,Boolean dis,Boolean isAlcolica, Integer graduacionAlcolica, Boolean isImportado) {
        super(iden,des,cantS,precioU,costoU,dis);
        this.isAlcolica = isAlcolica;
        this.graduacionAlcolica = graduacionAlcolica;
        this.isImportado = isImportado;
    }
    
     public Bebida(String iden,String des,Integer cantS,Double precioU,Double costoU,Boolean dis,Double descuento,Boolean isAlcolica, Integer graduacionAlcolica, Boolean isImportado) {
        super(iden,des,cantS,precioU,costoU,dis,descuento);
        this.isAlcolica = isAlcolica;
        this.graduacionAlcolica = graduacionAlcolica;
        this.isImportado = isImportado;
    }
 

//  Getters and Setters / toString
    public Boolean getIsAlcolica() {
        return isAlcolica;
    }

    public void setIsAlcolica(Boolean isAlcolica) {
        this.isAlcolica = isAlcolica;
    }

    public Integer getGraduacionAlcolica() {
        return graduacionAlcolica;
    }

    public void setGraduacionAlcolica(Integer graduacionAlcolica) {
        this.graduacionAlcolica = graduacionAlcolica;
    }

    public Boolean getIsImportado() {
        return isImportado;
    }

    public void setIsImportado(Boolean isImportado) {
        this.isImportado = isImportado;
    }

    @Override
    public String toString() {
        return "Bebida{id:"+getIdentificador()+" - nombre: "+getDescripcion()+" - cantStock:"+getCantidad()+" - precioUnidad:$"+getPrecioUnidad()+" - costoUnidad: "+getCostoUnidad()+" - disponibleVenta: "+getDisponibilidad() + " - esAlcolica:" + isAlcolica + " - graduacionAlcolica:" + graduacionAlcolica + "% - esImportado:" + isImportado + '}';
    }

  
    

}
