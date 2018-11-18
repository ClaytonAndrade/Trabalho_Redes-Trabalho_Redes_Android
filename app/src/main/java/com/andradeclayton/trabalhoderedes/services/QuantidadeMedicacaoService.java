package com.andradeclayton.trabalhoderedes.services;

import com.andradeclayton.trabalhoderedes.models.QuantidadeMedicacao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QuantidadeMedicacaoService {

    public static final String BASE_URL = "";

    @POST("quantidademedicacao/consult")
    Call<String> resultado(@Body QuantidadeMedicacao quantidadeMedicacao);
}
