/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import teamproject.SystemHelper;
import teamproject.report.ReportSystem;
import javax.swing.*;

/**
 *
 * @author qkekd
 */
public class LoginSystem extends JFrame {
    private User loginUser;
    private SystemHelper helper;
    private ArrayList<User> userDB;     //유저(일반 직원, 관리자) 정보
    private ReportSystem loginReport;
    
    public LoginSystem(ReportSystem reportSys){
        loginReport = reportSys;
    }
    
    public void init()throws IOException{
        loginUser = null;
        userDB = new ArrayList<>();
        helper = new SystemHelper();    
        helper.createDBFile(0, "login");
       //관리자 추가
       //User temp = new User("admin","Admin","관리자",true);
       //userDB.add(temp);
       //일반 직원 추가
       //temp = new User("alba","Alba","직원");
        ArrayList<String> readContext = helper.readDBFile(0);
        for(String temp : readContext){
            User tempUser = new User (temp.split(";")[0],temp.split(";")[1],temp.split(";")[2],Boolean.parseBoolean(temp.split(";")[3]));
            userDB.add(tempUser);
        }
    }
    
    
    public void login(){
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
          
            String ID = txtID.getText();
            //비밀번호 입력admin
            String password = txtPass.getText();

            //비교를 위한 객체 생성
           loginTryingUser = new User(ID,password); 
            //비교
            for(User temp : userDB){
                if(loginTryingUser.equals(temp)){
                    loginUser = temp;
                    dispose();
                    JOptionPane.showMessageDialog(null, "안녕하세요 " + loginUser.getName() + "님");
                    try {
                        loginReport.addReport("login", ID+ ";login");
                    } catch (IOException ex) {
                        Logger.getLogger(LoginSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
            if(loginUser == null)
                JOptionPane.showMessageDialog(null, "로그인 실패");
        }
    }); 
        add(panel);
        setVisible(true);
        setSize(600,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    
    public User runLoginSystem() throws IOException{
        init();
        login();
        while(loginUser==null){
            System.out.print("");
        }
        return loginUser;
    }
}
