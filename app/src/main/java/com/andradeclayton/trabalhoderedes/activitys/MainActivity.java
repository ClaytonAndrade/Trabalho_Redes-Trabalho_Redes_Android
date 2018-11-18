package com.andradeclayton.trabalhoderedes.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.andradeclayton.trabalhoderedes.R;
import com.andradeclayton.trabalhoderedes.models.QuantidadeMedicacao;
import com.andradeclayton.trabalhoderedes.services.QuantidadeMedicacaoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Spinner  spn_faixa_etaria;
    Spinner  spn_medicamento;
    EditText edt_peso_paciente;
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

        spn_faixa_etaria     = (Spinner)  findViewById(R.id.spinner_faixa_etaria);
        spn_medicamento      = (Spinner)  findViewById(R.id.spinner_tipo_remedios);
        edt_peso_paciente    = (EditText) findViewById(R.id.edt_peso_paciente);
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

    public String RequestConsulta(QuantidadeMedicacao quantidadeMedicacao){

        final String[] resposta = new String[1];
        final String respostaRequest = "A medida nào pode ser calculada";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QuantidadeMedicacaoService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuantidadeMedicacaoService service = retrofit.create(QuantidadeMedicacaoService.class);
        Call<String> reCall = service.resultado(quantidadeMedicacao);

        reCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e(TAG, "Erro: " + response.code());
                }else{
                     resposta[0] = response.body();

                    //respostaRequest = response.raw();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });

        return respostaRequest;
    }

    public void CalcularDosagem(View view) {

    }
}
