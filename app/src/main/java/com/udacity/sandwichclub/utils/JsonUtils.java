package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        try {
            // First we get all of the JSON elements
            JSONObject sandwichData = new JSONObject(json);
            JSONObject name = sandwichData.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichData.getString("placeOfOrigin");
            String description = sandwichData.getString("description");
            String image = sandwichData.getString("image");
            JSONArray ingredients = sandwichData.getJSONArray("ingredients");

            // Transforming the alsoKnownAs  and ingredients - JSONArray into an ArrayList so that it's compatible with Sandwich constructor
            List<String> alsoKnownAsList = convertToListFromJsonArray(alsoKnownAs);
            List<String> ingredientsList = convertToListFromJsonArray(ingredients);

            // Then we create an instance of Sandwich
            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;
        } catch (JSONException e) {
            Log.e("JSON Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertToListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }

}
