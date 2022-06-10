package us.pochaev.jsonapi.v1_0.converter.to;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonApiConverter {

	public static JSONObject convert(Object obj) {

        JSONObject data = new JSONObject();

        data.put("type", JsonApiObjectParser.parse(obj));
        data.put("id", JsonApiIdParser.parse(obj));

        data.put("attributes", JsonApiAttributeParser.parse(obj));

        JSONObject envelope = new JSONObject();
        envelope.put("data", data);

        return envelope;
	}

	public static JSONArray convert(Collection<?> collection) {

		JSONArray envelope = new JSONArray();

		for (Object element : collection) {
			envelope.put(convert(element));
		}

        return envelope;
	}

}
