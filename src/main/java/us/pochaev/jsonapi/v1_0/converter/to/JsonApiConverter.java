package us.pochaev.jsonapi.v1_0.converter.to;

import java.util.Collection;

import org.json.JSONObject;


public class JsonApiConverter {



	public static JSONObject convert(Object obj) {
        JSONObject data = new JSONObject();

        data.put("type", JsonApiObjectParser.parse(obj));
        data.put("id", JsonApiIdParser.parse(obj));

        data.put("attributes", JsonApiMetaItemParser.parse(obj));


        JSONObject envelope = new JSONObject();
        envelope.put("data", data);

        return envelope;

	}





	private boolean isCollection(Object object) {
        return Collection.class.isAssignableFrom(object.getClass());
    }
}
