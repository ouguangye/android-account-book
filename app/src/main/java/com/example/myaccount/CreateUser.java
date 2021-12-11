package com.example.myaccount;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.dataBase.DataBase;
import com.example.myaccount.dataBase.user.User;

public class CreateUser extends AppCompatActivity {

    private EditText usernameRegister;
    private EditText passwordRegister;
    private EditText confirmPassword;
    private Button registerButton;
    private DataBase accountDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.register);
        accountDataBase = DataBase.getInstance(this);

        registerButton.setOnClickListener(view -> {
            if(usernameRegister.getText().toString().equals("")||
                    passwordRegister.getText().toString().equals("")||
                    confirmPassword.toString().equals("")){
                Toast.makeText(this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
            }
            else if(!passwordRegister.getText().toString().equals(confirmPassword.getText().toString())){
                Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
            }
            else{
                new register(usernameRegister.getText().toString(),passwordRegister.getText().toString()).execute();
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateUser.this,LoginLow.class);
                startActivity(intent);
            }
        });
    }

    private class register extends AsyncTask<Void,Void,Void>{
        private String username;
        private String password;

        register(String username,String password){
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDataBase.getUserDao().addUser(new User(username,password));
            return null;
        }
    }
}