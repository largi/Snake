/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

/**
 *
 * @author marco
 */

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Snake extends JFrame{

    public Snake(){

        add(new Board());
        
        setResizable(false);
        pack();
        
        
        setLocationRelativeTo(null);
        setTitle("Snake");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args){
        
        EventQueue.invokeLater(new Runnable(){
            
            @Override
            public void run(){
                
                JFrame play = new Snake();
                play.setVisible(true);                
            }
        });
    }
}
