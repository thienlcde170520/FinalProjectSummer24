package Controller;


import DAO.GameDAO;
import DAO.GamerDAO;
import DAO.PublisherDAO;
import DAO.ReportDAO;
import DAO.ReviewDAO;
import Model.Users;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JavaMongo {
//

    private static final String CONNECTION_STRING = "mongodb+srv://viet81918:conchode239@cluster0.hzr2fsy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String CONNECTION_STRING_LOCAL = "mongodb://localhost:27017/";

    public static MongoClientSettings getConnection() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        // Adjust connection pool settings
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .applyToConnectionPoolSettings(builder
                        -> builder.maxSize(50) // Example: set max pool size
                        .minSize(5) // Example: set min pool size
                        .maxWaitTime(1000, TimeUnit.MILLISECONDS)) // Example: set max wait time
                .build();

        return settings;
    }

    public static MongoClientSettings getConnectionLocal() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        // Adjust connection pool settings
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING_LOCAL))
                .serverApi(serverApi)
                .applyToConnectionPoolSettings(builder
                        -> builder.maxSize(50) // Example: set max pool size
                        .minSize(5) // Example: set min pool size
                        .maxWaitTime(1000, TimeUnit.MILLISECONDS)) // Example: set max wait time
                .build();

        return settings;
    }


    public static void main(String[] args) {

        
//   ArrayList<Game> searchedGames = searchGames("", "", "2024", "", "", new String[]{"", ""});
//
//    // Print the results
//    System.out.println("\nSearched Games:");
//    for (Game game : searchedGames) {
//        System.out.println(game.getName() + " Year: " + game.getPublishDay() + " | Price: " + game.getPrice());
//    }

        String id = "gamer_4409249271";
        String name = "thiencajca";
        String email = "thienle2105@gmail.com";
        String password = "lecongthien1";
        String avatarLink = "https://drive.google.com/thumbnail?id=14MhZYp0uLsyJ2aE6LVHDmeSM7beHtNd-&sz=w1000";
        String dob = "01/01/2000";
        int role = 3;
        String bank = "";
        String description = "";

        // Gọi phương thức cập nhật
        updateProfile(id, name, email, password, avatarLink, dob, role, bank, description);


    }


    /*publisher*/
 /*----------------------------*/
    public static ArrayList<Users> getAllUser() {

        MongoClientSettings settings = getConnectionLocal();

        ArrayList<Users> usersList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {

                // Access the "FPT" database
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
        MongoClientSettings settings = getConnectionLocal();

        try (MongoClient mongoClient = MongoClients.create(settings)) {

            // Truy cập cơ sở dữ liệu "FPT"
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

    /*tao moi publisher*/
 /*---------------------*/
    public static Users getUserByEmail(String email) {
        MongoClientSettings settings = getConnectionLocal();

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

    /*get publisher by email*/
    public static void updatePassword(String email, String newPassword) {
        try ( MongoClient mongoClient = MongoClients.create(getConnection())) {

            // Truy cập cơ sở dữ liệu "FPT"
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
         try ( MongoClient mongoClientLocal = MongoClients.create(getConnectionLocal())) {

            // Truy cập cơ sở dữ liệu "FPT"
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Tạo một bộ lọc để truy vấn người dùng dựa trên Email
            Document filter = new Document("Email", email);

            // Tạo một document mới chứa thông tin mật khẩu mới
            Document updatePasswordDoc = new Document("$set", new Document("Password", newPassword));

            // Truy cập bộ sưu tập "Users"
            MongoCollection<Document> usersCollection = fpteamDBLocal.getCollection("Users");

            // Thực hiện update vào MongoDB trong collection "Users"
            usersCollection.updateOne(filter, updatePasswordDoc);

            // Truy cập bộ sưu tập "Gamers"
            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");

            // Thực hiện update vào MongoDB trong collection "Gamers"
            gamersCollection.updateOne(filter, updatePasswordDoc);

            // Truy cập bộ sưu tập "GamePublishers"
            MongoCollection<Document> publishersCollection = fpteamDBLocal.getCollection("GamePublishers");

            // Thực hiện update vào MongoDB trong collection "GamePublishers"
            publishersCollection.updateOne(filter, updatePasswordDoc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   public static void updateProfile(String id, String name, String email, String password, String AvatarLink, String DOB , int role, String Bank, String Description){

        try (MongoClient mongoClient = MongoClients.create(getConnection())) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");

            // Collection "Gamers"
            MongoCollection<Document> gamersCollection = fpteamDB.getCollection("Gamers");
            // Collection "Users"
          

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
            if(DOB != null && !DOB.isEmpty()){
                gamerUpdateFields.append("Date of Birth",DOB);
            }
            if (!gamerUpdateFields.isEmpty() ) {
              
                // Kiểm tra xem có gì để cập nhật không
                if (role == 3) {
                    // Cập nhật trong cả Gamers, Users và Publishers
                    gamersCollection.updateOne(query, new Document("$set", gamerUpdateFields));

                } 
            }

        } catch (MongoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
          try (MongoClient mongoClientLocal = MongoClients.create(getConnectionLocal())) {
            MongoDatabase fpteamDBLocal = mongoClientLocal.getDatabase("FPT");

            // Collection "Gamers"
            MongoCollection<Document> gamersCollection = fpteamDBLocal.getCollection("Gamers");
            // Collection "Users"
            MongoCollection<Document> usersCollection = fpteamDBLocal.getCollection("Users");
            // Collection "Publishers"
            MongoCollection<Document> publishersCollection = fpteamDBLocal.getCollection("GamePublishers");

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

             if(DOB != null && !DOB.isEmpty()){

                gamerUpdateFields.append("Date of Birth",DOB);
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
             if (AvatarLink != null && !AvatarLink.isEmpty()) {
                publisherUpdateFields.append("AvatarLink", AvatarLink);
            }
            if (email != null && !email.isEmpty()) {
                publisherUpdateFields.append("Email", email);
            }
            if (password != null && !password.isEmpty()) {
                publisherUpdateFields.append("Password", password);
            }
            if (Bank != null && !Bank.isEmpty()) {
                publisherUpdateFields.append("Bank_account", Bank);
            }
            if (Description != null && !Description.isEmpty()) {
                publisherUpdateFields.append("Description", Description);
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
