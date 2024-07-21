package Controller;

import DAO.*;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static Controller.StatisticController.getGame_Has_Genres;

@WebServlet("/get-statistic-data-for-publisher")
public class StatisticForPublisherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("account") == null){
            resp.sendRedirect(req.getContextPath() + "/Login.jsp");
        } else {
            Users user = (Users) req.getSession().getAttribute("account");
            if (user.getRole() == 2){
                HashMap<String, ArrayList<?>> map = new HashMap<>();
                ArrayList<Game> games = GameDAO.getGamesByPublisherName(user.getName());
                ArrayList<Bill> bills = TransactionBillDAO.getAllBills();
                ArrayList<Gamers> users = AdminDAO.getAllGamers();
                ArrayList<Genre> genres = GenreDAO.getAllGenres();
                ArrayList<StatisticController.Game_Has_genre> gameHasGenres = getGame_Has_Genres();
                map.put("games", games);
                map.put("bills", bills);
                map.put("gamers", users);
                map.put("genres", genres);
                map.put("gameHasGenres", gameHasGenres);
                Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new StatisticController.Gson_InstantTypeAdapter()).create();
                String json = gson.toJson(map);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
        }
    }
}