/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import static Controller.JavaMongo.getConnection;
import static Controller.JavaMongo.getConnectionLocal;
import Model.Gamers;
import Model.Review;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;
/**
 *
 * @author LENOVO
 */
public class GamerDAO {
     public static Gamers getGamerByReview(Review review) {
        Gamers gamer = null;
        
        try (MongoClient mongoClient = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            // Create a filter to find the gamer document based on the ID_Gamer from the review
            Bson gamerFilter = Filters.eq("ID", review.getIdGamer());

            // Find the gamer document in the Gamers collection
            Document gamerDoc = gamersCollection.find(gamerFilter).first();

            if (gamerDoc != null) {
                // Extract gamer attributes from the document
                String id = gamerDoc.getString("ID");
                String name = gamerDoc.getString("Name");
                String gmail = gamerDoc.getString("Gmail");
                String password = gamerDoc.getString("Password");
                int role = gamerDoc.getInteger("Role");
                Double money = gamerDoc.getDouble("Money");
                String avatarLink = gamerDoc.getString("AvatarLink");
                String registrationDate = gamerDoc.getString("RegistrationDate");
                String dob = gamerDoc.getString("Date of Birth");

            

                // Create a Gamer object
                gamer = new Gamers(id, name, gmail, password, role, money, avatarLink, registrationDate, dob);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return gamer;
    }
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
                             doc.getString("RegistrationDate"),
                            doc.getString("Date of Birth")
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

           public static List<Gamers> searchGamersByName(String name) {
        MongoClientSettings settings = getConnectionLocal();
        List<Gamers> gamersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            // Search for gamers where the name matches the filter
            FindIterable<Document> docs = gamersCollection.find(Filters.regex("Name", name, "i"));
            MongoCursor<Document> cursor = docs.iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Gamers gamer = new Gamers(
                        doc.getString("ID"),
                        doc.getString("Name"),
                        doc.getString("Email"),
                        doc.getString("Password"),
                        doc.getInteger("Role"),
                        doc.getDouble("Money"),
                        doc.getString("AvatarLink"),

                        doc.getString("RegistrationDate"),

                        doc.getString("Date of Birth")
                );
                gamersList.add(gamer);
            }

            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return gamersList;
    }
 public static void CreateNewGamerAccount(String id, String name, String password, String email, int role, Double Money, String AvatarLink, String RegistrationDate,String DOB) {


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
                    .append("RegistrationDate", RegistrationDate)
                    .append("Date of Birth", DOB);
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

                    .append("RegistrationDate", RegistrationDate)

                    .append("Date of Birth", DOB);
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

                         gamerDoc.getString("RegistrationDate"),

                        gamerDoc.getString("Date of Birth")
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
                gamerDoc.getString("RegistrationDate"),

                        gamerDoc.getString("Date of Birth")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

   public static void deleteGamer(Gamers gamer) {
        MongoClientSettings settingsLocal = getConnectionLocal(); 

        try (MongoClient mongoClient = MongoClients.create(settingsLocal)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            MongoCollection<Document> transactionCollection = fpteamDB.getCollection("BankTransactions");
            MongoCollection<Document> billCollection = fpteamDB.getCollection("Buy");
            MongoCollection<Document> followCollection = fpteamDB.getCollection("Follow");
            MongoCollection<Document> reviewCollection = fpteamDB.getCollection("Reviews");
             MongoCollection<Document> userCollection = fpteamDB.getCollection("Users");
               MongoCollection<Document> reportCollection = fpteamDB.getCollection("Reports");
            Bson gamerfilter = Filters.eq("ID", gamer.getId());
            gamersCollection.deleteMany(gamerfilter);
            Bson  transactionfilter = Filters.eq("payerId", gamer.getId());
            transactionCollection.deleteMany(transactionfilter);
            Bson  billfilter = Filters.eq("ID_Gamer", gamer.getId());
            billCollection.deleteMany(billfilter);
            Bson  followfilter = Filters.eq("ID_Gamer", gamer.getId());
            followCollection.deleteMany(followfilter);
            Bson  reviewfilter = Filters.eq("ID_Gamer", gamer.getId());
            reviewCollection.deleteMany(reviewfilter);
            Bson  userfilter = Filters.eq("ID", gamer.getId());
            userCollection.deleteMany(userfilter);
             Bson  reportfilter = Filters.eq("UserID", gamer.getId());
            reportCollection.deleteMany(reportfilter);
            
             
        } catch (MongoException e) {
            e.printStackTrace();
        }
          MongoClientSettings settings = getConnection(); 

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
     
            MongoCollection<Document> billCollection = fpteamDB.getCollection("Buy");
      
             MongoCollection<Document> userCollection = fpteamDB.getCollection("Users");
            Bson gamerfilter = Filters.eq("ID", gamer.getId());
            gamersCollection.deleteMany(gamerfilter);
            Bson  billfilter = Filters.eq("ID_Gamer", gamer.getId());
            billCollection.deleteMany(billfilter);
            Bson  userfilter = Filters.eq("ID", gamer.getId());
            userCollection.deleteMany(userfilter);
            
             
        } catch (MongoException e) {
            e.printStackTrace();
        }
        
        
    }
        
   
    public static void updateDefaultGamer(String Name, String AvartaLink, String Id) {
        try ( MongoClient mongoClient = MongoClients.create(getConnection())) {

            // Truy cập cơ sở dữ liệu "FPT"
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Tạo một bộ lọc để truy vấn người dùng dựa trên ID
            BasicDBObject query = new BasicDBObject();
            query.put("ID", Id);

            // Tạo một document mới chứa thông tin  mới
            Document updateNameDoc = new Document("$set", new Document("Name", Name));
            Document updateAvtarDoc = new Document ("$set", new Document("AvatarLink", AvartaLink));

            // Truy cập bộ sưu tập "Users" và thực hiện cập nhật
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");
            usersCollection.updateOne(query, updateNameDoc);

             // Truy cập bộ sưu tập "Gamers" và thực hiện cập nhật
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            gamersCollection.updateOne(query, updateNameDoc);
            gamersCollection.updateOne(query, updateAvtarDoc);

            

        } catch (Exception e) {
            e.printStackTrace();
        }
         try ( MongoClient mongoClientLocal = MongoClients.create(getConnectionLocal())) {

            // Truy cập cơ sở dữ liệu "FPT"
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Tạo một bộ lọc để truy vấn người dùng dựa trên ID
            BasicDBObject query = new BasicDBObject();
            query.put("ID", Id);

            // Tạo một document mới chứa thông tin  mới
            Document updatePasswordDoc = new Document("$set", new Document("Name", Name));
            Document updateAvatarDoc = new Document ("$set", new Document("AvatarLink", AvartaLink));
            // Truy cập bộ sưu tập "Users"
            MongoCollection<Document> usersCollection = fpteamDBLocal.getCollection("Users");

            // Thực hiện update vào MongoDB trong collection "Users"
            usersCollection.updateOne(query, updatePasswordDoc);

            // Truy cập bộ sưu tập "Gamers"
            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");

            // Thực hiện update vào MongoDB trong collection "Gamers"
            gamersCollection.updateOne(query, updatePasswordDoc);
            gamersCollection.updateOne(query, updateAvatarDoc);

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    }

