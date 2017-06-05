package foreveryoung;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws Exception {
        
        int tests = 0;
        int testsExitosos = 0;
        
        File carpeta = new File("./tests");
        File listaArchivos[] = carpeta.listFiles();

        for ( File archivo : listaArchivos ) {
            
            String nombre = archivo.getName();
            
            if ( archivo.isFile() && nombre.substring( nombre.length() - 3, nombre.length()).equals(".in") ) {
                
                System.out.println("TEST: " + nombre);
                
                tests = tests + 1;
                
                RandomAccessFile lectorTest = new RandomAccessFile( archivo.getAbsolutePath(), "r");
                RandomAccessFile lectorRes = new RandomAccessFile( archivo.getAbsolutePath().substring( 0 , archivo.getAbsolutePath().length() - 3) + ".ans", "r");
                
                String linea = lectorTest.readLine();
                String split[] = linea.split(" ");
                
                long resultado = Long.parseLong(lectorRes.readLine());
                
                lectorTest.close();
                lectorRes.close();
                
                System.out.println(linea);

                // Se recibe un número 'y' y un límite inferior
                long y = (long) Long.parseLong(split[0]);
                long limiteInferior = (long) Long.parseLong(split[1]);

                TestBusqueda busqueda = new TestBusqueda(y, limiteInferior);
                
                ExecutorService ejecutor = Executors.newSingleThreadExecutor();
                Future<Long> future = ejecutor.submit(busqueda);

                try {
                    
                    long resultadoAlgoritmo = future.get(2, TimeUnit.SECONDS);

                    if ( resultadoAlgoritmo == resultado ) {
                        
                        testsExitosos = testsExitosos + 1;
                        
                        System.out.println( resultadoAlgoritmo + " = " + resultado + " (ACEPTADO)");
                        
                    } else {
                        
                        System.out.println( resultadoAlgoritmo + " != " + resultado + " (RECHAZADO)");
                        
                    }

                } catch ( TimeoutException e ) {

                    future.cancel(true);
                    System.out.println("Tiempo de ejecucion superado (RECHAZADO).");

                } catch ( Exception e ) {
                    
                    System.out.println("ERROR " + e + " != " + resultado + " (RECHAZADO)");
                    
                }
                
                ejecutor.shutdownNow();
                System.out.println("-------------------------------------------------");
                
            }
            
        }
        
        System.out.println("Proceso completado, " + testsExitosos + " de " + tests + " exitosos.");
        System.exit(0);

    }

}
