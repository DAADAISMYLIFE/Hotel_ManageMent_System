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
    

public class LoginFrame extends JFrame{
    
    private SystemHelper helper;
    public ArrayList<User> userDB;
    
       
    public void makeForm(){
         userDB = new ArrayList<>();
         User temp = new User("admin","Admin","이승환",true);
       userDB.add(temp);
       
       //일반 직원 추가
       temp = new User("alba","Alba","강순우");
       userDB.add(temp);
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
//        frm.setTitle("로그인");
//        frm.setSize(330,300);
//        
//        frm.setLocationRelativeTo(null);
//        
//        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frm.getContentPane().setLayout(null);
//        
//        JButton B_login = new JButton("로그인");
//        JTextField txt = new JTextField();
//        
//        B_login.setBounds(100,200, 120,30);
//        txt.setBounds(10, 10, 100, 30);
//        
//        frm.getContentPane().add(B_login);
//        frm.getContentPane().add(txt);

//        frm.setVisible(true);
  
    }
}
    

