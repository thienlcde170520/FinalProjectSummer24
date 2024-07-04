/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnectionLocal;
import Model.Admin;
import Model.Gamers;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author ASUS
 */
public class AdminDAO {
    public static Admin getAdminByEmail(String email) {
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> adminCollection = fpteamDB.getCollection("Admins");

            BasicDBObject query = new BasicDBObject();
            query.put("Gmail", email);

            Document adminDoc = adminCollection.find(query).first();

            if (adminDoc != null) {
                return new Admin(
                        adminDoc.getString("ID"),
                        adminDoc.getString("Name"),
                        adminDoc.getString("Email"),
                        adminDoc.getString("Password"),
                        adminDoc.getInteger("Role")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }
    
   
    
}
