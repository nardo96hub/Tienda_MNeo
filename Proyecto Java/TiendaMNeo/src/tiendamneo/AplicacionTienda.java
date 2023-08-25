

package tiendamneo;


import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import tiendamneo.entidades.Producto;
import tiendamneo.entidades.Tienda;
import tiendamneo.entidades.Validar;

/**
 * 
 * @author Luciano Nardelli
 * 
 * Proyecto realizado con Java 8
 * 
 */

public class AplicacionTienda {

 
    public static void main(String[] args) {
   
       Scanner scan=new Scanner(System.in);
       
       Tienda MNeo=new Tienda();
       Integer op;
       Double valor;
       String resp="n";

       try{

       MNeo=MNeo.crearTienda();
       
       
       System.out.println("Si desea que se cree una lista de productos ya definidos elija 1 (Pone stockMax 150 si stock<30) , caso contrario cualquier numero");
       op=scan.nextInt();
       if(op==1){
           MNeo.productosCreados();
       }
       
       do{
           System.out.println("Menu de opciones de la Tienda "+MNeo.getNombre());
           System.out.println("-----------------------------");
           System.out.println("1_Comprar Producto\n2_Vender producto\n3_Actualizar producto\n4_Ver lista de Stock\n5_Ver Productos con descuesto menor a ingresado\n6_Ver productos menor a un precio\n7_Ver productos no disponibles a la venta\n8_Ver productos Disponible a venta\n9_Salir");
           System.out.println("Ingrese una opcion entre [1,9]");
           op=scan.nextInt();
           op=Validar.validarIntRango(op, 1, 9);
           switch(op){
               case 1:
                   MNeo.compraPro();
                   break;
                case 2:
                   MNeo.ventaPro();
                   break;
                case 3:
                    MNeo.actualizarPro();
                    break;
                case 4:
                   MNeo.listarStock();
                   break;
                case 5:
                    System.out.println("Ingrese el descuento que desea ver productos menor al ingresado entre [0,0 , 99,9]");
                    valor=scan.nextDouble();
                    valor=Validar.validarDoubleRango(valor, 0.0, 99.9);
                    MNeo.obtenerComestiblesConMenorDescuento(valor);
                   break;
                case 6:
                   System.out.println("Ingrese el precio para ver productos menor a este. Valor > 0,0");
                    valor=scan.nextDouble();
                    valor=Validar.validarDoubleMenor(valor, 0.0);
                    MNeo.listarProductosConUtilidadesInferiores(valor); 
                   break;
                case 7:
                    MNeo.listarStockNoDisponible();
                   break;
                case 8:
                    MNeo.listarStockDisponible();
                    break;
                default:
                    resp="n";
                
           }
           
           if(op!=9){
                System.out.println("Desea realizar otra operacion?. Oprima s/S para continuar");
                resp=scan.next();
            }
         
       }while((resp.length()==1 && resp.contains("S")) || (resp.length()==1 && resp.contains("s")));
        
        
     
       }catch(InputMismatchException error){
           System.out.println("Error al ingresar informacion: "+error.toString());
       }
     
     
       System.out.println("Programa desarrollado por Nardelli Luciano.\nFin del Programa. Adios");
       
    
        
    }

}
