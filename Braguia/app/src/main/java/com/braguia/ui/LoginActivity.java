package com.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.braguia.R;
import com.braguia.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

	private EditText email_input;
	private EditText password_input;

	private UserViewModel mUserViewModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Bundle extras = getIntent().getExtras();

		String logout = extras.getString("logout");


		// Get a new or existing ViewModel from the ViewModelProvider.
		mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

		email_input = (EditText) findViewById(R.id.email_input);
		password_input = (EditText) findViewById(R.id.password_input);

		RelativeLayout btnLogin = (RelativeLayout) findViewById(R.id.rectangle_1383);

		boolean already_logged = mUserViewModel.check_last_login();
		if(already_logged && !logout.equals("true")) {
			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(intent);
		}
		else {
			btnLogin.setOnClickListener(view -> {
				String username = email_input.getText().toString();
				String password = password_input.getText().toString();

				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
					Toast.makeText(LoginActivity.this, "Nome de utilizador e password obrigatórios.", Toast.LENGTH_LONG).show();
				} else {
					String u = username.replaceAll("\\s+", "");
					String p = password.replaceAll("\\s+", "");

					boolean sucess = mUserViewModel.login(u, p);
					if (sucess) {
						Toast.makeText(LoginActivity.this, "Login efetuado com sucesso.", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(LoginActivity.this, "Nome de utilizador ou password errados.", Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}
}