/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.food;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author kgb69
 */
public class addFoodFrame {
    
    public void add(){
         JFrame frmF = new JFrame();
         
         frmF.getContentPane().setLayout(null);
         
         JButton btn1 = new JButton("추가");
         JTextField add_name = new JTextField(10);
         
         
        btn1.setBounds(182, 120, 172, 30);
        
        frmF.getContentPane().add(btn1);
      
              
        frmF.setSize(500,500);
        frmF.setLocationRelativeTo(null);
        frmF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         btn1.addActionListener(event -> {          
            
        });
        
        frmF.setVisible(true);
    }
}
