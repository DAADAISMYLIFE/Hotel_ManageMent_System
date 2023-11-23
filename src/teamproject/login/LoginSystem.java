/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.login;

import java.awt.Container;
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
        ArrayList<String> readContext = helper.readDBFile(0);
        for(String temp : readContext){
            User tempUser = new User (temp.split(";")[0],temp.split(";")[1],temp.split(";")[2],Boolean.parseBoolean(temp.split(";")[3]));
            userDB.add(tempUser);
        }
    }
    
    
    public void login(){
        setTitle("로그인");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container swingContext = getContentPane();
        swingContext.setLayout(null);
        
        JLabel idLabel =new JLabel("ID : ");
        JLabel pswdLabel =new JLabel("Password : ");
        JTextField txtID = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton logBtn =new JButton("로그인");
        
        idLabel.setBounds(30, 30,100,30);
        txtID.setBounds(120, 30,100,30);
        pswdLabel.setBounds(30, 60,100,30);
        txtPass.setBounds(120, 60,100,30);
        logBtn.setBounds(90, 100,100,30);
        
        swingContext.add(idLabel);
        swingContext.add(pswdLabel);
        swingContext.add(txtID);
        swingContext.add(txtPass);
        swingContext.add(logBtn);

        
        logBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
            User loginTryingUser;

            //아이디 입력
            String ID = txtID.getText();
            //비밀번호 입력
            String password = txtPass.getText();
            //비교를 위한 객체 생성
           loginTryingUser = new User(ID,password); 
            //비교
            for(User temp : userDB){
                if(loginTryingUser.equals(temp)){
                    loginUser = temp;
                    JOptionPane.showMessageDialog(null, "안녕하세요 " + loginUser.getName() + "님");
                    dispose(); 
                    try {
                        loginReport.addReport("login", ID+ ";LOGIN");
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
        setVisible(true);
        setSize(280,180);
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
