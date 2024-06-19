package Controller;

import Model.BankTransactions;
import Model.Game;
import Model.Gamers;
import Model.Publishers;
import Model.Genre;
import Model.Review;
import Model.Users;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.text.SimpleDateFormat;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.bson.conversions.Bson;

public class JavaMongo {

    private static final String CONNECTION_STRING = "mongodb+srv://viet81918:conchode239@cluster0.hzr2fsy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

    public static MongoClientSettings getConnection() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();
        return settings;
    }

    public static void main(String[] args) {
        ArrayList<Gamers> gamersList = getAllGamers();
        System.out.println("List of Gamers:");
        for (Gamers gamer : gamersList) {
            System.out.println(gamer);
        }
     
    }

    public static void addGame(Game game) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            Document gameDoc = new Document()
                    .append("ID", game.getId())
                    .append("Name", game.getName())
                    .append("Price", game.getPrice())
                    .append("Publish_day", game.getPublishDay())
                    .append("Number_of_buyers", game.getNumberOfBuyers())
                    .append("LinkTrailer", game.getLinkTrailer())
                    .append("AvatarLink", game.getAvatarLink())
                    .append("GameLink", game.getGameLink())
                    .append("Description", game.getDescription())
                    .append("Minimum_CPU", game.getMinimumCPU())
                    .append("Minimum_RAM", game.getMinimumRAM())
                    .append("Minimum_GPU", game.getMinimumGPU())
                    .append("Maximum_CPU", game.getMaximumCPU())
                    .append("Maximum_RAM", game.getMaximumRAM())
                    .append("Maximum_GPU", game.getMaximumGPU());

            gamesCollection.insertOne(gameDoc);
            System.out.println("Game added successfully to MongoDB.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addGenreToGame(String gameId, String genreType) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> gameGenresCollection = fpteamDB.getCollection("Game_Has_Genre");

            Document gameGenreDoc = new Document()
                    .append("ID_Game", gameId)
                    .append("Type_of_genres", genreType);

            gameGenresCollection.insertOne(gameGenreDoc);
            System.out.println("Genre '" + genreType + "' added to game ID: " + gameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Game> getAllGames() {
        MongoClientSettings settings = getConnection();
        ArrayList<Game> gamesList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
            MongoCursor<Document> cursor = gamesCollection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();

                // Parse price
                Double price = doc.getDouble("Price");

                // Parse number of buyers
                Integer numberOfBuyers = doc.getInteger("Number_of_buyers");

                // Parse nested fields for configuration
                Game game = new Game(
                        doc.getString("ID"),
                        doc.getString("Name"),
                        price,
                        doc.getString("Publish_day"),
                        numberOfBuyers,
                        doc.getString("LinkTrailer"),
                        doc.getString("AvatarLink"),
                        doc.getString("GameLink"),
                        doc.getString("Description"),
                        doc.getString("Minimum_CPU"),
                        doc.getString("Minimum_RAM"),
                        doc.getString("Minimum_GPU"),
                        doc.getString("Maximum_CPU"),
                        doc.getString("Maximum_RAM"),
                        doc.getString("Maximum_GPU")
                );
                gamesList.add(game);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return gamesList;
    }

    public static ArrayList<Genre> getAllGenres() {
        ArrayList<Genre> genresList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> Collection = fpteamDB.getCollection("Genres");

            MongoCursor<Document> cursor = Collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Genre genre = new Genre(
                        doc.getString("Type_of_Genre"),
                        doc.getString("Description")
                );
                genresList.add(genre);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return genresList;
    }

    public static ArrayList<Review> getReviewByGame(Game game) {
        ArrayList<Review> reviews = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            MongoCursor<Document> cursor = collection.find(new Document("ID_Game", game.getId())).iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Review review = new Review(
                        doc.getString("ID_Gamer"),
                        doc.getString("ID_Game"),
                        doc.getDouble("Rating"),
                        doc.getString("Description")
                );
                reviews.add(review);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public static double getAverageRatingByGame(Game game) {
        double averageRating = 0.0;

        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            List<Document> pipeline = Arrays.asList(
                    new Document("$match", new Document("ID_Game", game.getId())),
                    new Document("$group", new Document("_id", "$ID_Game")
                            .append("averageRating", new Document("$avg", "$Rating")))
            );

            AggregateIterable<Document> result = collection.aggregate(pipeline);
            if (result.first() != null) {
                averageRating = result.first().getDouble("averageRating");
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return averageRating;
    }

    public static ArrayList<Gamers> getAllGamers() {

        MongoClientSettings settings = getConnection();

        ArrayList<Gamers> gamersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Access the "FPTeam" database
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

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
                            doc.getInteger("Money", 0),
                            doc.getString("AvatarLink") // Get AvatarLink from the document

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

    /*publisher*/
    public static ArrayList<Publishers> getAllPublishers() {
        

        MongoClientSettings settings = getConnection();

        ArrayList<Publishers> publishersList = new ArrayList<>();

          try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Access the "FPTeam" database
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

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
                            doc.getInteger("Profit",0),                                                  
                            doc.getString("Description"),
                            doc.getString("AvatarLink"),
                            doc.getInteger("Money"),
                            doc.getInteger("Role",0), 
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
    /*----------------------------*/
   
    public static ArrayList<Users> getAllUser() {

        MongoClientSettings settings = getConnection();

        ArrayList<Users> usersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Access the "FPTeam" database
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

                // Access the "Gamers" collection
                MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");
                MongoCursor<Document> cursor = usersCollection.find().iterator();

                // Query the collection and iterate over the cursor to print each document
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    Users users = new Users(
                            doc.getString("ID"),
                            doc.getString("Name"),
                            doc.getString("Email"),
                            doc.getString("Password"),
                            doc.getInteger("Role")
                    );
                    usersList.add(users);
                }
                cursor.close();
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }

        return usersList;
    }

    public static Users getUserById(String id) {
        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            // Truy cập cơ sở dữ liệu "FPTeam"
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

            // Truy cập bộ sưu tập "Users"
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");

            // Tạo một bộ lọc để truy vấn người dùng dựa trên ID
            BasicDBObject query = new BasicDBObject();
            query.put("ID", id);

            // Thực hiện truy vấn và lấy ra kết quả
            Document userDoc = usersCollection.find(query).first();

            if (userDoc != null) {
                // Tạo một đối tượng Users từ thông tin trong document
                Users user = new Users(
                        userDoc.getString("ID"),
                        userDoc.getString("Name"),
                        userDoc.getString("Email"),
                        userDoc.getString("Password"),
                        userDoc.getInteger("Role")
                );

                // Trả về đối tượng Users đã tạo
                return user;
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        // Trả về null nếu không tìm thấy người dùng với ID đã cung cấp
        return null;
    }

    
    public static void CreateNewGamerAccount(String name, String password, String email, int role, int Money, String AvatarLink){
        MongoClientSettings settings = getConnection();
        try (MongoClient mongoClient = MongoClients.create(settings)) {

            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

            // Access the "Gamers" collection
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");

            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            Document user = new Document("Name", name)
                    .append("Password", password)
                    .append("Email", email)
                    .append("Role", role);
            usersCollection.insertOne(user);

            Document gamer = new Document("Name", name)
                    .append("Password", password)
                    .append("Email", email)
                    .append("Role", role);
            gamersCollection.insertOne(gamer);
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
    
    
    /*tao moi publisher*/
    public static void CreateNewPublisgherAccount(String name, String password, String email, int role, int Money, String AvatarLink){
        MongoClientSettings settings = getConnection();
        try(MongoClient mongoClient = MongoClients.create(settings)){
            
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

                // Access the "Users" collection
        MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");
        // Access the "Gamers" collection
        MongoCollection<Document> gamePublishersCollection = fpteamDB.getCollection("GamePublishers");
        
        Document user = new Document("Name", name)
                        .append("Password", password)
                        .append("Email", email)
                        .append("Role", role);
        usersCollection.insertOne(user);
        
        Document gamer = new Document("Name", name)
                        .append("Password", password)
                        .append("Email", email)
                        .append("Money", Money)
                        .append("AvatarLink", AvatarLink)
                        .append("Role", role);
        gamePublishersCollection.insertOne(gamer);
        }catch (MongoException e) {
        e.printStackTrace();
         }
    }
    /*---------------------*/
    
    public Users getUserByEmail(String email) {
        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");

            BasicDBObject query = new BasicDBObject();
            query.put("Email", email);

            Document userDoc = usersCollection.find(query).first();

            if (userDoc != null) {
                return new Users(
                        userDoc.getString("ID"),
                        userDoc.getString("Name"),
                        userDoc.getString("Email"),
                        userDoc.getString("Password"),
                        userDoc.getInteger("Role")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static Gamers getGamerByEmail(String email){
        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
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
                        gamerDoc.getInteger("Money"),
                        gamerDoc.getString("AvatarLink")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static void updatePassword(String email, String newPassword) {
        try (com.mongodb.client.MongoClient mongoClient = MongoClients.create(getConnection())) {
            // Truy cập cơ sở dữ liệu "FPTeam"
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

            // Tạo một bộ lọc để truy vấn người dùng dựa trên Email
            Document filter = new Document("Email", email);

            // Tạo một document mới chứa thông tin mật khẩu mới
            Document updatePasswordDoc = new Document("$set", new Document("Password", newPassword));

            // Truy cập bộ sưu tập "Users"
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");

            // Thực hiện update vào MongoDB trong collection "Users"
            usersCollection.updateOne(filter, updatePasswordDoc);

            // Truy cập bộ sưu tập "Gamers"
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            // Thực hiện update vào MongoDB trong collection "Gamers"
            gamersCollection.updateOne(filter, updatePasswordDoc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      public static void insertTransaction(String partnerCode, String orderId, String requestId, String amount,
                                         String orderInfo, String orderType, String transId, String payType, String signature, String payerId) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> transactionsCollection = fpteamDB.getCollection("BankTransactions");
             MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");

            Document transactionDoc = new Document()
                    .append("partnerCode", partnerCode)
                    .append("orderId", orderId)
                    .append("requestId", requestId)
                    .append("amount", amount)
                    .append("orderInfo", orderInfo)
                    .append("orderType", orderType)
                    .append("transId", transId)
                    .append("payType", payType)
                    .append("signature", signature)
                    .append("payerId", payerId)
                    .append("createdAt", new Date());

            transactionsCollection.insertOne(transactionDoc);
            System.out.println("Transaction inserted successfully into MongoDB.");
              // Retrieve the corresponding gamer from the Gamers collection
            Bson filter = Filters.eq("ID", payerId);
            Document gamerDoc = gamersCollection.find(filter).first();

            if (gamerDoc != null) {
                // Update the money field in the Gamers collection
                int currentMoney = gamerDoc.getInteger("Money");
                int transactionAmount = Integer.parseInt(amount);
                int updatedMoney = currentMoney + transactionAmount;

                Bson updateOperation = Updates.set("Money", updatedMoney);
                gamersCollection.updateOne(filter, updateOperation);

                System.out.println("Gamer's money updated successfully.");
            } else {
                System.out.println("Gamer not found.");
            }
        } catch (Exception e) {
           throw new Exception("Error inserting transaction into MongoDB: " + e.getMessage());
        }
        }
     
        public static ArrayList<BankTransactions> getTransactionHistoryByPayerId(String payerId) {
        ArrayList<BankTransactions> transactionsList = new ArrayList<>();

        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> transactionsCollection = fpteamDB.getCollection("BankTransactions");

            BasicDBObject query = new BasicDBObject("payerId", payerId);

            MongoCursor<Document> cursor = transactionsCollection.find(query).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                BankTransactions transaction = new BankTransactions(
                        doc.getString("partnerCode"),
                        doc.getString("orderId"),
                        doc.getString("requestId"),
                        doc.getString("amount"),
                        doc.getString("orderInfo"),
                        doc.getString("orderType"),
                        doc.getString("transId"),
                        doc.getString("payType"),
                        doc.getString("signature"),
                        doc.getString("payerId"),
                        doc.getDate("createdAt").toInstant()
                );
                transactionsList.add(transaction);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return transactionsList;
    }

    }



