package br.com.whatsapp.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.whatsapp.cursoandroid.whatsapp.R;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void abrirCadastroUsuario(View view){
        Intent i = new Intent(LoginActivity.this,CadastroUsuarioActivity.class);
        startActivity(i);
    }

}
