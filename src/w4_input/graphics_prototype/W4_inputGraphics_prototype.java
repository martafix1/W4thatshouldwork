/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        


  import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane; 


/**
 *
 * @author m
 */
public class W4_inputGraphics_prototype extends JFrame {

    public static void main(String[] args) {
        W4_inputGraphics_prototype app = new W4_inputGraphics_prototype();
       app.init();
        //System.exit(0);

    }

    public void init(){
    Render rnd = new Render();    
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout( new BorderLayout());
   // this.setSize(30,30);
    this.setVisible(true);
    this.add(rnd);
    this.pack();
    this.setLocation(300,300);
    this.setTitle("Čtverešek kterim se hibe");
    this.setResizable(false);
    this.setBackground(Color.yellow);
    
    rnd.start(); 
    
    }

}
