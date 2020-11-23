/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aproximacaopithreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author anderson
 */
public class AproximacaoPIThreads {

    public void contaCallable(int loop, int slice){
        double pi=1.;
        List<callable.conta> tarefas = new ArrayList();
        
        for(int i=1; i<loop; i++)
            tarefas.add(new callable.conta((i-1)*slice + 1, i*slice));

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Double> completionService = new ExecutorCompletionService<>(threadPool);

        //executa as tarefas
        for (callable.conta tarefa : tarefas) {
            completionService.submit(tarefa);
        }

        //aguarda e imprime o retorno de cada uma
        for (callable.conta tarefa : tarefas) {
            try {
                pi*=completionService.take().get();
            } catch (InterruptedException | ExecutionException ex) {
            }
        }
        System.out.println("slide: " + slice + "  loop: " + loop + "->" + 
                (pi = 2.*pi) + ": " + (Math.PI - pi));
        threadPool.shutdown();
    }
    
    public void contaRunnable(int loop, int slice) throws InterruptedException{
        double pi=1.;
        List<runnable.conta> tarefas = new ArrayList();
        
        for(int i=1; i<loop; i++)
            tarefas.add(new runnable.conta((i-1)*slice + 1, i*slice));

        //executa as tarefas
        for (runnable.conta tarefa : tarefas) {
            (new Thread(tarefa)).start();
        }
        for (runnable.conta tarefa : tarefas) {
            (new Thread(tarefa)).join();
            pi *= tarefa.getPi();
        }
        
        System.out.println("slice: " + slice + "  loop: " + loop + "->" + 
                (pi = 2.*pi) + ": " + (Math.PI - pi));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        for(int loop=10; loop<100000000; loop*=10)    
            for(int slice=10; slice<1000000; slice*=10)    
                new AproximacaoPIThreads().contaCallable(loop, slice);

        for(int loop=10; loop<100000000; loop*=10)    
            for(int slice=10; slice<1000000; slice*=10)    
                new AproximacaoPIThreads().contaRunnable(loop, slice);
    }
    
}
