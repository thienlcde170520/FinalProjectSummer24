package Controller;


import DAO.*;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static Controller.JavaMongo.getConnectionLocal;

@WebServlet("/get-statistic-data")
public class StatisticController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, ArrayList<?>> map = new HashMap<>();
        ArrayList<Game> games = GameDAO.getAllGames();
        ArrayList<Bill> bills = TransactionBillDAO.getAllBills();
        ArrayList<Gamers> users = AdminDAO.getAllGamers();
        ArrayList<Genre> genres = GenreDAO.getAllGenres();
        ArrayList<Publishers> publishers = PublisherDAO.getAllPublishers();
        ArrayList<Game_Has_genre> gameHasGenres = getGame_Has_Genres();
        ArrayList<Publish> publishes = getTruePublish();
        map.put("games", games);
        map.put("bills", bills);
        map.put("gamers", users);
        map.put("genres", genres);
        map.put("publishers", publishers);
        map.put("gameHasGenres", gameHasGenres);
        map.put("publishes", publishes);
        Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new Gson_InstantTypeAdapter()).create();
        String json = gson.toJson(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
    public static class Gson_InstantTypeAdapter extends TypeAdapter<Instant>{
        @Override
        public void write (JsonWriter jsonWriter , Instant instant ) throws IOException
        {
            jsonWriter.value( instant.toString() );  // Writes in standard ISO 8601 format.
        }

        @Override
        public Instant read ( JsonReader jsonReader ) throws IOException
        {
            return Instant.parse( jsonReader.nextString() );   // Parses standard ISO 8601 format.
        }
    }
    public static class Game_Has_genre{
        public String ID_Game;
        public String Type_of_genres;

        public Game_Has_genre(String ID_Game, String type_of_genres) {
            this.ID_Game = ID_Game;
            Type_of_genres = type_of_genres;
        }
    }
    public static ArrayList<Game_Has_genre> getGame_Has_Genres(){
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Game_Has_genre> gameHasGenres = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> Collection = fpteamDB.getCollection("Game_Has_Genre");

            MongoCursor<Document> cursor = Collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Game_Has_genre gameHasGenre = new Game_Has_genre(
                        doc.getString("ID_Game"),
                        doc.getString("Type_of_genres")
                );
                gameHasGenres.add(gameHasGenre);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return gameHasGenres;
    }
    public static class Publish{
        public String ID_Game;
        public String ID_Game_Publisher;
        public String ID_Admin;
        public boolean isPublishable;

        public Publish(String ID_Game, String ID_Game_Publisher, String ID_Admin, boolean isPublishable) {
            this.ID_Game = ID_Game;
            this.ID_Game_Publisher = ID_Game_Publisher;
            this.ID_Admin = ID_Admin;
            this.isPublishable = isPublishable;
        }
    }

    public static ArrayList<Publish> getTruePublish(){
        MongoClientSettings settings = getConnectionLocal();
        ArrayList<Publish> publishes = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase fpteamDB = mongoClient.getDatabase("FPT");
            MongoCollection<Document> Collection = fpteamDB.getCollection("Publish");
            MongoCursor<Document> cursor = Collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Publish publish = new Publish(
                        doc.getString("ID_Game"),
                        doc.getString("ID_Game_Publisher"),
                        doc.getString("ID_Admin"),
                        doc.getBoolean("isPublishable")
                );
                publishes.add(publish);
            }
            cursor.close();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return publishes;
    }
}
