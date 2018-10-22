package br.com.whatsapp.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.whatsapp.cursoandroid.whatsapp.R;
import br.com.whatsapp.cursoandroid.whatsapp.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {
    private Button botaoSair;
    private FirebaseAuth autenticacao;
    private android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth usuarioAutenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return  true;
            default:
                 return super.onOptionsItemSelected(item);
        }

    }

    public void deslogarUsuario(){

        usuarioAutenticacao.signOut();
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(i);
        finish();

    }
}