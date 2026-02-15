package com.braguia.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.braguia.SessionManager;
import com.braguia.model.User;
import com.braguia.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private final LiveData<List<User>> mAllUsers;

    private final List<String> mAllEmails;

    private LiveData<User> loggedUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
        mAllEmails = mRepository.getAllEmails();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public List<String> getAllEmails() { return mAllEmails; }

    public boolean login(String username, String password) {
        mRepository.makeLoginRequest(username,password);
        String sessionId = UserRepository.getmSessionManager().fetchSessionId();
        String token = UserRepository.getmSessionManager().fetchAuthToken();

        if(sessionId!=null && token!=null) {
            mRepository.defineLoggedUserRequest("csrftoken=" + token + "; sessionid=" + sessionId);
            return true;
        }
        else {
            return false;
        }
    }

    public SessionManager getSession() {
        return mRepository.getmSessionManager();
    }

    public void logout() {
        mRepository.logout();
    }

    public boolean check_last_login() {
        String sessionId = UserRepository.getmSessionManager().fetchSessionId();
        String token = UserRepository.getmSessionManager().fetchAuthToken();

        return sessionId != null && token != null;
    }

    //public LiveData<User> getLoggedUser() { return mRepository.getLoggedUser(username); }

    //public void setLoggedUser(String username) { loggedUser = mRepository.getLoggedUser(username); }

}
