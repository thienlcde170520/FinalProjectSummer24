/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Gamers;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import java.util.ArrayList;
import org.bson.conversions.Bson;
/**
 *
 * @author LENOVO
 */
public class GamerDAO {
    
     public static boolean hasGamerBoughtGame(String gamerId, String gameId) {
        MongoClientSettings settings = getConnectionLocal();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Buy" collection
            MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");

            // Construct the filter to check if the game has been bought by the gamer
            Bson filter = and(eq("ID_Gamer", gamerId), eq("ID_Game", gameId));

            // Check if any document matches the filter
            Document result = buyCollection.find(filter).first();

            // If result is not null, it means the game has been bought by the gamer
            return result != null;

        } catch (MongoException e) {
            e.printStackTrace();
            return false; // Handle exceptions as per your application's error handling strategy
        }
    }
         public static ArrayList<Gamers> getAllGamers() {

        MongoClientSettings settings = getConnectionLocal();

        ArrayList<Gamers> gamersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {

                // Access the "FPT" database
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

                // Access the "Gamers" collection
                MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
                MongoCursor<Document> cursor = gamersCollection.find().iterator();

                // Query the collection and iterate over the cursor to print each document
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    Gamers gamers = new Gamers(
                            doc.getString("ID"),
                            doc.getString("Name"),
                            doc.getString("Email"),
                            doc.getString("Password"),
                            doc.getInteger("Role"),
                            doc.getDouble("Money"),
                            doc.getString("AvatarLink"), // Get AvatarLink from the document
                            doc.getString("RegistrationDate")
                    );
                    gamersList.add(gamers);
                }
                cursor.close();
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return gamersList;
    }
 public static void CreateNewGamerAccount(String id, String name, String password, String email, int role, Double Money, String AvatarLink, String RegistrationDate) {

        MongoClientSettings settings = getConnection();
        try (MongoClient mongoClient = MongoClients.create(settings)) {

            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Gamers" collection
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");

            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            Document user = new Document("ID", id)
                    .append("Name", name)
                    .append("Password", password)
                    .append("Email", email)
                    .append("Role", role);
            usersCollection.insertOne(user);

            Document gamer = new Document("ID", id)
                    .append("Name", name)
                    .append("Password", password)
                    .append("Email", email)
                    .append("Money", Money)
                    .append("AvatarLink", AvatarLink)
                    .append("Role", role)
                    .append("RegistrationDate", RegistrationDate);
            gamersCollection.insertOne(gamer);
        } catch (MongoException e) {
            e.printStackTrace();
        }
         MongoClientSettings settingsLocal = getConnectionLocal();
        try (MongoClient mongoClientLocal = MongoClients.create(settingsLocal)) {

            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Access the "Gamers" collection
            MongoCollection<Document> usersCollection = fpteamDBLocal.getCollection("Users");

            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");

            Document user = new Document("ID", id)
                    .append("Name", name)
                    .append("Password", password)
                    .append("Email", email)
                    .append("Role", role);
            usersCollection.insertOne(user);

            Document gamer = new Document("ID", id)
                    .append("Name", name)
                    .append("Password", password)
                    .append("Email", email)
                    .append("Money", Money)
                    .append("AvatarLink", AvatarLink)
                    .append("Role", role)
                    .append("RegistrationDate", RegistrationDate);
            gamersCollection.insertOne(gamer);
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
         public static Gamers getGamerByEmail(String email) {
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            BasicDBObject query = new BasicDBObject();
            query.put("Email", email);

            Document gamerDoc = gamersCollection.find(query).first();

            if (gamerDoc != null) {
                return new Gamers(
                        gamerDoc.getString("ID"),
                        gamerDoc.getString("Name"),
                        gamerDoc.getString("Email"),
                        gamerDoc.getString("Password"),
                        gamerDoc.getInteger("Role"),
                        gamerDoc.getDouble("Money"),
                        gamerDoc.getString("AvatarLink"),
                        gamerDoc.getString("RegistrationDate")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }
           public static Gamers getGamerByGamerId(String Id) {
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            BasicDBObject query = new BasicDBObject();
            query.put("ID", Id);

            Document gamerDoc = gamersCollection.find(query).first();

            if (gamerDoc != null) {
                return new Gamers(
                        gamerDoc.getString("ID"),
                        gamerDoc.getString("Name"),
                        gamerDoc.getString("Email"),
                        gamerDoc.getString("Password"),
                        gamerDoc.getInteger("Role"),
                        gamerDoc.getDouble("Money"),
                        gamerDoc.getString("AvatarLink"),
                        gamerDoc.getString("RegistrationDate")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }
        
        
    }

