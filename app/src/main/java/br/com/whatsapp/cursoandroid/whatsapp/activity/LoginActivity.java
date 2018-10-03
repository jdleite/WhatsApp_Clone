package br.com.whatsapp.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.whatsapp.cursoandroid.whatsapp.R;
import br.com.whatsapp.cursoandroid.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone;
    private EditText codPais;
    private EditText nome;
    private EditText codArea;

    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = (EditText) findViewById(R.id.edit_telefone);
        codPais = (EditText) findViewById(R.id.edit_cod_pais);
        codArea = (EditText) findViewById(R.id.edit_cod_area);
        nome = (EditText) findViewById(R.id.edit_nome);
        cadastrar = (Button) findViewById(R.id.bt_cadastrar);



        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");


        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskPais = new MaskTextWatcher(codPais, simpleMaskCodPais);

        MaskTextWatcher maskArea = new MaskTextWatcher(codArea, simpleMaskCodArea);

        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);

        telefone.addTextChangedListener(maskTelefone);
        codPais.addTextChangedListener(maskPais);
        codArea.addTextChangedListener(maskArea);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto = codPais.getText().toString() + codArea.getText().toString() + telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");

                Random random = new Random();
                int numeroAleatorio = random.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroAleatorio);

                Preferencias preferencias = new Preferencias(getApplicationContext());

                preferencias.salvarUsuarioPreferencias(nomeUsuario,telefoneSemFormatacao,token);

                /*
                HashMap<String,String> usuario = preferencias.getDadosUsuario();

                Log.i("TOKEN","NOME" + usuario.get("nome") + "FONE" + usuario.get("telefone"));

                */



            }
        });


       // Intent i = new Intent(LoginActivity.this,ValidadorActivity.class);

       // startActivity(i);

    }
}
