package Common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name="MoMoPaymentServlet", urlPatterns={"/atm_momo"})
public class MoMoPaymentServlet extends HttpServlet {
    private static final String ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
    private static final String PARTNER_CODE = "MOMOBKUN20180529";
    private static final String ACCESS_KEY = "klm05TvNBzhg7h7j";
    private static final String SECRET_KEY = "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa";
    private static final String ORDER_INFO = "Thanh toan qua MoMo";
    private static final String AMOUNT = "10000";
    private static final String REDIRECT_URL = "https://barely-handy-mink.ngrok-free.app/FPTeam/paymentSuccess.jsp";
    private static final String IPN_URL = "http://localhost:9999/FPTeam/paymentSuccessServlet";
    private static final String EXTRA_DATA = "";
    private static final Logger LOGGER = Logger.getLogger(MoMoPaymentServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Received POST request");
        
        String partnerCode = PARTNER_CODE;
        String accessKey = ACCESS_KEY;
        String secretKey = SECRET_KEY;
        String orderId = String.valueOf(System.currentTimeMillis());
        String orderInfo = req.getParameter("order-info");
        String amount = req.getParameter("amount");
        String ipnUrl = REDIRECT_URL;   
        String redirectUrl = IPN_URL ;
        String extraData = EXTRA_DATA;

        long requestId = System.currentTimeMillis();
        //String requestType = "payWithATM";
        String requestType = req.getParameter("requestType");
        if (requestType == null || (!requestType.equals("payWithATM") && !requestType.equals("captureWallet"))) {
            LOGGER.log(Level.SEVERE, "Invalid requestType: {0}", requestType);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String rawHash = String.format("accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%d&requestType=%s",
                accessKey, amount, extraData, ipnUrl, orderId, orderInfo, partnerCode, redirectUrl, requestId, requestType);
        String signature = hmacSHA256(rawHash, secretKey);

        Map<String, String> data = new HashMap<>();
        data.put("partnerCode", partnerCode);
        data.put("partnerName", "Test");
        data.put("storeId", "MomoTestStore");
        data.put("requestId", String.valueOf(requestId));
        data.put("amount", amount);
        data.put("orderId", orderId);
        data.put("orderInfo", orderInfo);
        data.put("redirectUrl", redirectUrl);
        data.put("ipnUrl", ipnUrl);
        data.put("lang", "vi");
        data.put("extraData", extraData);
        data.put("requestType", requestType);
        data.put("signature", signature);

        LOGGER.log(Level.INFO, "Request data: {0}", data);

        String jsonResponse = execPostRequest(ENDPOINT, data);
        LOGGER.log(Level.INFO, "Response from MoMo: {0}", jsonResponse);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonResult = mapper.readValue(jsonResponse, Map.class);

        if (jsonResult.containsKey("payUrl")) {
            LOGGER.log(Level.INFO, "Redirecting to: {0}", jsonResult.get("payUrl"));
            resp.sendRedirect(jsonResult.get("payUrl"));
        } else {
            LOGGER.log(Level.SEVERE, "Error in response: {0}", jsonResult);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(jsonResponse);
            out.flush();
        }
    }

    private String execPostRequest(String url, Map<String, String> data) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            String json = new ObjectMapper().writeValueAsString(data);
            StringEntity entity = new StringEntity(json);

            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
    }

    private String hmacSHA256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC SHA256", e);
        }
    }
}


    

