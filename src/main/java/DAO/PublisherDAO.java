/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Publishers;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class PublisherDAO {
     public static void publishGame(String gameId, String publisherId) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Publish");

            Document gamePublishDoc = new Document()
                    .append("ID_Game", gameId)
                    .append("ID_Game_Publisher", publisherId)
                    .append("ID_Admin", "admin_1")
                    .append("isPublishable", true);

            gamesCollection.insertOne(gamePublishDoc);
            System.out.println("Game ID: " + gameId + " published by publisher ID: " + publisherId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public static Publishers getPublisherByGameId(String gameId) {
        MongoClientSettings settings = getConnectionLocal();
        Publishers publisher = null;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Step 1: Retrieve the publisher ID from the "Publish" collection
            MongoCollection<Document> publishCollection = fpteamDB.getCollection("Publish");
            Bson filter = Filters.eq("ID_Game", gameId);
            Document publishDoc = publishCollection.find(filter).first();

            if (publishDoc != null) {
                String publisherId = publishDoc.getString("ID_Game_Publisher");

                // Step 2: Retrieve the publisher details from the "GamePublishers" collection
                MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");
                Bson publisherFilter = Filters.eq("ID", publisherId);
                Document publisherDoc = gamePublishersCollection.find(publisherFilter).first();

                if (publisherDoc != null) {
                    publisher = new Publishers(
                            publisherDoc.getString("ID"),
                            publisherDoc.getString("Name"),
                            publisherDoc.getString("Password"),
                            publisherDoc.getString("Email"),
                            publisherDoc.getString("Bank_account"),
                            publisherDoc.getDouble("Profit"),
                            publisherDoc.getString("Description"),
                            publisherDoc.getString("AvatarLink"),
                            publisherDoc.getDouble("Money"),
                            publisherDoc.getInteger("Role", 0),
                            publisherDoc.getString("RegistrationDate")
                    );
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return publisher;
    }
 public static ArrayList<Publishers> getAllPublishers() {

        MongoClientSettings settings = getConnectionLocal();

        ArrayList<Publishers> publishersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {

                // Access the "FPT" database
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

                // Access the "Gamers" collection
                MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");
                MongoCursor<Document> cursor = gamePublishersCollection.find().iterator();

                // Query the collection and iterate over the cursor to print each document
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    Publishers publishers = new Publishers(
                            doc.getString("ID"),
                            doc.getString("Name"),
                            doc.getString("Password"),
                            doc.getString("Email"),
                            doc.getString("Bank_account"),
                            doc.getDouble("Profit"),
                            doc.getString("Description"),
                            doc.getString("AvatarLink"),
                            doc.getDouble("Money"),
                            doc.getInteger("Role", 0),
                            doc.getString("RegistrationDate")
                    );
                    publishersList.add(publishers);
                }
                cursor.close();
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return publishersList;
    }
   public static void CreateNewPublisgherAccount(String id, String name, String password, String email, String bank_account,
            Double profit, String Description, String AvatarLink,
            Double Money, int role, String RegistrationDate) {
        MongoClientSettings settings = getConnection();
        try (MongoClient mongoClient = MongoClients.create(settings)) {

            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Access the "Users" collection
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");
            // Access the "Gamers" collection
            MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");

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
                    .append("Bank_account", bank_account)
                    .append("Profit", profit)
                    .append("Description", Description)
                    .append("AvatarLink", AvatarLink)
                    .append("Money", Money)
                    .append("Role", role)
                    .append("RegistrationDate", RegistrationDate);
            gamePublishersCollection.insertOne(gamer);
        } catch (MongoException e) {
            e.printStackTrace();
        }

    } public static Publishers getPublisherByEmail(String email) {
        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");

            // Query for finding the publisher by email
            BasicDBObject query = new BasicDBObject("Email", email);

            // Retrieve the document for the publisher
            Document publisherDoc = publishersCollection.find(query).first();

            if (publisherDoc != null) {
                System.out.println("Retrieved publisher document: " + publisherDoc.toJson()); // Debugging output
                return new Publishers(
                        publisherDoc.getString("ID"),
                        publisherDoc.getString("Name"),
                        publisherDoc.getString("Email"),
                        publisherDoc.getString("Password"),
                        publisherDoc.getString("Bank_account"),
                        publisherDoc.getDouble("Profit"),
                        publisherDoc.getString("Description"),
                        publisherDoc.getString("AvatarLink"),
                        publisherDoc.getDouble("Money"),
                        publisherDoc.getInteger("Role"),
                        publisherDoc.getString("RegistrationDate")
                );
            } else {
                System.out.println("Publisher not found with email: " + email); // Debugging output
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null; // Return null if no publisher found with the given email
    }


  
}
