package foreveryoung;

import java.util.AbstractMap.SimpleEntry;

public class Base {
    
    private static class DigitoInvalidoEnBase extends Exception { }
    
    static public int deBase10(int numero, int base) throws DigitoInvalidoEnBase {
        
        int res = 0;
        int multiplo = 1;
        
        // Inicializar una variable con la division, la multiplicación es más rápida
        double baseInversa = 1.0 / base;
        
        while ( numero > 0 ) {
            
            // El resto de una division siempre es mayor o igual que cero
            int resto = Math.floorMod(numero, base);
            
            // Verificar que el resto se encuentre en un rango válido
            if ( resto > 9 ) {
                
                // Digito inválido, el resto debe estar en rango [0, 9]
                throw new DigitoInvalidoEnBase();
                
            }
            
            res += resto * multiplo;
            multiplo *= 10;
            
            numero -= resto;
            numero *= baseInversa;
            
        }
        
        return res;
        
    }
    
    static public int aBase10(int numero, int base) {
        
        int res = 0;
        int exponente = (int) Math.log10(numero);
        
        int multiplo = (int) Math.pow(10, exponente);
        int multiploBase = (int) Math.pow(base, exponente);
        
        // Iniciar una variable con la división, la multiplicación es más rápida
        double baseInversa = 1.0 / base;
        
        while ( numero > 0 ) {
            
            int digito = (int) Math.floor( numero / multiplo );
            
            numero -= digito * multiplo;
            res += digito * multiploBase;
            
            multiploBase = (int) Math.floor( multiploBase * baseInversa );
            multiplo *= 0.1;
            exponente -= 1;
            
        }
        
        return res;
        
    }
    
    static public int buscarBase(int y, int lowerBound) {
        
        // Par base-valor con valor menor
        SimpleEntry<Integer, Integer> par = null;
        
        // Base 1 y 0 son inválidas, comenzar desde base 2        
        for (int base = 2; base <= y; base++) {
            
            try {
                
                // Si se reciben datos válidos desde 'deBase10', el algoritmo procede normalmente
                int valor = deBase10(y, base);
                
                // Verificar que el valor de 'y' en esta base sea mayor que el límite inferior
                if ( valor >= lowerBound ) {

                    // Si no hay par actual, o el valor en esta base es menor que el valor en el par actual, establecer un nuevo par
                    if ( par == null || valor < par.getValue() ) {

                        par = new SimpleEntry<Integer, Integer>(base, valor);

                    }

                }
                
            } catch ( DigitoInvalidoEnBase e ) {
                
                // Si el algoritmo recibe un dato inválido, proceder con la siguiente base
                continue;
                
            }
            
        }
        
        // Si existe par, retornar la base
        if ( par != null ) {
            
            return par.getKey();
            
        }
        
        return -1;
        
    }
    
}
