package com.example.amuntimilsina.bideshisawari.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getPlace(JSONObject googlePlaceJSON) throws JSONException {
        HashMap<String,String> googlePlaceMap =new HashMap<>();
        String placeName="-NA-";
        String rating="-NA-";
        String vicinity="-NA-";
        String lat="-NA-";
        String lang="-NA-";
        String reference="-NA-";
        if(!googlePlaceJSON.isNull("name"))
        {
            placeName=googlePlaceJSON.getString("name");
        }

        if(!googlePlaceJSON.isNull("vicinity"))
        {
            vicinity=googlePlaceJSON.getString("vicinity");
        }
        lat=googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
        lang=googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
        reference=googlePlaceJSON.getString("reference");
        rating=googlePlaceJSON.getString("rating");
        googlePlaceMap.put("Place_Name",placeName);
        googlePlaceMap.put("rating",rating);
        googlePlaceMap.put("Vicinity",vicinity);
        googlePlaceMap.put("lat",lat);
        googlePlaceMap.put("lang",lang);
        googlePlaceMap.put("reference",reference);
        return googlePlaceMap;
    }
    private List<HashMap<String, String>>getAllPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placelist = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i = 0; i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placelist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placelist;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllPlaces(jsonArray);
    }

}