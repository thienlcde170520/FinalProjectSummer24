/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Users implements Serializable{
    private String id;
    private String name;
    private String gmail;
    private String password;
    private int role;
    
    public Users(){}

    public Users(String id, String name, String gmail, String password, int role) {
        this.id = id;
        this.name = name;
        this.gmail = gmail;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", name=" + name + ", email=" + gmail + ", password=" + password + ", role=" + role + '}';
    }

    
    
}
