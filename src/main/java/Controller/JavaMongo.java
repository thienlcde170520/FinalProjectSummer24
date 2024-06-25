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
        ArrayList<Users> usersList = getAllUser();
        System.out.println("List of users:");
        for (Users use : usersList) {
            System.out.println(use);
        }
        
         // Replace with a valid email from your database
        String testEmail = "gamochoRock12@gmail.com";
        
        Publishers p = getPublisherByEmail(testEmail);
        
        if (p != null) {
            System.out.println("pub Details:");
            System.out.println("ID: " + p.getId());
            System.out.println("Name: " + p.getName());
            System.out.println("Email: " + p.getGmail());
            System.out.println("Password: " + p.getPassword());
            System.out.println("Role: " + p.getRole());
            
        } else {
            System.out.println("No gamer found with email: " + testEmail);
        }
        
        
    }

    public static void addGame(Game game) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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

    public static void addGenreToGame(String gameId, String genreType) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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
                            doc.getInteger("Money", 0),
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

    /*publisher*/
    public static ArrayList<Publishers> getAllPublishers() {

        MongoClientSettings settings = getConnection();

        ArrayList<Publishers> publishersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Access the "FPTeam" database
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
                            doc.getInteger("Profit", 0),
                            doc.getString("Description"),
                            doc.getString("AvatarLink"),
                            doc.getInteger("Money"),
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

    /*----------------------------*/

    public static ArrayList<Users> getAllUser() {

        MongoClientSettings settings = getConnection();

        ArrayList<Users> usersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Access the "FPTeam" database
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

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
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

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


    
    public static void CreateNewGamerAccount(String id,String name, String password, String email, int role, int Money, String AvatarLink, String RegistrationDate){

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
                    .append("RegistrationDate",RegistrationDate);
            gamersCollection.insertOne(gamer);
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }

    /*tao moi publisher*/

    public static void CreateNewPublisgherAccount(String id, String name, String password, String email,String bank_account,
            int profit,String Description, String AvatarLink,
            int Money, int role,String RegistrationDate){
        MongoClientSettings settings = getConnection();
        try(MongoClient mongoClient = MongoClients.create(settings)){
            
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
                        .append("Bank_account",bank_account)
                        .append("Profit", profit)
                        .append("Description", Description)                        
                        .append("AvatarLink", AvatarLink)
                        .append("Money", Money)                      
                        .append("Role", role)
                        .append("RegistrationDate", RegistrationDate);
        gamePublishersCollection.insertOne(gamer);
        }catch (MongoException e) {
        e.printStackTrace();
         }

    }

    /*---------------------*/

    
    public Users getUserByEmail(String email) {
        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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

    public static Gamers getGamerByEmail(String email) {
        MongoClientSettings settings = getConnection();

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
                        gamerDoc.getInteger("Money"),
                        gamerDoc.getString("AvatarLink"),
                        gamerDoc.getString("RegistrationDate")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*get publisher by email*/
    public static Publishers getPublisherByEmail(String email){

        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");

//GamePublishers
            BasicDBObject query = new BasicDBObject();
            query.put("Email", email);

            Document publisherDoc = publishersCollection.find(query).first();

            if (publisherDoc != null) {
                return new Publishers(
                        publisherDoc.getString("ID"),
                        publisherDoc.getString("Name"),
                        publisherDoc.getString("Email"),
                        publisherDoc.getString("Password"),
                        publisherDoc.getString("Bank_account"),
                        publisherDoc.getInteger("Profit"),
                        publisherDoc.getString("Description"),
                        publisherDoc.getString("AvatarLink"),
                        publisherDoc.getInteger("Money"),
                        publisherDoc.getInteger("Role"),                                          
                        publisherDoc.getString("RegistrationDate")

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
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

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
            
            // Truy cập bộ sưu tập "GamePublishers"
            MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");

            // Thực hiện update vào MongoDB trong collection "GamePublishers"
            publishersCollection.updateOne(filter, updatePasswordDoc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      public static void insertTransaction(String partnerCode, String orderId, String requestId, String amount,
                                         String orderInfo, String orderType, String transId, String payType, String signature, String payerId) throws Exception {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> transactionsCollection = fpteamDB.getCollection("BankTransactions");
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");
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
            Document pubDoc = publishersCollection.find(filter).first();  
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
            
            if(pubDoc!=null){
                int currentMoney = pubDoc.getInteger("Money");
                int transactionAmount = Integer.parseInt(amount);
                int updatedMoney = currentMoney + transactionAmount;

                Bson updateOperation = Updates.set("Money", updatedMoney);
                publishersCollection.updateOne(filter, updateOperation);

                System.out.println("Phublisher's money updated successfully.");
            }
            
            
        } catch (Exception e) {
           throw new Exception("Error inserting transaction into MongoDB: " + e.getMessage());
        }
        }
     
        public static ArrayList<BankTransactions> getTransactionHistoryByPayerId(String payerId) {
        ArrayList<BankTransactions> transactionsList = new ArrayList<>();

        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
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

    

    
    public static void updateProfile(String id, String name, String email, String password, String AvatarLink, int role) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Collection "Gamers"
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            // Collection "Users"
            MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");
            // Collection "Publishers"
            MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");
        
            // Tạo một bộ lọc để xác định gamer cần cập nhật dựa trên ID
            BasicDBObject query = new BasicDBObject();
            query.put("ID", id);

            // Tạo một document mới chứa thông tin cập nhật (nếu có) cho Gamers
            Document gamerUpdateFields = new Document();
            if (name != null && !name.isEmpty()) {
                gamerUpdateFields.append("Name", name);
            }
            if (email != null && !email.isEmpty()) {
                gamerUpdateFields.append("Email", email);
            }
            if (password != null && !password.isEmpty()) {
                gamerUpdateFields.append("Password", password);
            }
            if (AvatarLink != null && !AvatarLink.isEmpty()) {
                gamerUpdateFields.append("AvatarLink", AvatarLink);
            }

            // Tạo một document mới chứa thông tin cập nhật cho Users
            Document userUpdateFields = new Document();
            if (name != null && !name.isEmpty()) {
                userUpdateFields.append("Name", name);
            }
            if (email != null && !email.isEmpty()) {
                userUpdateFields.append("Email", email);
            }
            if (password != null && !password.isEmpty()) {
                userUpdateFields.append("Password", password);
            }
            
            /// Tạo một document mới chứa thông tin cập nhật cho Publishers
            Document publisherUpdateFields = new Document();
            if (name != null && !name.isEmpty()) {
                publisherUpdateFields.append("Name", name);
            }
            if (email != null && !email.isEmpty()) {
                publisherUpdateFields.append("Email", email);
            }
            if (password != null && !password.isEmpty()) {
                publisherUpdateFields.append("Password", password);
            }
            
            if (!gamerUpdateFields.isEmpty() || !userUpdateFields.isEmpty() || !publisherUpdateFields.isEmpty()) {
                    usersCollection.updateOne(query, new Document("$set", userUpdateFields));
                
                    // Kiểm tra xem có gì để cập nhật không
                    if (role == 3) {
                        // Cập nhật trong cả Gamers, Users và Publishers
                        gamersCollection.updateOne(query, new Document("$set", gamerUpdateFields));
                        

                    } else if (role == 2) {
                        // Chỉ cập nhật trong Users và Publishers
                        publishersCollection.updateOne(query, new Document("$set", publisherUpdateFields));

                    }
            }
            
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    



}
