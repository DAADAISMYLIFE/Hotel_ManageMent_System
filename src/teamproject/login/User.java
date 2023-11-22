/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamproject.login;

/**
 *
 * @author qkekd
 */
public class User {
    private String ID;
    private String password;
    private String name;
    private boolean isManager;

    //생성자
    public User(String ID, String password, String name, boolean isManager) {
        this.ID = ID;
        this.password = password;
        this.name = name;
        this.isManager = isManager;
    }
    //기본은 매니저가 아닌 알바 유저
    public User(String ID, String password, String name) {
        this(ID,password,name,false);
    }
    
    //임시로 만든 값
    public User(String ID, String password) {
        this(ID,password,"",false);
    }
    
    public boolean equals(Object object){
        User user = (User) object;
        if(user.getID().equals(this.getID()) && user.getPassword().equals(this.getPassword())){
            return true;
        }
        else
            return false;
    }
    
    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }
}
