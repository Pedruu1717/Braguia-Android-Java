package com.braguia.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.braguia.RetrofitHelper;
import com.braguia.SessionManager;
import com.braguia.model.BraguiaDatabase;
import com.braguia.model.LoginRequest;
import com.braguia.model.LoginResponse;
import com.braguia.model.User;
import com.braguia.model.UserAPI;
import com.braguia.model.UserDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDAO mUserDAO;
    private LiveData<List<User>> mAllUsers;
    private List<String> mAllEmails;

    private static SessionManager mSessionManager;

    // Note that in order to unit test the app you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public UserRepository(Application application) {
        BraguiaDatabase db = BraguiaDatabase.getInstance(application);
        mUserDAO = db.userDAO();
        mAllUsers = mUserDAO.getAlphabetizedUsers();
        mAllEmails = mUserDAO.getAllEmails();
        mSessionManager = new SessionManager(application.getApplicationContext());
    }

    /* Get API */
    public static UserAPI getUserAPI() {
        UserAPI api = RetrofitHelper.getRetrofit().create(UserAPI.class);

        return api;
    }

    //public LiveData<User> getLoggedUser(String username) { return mUserDAO.getUser(username); }

    public static SessionManager getmSessionManager() {
        return mSessionManager;
    }

    public void login(List<String> cookies) {  new LoginAsyncTask(mSessionManager).execute(cookies); }
    public void logout() {
        getUserAPI().logout();
        mUserDAO.deleteAll();
        new LogoutAsyncTask(mSessionManager).execute();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public List<String> getAllEmails() { return mAllEmails; }

    /* Get logged user and store it in local DB */
    public void defineLoggedUserRequest(String tokenAndSessionId) {
        Call<User> call = getUserAPI().getUser(tokenAndSessionId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.isSuccessful()) {
                    insert(response.body());
                    Log.i("Logged user fetched!", "Response code: " + response.code());
                }
                else {
                    Log.e("main", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public void makeLoginRequest(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        Call<LoginResponse> call = getUserAPI().login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    login(response.headers().values("Set-Cookie"));
                    Log.i("Login Successful", "Response code: " + response.code());
                }
                else {
                    Log.e("main", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }

        });
    }

    private static class LoginAsyncTask extends AsyncTask<List<String>,Void,Void> {
        private SessionManager sessionManager;

        public LoginAsyncTask(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
        }

        @Override
        protected Void doInBackground(List<String>... cookieList) {
            String authToken = (cookieList[0].get(0).split("="))[1].split(";")[0];
            String sessionId = (cookieList[0].get(1).split("="))[1].split(";")[0];

            sessionManager.saveAuthToken(authToken, sessionId);
            UserRepository.mSessionManager = sessionManager;
            Log.i("Session Active", "CSRF Token: " + sessionManager.fetchAuthToken() + " Session ID: " + sessionManager.fetchSessionId());

            return null;
        }
    }


    private static class LogoutAsyncTask extends AsyncTask<Void,Void,Void> {
        private SessionManager sessionManager;

        public LogoutAsyncTask(SessionManager sessionManager) {
            this.sessionManager = sessionManager;
        }

        @Override
        protected Void doInBackground(Void... x) {
            sessionManager.saveAuthToken(null, null);
            UserRepository.mSessionManager = sessionManager;
            Log.i("Logged Out", "Logged out successfully.");

            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<User,Void,Void> {
        private UserDAO userDAO;

        public InsertAsyncTask(UserDAO catDao) {
            this.userDAO=catDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDAO.insert(user[0]);
            return null;
        }
    }

    public void insert(User user){
        new UserRepository.InsertAsyncTask(mUserDAO).execute(user);
    }

}