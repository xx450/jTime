/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xx450
 */
public class Chrono {
    
    private long span;
    private long started;

    public Chrono() {
        this.span = 0;
        this.started = -1;
    }
    
    public void start() {
        normalize();
        if (!isStarted()) {
            started = System.currentTimeMillis();
        }
    }
    
    public void stop() {
        normalize();
        if (isStarted()) {
            started = -1;
        }
    }
    
    public boolean isStarted() {
        return started >= 0;
    }
    
    public long getTotal() {
        normalize();
        return this.span;
    }

    public void normalize() {
        if (started >= 0) { 
            span += System.currentTimeMillis() - started;
            started = System.currentTimeMillis();
        }
    }
    
    @Override
    public String toString() {
        normalize();
        
        long total = span / 1000;
        long hh = total / 60 / 60;
        total -= hh * 60 * 60;
        long mm = total / 60;
        total -= mm * 60;
        long ss = total % 60;
        
        return "[" + hh + "h" + mm + "m" + ss + "s]";
    }
        
}
