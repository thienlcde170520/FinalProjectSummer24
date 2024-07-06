
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Game;
import Model.Gamers;
import Model.Publishers;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author LENOVO
 */
public class PublisherDAO {
     public static void publishGame(String gameId, String publisherId) {
        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Publish");

            Document gamePublishDoc = new Document()
                    .append("ID_Game", gameId)
                    .append("ID_Game_Publisher", publisherId)
                    .append("ID_Admin", "")
                    .append("isPublishable", false);

            gamesCollection.insertOne(gamePublishDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public static Integer getNumberOfGameSoughtByPublisher(String publisherId) {
        MongoClientSettings settings = getConnectionLocal();
        int numberOfGamesBought = 0;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> billCollection = fpteamDB.getCollection("Buy");
                MongoCollection<Document> publishCollection = fpteamDB.getCollection("Publish");

                // Get all game IDs published by the publisher
                List<String> gameIds = new ArrayList<>();
                for (Document gameDoc : publishCollection.find(eq("ID_Game_Publisher", publisherId))) {
                    gameIds.add(gameDoc.getString("ID_Game"));
                }

                // Sum the number of purchases for these games
                if (!gameIds.isEmpty()) {
                    List<Bson> pipeline = List.of(
                        match(in("ID_Game", gameIds)),
                        group(null, sum("totalPurchases", 1))
                    );

                    List<Document> results = billCollection.aggregate(pipeline).into(new ArrayList<>());
                    if (!results.isEmpty()) {
                        numberOfGamesBought = results.get(0).getInteger("totalPurchases");
                    }
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return numberOfGamesBought;
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
      public static Publishers getPublisherByPublisherId(String publisherID) {
        MongoClientSettings settings = getConnectionLocal();
        Publishers publisher = null;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");

                Document doc = gamePublishersCollection.find(Filters.eq("ID", publisherID)).first();

                if (doc != null) {
                    publisher = new Publishers(
                            doc.getString("ID"),
                            doc.getString("Name"),

                            doc.getString("Email"),
                            doc.getString("Password"),

                            doc.getString("Bank_account"),
                            doc.getDouble("Profit"),
                            doc.getString("Description"),
                            doc.getString("AvatarLink"),
                            doc.getDouble("Money"),
                            doc.getInteger("Role", 2),
                            doc.getString("RegistrationDate")
                    );
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return publisher;
    }
      public static Publishers getPublisherByName(String name) {
        MongoClientSettings settings = getConnectionLocal();
        Publishers publisher = null;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
                MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");

                Document doc = gamePublishersCollection.find(Filters.eq("Name", name)).first();

                if (doc != null) {
                    publisher = new Publishers(
                            doc.getString("ID"),
                            doc.getString("Name"),

                            doc.getString("Email"),
                            doc.getString("Password"),

                            doc.getString("Bank_account"),
                            doc.getDouble("Profit"),
                            doc.getString("Description"),
                            doc.getString("AvatarLink"),
                            doc.getDouble("Money"),
                            doc.getInteger("Role", 2),
                            doc.getString("RegistrationDate")
                    );
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
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
         MongoClientSettings settingsLocal = getConnectionLocal();
        try (MongoClient mongoClientLocal = MongoClients.create(settingsLocal)) {

            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Access the "Users" collection
            MongoCollection<Document> usersCollection = fpteamDBLocal.getCollection("Users");
            // Access the "Gamers" collection
            MongoCollection<Document> gamePublishersCollection = fpteamDBLocal.getCollection("GamePublishers");

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
        MongoClientSettings settings = getConnectionLocal();

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

   public static void deletePublisher(Publishers publisher) {
       
      MongoClientSettings settingsLocal = getConnectionLocal(); 

        try (MongoClient mongoClient = MongoClients.create(settingsLocal)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> publisherCollection = fpteamDB.getCollection("GamePublishers");
        
            ArrayList<Game> games = GameDAO.getGamesByPublisherName(publisher.getName());
            for (Game game : games){
                   GameDAO.deleteGame(game.getId());
                   
            }
           
             MongoCollection<Document> userCollection = fpteamDB.getCollection("Users");
            Bson gamerfilter = Filters.eq("ID", publisher.getId());
            publisherCollection.deleteMany(gamerfilter);        
            Bson  userfilter = Filters.eq("ID", publisher.getId());
            userCollection.deleteMany(userfilter);
            
             
        } catch (MongoException e) {
            e.printStackTrace();
        }
          MongoClientSettings settings = getConnection(); 

         try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> publisherCollection = fpteamDB.getCollection("GamePublishers");
        
            ArrayList<Game> games = GameDAO.getGamesByPublisherName(publisher.getName());
            for (Game game : games){
                   GameDAO.deleteGame(game.getId());
                   
            }
           
             MongoCollection<Document> userCollection = fpteamDB.getCollection("Users");
            Bson gamerfilter = Filters.eq("ID", publisher.getId());
            publisherCollection.deleteMany(gamerfilter);        
            Bson  userfilter = Filters.eq("ID", publisher.getId());
            userCollection.deleteMany(userfilter);
            
             
        } catch (MongoException e) {
            e.printStackTrace();
        }
        
        
    
}
}


  
