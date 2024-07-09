/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import Controller.JavaMongo;
import static DAO.GamerDAO.getAllGamers;
import Model.Gamers;
import Model.Users;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class CheckValid extends JavaMongo {

    public static Users userValid(String email, String password) throws Exception {

        ArrayList<Users> users = getAllUser();

        for (Users u : users) {
            if (u.getGmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
    
    public static Users CheckEmail(String email){
        ArrayList<Users> users = getAllUser();

            for (Users u : users) {
                if (u.getGmail().equals(email)) {
                    return u;
                }
            }
            return null;
    }
    
    public static Gamers CheckEmailGamers(String email){
        ArrayList<Gamers> gamers = getAllGamers();

            for (Gamers g : gamers) {
                if (g.getGmail().equals(email)) {
                    return g;
                }
            }
            return null;
    }
   
    public static Users CheckEmailVSId(String email, String id){
        ArrayList<Users> users = getAllUser();

            for (Users u : users) {
                if (u.getGmail().equals(email) && u.getId().equals(id)) {
                    return u;
                }
            }
            return null;
    }
}
