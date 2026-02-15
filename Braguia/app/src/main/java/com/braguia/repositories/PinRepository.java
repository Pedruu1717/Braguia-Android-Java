package com.braguia.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.braguia.RetrofitHelper;
import com.braguia.model.BraguiaDatabase;
import com.braguia.model.Pin;
import com.braguia.model.PinAPI;
import com.braguia.model.PinDAO;
import com.braguia.model.PinResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class PinRepository {

    public static PinDAO pinDAO;
    public static MediatorLiveData<List<Pin>> allPins;
    private BraguiaDatabase database;

    public PinRepository(Application application){
        database = BraguiaDatabase.getInstance(application);
        pinDAO = database.pinDAO();
        allPins = new MediatorLiveData<>();
        allPins.addSource(
                pinDAO.getPins(), localPins -> {
                    // TODO: ADD cache validation logic
                    if (localPins != null && localPins.size() > 0) {
                        allPins.setValue(localPins);
                    } else {
                        makeRequest();
                    }
                }
        );
    }

    /* Get API */
    public static PinAPI getPinAPI() {
        PinAPI api = RetrofitHelper.getRetrofit().create(PinAPI.class);

        return api;
    }

    public void insert(List<Pin> pins){
        new InsertAsyncTask(pinDAO).execute(pins);
    }

    private void makeRequest() {
        String sessionId = UserRepository.getmSessionManager().fetchSessionId();
        String token = UserRepository.getmSessionManager().fetchAuthToken();

        Call<List<PinResponse>> call = getPinAPI()
                .getPins("csrftoken=" + token + "; sessionid=" + sessionId);

        call.enqueue(new retrofit2.Callback<List<PinResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<PinResponse>> call, @NonNull Response<List<PinResponse>> response) {
                if(response.isSuccessful()) {
                    List<Pin> pins = new ArrayList<>();
                    List<PinResponse> pins_response = response.body();

                    for (int i=0; i<pins_response.size(); i++) {
                        PinResponse pin = pins_response.get(i);

                        ArrayList<Map<String,Object>> media = pin.getMedia();
                        StringBuilder urls = new StringBuilder();
                        for (int e=0; e < media.size(); e++) {
                            urls.append((String) media.get(e).get("media_file"));
                            urls.append("; ");
                        }

                        Pin new_pin = new Pin(pin.getId(),urls.toString(),pin.getPin_name(),
                                pin.getPin_desc(), pin.getPin_lat(),pin.getPin_lng(),
                                pin.getPin_alt());

                        pins.add(new_pin);
                    }

                    insert(pins);
                    Log.i("All pins fetched!", "Response code: " + response.code());
                }
                else{
                    Log.e("main", "onFailure: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PinResponse>> call, @NonNull Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public static LiveData<List<Pin>> getAllPins(){
        return allPins;
    }

    public static LiveData<Pin> getPinById(String id) {
        return pinDAO.getPinById(id);
    }

    private static class InsertAsyncTask extends AsyncTask<List<Pin>,Void,Void> {
        private PinDAO pinDAO;

        public InsertAsyncTask(PinDAO catDao) {
            this.pinDAO=catDao;
        }

        @Override
        protected Void doInBackground(List<Pin>... lists) {
            pinDAO.insert(lists[0]);
            return null;
        }
    }

}
