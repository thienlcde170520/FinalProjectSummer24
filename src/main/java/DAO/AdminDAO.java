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

    public static ArrayList<Gamers> getAllGamers(){
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Gamers> gamers = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> buyCollection = fpteamDB.getCollection("Gamers");
            try (MongoCursor<Document> cursor = buyCollection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document billDoc = cursor.next();
                    String id = billDoc.getString("ID");
                    String name = billDoc.getString("Name");
                    String password = "";
                    Double money = Double.valueOf(billDoc.getInteger("Money"));
                    String avatar = billDoc.getString("AvatarLink");
                    String RegistrationDate = billDoc.getString("RegistrationDate");
                    String Date_of_Birth = billDoc.getString("Date of Birth");
                    String email = billDoc.getString("Email");
                    int role = billDoc.getInteger("Role");
                    Gamers gamer = new Gamers();
                    gamer.setId(id);
                    gamer.setName(name);
                    gamer.setPassword(password);
                    gamer.setGmail(email);
                    gamer.setRole(role);
                    gamer.setMoney(money);
                    gamer.setAvatarLink(avatar);
                    gamer.setRegistrationDate(RegistrationDate);
                    gamer.setDOB(Date_of_Birth);
                    gamers.add(gamer);
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return gamers;
    }
}

   
