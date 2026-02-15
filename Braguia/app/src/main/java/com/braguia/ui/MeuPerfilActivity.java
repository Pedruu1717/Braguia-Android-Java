package com.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.braguia.R;
import com.braguia.model.User;
import com.braguia.viewModel.UserViewModel;

public class MeuPerfilActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        // Get a new or existing ViewModel from the ViewModelProvider.

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mUserViewModel.getAllUsers().observe(this, user -> {
            if (user.isEmpty()) {
                Log.i("EMPTY", "EMPTY");
            }
            else {
                loggedUser = user.get(0);
                Log.i("NICE", user.get(0).getUsername());

                //TODO Mostrar info do user nesta atividade
                String first_name = loggedUser.getFirstName();
                String last_name = loggedUser.getLastName();
                String username = loggedUser.getUsername();
                String email = loggedUser.getEmail();
                String user_type = loggedUser.getUserType();
                Boolean is_staff = loggedUser.getIsStaff();
                String date_joined = loggedUser.getDateJoined();

                TextView name_tv = findViewById(R.id.nome);
                TextView username_tv = findViewById(R.id.username);
                TextView email_tv = findViewById(R.id.email);
                TextView user_type_tv = findViewById(R.id.user_type);
                TextView is_staff_tv = findViewById(R.id.is_staff);
                TextView date_joined_tv = findViewById(R.id.date_joined);

                name_tv.append(first_name +" "+ last_name);
                username_tv.append(username);
                if(!email.isEmpty()){ email_tv.append(email); }
                user_type_tv.append(user_type);
                is_staff_tv.append(is_staff.toString());
                date_joined_tv.append(date_joined);

                RelativeLayout logout_btn = findViewById(R.id.rectangle_1386);
                logout_btn.setOnClickListener(view -> {
                    mUserViewModel.logout();
                    Toast.makeText(MeuPerfilActivity.this, "Sessão terminada com sucesso.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MeuPerfilActivity.this, LoginActivity.class);
                    intent.putExtra("logout","true");
                    startActivity(intent);
                });

            }
        });



    }
}