package tiendamneo.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import tiendamneo.comparator.Comparadores;
import tiendamneo.entidades.herencia.Bebida;
import tiendamneo.entidades.herencia.Envasado;
import tiendamneo.entidades.herencia.Limpieza;
import tiendamneo.enumeracion.TipoAplicacion;
import tiendamneo.enumeracion.TipoEnvase;
import tiendamneo.enumeracion.TipoProducto;

import tiendamneo.interfaces.InterTienda;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public class Tienda implements InterTienda{
    private String nombre;
    private Integer numMaxStock;
    private Double saldoCaja;
    // Otra forma de plantearlo es crear HashMap<Integer,ArrayList<Producto>> esto serviria para que se eliminen muchos switch() siendo 1:Envasado 2:Bebida 3:Limpieza
    private HashMap<TipoProducto,ArrayList<Producto>> stock;
    
    
    Scanner scan=new Scanner(System.in);
    
    
    public Tienda(){
        
    }

    public Tienda(String nombre, Integer numMaxStock, Double saldoCaja, HashMap<TipoProducto, ArrayList<Producto>> stock) {
        this.nombre = nombre;
        this.numMaxStock = numMaxStock;
        this.saldoCaja = saldoCaja;
        this.stock = stock;
    }
    
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    //          FUNCIONES PRIVADAS 
    
    // Se encarga de consultar si un producto pertenece al array
    /**
     * 
     * @param busqueda :ArrayList<Producto> recibe el stock del tipo de producto a buscar si coincide
     * @param pro :Producto  producto a buscar si coincide en la lista
     * @return : Retorna true si pro e busqueda sino retorna false
     */
    private Boolean buscarProducto(ArrayList<Producto> busqueda,Producto pro){
        Boolean coincide=false;
        for(Producto bus:busqueda){
            if(bus.getDescripcion().equals(pro.getDescripcion())){
                coincide=true;
            }
        }
        return coincide;
    }
    
    // Siempre el producto se encuentra en el ArrayList ya que antes busque si coincide
    /**
     * 
     * @param sto : ArrayList<Producto> recibe el stock del tipo de producto a buscar
     * @param pro :Producto  producto a buscar ( la funcion se llama si pro e sto). Por lo tanto encuentra ubicacion
     * @return Retorna el indice del sto donde se encontra el pro
     */
    private Integer ubicacionPro(ArrayList<Producto> sto,Producto pro){
        Integer pos,i;
        pos=null;
        i=0;
        while(pos==null){
            if(sto.get(i).getDescripcion().equals(pro.getDescripcion())){
                pos=i;
            }
            i++;
        }
        return pos;
    }
    
    // Realiza la suma de las cantidades de los productos
    /**
     * 
     * @return Retorna la suma de las cantidades de todos los productos
     */
    private Integer sumarProductos(){
        Integer sum=0;
        for(Producto pro:stock.get(TipoProducto.Envasado)){
            sum+=pro.getCantidad();
        }
        for(Producto pro:stock.get(TipoProducto.Bebida)){
            sum+=pro.getCantidad();
        }
        for(Producto pro:stock.get(TipoProducto.Limpieza)){
            sum+=pro.getCantidad();
        }
        return sum;
    }
    
    //Busco si el producto pertenece al stock de existir actualizo la cantidad sino se agrega al stock
    /**
     * 
     * @param tipo: TipoProducto (Envasado,Bebida,Limpieza)
     * @param prod :Producto
     */
    private void actualizoTipo(TipoProducto tipo,Producto prod){
        Integer i=null;
        //Busco el objeto si existe actualizo la cantidad sino lo agrego
        if(buscarProducto(stock.get(tipo), prod)){
            i=ubicacionPro(stock.get(tipo),prod);  //Como se encontre siempre encuentra ubi
            //Actualizo
            stock.get(tipo).get(i).setPrecioUnidad(prod.getPrecioUnidad());
            stock.get(tipo).get(i).setCantidad(prod.getCantidad());
            stock.get(tipo).get(i).setCostoUnidad(prod.getCostoUnidad());
            stock.get(tipo).get(i).setDisponibilidad(prod.getDisponibilidad());
                                            
        }else{
            stock.get(tipo).add(prod);
        }                        
    }
    
    // Realiza la compra
    /**
     * 
     * @param op : si es 1 representa Producto Envasado, si es 2 representa Producto Bebida y si es 3 representa Producto Limpieza
     * @param pro : es el producto a agregar siempre que no se encuentre registrado en stock
     */
    private void realizarCompra(Integer op,Producto pro){
        
        
        switch(op){
            case 1:
                //Busco el objeto si existe actualizo la cantidad sino lo agrego
                actualizoTipo(TipoProducto.Envasado, pro);
                break;
            case 2:
                 actualizoTipo(TipoProducto.Bebida, pro);
              
                break;                       
            case 3:
                 actualizoTipo(TipoProducto.Limpieza, pro);
                break;    
        }
    }
    // Muestra una especie de Ticket
    /**
     * 
     * @param tipo : TipoProducto (Envasado,Bebida,Limpieza)
     * @return Retorna la cantidad de elemementos que tiene el Stock en especifico otra forma de realizarlo es cant=stock.get(tipo).size();
     */
    private Integer mostarArray(TipoProducto tipo){
        Integer cant=0;
        if(!stock.get(tipo).isEmpty()){
            System.out.println("Lista de productos de: "+tipo.name());
            for(Producto pro:stock.get(tipo)){
                System.out.println((cant+1)+") "+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" * $"+pro.getPrecioUnidad());
                cant++;
            }
        }
        return cant;
    }
   
    // Realiza la venta de un producto y lo retorna
    /**
     * 
     * @param tipo : TipoProducto (Envasado,Bebida,Limpieza)
     * @return Producto que se vende
     */
    private Producto operacionVenta(TipoProducto tipo){
        Producto pro;
        Producto prod=null;
        
        Integer pos,cantP,cantPT;
        Double precioD;
        
        try{
            cantPT=mostarArray(tipo);
            System.out.println("Ingrese una opcion entre [1,"+cantPT+"]");
            pos=scan.nextInt();
            pos=Validar.validarIntRango(pos, 1, cantPT);
            pro=stock.get(tipo).get(pos-1);
            
            if(pro.getDisponibilidad()){
                System.out.println("Ingrese la cantidad que desea comprar (maximo 10)");
                cantP=scan.nextInt();
                cantP=Validar.validarIntRango(cantP,1 , 10);
                
                if(pro!=null){
                    prod=new Producto(pro.getIdentificador(), pro.getDescripcion(), pro.getCantidad(), pro.getPrecioUnidad(), pro.getCostoUnidad(), pro.getDisponibilidad(), pro.getDescuento());
                    precioD=prod.getPrecioUnidad()-prod.getDescuento()*prod.getPrecioUnidad()/100;
                    if(precioD>prod.getCostoUnidad()){
                        System.out.println("El descuento registrado para el producto "+pro.getDescripcion()+" no pudo ser aplicado");
                    }else{
                        
                        prod.setPrecioUnidad(precioD);
                    }
                }
                if(cantP>prod.getCantidad()){
                    System.out.println("Cantidad mayor a la disponible, pero puede comprar:"+prod.getCantidad());
                    setSaldoCaja(getSaldoCaja()+prod.getCantidad()*prod.getPrecioUnidad());
                    
                    
                    stock.get(tipo).get(pos-1).setCantidad(0);
                    stock.get(tipo).get(pos-1).setDisponibilidad(false);

                }else{
                    setSaldoCaja(getSaldoCaja()+cantP*prod.getPrecioUnidad());
                    stock.get(tipo).get(pos-1).setCantidad(prod.getCantidad()-cantP);
                    prod.setCantidad(cantPT);
                }
                           

            }else {
                System.out.println("El producto: "+prod.getIdentificador()+" "+prod.getDescripcion()+" no se encuentra disponible a la venta");
            }
        }catch(InputMismatchException error){
           System.out.println("Error al ingresar informacion: "+error.toString());
       }
        
        return prod;
    }
    
    
    // Lista segun el Tipo para actualizar
    /**
     * 
     * @param op : Integer si op=1 Representa Envasado, si op=2 Representa Bebida y si op=3 Representa Limpieza
     * @return El tamaño del stock del tipo de producto 
     */
    private Integer listarTipoProducto(Integer op){
        Integer c=-1;
        char tipo='A';
        switch (op){
            case 1:
                for(Producto pro:stock.get(TipoProducto.Envasado)){
                    c++;
                    System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" dispo:"+pro.getDisponibilidad()+ " posStock:"+c);
                    tipo++;
                }
                break;
            case 2:
                 for(Producto pro:stock.get(TipoProducto.Bebida)){
                    c++;
                    System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" dispo:"+pro.getDisponibilidad()+ " posStock:"+c);
                    tipo++;
                    
                }
                break;
            default:
                for(Producto pro:stock.get(TipoProducto.Limpieza)){
                    c++;
                    System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" dispo:"+pro.getDisponibilidad()+ " posStock:"+c);
                    tipo++;
                }
        }
        return c;
    }
    
    // Se utiliza para agregar un Producto a una lista de Productos
    /**
     * 
     * @param tipo : TipoProducto (Envasado,Bebida,Limpieza)
     * @param p: ArrayList<Producto> agrega un producto si el precio es menor a uno ingresado
     * @param precio: Double precio ingresado para comparar
     * @return p 
     */
    private ArrayList<Producto> agregarArray(TipoProducto tipo,ArrayList<Producto> p,Double precio){
         for(Producto pro: stock.get(tipo)){
            if(pro.getPrecioUnidad()<precio){
                p.add(pro);
                
            }           
        }
        return p;
    }
    
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //          FUNCIONES DE INTERFACES
    
    
    // Creo la Tienda MNeo
    /**
     * 
     * @return t : Tienda creada
     */
    @Override
    public Tienda crearTienda() {
        Tienda t=null;        
        HashMap<TipoProducto,ArrayList<Producto>> sto=new HashMap();
        Integer capStock;
        Double saldo;
        
        sto.put(TipoProducto.Envasado, new ArrayList<>());
        sto.put(TipoProducto.Bebida,new ArrayList<>());
        sto.put(TipoProducto.Limpieza, new ArrayList<>());
        
        try{
            System.out.println("Bienvenido a la creacion de la Tienda: MNeo");
            System.out.println("-------------------------------------------");
            System.out.println("Ingrese la capacidad maxima de Stock como minimo 10");
            capStock=scan.nextInt();
            capStock=Validar.validarIntMenor(capStock, 10);

            System.out.println("Ingrese el saldo minimo de la tienda> 100,0");
            saldo=scan.nextDouble();
            saldo=Validar.validarDoubleMenor(saldo, 100.0);

            sto.put(TipoProducto.Envasado, new ArrayList<>());
            sto.put(TipoProducto.Bebida,new ArrayList<>());
            sto.put(TipoProducto.Limpieza, new ArrayList<>());

            t=new Tienda("MNeo", capStock, saldo, sto);

        }catch (InputMismatchException error){
            System.out.println("Error al ingresar informacion: "+error.toString());
        }

            return t;
    }
    
    //Productos creados para no cargar a mano y pone stock 150 
    @Override
    public void productosCreados(){
        if(getNumMaxStock()<30){
            setNumMaxStock(150);
        }
        
      
        stock.get(TipoProducto.Envasado).add(new Envasado("AB123", "Dulce de Batata", 3, 50.50, 40.25, true,15.5, TipoEnvase.Lata, false,true,LocalDate.parse("2023-12-21"),500));
        stock.get(TipoProducto.Envasado).add(new Envasado("AB321", "Aceitunas", 2, 10.0, 5.0, true,10.5, TipoEnvase.Vidrio, false,true,LocalDate.parse("2023-11-15"),100));
        stock.get(TipoProducto.Envasado).add(new Envasado("AB024", "Papas fritas", 3, 10.0, 5.0, true,20.0, TipoEnvase.Plastico, false,false,LocalDate.now(),0));
        stock.get(TipoProducto.Envasado).add(new Envasado("AB008", "Azucar", 1, 5.80, 5.0, false,12.5, TipoEnvase.Plastico, true,false,LocalDate.now(),0));
        stock.get(TipoProducto.Envasado).add(new Envasado("AB888", "Arroz", 6, 48.0, 45.0, true,18.45, TipoEnvase.Plastico, false,true,LocalDate.parse("2024-02-18"),150));
        
        
        stock.get(TipoProducto.Bebida).add(new Bebida("AC245","Coca Cola", 8, 17.0, 10.0, true,10.5, false, 0, false));
        stock.get(TipoProducto.Bebida).add(new Bebida("AC456", "Gancia", 3, 20.5, 18.5, true,8.0, true, 30, true));
        stock.get(TipoProducto.Bebida).add(new Bebida("AC888", "Fernet", 5, 30.5, 25.0, true,12.0, true, 40, true));
        stock.get(TipoProducto.Bebida).add(new Bebida("AC852", "Pepsi", 2, 6.0, 3.85, true,14.5, false, 0, false));
        
       
        stock.get(TipoProducto.Limpieza).add(new Limpieza("AZ147", "Limpia Pisos", 5, 10.5,8.0, true,15.75, TipoAplicacion.Pisos));
        stock.get(TipoProducto.Limpieza).add(new Limpieza("AZ357", "Campera", 1, 150.0, 100.0, false, 50.5,TipoAplicacion.Ropa));
        stock.get(TipoProducto.Limpieza).add(new Limpieza("AZ478", "Sarten", 2, 70.0, 60.0, true, 20.0,TipoAplicacion.Cocina));
        stock.get(TipoProducto.Limpieza).add(new Limpieza("AZ159", "Jabon", 6, 30.5, 30.0, true,30.5, TipoAplicacion.Multiuso));
        stock.get(TipoProducto.Limpieza).add(new Limpieza("AZ458", "Camisa", 3, 68.0, 60.0, true,30.85, TipoAplicacion.Ropa));
    }
    
    //Realizacion de compra de un Producto
    @Override
    public void compraPro() {
      
        Integer cant,op;
        Producto pro=new Producto();
        
        try{
            cant=sumarProductos();
            if(cant<getNumMaxStock()){
                System.out.println("Ingreso Operacion Compra");
                System.out.println("------------------------");
                System.out.println("Ingrese el tipo de producto que desea comprar\n1_Emvasado\n2_Bebida\n3_Limpieza");
                op=scan.nextInt();
                op=Validar.validarIntRango(op, 1, 3);
                pro=pro.agregarProducto(op);
                if(pro.getCantidad()*pro.getCostoUnidad()<getSaldoCaja()){
                    realizarCompra(op,pro);

                }else{
                    System.out.println("El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja\n");
                }

            }else{
                System.out.println("El producto no podrá ser agregado a la tienda por alcazar el maximo stock habilitado");
            }
        
        }catch(InputMismatchException error){
           System.out.println("Error al ingresar informacion: "+error.toString());
       }
    }
    
    
   
    //Realizacion de una venta de Producto
    @Override
    public void ventaPro() {
        ArrayList<Producto> compra=new ArrayList<>();
        Producto pro=null;
        Integer op,cant;
        Double total;
        total=0.0;
        
        cant=0;op=-1;
        
        try{
            System.out.println("Ingreso Operacion Venta");
            System.out.println("-----------------------");
            System.out.println("Bienvenido cliente debe tener en cuenta lo siguiente:");
            System.out.println("Podra comprar como maximo 3 productos y hasta 10 unidades de cada uno\n");
            while(cant<3&&op!=4){
                System.out.println("Realizacion de la compra "+(cant+1));
                System.out.println("\n Ingrese que tipo de producto desea comprar\n1_Envasado\n2_Bebida\n3_Limpieza\n4_Salir");
                op=scan.nextInt();
                op=Validar.validarIntRango(op, 1, 4);
                switch(op){
                    case 1:
                        pro=operacionVenta(TipoProducto.Envasado);
                        if(pro!=null){
                            compra.add(pro);
                        }
                        break;
                        
                    case 2:
                        pro=operacionVenta(TipoProducto.Bebida);
                         if(pro!=null){                            
                            compra.add(pro);
                        }
                        break;
                    case 3:
                        pro=operacionVenta(TipoProducto.Limpieza);
                        if(pro!=null){                            
                            compra.add(pro);
                        }
                        break;
                }
                cant++;
            }

            if(compra.size()>0){
                System.out.println("El cliente realizo la siguiente compra");
                for(Producto p: compra){


                    System.out.println(p.getIdentificador()+" "+p.getDescripcion()+" "+p.getCantidad()+" * $"+p.getPrecioUnidad());

                    total+=p.getCantidad()*p.getPrecioUnidad();
                }
                System.out.println("Total Venta: $"+total);
            }else{
                System.out.println("No realizo compra");
            }

        }catch(InputMismatchException error){
           System.out.println("Error al ingresar informacion: "+error.toString());
       }
        
    }
    
    // Realizacion de actualizacion de un producto
    @Override
    public void actualizarPro(){
        Integer op,id,cant,dato,op1;
        Producto p;
        Double precio;
        
        System.out.println("Ingreso Operacion Actualizacion");
        System.out.println("-------------------------------");
        
       
        listarStock();
        System.out.println("Ingrese el tipo de producto que desea actualizar ");
        System.out.println("1_Envasado\n2_Bebida\n3_Limpieza\nIngrese una opcion entre [1,3]");
        op=scan.nextInt();
        op=Validar.validarIntRango(op, 1, 3);
        cant=listarTipoProducto(op);
        if(cant>=0){
            System.out.println("Ingrese una opcion entre [1,"+(cant+1)+"]");  
            id=scan.nextInt();
            id=Validar.validarIntRango(id, 1, cant+1);
            
            //op: Tipo producto  -  cant: cantidad del Tipo producto  - id: modificar
            switch(op){
                case 1:
                    p=stock.get(TipoProducto.Envasado).get(id-1);
                    break;
                case 2:
                    p=stock.get(TipoProducto.Bebida).get(id-1);
                    break;
                default:
                    p=stock.get(TipoProducto.Limpieza).get(id-1);

                System.out.println("Se actualizara el siguiente producto: "+p);
                System.out.println("1_Aumentar cantidad\n2_Cambiar precio de venta\n3_Cambio precio de costo\n4_Salir");
                System.out.println("Ingrese una opcion entre [1,4]");
                op1=scan.nextInt();
                op1=Validar.validarIntRango(op1, 1,4);
                switch(op1){
                    case 1:
                        System.out.println("Ingrese una cantidad para aumentar ");
                        dato=scan.nextInt();
                        dato=Validar.validarIntMenor(dato, 1);
                        if(sumarProductos()+dato<getNumMaxStock() && (getSaldoCaja()-dato*p.getCostoUnidad())>0){
                            
                            switch(op){
                                case 1:
                                    stock.get(TipoProducto.Envasado).get(id-1).setCantidad(p.getCantidad()+dato);
                                    stock.get(TipoProducto.Envasado).get(id-1).setDisponibilidad(true);
                                    setSaldoCaja(getSaldoCaja()-dato*p.getCostoUnidad());
                                    break;

                                case 2:
                                    stock.get(TipoProducto.Bebida).get(id-1).setCantidad(p.getCantidad()+dato);
                                    stock.get(TipoProducto.Bebida).get(id-1).setDisponibilidad(true);
                                    setSaldoCaja(getSaldoCaja()-dato*p.getCostoUnidad());
                                    break;
                                default:
                                    stock.get(TipoProducto.Limpieza).get(id-1).setCantidad(p.getCantidad()+dato);
                                    stock.get(TipoProducto.Limpieza).get(id-1).setDisponibilidad(true);
                                    setSaldoCaja(getSaldoCaja()-dato*p.getCostoUnidad());
                                    
                                    

                            }
                        }else {
                            System.out.println("No se cumple condiciones para actualizar cantidad");
                        }
                        break;

                    case 2:
                        System.out.println("Ingrese un precio > "+p.getCostoUnidad());
                        precio=scan.nextDouble();
                        precio=Validar.validarDoubleMenor(precio, p.getCostoUnidad());
                        if(precio>p.getCostoUnidad()){
                            switch(op){
                                case 1:
                                    stock.get(TipoProducto.Envasado).get(id-1).setPrecioUnidad(precio);
                                    break;                           
                                case 2:
                                    stock.get(TipoProducto.Bebida).get(id-1).setPrecioUnidad(precio);                                
                                    break;
                                default:
                                    stock.get(TipoProducto.Limpieza).get(id-1).setPrecioUnidad(precio);

                            }
                        }else {
                            System.out.println("No se cumplen condiciones para actualizar precioU");
                        }
                        break;

                    case 3:
                        System.out.println("Ingrese un precio entre [0,0 , "+p.getPrecioUnidad()+"]");
                        precio=scan.nextDouble();
                        precio=Validar.validarDoubleRango(precio, 0.0, p.getPrecioUnidad());
                        if(precio<p.getPrecioUnidad()){
                            switch(op){                      
                                case 1:
                                    stock.get(TipoProducto.Envasado).get(id-1).setCostoUnidad(precio);
                                    break;                           
                                case 2:
                                    stock.get(TipoProducto.Bebida).get(id-1).setCostoUnidad(precio);                                
                                    break;
                                default:
                                    stock.get(TipoProducto.Limpieza).get(id-1).setCostoUnidad(precio);
                            }
                        }else{
                            System.out.println("No se cumple condicion para que actualizar costoU");
                        }
                        break;
                }                    
            }
        }                     
    }
   
    // Lista todos los productos
    @Override
    public void listarStock(){
        char tipo='A';
       
        System.out.println("Lista de Productos en Stock");
        System.out.println("---------------------------------------");
        System.out.println("1_Productos Emvasados");
        for(Producto pro:stock.get(TipoProducto.Envasado)){
            
            System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" dispo:"+pro.getDisponibilidad());
            tipo++;
           
            
        }
        System.out.println("\n2_Productos de Bebidas");
        tipo='A';
      
        for(Producto pro:stock.get(TipoProducto.Bebida)){
            
            System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" dispo: "+pro.getDisponibilidad());
            tipo++;
           
        }
        System.out.println("\n3_Productos de Limpieza");
        tipo='A';
       
        for(Producto pro:stock.get(TipoProducto.Limpieza)){
            
            System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" dispo: "+pro.getDisponibilidad());
            tipo++;
          
        }
    }
    
    // Lista todos los productos Envasado que son Comestibles, NO importados y menor a un descuento ingresado ordenado por precio
    /**
     * 
     * @param descuento :Double descuento ingresado por teclado
     */
    @Override
    public void obtenerComestiblesConMenorDescuento(Double descuento) {
        Envasado em;
        ArrayList<Producto> p=new ArrayList<>();
        for(Producto pro: stock.get(TipoProducto.Envasado)){
            em=(Envasado) pro;
            
            if(em.getComestible() && em.getDescuento()<descuento && !em.getIsImportado()){
                p.add(em);
            }
        }
        if(p.size()>0){
            Collections.sort(p,Comparadores.PrecioUA);
            System.out.println("La lista de los productos comestibles no importados con descuento menor a "+descuento+"% son:");
            for(Producto pro: p){
                System.out.println(pro.getDescripcion().toUpperCase()+ " descuento:"+pro.getDescuento()+" precio:$"+pro.getPrecioUnidad());
                
            }
            System.out.println("");
        }else{
            System.out.println("No hay productos comestibles no importados con descuento menor a "+descuento+"%");
        }                
    }

    // Lista productos con precio menor a uno ingresado ordenados por descuento
    /**
     * 
     * @param precio :Double precio ingresado para comparar
     */
    @Override
    public void listarProductosConUtilidadesInferiores(Double precio) {
        ArrayList<Producto> precios=new ArrayList<>();
        
        precios=agregarArray(TipoProducto.Envasado,precios,precio);
        precios=agregarArray(TipoProducto.Bebida,precios,precio);
        precios=agregarArray(TipoProducto.Limpieza,precios,precio);
   
        if(precios.size()>0){
            System.out.println("Lista de productos menor a $"+precio+" ordenado por descuentos");
           Collections.sort(precios,Comparadores.DescuentoA); 
           for(Producto p:precios){
               System.out.println("Tipo: "+p.getClass().getSimpleName()+" - Cod:"+p.getIdentificador()+" - Nom:"+p.getDescripcion()+" - Cant:"+p.getCantidad()+" - Precio:$"+p.getPrecioUnidad()+" - descuento:"+p.getDescuento()+"%");
           }
        }else{
            System.out.println("No hay precios menores a $"+precio);
        }
        
       
    }
    
    //Lista todos los productos que no estan disponible a la venta
    @Override
    public void listarStockNoDisponible(){
        char tipo='A';
        int i=0;
        System.out.println("Lista de Productos no Disponibles en Stock");
        System.out.println("---------------------------------------");
        System.out.println("1_Productos Emvasados");
        for(Producto pro:stock.get(TipoProducto.Envasado)){
            if(!pro.getDisponibilidad()){
                System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" posStock:"+i);
                tipo++;
                
            }
             i++;                          
        }
        System.out.println("\n2_Productos de Bebidas");
        tipo='A';
        i=0;
        for(Producto pro:stock.get(TipoProducto.Bebida)){
            if(!pro.getDisponibilidad()){
                System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" posStock:"+i);
                tipo++;
                 
            }i++;
        }
        System.out.println("\n3_Productos de Limpieza");
        tipo='A';
        i=0;
         for(Producto pro:stock.get(TipoProducto.Limpieza)){
            if(!pro.getDisponibilidad()){
                System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad()+" posStock:"+i);
                tipo++;
                 
            }i++;
        }
    }
    
    //Lista todos los productos disponibles a la venta
    @Override
    public void listarStockDisponible(){
        char tipo='A';
        System.out.println("Lista de Productos Disponibles en Stock");
        System.out.println("---------------------------------------");
        System.out.println("1_Productos Emvasados");
        for(Producto pro:stock.get(TipoProducto.Envasado)){
            if(pro.getDisponibilidad()){
                System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad());
                tipo++;
            }
        }
        System.out.println("\n2_Productos de Bebidas");
        tipo='A';
         for(Producto pro:stock.get(TipoProducto.Bebida)){
            if(pro.getDisponibilidad()){
                System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad());
                tipo++;
            }
        }
        System.out.println("\n3_Productos de Limpieza");
        tipo='A';
         for(Producto pro:stock.get(TipoProducto.Limpieza)){
            if(pro.getDisponibilidad()){
                System.out.println("\t"+tipo+")"+pro.getIdentificador()+" "+pro.getDescripcion()+" "+pro.getCantidad()+" x $"+pro.getPrecioUnidad());
                tipo++;
            }
        }
    }
    
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------    
    
    //  Getters and Setters / toString

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumMaxStock() {
        return numMaxStock;
    }

    public void setNumMaxStock(Integer numMaxStock) {
        this.numMaxStock = numMaxStock;
    }

    public Double getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(Double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

    public HashMap<TipoProducto, ArrayList<Producto>> getStock() {
        return stock;
    }

    public void setStock(HashMap<TipoProducto, ArrayList<Producto>> stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Tienda{" + "nombre:" + nombre + ", numMaxStock:" + numMaxStock + ", saldoCaja:" + saldoCaja + ", stock:" + stock + '}';
    }
   
}
