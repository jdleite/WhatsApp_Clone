package br.com.whatsapp.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import br.com.whatsapp.cursoandroid.whatsapp.R;
import br.com.whatsapp.cursoandroid.whatsapp.config.ConfiguracaoFirebase;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("pontossss").setValue("405");

    }

    public void abrirCadastroUsuario(View view) {
        Intent i = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(i);
    }

}
