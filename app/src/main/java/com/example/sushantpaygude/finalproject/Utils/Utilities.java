package com.example.sushantpaygude.finalproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sushantpaygude on 4/19/18.
 */

public class Utilities {

    //YELP API IDS AND URLS
    public static String YELPAuthorization = "Authorization";

    public static String YELP_CLIENTID = "";
    public static String YELP_API_KEY = "";

    public static String YELP_SEARCH_BASE_STRING = "https://api.yelp.com/v3/businesses/search?";

    public static String YELP_GET_RESTAURANTS_BY_LOCATION = YELP_SEARCH_BASE_STRING + "latitude=%s&longitude=%s&radius=10000";


    public static String GOOGLE_PLACES_API = "";
    public static String RESTAURANTS = "Restaurants";
    public static int RC_SIGN_IN = 999;

    public static Map<String, String> getYELPTokenParams(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String, String> params = new HashMap<>();
        params.put(YELPAuthorization, YELP_API_KEY);
        return params;
    }


    public static String GOOGLESIGNINACCOUNT = "GOOGLESIGNINACCOUNT";
    public static String GOOGLESIGNINCLIENT = "GOOGLESIGNINACCOUNT";

    
    //TICKETMASTERS API IDS AND URLS
    public static String TicketMasterAuthorization = "Authorization";
    public static String TICKETMASTER_API_KEY = "";
    public static String TICKETMASTER_SEARCH_BASE_STRING = "https://app.ticketmaster.com/discovery/v2/events.json?";
    public static String TICKETMASTER_GET_EVENTS_BY_LOCATION = TICKETMASTER_SEARCH_BASE_STRING + "geoPoint=%s&radius=%s&apikey=%s";

    public static Map<String, String> getTicketMasterTokenParams(Context context) {
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Map<String, String> params = new HashMap<>();
        params.put(TicketMasterAuthorization, TICKETMASTER_API_KEY);
        return params;
    }

    //"https://app.ticketmaster.com/discovery/v2/events.json?geoPoint=dqcrq&radius=10&apikey=xci6BKuaudQC0tMXRUZnvFSIF6trOVfd"
}
