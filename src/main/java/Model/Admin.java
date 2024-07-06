/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


public class Admin extends Users{
    public Admin(String id, String name, String gmail, String password, int role){
        super(id, name, gmail, password, role);
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString()+ '}';
    }
    

