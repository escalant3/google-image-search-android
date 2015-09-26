package com.escalant3.googleimagesearchapp.deserializers;


import com.escalant3.googleimagesearchapp.models.GoogleImagesResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GoogleImagesDeserializer implements JsonDeserializer<GoogleImagesResponse> {

    @Override
    public GoogleImagesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject main = json.getAsJsonObject();

        JsonObject data = main.get("responseData").getAsJsonObject();
        JsonArray results = data.get("results").getAsJsonArray();

        ArrayList<String> payloadResults = new ArrayList<>();

        for (JsonElement result : results) {
            payloadResults.add(result.getAsJsonObject().get("tbUrl").getAsString());
        }

        return new GoogleImagesResponse(payloadResults);
    }
}
