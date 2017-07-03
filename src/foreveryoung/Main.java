package foreveryoung;

import java.util.AbstractMap.SimpleEntry;
import java.util.Scanner;

public class Main {
    
    private static class ExcepcionBase extends Exception {

    }

    public static Scanner lector = new Scanner(System.in);

    public static int de10(int numero, int base) throws ExcepcionBase {

        int potencia = 1;
        int transformado = 0;

        while (numero > 0) {
            int digito = numero % base;

            if (digito >= 10) {
                throw new ExcepcionBase();
            }

            transformado += digito * potencia;
            potencia *= 10;
            numero = (numero - digito) / base;
        }

        return transformado;

    }

    public static void mostrarBases(int numero, int limiteInferior) {

        for (int i = 10; i < numero; i++) {

            try {

                System.out.println("Base: " + i + " numero: " + de10(numero, i));

            } catch (ExcepcionBase e) {

                System.out.println("Base: " + i + " error");

            }

        }

    }

    public static Integer busquedaBinaria(int numero, int limiteInferior, int izquierda, int derecha) {
        
        int medio   =   (izquierda + derecha) / 2;
        
        // A mayor base, menor número
        for (boolean error = true; error && izquierda < medio;) {
            
            error   =   false;
            
            try {
                // Busqueda binaria a traves del límite inferior
                int transformado = de10(numero, medio);
                
                if ( transformado >= limiteInferior ) {
                    
                    // Es mayor que el límite inferior, buscar hacia la derecha
                    Integer busqueda = busquedaBinaria(numero, limiteInferior, (int) Math.floor( (izquierda + derecha ) * 0.5), derecha);
                    
                    if ( busqueda == null ) {
                        // No se ha encontrado nada, buscar hacia la izquierda
                        return busquedaBinaria(numero, limiteInferior, izquierda, (int) Math.floor( ( izquierda + derecha ) * 0.5) );
                        
                    }
                    
                    return busqueda;
                    
                } else {
                    
                    // Es menor que el límite inferior, buscar hacia la izquierda (aqui no se puede considerar buscar hacia la derecha)
                    return busquedaBinaria(numero, limiteInferior, izquierda, (int) Math.ceil( (izquierda + derecha ) * 0.5 ) );
                    
                }

            } catch (ExcepcionBase e) {
                // Error al hacer cambio de base, apareció un digito mayor que 9
                // Reducir el intervalo hacia la derecha
                error  =   true;
                medio  =   medio - 1;

            }

        }
        
        if ( izquierda == medio ) {
            // Si el rango de busqueda es en un intervalo de rango 1, comparar el valor único dentro del intervalo
            try {
                // Verificar que sea mayor que el límite inferior
                if ( de10(numero, izquierda) >= limiteInferior ) {
                    
                    // Es un posible resultado
                    return izquierda;
                    
                }
                
            } catch (ExcepcionBase e) {
                
            }
            
        }
        
        return null;

    }

    public static void main(String[] args) {

        String Line = lector.nextLine();
        String split[] = Line.split(" ");

        // Se recibe un número 'y' y un límite inferior
        int y = (int) Integer.parseInt(split[0]);
        int limiteInferior = (int) Integer.parseInt(split[1]);
        
        System.out.println(busquedaBinaria(y, limiteInferior, 10, y));

    }

}
