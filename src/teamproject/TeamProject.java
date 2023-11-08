/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package teamproject;
import java.io.IOException;

/**
 *
 * @author qkekd
 */
public class TeamProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        NewJFrame jf = new NewJFrame();
        jf.pakeMain();
        IntegrateManager IM = new IntegrateManager();
        IM.initIM();
        IM.runIM();
        
    }
    
}
