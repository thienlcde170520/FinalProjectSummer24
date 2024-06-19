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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
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
    ArrayList<Game> games = getGamesByGamerId("gamer_1");
    for (Game g : games){
        System.out.print(g);
    }
    }
  public static boolean hasGamerBoughtGame(String gamerId, String gameId) {
        MongoClientSettings settings = getConnection();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

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
  public static void addPurchase(String billId, String gamerId, String gameId, String buyTime, int buyPrice) {
    MongoClientSettings settings = getConnection();
    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

        // Access the collections
        MongoCollection<Document> buyCollection = fpteamDB.getCollection("Buy");
        MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
        MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");
        MongoCollection<Document> publishersCollection = fpteamDB.getCollection("GamePublishers");

        // Retrieve the current balance of the gamer
        Document gamerDoc = gamersCollection.find(Filters.eq("ID", gamerId)).first();
        if (gamerDoc == null) {
            System.out.println("Gamer not found with ID: " + gamerId);
            return;
        }
        
        // Check if the game exists in the Games collection
        
        Document gameDoc = gamesCollection.find(Filters.eq("ID", gameId)).first();
        if (gameDoc == null) {
            System.out.println("Game not found with ID: " + gameId);
            return;
        }

        int currentMoney = gamerDoc.getInteger("Money");
        if (currentMoney >= buyPrice) {
            // Calculate the new balance for the gamer after purchase
            int newBalance = currentMoney - buyPrice;

            // Update the Money field in the Gamers collection
            Bson updateBalance = Updates.set("Money", newBalance);
            gamersCollection.updateOne(Filters.eq("ID", gamerId), updateBalance);
            System.out.println("Gamer balance updated: " + newBalance);

            // Calculate the profit as 90% of the buy price
            int profitAmount = (int) (0.9 * buyPrice);

            // Update the profit for the publisher
            String publisherId = JavaMongo.getPublisherByGameId(gameId).getId(); // Assuming this method gets publisher ID
            Document publisherDoc = publishersCollection.find(Filters.eq("ID", publisherId)).first();
            if (publisherDoc != null) {
                int currentProfit = publisherDoc.getInteger("Profit");
                int newProfit = currentProfit + profitAmount;

                // Update the Profit field in the Publishers collection
                Bson updateProfit = Updates.set("Profit", newProfit);
                publishersCollection.updateOne(Filters.eq("ID", publisherId), updateProfit);
                System.out.println("Profit updated for publisher " + publisherId + ": " + newProfit);

                // Increment the number of buyers for the game in the Games collection
                int currentBuyers = gameDoc.getInteger("Number_of_buyers");
                int newBuyers = currentBuyers + 1;
                Bson updateBuyers = Updates.set("Number_of_buyers", newBuyers);
                gamesCollection.updateOne(Filters.eq("ID", gameId), updateBuyers);
                System.out.println("Number of buyers updated for game " + gameId + ": " + newBuyers);

                // Create the document to be inserted in the Buy collection
                Document buyDoc = new Document("ID_Bill", billId)
                        .append("ID_Gamer", gamerId)
                        .append("ID_Game", gameId)
                        .append("Buy_time", buyTime)
                        .append("Buy_price", buyPrice);

                // Insert the document into the Buy collection
                buyCollection.insertOne(buyDoc);
                System.out.println("Purchase added: " + buyDoc.toJson());
            } else {
                System.out.println("Publisher not found with ID: " + publisherId);
            }
        } else {
            System.out.println("Insufficient funds for gamer ID: " + gamerId);
        }
    } catch (MongoException e) {
        e.printStackTrace();
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

    public static void publishGame(String gameId, String publisherId) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
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
 private static ArrayList<String> getGenreTypesByGameID(String gameID) {
        MongoClientSettings settings = getConnection();
        ArrayList<String> genreTypes = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

            // Access the "GameGenres" collection (assuming this collection maps games to genres)
            MongoCollection<Document> gameGenresCollection = fpteamDB.getCollection("Game_Has_Genre");

            // Create a filter to search for the game by ID_Game
            Bson filter = Filters.eq("ID_Game", gameID);

            // Find all documents that match the filter
            for (Document gameGenreDoc : gameGenresCollection.find(filter)) {
                if (gameGenreDoc != null) {
                    // Extract the Type_of_genre attribute from the document
                    String genreType = gameGenreDoc.getString("Type_of_genres");
                    if (genreType != null) {
                        genreTypes.add(genreType);
                    }
                }
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return genreTypes;
    }

    // Method to get genre details by genre type
    private static Genre getGenreDetailsByType(String genreType) {
        MongoClientSettings settings = getConnection();
        Genre genre = null;
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

            // Access the "Genres" collection
            MongoCollection<Document> genresCollection = fpteamDB.getCollection("Genres");

            // Create a filter to search for the genre by Type_of_Genre
            Bson filter = Filters.eq("Type_of_Genre", genreType);

            // Find the first document that matches the filter
            Document genreDoc = genresCollection.find(filter).first();

            if (genreDoc != null) {
                // Extract genre attributes from the document
                String type = genreDoc.getString("Type_of_Genre");
                String description = genreDoc.getString("Description");

                // Create a Genre object
                genre = new Genre(type, description);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return genre;
    }

    // Method to get genres by game ID
    public static ArrayList<Genre> getGenresByGameID(String gameID) {
        ArrayList<Genre> genres = new ArrayList<>();
        ArrayList<String> genreTypes = getGenreTypesByGameID(gameID);
        for (String genreType : genreTypes) {
            Genre genre = getGenreDetailsByType(genreType);
            if (genre != null) {
                genres.add(genre);
            }
        }
        return genres;
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
public static void deleteReview(String gamerId, String gameId) {
    try (MongoClient mongoClient = MongoClients.create(getConnection())) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
        MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

        // Create the filter to find the review to delete
        Bson filter = Filters.and(
                Filters.eq("ID_Gamer", gamerId),
                Filters.eq("ID_Game", gameId)
        );

        // Print debug information
        System.out.println("Attempting to delete review with gamerId: " + gamerId + ", gameId: " + gameId);

        // Delete the review document from the Reviews collection
        DeleteResult result = collection.deleteOne(filter);

        // Check if deletion was successful
        if (result.getDeletedCount() > 0) {
            System.out.println("Review deleted successfully for gamerId: " + gamerId + ", gameId: " + gameId);
        } else {
            System.out.println("Review not found or already deleted for gamerId: " + gamerId + ", gameId: " + gameId);
        }
    } catch (MongoException e) {
        e.printStackTrace();
    }
}


    public static void addReview(String gamerId, String gameId, double rating, String description) {
        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
            MongoCollection<Document> collection = fpteamDB.getCollection("Reviews");

            // Create a new review document
            Document reviewDoc = new Document("ID_Gamer", gamerId)
                    .append("ID_Game", gameId)
                    .append("Rating", rating)
                    .append("Description", description);

            // Insert the review document into the Reviews collection
            collection.insertOne(reviewDoc);

            System.out.println("Review added successfully: " + reviewDoc.toJson());
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

       public static Publishers getPublisherByGameId(String gameId) {
        MongoClientSettings settings = getConnection();
        Publishers publisher = null;

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

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
                            publisherDoc.getInteger("Profit", 0),
                            publisherDoc.getString("Description"),
                            publisherDoc.getString("AvatarLink"),
                            publisherDoc.getInteger("Money"),
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
public static ArrayList<Game> getGamesByGamerId(String gamerId) {
    ArrayList<Game> games = new ArrayList<>();

    MongoClientSettings settings = getConnection();
    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

        // Access the collections
        MongoCollection<Document> billCollection = fpteamDB.getCollection("Buy");
        MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

        // Create a filter to find bills associated with the gamerId
        Bson filter = Filters.eq("ID_Gamer", gamerId);

        // Find all documents in Bill collection that match the filter
        FindIterable<Document> billDocs = billCollection.find(filter);

        for (Document billDoc : billDocs) {
            String gameId = billDoc.getString("ID_Game");
            
            // Retrieve the game details from Games collection based on gameId
            Game game = getGameByGameID(gameId);
            
            if (game != null) {
                games.add(game);
            } else {
                System.out.println("Game not found with ID: " + gameId);
            }
        }
    } catch (MongoException e) {
        e.printStackTrace();
    }

    return games;
}

 public static Game getGameByGameID(String gameID) {
        MongoClientSettings settings = getConnection();
        Game game = null;
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

            // Access the "Games" collection
            MongoCollection<Document> gamesCollection = fpteamDB.getCollection("Games");

            // Create a filter to search for the game by ID
            Bson filter = Filters.eq("ID", gameID);

            // Find the first document that matches the filter
            Document gameDoc = gamesCollection.find(filter).first();

            if (gameDoc != null) {
                // Extract game attributes from the document
                String id = gameDoc.getString("ID");
                String name = gameDoc.getString("Name");
                double price = gameDoc.getDouble("Price");
                String publishDay = gameDoc.getString("Publish_day");
                int numberOfBuyers = gameDoc.getInteger("Number_of_buyers");
                String linkTrailer = gameDoc.getString("LinkTrailer");
                String avatarLink = gameDoc.getString("AvatarLink");
                String gameLink = gameDoc.getString("GameLink");
                String description = gameDoc.getString("Description");
                String minimumCPU = gameDoc.getString("Minimum_CPU");
                String minimumRAM = gameDoc.getString("Minimum_RAM");
                String minimumGPU = gameDoc.getString("Minimum_GPU");
                String maximumCPU = gameDoc.getString("Maximum_CPU");
                String maximumRAM = gameDoc.getString("Maximum_RAM");
                String maximumGPU = gameDoc.getString("Maximum_GPU");

                // Create a Game object
                game = new Game(id, name, price, publishDay, numberOfBuyers, linkTrailer, avatarLink, gameLink, description, minimumCPU, minimumRAM, minimumGPU, maximumCPU, maximumRAM, maximumGPU);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return game;
    }

 
 
    public static void CreateNewGamerAccount(String id,String name, String password, String email, int role, int Money, String AvatarLink, String RegistrationDate){

        MongoClientSettings settings = getConnection();
        try (MongoClient mongoClient = MongoClients.create(settings)) {

            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

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
            
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

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

    public static Gamers getGamerByEmail(String email) {
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
                        gamerDoc.getString("AvatarLink"),
                        gamerDoc.getString("RegistrationDate")
                );
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    } public static Gamers getGamerByGamerId(String Id) {
        MongoClientSettings settings = getConnection();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
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
public static Publishers getPublisherByEmail(String email) {
    MongoClientSettings settings = getConnection();

    try (MongoClient mongoClient = MongoClients.create(settings)) {
        MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");
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
                publisherDoc.getInteger("Profit"),
                publisherDoc.getString("Description"),
                publisherDoc.getString("AvatarLink"),
                publisherDoc.getInteger("Money"),
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

    

    
    public static void updateGamerProfile(String id, String name, String email, String password, String AvatarLink) {
            try (MongoClient mongoClient = MongoClients.create(getConnection())) {
                MongoDatabase fpteamDB = mongoClient.getDatabase("FPTeam");

                // Collection "Gamers"
                MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
                // Collection "Users"
                MongoCollection<Document> usersCollection = fpteamDB.getCollection("Users");

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

                // Tạo một document mới chứa thông tin cập nhật
                Document gamerUpdateDoc = new Document("$set", gamerUpdateFields);
                Document userUpdateDoc = new Document("$set", userUpdateFields);

                // Thực hiện update vào MongoDB trong collection "Gamers"
                gamersCollection.updateOne(query, gamerUpdateDoc);
                // Thực hiện update vào MongoDB trong collection "Users"
                usersCollection.updateOne(query, userUpdateDoc);

                System.out.println("Gamer profile updated successfully with ID: " + id);
            } catch (MongoException e) {
                e.printStackTrace();
            }
    }
    
    


}
