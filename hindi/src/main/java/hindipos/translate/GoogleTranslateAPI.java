package hindipos.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GoogleTranslateAPI {
	private String key;
	 
    public GoogleTranslateAPI(String apiKey) {
        key = apiKey;
    }
 
    public String translte(String text, String from, String to) {
        StringBuilder result = new StringBuilder();
        HttpsURLConnection conn = null;
        URL url;
        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String urlStr = "https://www.googleapis.com/language/translate/v2?key=" + key + "&q=" + encodedText + "&target=" + to + "&source=" + from;
 
            url = new URL(urlStr);
 
            conn = (HttpsURLConnection) url.openConnection();
            InputStream stream;
            if (conn.getResponseCode() == 200) //success
            {
                stream = conn.getInputStream();
            } else
                stream = conn.getErrorStream();
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
 
            JsonParser parser = new JsonParser();
 
            JsonElement element = parser.parse(result.toString());
 
            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.get("error") == null) {
                    String translatedText = obj.get("data").getAsJsonObject().
                    get("translations").getAsJsonArray().
                    get(0).getAsJsonObject().
                    get("translatedText").getAsString();
                    return translatedText;
 
                }
            }
 
            if (conn.getResponseCode() != 200) {
                System.err.println(result);
            }
 
        } catch (IOException | JsonSyntaxException ex) {
            System.err.println(ex.getMessage());
        }
        finally
        {
        	conn.disconnect();
//        	System.out.println("Connection closed");
        }
 
        return null;
    }

}
