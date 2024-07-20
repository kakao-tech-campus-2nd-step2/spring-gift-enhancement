package gift.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseUtility {

    public Map<String, String> makeResponse(String msg) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", msg);
        return response;
    }
}
