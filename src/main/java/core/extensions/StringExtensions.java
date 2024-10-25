package core.extensions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

public class StringExtensions {
    public static boolean isNullOrBlank(String text) {
        return text == null || text.equalsIgnoreCase("default") || text.equalsIgnoreCase("");
	}

    public static DesiredCapabilities convertJsonToCapabilities(String json) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		Map<String, String> caps = convertJsonToMap(json);
		if (caps != null) {
			Set<String> keys = caps.keySet();
			for (String key : keys) {
				capabilities.setCapability(key, caps.get(key));
			}
		}
		return capabilities;
	}

    public static Map<String, String> convertJsonToMap(String json) throws JSONException {
		JSONObject jsonObj = new JSONObject(json);
		Map<String, String> map = new HashMap<>();
		Iterator<String> keys = jsonObj.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = jsonObj.getString(key);
			map.put(key, value);
		}
		return map;
	}
}
