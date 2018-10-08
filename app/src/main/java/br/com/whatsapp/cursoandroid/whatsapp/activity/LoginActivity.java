package br.com.whatsapp.cursoandroid.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import br.com.whatsapp.cursoandroid.whatsapp.Manifest;
import br.com.whatsapp.cursoandroid.whatsapp.R;
import br.com.whatsapp.cursoandroid.whatsapp.helper.Permissao;
import br.com.whatsapp.cursoandroid.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {
    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codArea;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        nome       = (EditText) findViewById(R.id.edit_nome);
        telefone   = (EditText) findViewById(R.id.edit_telefone);
        codPais    = (EditText) findViewById(R.id.edit_cod_pais);
        codArea    = (EditText) findViewById(R.id.edit_cod_area);
        cadastrar  = (Button) findViewById(R.id.bt_cadastrar);

        /*Definir mascaras*/
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher maskCodPais = new MaskTextWatcher(codPais, simpleMaskCodPais);
        MaskTextWatcher maskCodArea = new MaskTextWatcher(codArea, simpleMaskCodArea);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);

        codPais.addTextChangedListener( maskCodPais );
        codArea.addTextChangedListener( maskCodArea );
        telefone.addTextChangedListener( maskTelefone );

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString() +
                                codArea.getText().toString() +
                                telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                //Gerar token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt( 9999 - 1000 ) + 1000;
                String token = String.valueOf( numeroRandomico );
                String mensagemEnvio = "WhatsApp Código de Confirmação: " + token;

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias( LoginActivity.this );
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                //Envio do SMS
                telefoneSemFormatacao = "5554";
                boolean enviadoSMS =  enviaSMS( "+" + telefoneSemFormatacao, mensagemEnvio );

                /*
                HashMap<String, String> usuario = preferencias.getDadosusuario();
                Log.i("TOKEN", "NOME:" + usuario.get("nome") + " FONE: "+ usuario.get("telefone") );
                */
            }
        });


    }

    /*Envio do SMS*/
    private boolean enviaSMS(String telefone, String mensagem){

        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for( int resultado : grantResults ){

            if( resultado == PackageManager.PERMISSION_GRANTED){
                alertaValidacaoPermissao();
            }

        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
