/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package callable;

import java.util.concurrent.Callable;
;

/**
 *
 * @author anderson
 */
public class conta implements Callable<Double>{
    private int i_0, i_n;
    Double pi;
    public conta(int i_0, int i_n){
        this.i_n = i_n;
        this.i_0 = i_0;
    }
    @Override
    public Double call() throws Exception {
        Double pi=1.0;
        for(int i=i_0; i<i_n; i++)
            pi*= (2.*i)*(2.*i)/
                    (2.*i-1)/(2.*i+1);
        return pi;
    }
}
