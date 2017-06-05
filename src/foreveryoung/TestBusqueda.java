/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foreveryoung;

import java.util.concurrent.Callable;

/**
 *
 * @author matia
 */
public class TestBusqueda implements Callable<Long> {
    
    private final long numero;
    private final long limiteInferior;
    
    public TestBusqueda(long numero, long limiteInferior) throws InterruptedException {
        
        this.numero = numero;
        this.limiteInferior = limiteInferior;
        
    }
    
    @Override
    public Long call() throws Exception {
        return this.busquedaBinaria(10, this.numero);
        
    }
    
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

    private Long busquedaBinaria(long izquierda, long derecha) {
        
        if (this.limiteInferior <= 10) {

            return this.numero;

        }

        long suma = izquierda + derecha;
        long medio = suma / 2;

        if (izquierda == medio) {
            // Si el rango de busqueda es en un intervalo de rango 1, comparar el valor único dentro del intervalo
            try {
                // Verificar que sea mayor que el límite inferior
                if (de10(this.numero, izquierda) >= this.limiteInferior) {

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

            transformado = de10(this.numero, medio);

        } catch (ExcepcionBase e) {
            // Si hay un dígito mayor que 9, se considera como 9.
            transformado = e.getNumero();
            
        }
        
        if (transformado >= this.limiteInferior) {

            // Es mayor que el límite inferior, buscar hacia la derecha
            Long busqueda = busquedaBinaria(medio, derecha);

            if ( busqueda == null ) {
                
                return busquedaBinaria(izquierda, medio + (suma % 2));
                
            }
            
            return busqueda;

        } else {

            // Es menor que el límite inferior, buscar hacia la izquierda (aqui no se puede considerar buscar hacia la derecha)
            return busquedaBinaria(izquierda, medio + (suma % 2));

        }

    }
    
}
