package tiendamneo.entidades;

import java.util.Scanner;

public  class  Validar {
    static Scanner scan=new Scanner(System.in);
   
    public static Integer validarIntRango(Integer val,Integer a,Integer b){
       while(val<a || val>b){
           System.out.println("Error.Ingrese una opcion entre ["+a+","+b+"] ");
           val=scan.nextInt();
       }
       return val;
    }
    public static Integer validarIntMenor(Integer val,Integer a){
        while(val<a){
            System.out.println("Error.Ingrese un valor >"+a+": ");
            val=scan.nextInt();
        }
        return val;
    }
    public static Double validarDoubleMenor(Double val,Double a){
         while(val<a){
            System.out.println("Error.Ingrese un valor >"+a+": ");
            val=scan.nextDouble();
        }
        return val;
    }
    public static Double validarDoubleRango(Double val,Double a,Double b){
       while(val<a || val>b){
           System.out.println("Error.Ingrese una opcion entre ["+a+","+b+"] ");
           val=scan.nextDouble();
       }
       return val;
    }
}
