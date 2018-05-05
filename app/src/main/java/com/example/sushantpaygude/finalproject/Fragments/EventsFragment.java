package com.example.sushantpaygude.finalproject.Fragments;
//package com.example.sushantpaygude.finalproject.POJOs.TicketMaster.EventResponse;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sushantpaygude.finalproject.Adapters.EventRecyclerViewAdapter;
import com.example.sushantpaygude.finalproject.POJOs.TicketMaster.EventResponse.Event;

import com.example.sushantpaygude.finalproject.POJOs.TicketMaster.EventResponse.TicketMasterResponse;
import com.example.sushantpaygude.finalproject.R;
import com.example.sushantpaygude.finalproject.Utils.Utilities;
import com.example.sushantpaygude.finalproject.Utils.VolleySingleton;

import com.fonfon.geohash.GeoHash;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private RequestQueue requestQueue;
    private RecyclerView eventsrecyclerView;
    private EventRecyclerViewAdapter eventRecyclerViewAdapter;
    private ArrayList<Event> ticketMasterEventsArrayList = new ArrayList<>();

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventsrecyclerView = view.findViewById(R.id.eventsRecyclerView);
        eventsrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(eventsrecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        eventsrecyclerView.addItemDecoration(dividerItemDecoration);

        eventRecyclerViewAdapter = new EventRecyclerViewAdapter(ticketMasterEventsArrayList);

        eventsrecyclerView.setAdapter(eventRecyclerViewAdapter);
//        int page = 0;
//        int totalpages = 1;
//        do {
            getEvents(39.260700,-76.699453,10);
//        }while (page<totalpages);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private String getGeoHash(double latitude,double longitude)
    {
        Location location = new Location("geohash");
//        location.setLatitude(53.2030476);
//        location.setLongitude(45.0324948);

        location.setLatitude(latitude);
        location.setLongitude(longitude);

        GeoHash hash = GeoHash.fromLocation(location, 9);
        return hash.toString(); //"v12n8trdj"
    }

    private void getEvents(double latitude,double longitude, int radius) {//Provide radius on miles

        String geoHash = getGeoHash(latitude,longitude);
        String url = String.format(Utilities.TICKETMASTER_GET_EVENTS_BY_LOCATION, geoHash, String.valueOf(radius),Utilities.TICKETMASTER_API_KEY);

        //String url = "https://app.ticketmaster.com/discovery/v2/events.json?geoPoint=dqcrq&radius=10&apikey=xci6BKuaudQC0tMXRUZnvFSIF6trOVfd";
        Log.i("URL", ":" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("resonse", ":" + response);
                TicketMasterResponse ticketMasterResponse = new Gson().fromJson(response.toString(), TicketMasterResponse.class);
                Log.i("Page","Pagesize:" + ticketMasterResponse.getPage().getSize());
                Log.i("Page","PageTotalElements:" + ticketMasterResponse.getPage().getTotalElements());
                Log.i("Page","PageNumber:" + ticketMasterResponse.getPage().getNumber());
                Log.i("Page","TotalPage:" + ticketMasterResponse.getPage().getTotalPages());
                for (Event e : ticketMasterResponse.getEmbedded().getEvents()) {

                    ticketMasterEventsArrayList.add(e);
                }
                eventRecyclerViewAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    Log.e("Error", ":" + jsonError);
                }

            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Utilities.getTicketMasterTokenParams(getContext());
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
