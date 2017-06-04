package foreveryoung;

import java.util.AbstractMap.SimpleEntry;
import java.util.Scanner;

public class Main {

    private static class ExcepcionBase extends Exception {

        private long numero;

        public ExcepcionBase(long numero) {

            this.numero = numero;

        }

        public long getNumero() {

            return this.numero;

        }

    }

    public static Scanner lector = new Scanner(System.in);

    public static long de10(long numero, long base) throws ExcepcionBase {

        long potencia = 1;
        long transformado = 0;
        boolean excepcion = false;

        while (numero > 0) {
            long digito = numero % base;

            if (digito > 9) {

                excepcion = true;
                digito = 9;

            }

            transformado += digito * potencia;
            potencia *= 10;
            numero = (numero - digito) / base;
        }

        if (excepcion) {
            
            throw new ExcepcionBase(transformado);

        }

        return transformado;

    }

    public static Long busquedaBinaria(long numero, long limiteInferior, long izquierda, long derecha) {
        
        if (limiteInferior <= 10) {

            return numero;

        }

        long suma = izquierda + derecha;
        long medio = suma / 2;

        if (izquierda == medio) {
            // Si el rango de busqueda es en un intervalo de rango 1, comparar el valor único dentro del intervalo
            try {
                // Verificar que sea mayor que el límite inferior
                if (de10(numero, izquierda) >= limiteInferior) {

                    // Es un posible resultado
                    return izquierda;

                }

            } catch (ExcepcionBase e) {
                
                // Si el número contiene un dígito mayor que 9, no se puede retornar
            }
            
            return null;
            
        }

        // Busqueda binaria a traves del límite inferior
        long transformado;

        try {

            transformado = de10(numero, medio);

        } catch (ExcepcionBase e) {
            // Si hay un dígito mayor que 9, se considera como 9.
            transformado = e.getNumero();
            
        }
        
        if (transformado >= limiteInferior) {

            // Es mayor que el límite inferior, buscar hacia la derecha
            Long busqueda = busquedaBinaria(numero, limiteInferior, medio, derecha);

            if (busqueda == null) {
                
                // No se ha encontrado nada, buscar hacia la izquierda
                return busquedaBinaria(numero, limiteInferior, izquierda, medio + (suma % 2));

            }

            return busqueda;

        } else {

            // Es menor que el límite inferior, buscar hacia la izquierda (aqui no se puede considerar buscar hacia la derecha)
            return busquedaBinaria(numero, limiteInferior, izquierda, medio + (suma % 2));

        }

    }

    public static void main(String[] args) throws Exception {

        String Line = lector.nextLine();
        String split[] = Line.split(" ");

        // Se recibe un número 'y' y un límite inferior
        long y = (long) Long.parseLong(split[0]);
        long limiteInferior = (long) Long.parseLong(split[1]);

        System.out.println(busquedaBinaria(y, limiteInferior, 10, y));

    }

}
