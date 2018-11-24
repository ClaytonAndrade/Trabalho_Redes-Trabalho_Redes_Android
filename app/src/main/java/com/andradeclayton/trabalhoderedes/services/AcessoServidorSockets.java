package com.andradeclayton.trabalhoderedes.services;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AcessoServidorSockets extends AsyncTask {
    // DEVE COLOCAR AQUI O IP DA SUA MÁQUINA
    private static String IP = "172.22.25.156";
    private static int PORT = 4500;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public AcessoServidorSockets(){

    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            socket = new Socket(IP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            Log.d("DEBUG ERROR", e.toString());
        }
        return null;
    }

    public boolean sendMessage(String message){
        out.println(message);
        return true;
    }

    private void closeAll(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
