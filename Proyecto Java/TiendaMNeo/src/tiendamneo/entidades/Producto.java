package tiendamneo.entidades;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import tiendamneo.entidades.herencia.Bebida;
import tiendamneo.entidades.herencia.Envasado;
import tiendamneo.entidades.herencia.Limpieza;
import tiendamneo.enumeracion.TipoAplicacion;
import tiendamneo.enumeracion.TipoEnvase;
import tiendamneo.interfaces.InterDescuento;
import tiendamneo.interfaces.InterProducto;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public class Producto implements InterProducto,InterDescuento{
    
    private String identificador;  // Tamaño de 5 caracteres con Formato AXYYY  X caracter Y numeruco
    private String descripcion;
    private Integer cantidad;
    private Double precioUnidad;
    private Double costoUnidad;
    private Boolean disponibilidad;
    private Double descuento; 
    
    Scanner scan=new Scanner(System.in);
    
    public Producto(){
        
    }

    public Producto(String identificador, String descripcion, Integer cantStock, Double precioUnidad, Double costoUnidad, Boolean disponibilidad) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.cantidad = cantStock;
        this.precioUnidad = precioUnidad;
        this.costoUnidad = costoUnidad;
        this.disponibilidad = disponibilidad;
              
    }
    
    //Se utiliza cuando creo productos Automatico
    public Producto(String identificador, String descripcion, Integer cantStock, Double precioUnidad, Double costoUnidad, Boolean disponibilidad,Double descuento){
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.cantidad = cantStock;
        this.precioUnidad = precioUnidad;
        this.costoUnidad = costoUnidad;
        this.disponibilidad = disponibilidad;
        this.descuento=descuento;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Funciones Privadas
    
    // Realiza la suma entre un String e Integer para que el String tenga length 5
    /**
     * 
     * @param ind : String de length 2 que contiene el formato X   XXYYY
     * @param num : Integer numero que contiene el formato Y
     * @return Identificador
     */
    private String asignarIdentificador(String ind,Integer num){

        if(num<10){
            ind=ind+"00"+num;
        }else if(num>9 &&num<100){
            ind=ind+"0"+num;
        }else{
            ind+=num;
        }
        
        return ind;
    }
    /**
     * 
     * @return Ingreso y validacion de  10,0< descuento <25,0
     */ 
    private Double descuento_1025(){
        Double des;
        System.out.println("Ingrese el descuento del producto [10,0 , 25,0]");
        des=scan.nextDouble();
        des=Validar.validarDoubleRango(des, 10.0, 25.0);
    
        return des;
    }
    
    /**
     * 
     * @return Ingreso y validacion de descuento <99.9
     */
    private Double descuento_99(){
        Double des;
        System.out.println("Ingrese el descuento del producto [0,0 , 99,9]");
        des=scan.nextDouble();
        des=Validar.validarDoubleRango(des, 0.0, 99.9);
        return des;
    }
   
     // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Funcion principal en Producto para crear productos y agregar al Stock

//Crear un producto y lo retorna  recibe por paramentro el tipo de producto valor entre [1,3]
    
    /**
     * 
     * @param tipo  : si tipo=1 crea Producto envasado, si tipo=2 crea Producto Bebiba y si tipo=3 crea Producto Limpieza 
     * @return pro: Producto a agregar en Stock
     */
    
    @Override
    public Producto agregarProducto(Integer tipo) {
      
        String id,des;
        Integer num,cant,op,indAlc,dia,mes,anio;
        Boolean dis,imp,alc,com;
        Double preU,cosU,desc;        
        
        Producto pro=null;        
        //Creados para acceder a los metodos de los hijos        
        Bebida beb=null;
        Envasado em=null;
        
        try{
            System.out.println("Crear Producto\n--------------\n");
            
            System.out.println("Ingrese una descripcion del producto a crear: ");
            des=scan.nextLine();
            System.out.println("Es disponible para la venta el producto? [true,false] : ");
            dis=scan.nextBoolean();
            System.out.println("Ingrese el precio del costo del producto > 0,0: ");
            cosU=scan.nextDouble();
            cosU=Validar.validarDoubleMenor(cosU, 0.0);
            
            System.out.println("Ingrese el precio a la venta del producto >"+cosU+ ": ");
            preU=scan.nextDouble();                        
            preU=Validar.validarDoubleMenor(preU, cosU);
            
            
            System.out.println("Ingrese la cantidad del producto > 0: ");
            cant=scan.nextInt();
            cant=Validar.validarIntMenor(cant, 0);
           
            
            System.out.println("Ingrese un num entre [0,999] :");
            num=scan.nextInt();
            num=Validar.validarIntRango(num, 0, 999);
            
            switch(tipo){
                case 1:
                    
                    id="AB";
                    id=asignarIdentificador(id,num);
                  
                    
                    System.out.println("El embase es importado? [true,false]: ");
                    imp=scan.nextBoolean();
                    System.out.println("Ingrese el tipo de envase:\n1_Plastico\n2_Vidrio\n3_Lata\nIngrese una opcion entre [1,3]");
                    op=scan.nextInt();
                    op=Validar.validarIntRango(op, 1, 3);
                    
                    switch(op){
                        case 1:

                            em=new Envasado(id, des, cant, preU, cosU, dis, TipoEnvase.Plastico, imp);
                            
                            break;
                        case 2:
                            em=new Envasado(id, des, cant, preU, cosU, dis, TipoEnvase.Vidrio, imp);
                            break;
                        case 3:
                            em=new Envasado(id, des, cant, preU, cosU, dis, TipoEnvase.Lata, imp);
                            break;
                    }
                    System.out.println("El producto es comestible? [true,false]");
                    com=scan.nextBoolean();
                    em.setComestible(com);
                    if(em.getComestible()){
                        System.out.println("Ingrese la cantidad de calorias");
                        indAlc=scan.nextInt();
                        indAlc=Validar.validarIntMenor(indAlc, 0);
                        em.setCalorias(indAlc);
                        
                        
                        System.out.println("Ingrese fecha de vencimiento\nIngrese 2022 < anio < 2024");
                        anio=scan.nextInt();
                        anio=Validar.validarIntRango(anio, 2022, 2024);
                        System.out.println("Ingrese valor de mes entre [1,12]");
                        mes=scan.nextInt();
                        mes=Validar.validarIntRango(mes, 1, 12);
                        System.out.println("Ingrese valor de dia valor entre [1,31]");
                        dia=scan.nextInt();
                        dia=Validar.validarIntRango(dia, 1, 31);
                        if(mes==4 && mes==6 && mes==9 && mes==11 ){
                            dia=Validar.validarIntRango(dia,1, 31);
                            
                        }else if(mes==2){
                            if(anio%4==0){
                                dia=Validar.validarIntRango(dia, 1, 29);
                            }else {
                                dia=Validar.validarIntRango(dia, 1, 28);   
                            }
                            
                        }
                        if(dia<10 && mes<10){
                            em.setFechaVencimienti(LocalDate.parse(anio+"-0"+mes+"-0"+dia));
                        }else if(dia<10 &&mes>10){
                            em.setFechaVencimienti(LocalDate.parse(anio+"-"+mes+"-0"+dia));
                        }else if(dia>10 && mes<10){
                            em.setFechaVencimienti(LocalDate.parse(anio+"-0"+mes+"-"+dia));
                        }else{
                            em.setFechaVencimienti(LocalDate.parse(anio+"-"+mes+"-"+dia));
                        }
                        
                        
                    }else{
                        em.setCalorias(0);
                        em.setFechaVencimienti(null);
                        em.setFechaVencimienti(LocalDate.now());
                    }
                    if(em.getIsImportado()){
                        em.setDescuento(10.0);
                    }
                    pro=em;
                    System.out.println("Ingrese el descuento del producto <= 20,0");
                    desc=scan.nextDouble();
                    desc=Validar.validarDoubleRango(desc, 0.0, 20.0);
                    
                    if(getDescuento()!=null){
                         pro.setDescuento(desc+getDescuento());
                    }else{
                        pro.setDescuento(desc);
                    } 
                    break;
                case 2:
                    id="AC";
                    id=asignarIdentificador(id,num);
                    System.out.println("La bebida es alcolica? [true, false]: ");
                    alc=scan.nextBoolean();
                    if(!alc){
                        System.out.println("Ingrese el porcentaje de alcohol [0,100]");
                        indAlc=scan.nextInt(); 
                        indAlc=Validar.validarIntRango(indAlc, 1, 100);
                    }else {
                        indAlc=0;
                    }

                    System.out.println("La bebida es importado? [true,false]: ");
                    imp=scan.nextBoolean();
                    
                    beb=new Bebida(id, des, cant, preU, cosU, dis, alc,indAlc, imp);
                    if(beb.getIsImportado()){
                        beb.setDescuento(10.0);
                    }
                    pro=beb;
                                        
                    System.out.println("Ingrese el descuento del producto [0,0 , 15,0]");
                    desc=scan.nextDouble();
                    desc=Validar.validarDoubleRango(desc, 0.0, 15.0);
                    pro.setDescuento(desc+getDescuento());
                    
                    break;
                case 3:
                    id="AZ";
                    id=asignarIdentificador(id,num);
                    System.out.println("Ingrese el tipo de aplicacion:\n1_Cocina\n2_Pisos\n3_Ropa\n4_Multiuso\n");
                    System.out.println("Ingrese una opcion entre [1,4]");
                    op=scan.nextInt();
                    op=Validar.validarIntRango(op, 1, 4);
                    switch(op){
                        case 1:
                            // String iden,String des,Short cantS,Double precioU,Double costoU,Boolean dis,TipoAplicacion aplicacion
                            pro=new Limpieza(id, des, cant, preU, cosU, dis, TipoAplicacion.Cocina);
                            
                           
                            desc=this.descuento_1025();
                            pro.setDescuento(desc);
                            break;
                        case 2:
                            pro=new Limpieza(id, des, cant, preU, cosU, dis, TipoAplicacion.Pisos);
                            descuento=descuento_1025();
                            pro.setDescuento(descuento);
                            break;
                        case 3:
                            pro=new Limpieza(id, des, cant, preU, cosU, dis, TipoAplicacion.Ropa);                                                       
                            desc=descuento_99();
                            pro.setDescuento(desc);
                            break;
                        case 4:
                            pro=new Limpieza(id, des, cant, preU, cosU, dis, TipoAplicacion.Multiuso);
                            
                            desc=descuento_99();
                            pro.setDescuento(desc);
                            break;
                    }
                    break;  
            }
            
             
        }catch (InputMismatchException error){
            System.out.println("Error al ingresar informacion: "+error.toString());
        }
        
        return pro;
                    
    }
    
    @Override
    public void setDescuento(Double descuento){
         this.descuento=descuento;
    }
    @Override
    public Double getDescuento(){
        return descuento;
    }
    @Override
    public Double getPrecioVentaConDescuento(){
        return precioUnidad-precioUnidad*descuento/100;
    }
    
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    
//  Getters and Setters / toString

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantStock) {
        this.cantidad = cantStock;
    }

    public Double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Double getCostoUnidad() {
        return costoUnidad;
    }

    public void setCostoUnidad(Double costoUnidad) {
        this.costoUnidad = costoUnidad;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public String toString() {
        
        return "Producto{" + "id:" + identificador + ", descripcion:" + descripcion + ", cantStock:" + cantidad + ", precioUnidad:$" + precioUnidad + ", costoUnidad:$" + costoUnidad + ", disponibilidad:" + disponibilidad + '}';
    }

   
}
