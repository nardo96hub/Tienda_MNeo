package tiendamneo.comparator;

import java.util.Comparator;
import tiendamneo.entidades.Producto;

public class Comparadores {
    
    public static Comparator<Producto> PrecioUA=new Comparator<Producto>(){
    @Override
      public int compare(Producto a, Producto b) {
        return a.getPrecioUnidad().compareTo(b.getPrecioUnidad());
      }
    };
         
    public static Comparator<Producto> DescuentoA=new Comparator<Producto>(){
      @Override
      public int compare(Producto a, Producto b) {
        return a.getDescuento().compareTo(b.getDescuento());
      }  
    };
}
