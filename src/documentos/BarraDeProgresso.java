/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentos;

import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 *
 * @author Victor
 */
public class BarraDeProgresso extends JDialog{
    
    JProgressBar barra = new JProgressBar();
    public BarraDeProgresso(String titulo, boolean mode){
        setTitle(titulo);
        UIManager.put("nimbusOrange", new Color(0, 204, 0));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);        
        //barra.setForeground(Color.white);
        barra.setBounds(40, 40, 500, 30);
        barra.setStringPainted(true);
        barra.setValue(2);
        add(barra);
        @SuppressWarnings("SleepWhileInLoop")
        Thread hi = new Thread(() -> {
            while(barra.getValue()<100){
                try {
                    sleep(100);
                    barra.setValue(barra.getValue()+2);
                    if(barra.getValue() == 100){
                        barra.setValue(0);    
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(BarraDeProgresso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        hi.start();
        this.dispose();
    }
    public void executa(){
        UIManager.put("nimbusOrange", new Color(0, 204, 0));
        configurarJanela();     
    }
    public void fechar(){
        this.dispose();
        setVisible(false);
    }
    
    public void configurarJanela(){
        UIManager.put("nimbusOrange", new Color(0, 204, 0));      
        setLayout(null);        
        setSize(600,150);        
        setLocationRelativeTo(null);        
        setVisible(true);        
        setResizable(false);       
        //setModal(true);
                
        
    }   
 
}
