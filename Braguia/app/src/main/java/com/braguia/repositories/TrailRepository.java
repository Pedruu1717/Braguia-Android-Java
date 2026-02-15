package com.braguia.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.braguia.RetrofitHelper;
import com.braguia.model.BraguiaDatabase;
import com.braguia.model.Edge;
import com.braguia.model.EdgeDAO;
import com.braguia.model.EdgeResponse;
import com.braguia.model.Trail;
import com.braguia.model.TrailAPI;
import com.braguia.model.TrailDAO;
import com.braguia.model.TrailResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

//import android.telecom.Call;
//import com.google.android.gms.common.api.Response;

public class TrailRepository {

    public TrailDAO trailDAO;
    public EdgeDAO edgeDAO;
    public MediatorLiveData<List<Trail>> allTrails;
    public MediatorLiveData<List<Edge>> allEdges;
    private BraguiaDatabase database;

    public TrailRepository(Application application){
        database = BraguiaDatabase.getInstance(application);
        trailDAO = database.trailDAO();
        allTrails = new MediatorLiveData<>();
        allTrails.addSource(
                trailDAO.getTrails(), localTrails -> {
                    // TODO: ADD cache validation logic
                    if (localTrails != null && localTrails.size() > 0) {
                        allTrails.setValue(localTrails);
                    } else {
                        makeRequest();
                    }
                }
        );

        edgeDAO = database.edgeDAO();
        allEdges = new MediatorLiveData<>();
        allEdges.addSource(
                edgeDAO.getEdges(), localEdges -> {
                    // TODO: ADD cache validation logic
                    if (localEdges != null && localEdges.size() > 0) {
                        allEdges.setValue(localEdges);
                    }
                }
        );
    }

    /* Get API */
    public static TrailAPI getTrailAPI() {
        TrailAPI api = RetrofitHelper.getRetrofit().create(TrailAPI.class);

        return api;
    }

    public void insert(List<Trail> trails){
        new InsertAsyncTask(trailDAO).execute(trails);
    }

    public void insertEdges(List<Edge> edges){
        new InsertEdgesAsyncTask(edgeDAO).execute(edges);
    }

    private void makeRequest() {
        Call<List<TrailResponse>> call = getTrailAPI().getTrails();

        call.enqueue(new retrofit2.Callback<List<TrailResponse>>() {
            @Override

            public void onResponse(@NonNull Call<List<TrailResponse>> call, @NonNull Response<List<TrailResponse>> response) {
                if(response.isSuccessful()) {
                    List<Trail> trails = new ArrayList<>();
                    List<Edge>  edges = new ArrayList<>();
                    List<TrailResponse> trails_response = response.body();
                    List<EdgeResponse> edges_response = new ArrayList<>();

                    for (int i=0; i<trails_response.size(); i++) {
                        TrailResponse trail = trails_response.get(i);

                        ArrayList<EdgeResponse> trail_edges = trails_response.get(i).getEdges();
                        edges_response.addAll(trail_edges);

                        Trail new_trail = new Trail(trail.getId(),trail.getImage_url(),
                                trail.getTrail_name(),trail.getTrail_desc(),
                                trail.getTrail_duration(),trail.getTrail_difficulty());
                        trails.add(new_trail);
                    }

                    for (int i=0; i<edges_response.size(); i++) {
                        EdgeResponse edge = edges_response.get(i);

                        Edge new_edge = new Edge(edge.getId(),edge.getEdge_start().getId(),
                                edge.getEdge_end().getId(),edge.getEdge_transport(),
                                edge.getEdge_duration(), edge.getEdge_desc(), edge.getEdge_trail());

                        edges.add(new_edge);

                    }

                    insert(trails);
                    insertEdges(edges);
                    Log.i("Trails & Edges fetched!", "Response code: " + response.code());

                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TrailResponse>> call, @NonNull Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Trail>> getAllTrails(){
        return allTrails;
    }

    public LiveData<List<Edge>> getEdgesByTrailId(String id){
        return edgeDAO.getEdgesByTrailId(id);
    }

    public LiveData<Trail> getTrailById(String id) {
        return trailDAO.getTrailById(id);
    }

    private static class InsertAsyncTask extends AsyncTask<List<Trail>,Void,Void> {
        private TrailDAO trailDAO;

        public InsertAsyncTask(TrailDAO catDao) {
            this.trailDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<Trail>... lists) {
            trailDAO.insert(lists[0]);
            return null;
        }
    }

    private static class InsertEdgesAsyncTask extends AsyncTask<List<Edge>,Void,Void> {
        private EdgeDAO edgeDAO;

        public InsertEdgesAsyncTask(EdgeDAO catDao) {
            this.edgeDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<Edge>... lists) {
            edgeDAO.insert(lists[0]);
            return null;
        }
    }

}
