package com.example.myaccount;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.dataBase.DataBase;
import com.example.myaccount.dataBase.user.User;

import java.util.List;

public class LoginLow extends AppCompatActivity {

    private DataBase accountDataBase;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView createText;
    public User user;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_low);
        accountDataBase = DataBase.getInstance(this);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        createText = findViewById(R.id.create);

        loginButton.setOnClickListener(view ->{
            if(usernameInput.getText().toString().equals("") || passwordInput.getText().toString().equals("")){
                Toast.makeText(this,"请输入完整的用户名和密码",Toast.LENGTH_LONG).show();
            }
            else{
                new getUser(usernameInput.getText().toString(),passwordInput.getText().toString()).execute();
                if(flag){

                }
                else{
                    Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

        createText.setOnClickListener(view -> {
            Intent intent= new Intent(LoginLow.this,CreateUser.class);
            startActivity(intent);
        });
    }


    public class getUser extends AsyncTask<Void,Void,Void>{
        private String username;
        private String password;
        getUser(String username,String password){
            this.username = username;
            this.password = password;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            List<User>u =  accountDataBase.getUserDao().getUser(username,password);
            if(u.isEmpty())flag=false;
            else{
                flag = true;
                user = u.get(0);
                Intent intent = new Intent(LoginLow.this,MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
            return null;
        }
    }
}