package com.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.braguia.R;
import com.braguia.viewModel.UserViewModel;


public class WelcomePageActivity extends AppCompatActivity {
	public static final int HOME_ACTIVITY_REQUEST_CODE = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomepage);

		// Get button FAB. ("Começar")
		RelativeLayout comecar_btn = findViewById(R.id.comecar_btn);
		UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

		// Start NewWordActivity when the user taps the button FAB. TODO mudar p loginActivity
		comecar_btn.setOnClickListener(view -> {
			Intent intent = new Intent(WelcomePageActivity.this, LoginActivity.class);
			if (userViewModel.check_last_login()) {
				intent.putExtra("logout", "false");
			} else {
				intent.putExtra("logout", "true");
			}
			startActivity(intent);

		});
	}

}
