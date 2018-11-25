package com.andradeclayton.trabalhoderedes.activitys;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andradeclayton.trabalhoderedes.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Spinner  spn_faixa_etaria;
    Spinner  spn_medicamento;
    EditText edt_ip;
    Button   btn_calcular_dosagem;
    TextView txv_resultado;

    private String faixa_etaria;
    private String medicamento;

    ArrayAdapter<String> arrayAdapter_spn_faixa_etaria_itens;
    ArrayAdapter<String> arrayAdapter_spn_medicamentos_itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        spn_faixa_etaria     = (Spinner)  findViewById(R.id.spinner_faixa_etaria);
        spn_medicamento      = (Spinner)  findViewById(R.id.spinner_tipo_remedios);
        edt_ip               = (EditText) findViewById(R.id.edt_ip);
        btn_calcular_dosagem = (Button)   findViewById(R.id.btn_calcular);
        txv_resultado        = (TextView) findViewById(R.id.txv_resultado);

        arrayAdapter_spn_faixa_etaria_itens = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.faixa_etaria));
        arrayAdapter_spn_faixa_etaria_itens.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_faixa_etaria.setAdapter(arrayAdapter_spn_faixa_etaria_itens);

        spn_faixa_etaria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faixa_etaria = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayAdapter_spn_medicamentos_itens = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.medicamentos));
        arrayAdapter_spn_medicamentos_itens.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn_medicamento.setAdapter(arrayAdapter_spn_medicamentos_itens);

        spn_medicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medicamento = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void CalcularDosagem(View view) {
        final String ip;
        final String mensagem;
        boolean validacaoip = false;

        hideKeyboard(this, view);

        if(!validaIp()){
            return;
        }
        mensagem = faixa_etaria + " " + medicamento;
        ip = edt_ip.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                conectarSocket(mensagem, ip);
            }
        }).start();
    }

    private void conectarSocket(String msg, String ip) {
        Socket socket = null;
        String mens = "Nào conseguimos manter uma conexão";
        DataOutputStream canalSaida = null;
        DataInputStream canalEntrada = null;

        try {

            socket = new Socket(ip, 8080);

            canalSaida = new DataOutputStream(socket.getOutputStream());
            canalSaida.writeUTF(msg);

            canalEntrada = new DataInputStream(socket.getInputStream());
            mens = canalEntrada.readLine();
            txv_resultado.setText(mens);


        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }finally {
            try{
                if(socket != null){
                    socket.close();
                }
                if(canalSaida != null){
                    canalSaida.close();
                }
                if(canalEntrada != null){
                    canalEntrada.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validaIp(){

        if(edt_ip.getText().toString().equals("")){
            AlertDialog alerta;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Atenção");
            builder.setMessage("O campo IP deve ser configurado");
            alerta = builder.create();
            alerta.show();

            edt_ip.requestFocus();

            return false;
        }

        return true;
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
