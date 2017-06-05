/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foreveryoung;

/**
 *
 * @author matia
 */
public class ExcepcionBase extends Exception {

    private long numero;

    public ExcepcionBase(long numero) {

        this.numero = numero;

    }

    public long getNumero() {

        return this.numero;

    }

}
