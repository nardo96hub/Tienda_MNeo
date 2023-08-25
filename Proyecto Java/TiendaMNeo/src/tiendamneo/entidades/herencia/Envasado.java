package tiendamneo.entidades.herencia;

import java.time.LocalDate;
import tiendamneo.entidades.Producto;
import tiendamneo.enumeracion.TipoEnvase;
import tiendamneo.interfaces.InterComestible;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public class Envasado extends Producto implements InterComestible{
    private TipoEnvase envase;  
    private Boolean isImportado;
    
    
    private Boolean isComestible;
    private LocalDate vencimiento;
    private Integer calorias;
    
    
    
    public Envasado() {
        super();
    }

    public Envasado(String iden,String des,Integer cantS,Double precioU,Double costoU,Boolean dis,TipoEnvase envase, Boolean isImportado) {
        super(iden,des,cantS,precioU,costoU,dis);
        this.envase = envase;
        this.isImportado = isImportado;
    }
    public Envasado(String iden,String des,Integer cantS,Double precioU,Double costoU,Boolean dis,Double descuento,TipoEnvase envase, Boolean isImportado,Boolean isCom,LocalDate venci,Integer calo){
        super(iden,des,cantS,precioU,costoU,dis,descuento);
        this.envase = envase;
        this.isImportado = isImportado;
        
        this.isComestible=isCom;
        this.vencimiento=venci;
        this.calorias=calo;
    }
    
    @Override
    public void setFechaVencimienti(LocalDate venci) {
        this.vencimiento=venci;
    }

    @Override
    public LocalDate getFechaVencimiento() {
        return vencimiento;
    }

    @Override
    public void setCalorias(Integer caloria) {
        this.calorias=caloria;
    }

    @Override
    public Integer getCalorias() {
       return calorias;
    }
    
    @Override
    public void setComestible(Boolean isCom){
        this.isComestible=isCom;
    }
    @Override
    public Boolean getComestible(){
        return isComestible;
    }
    
    
    //  Getters and Setters / toString

    public TipoEnvase getEnvase() {
        return envase;
    }

    public void setEnvase(TipoEnvase envase) {
        this.envase = envase;
    }

    public Boolean getIsImportado() {
        return isImportado;
    }

    public void setIsImportado(Boolean isImportado) {
        this.isImportado = isImportado;
    }

    @Override
    public String toString() {
        // "id:" + identificador + ", descripcion:" + descripcion + ", cantStock:" + cantStock + ", precioUnidad:$" + precioUnidad + ", costoUnidad:$" + costoUnidad + ", disponibilidad:" + disponibilidad 
        return "Emvasado{"+"id:"+getIdentificador()+" - nombre: "+getDescripcion()+" - cantStock:"+getCantidad()+" - precioUnidad:$"+getPrecioUnidad()+" - costoUnidad: "+getCostoUnidad()+" - disponibleVenta: "+getDisponibilidad() + " - envase:" + envase.name()+ " - esImportado:" + isImportado + '}';
    }

   
  
    
    
}
