/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import teamproject.login.LoginSystem;
import teamproject.login.User;

/**
 *
 * @author kgb69
 */
public class LoginFrame extends JFrame{
   
    
    private SystemHelper helper;
    public ArrayList<User> userDB;
    
    public void makeForm(){
       
        JPanel panel = new JPanel();
        JLabel label =new JLabel("ID : ");
        JLabel pswrd =new JLabel("Password : ");
        JTextField txtID = new JTextField(10);
        JPasswordField txtPass = new JPasswordField(10);
        JButton logBtn =new JButton("로그인");
        
        panel.add(label);
        panel.add(txtID);
        panel.add(pswrd);
        panel.add(txtPass);
        panel.add(logBtn);
        
        logBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
            User loginTryingUser;

            //아이디 입력
            System.out.print("ID : ");
            String ID = txtID.getText();
            //비밀번호 입력admin
            System.out.print("PASS : ");
            String password = txtPass.getText();

            //비교를 위한 객체 생성
           loginTryingUser = new User(ID,password);
            
            //비교
            for(User temp : userDB){
                if(loginTryingUser.equals(temp)){
                    
                 JOptionPane.showMessageDialog(null, "로그인 성공");
                 LoginSystem.loginUser = temp;
                 dispose();
                 break;
                }
            }
             if(LoginSystem.loginUser == null){
                System.out.println("정보를 찾을 수 없습니다.");
                JOptionPane.showMessageDialog(null, "로그인 실패");
             }   
        }
    });
       
        add(panel);
        setVisible(true);
        setSize(600,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}