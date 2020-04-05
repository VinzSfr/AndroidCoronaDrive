package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        if(savedInstanceState!= null){
            if(!mTextViewResult.getText().equals(R.string.defaultMessage)){
                findViewById(R.id.button).setVisibility(View.INVISIBLE);
            }
        }
    }

    public void getNewTicket(View view) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://bca5bf5c.ngrok.io/api/GetNewTicket";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Toast.makeText(getApplicationContext(), "Numéro obtenu avec succès!", Toast.LENGTH_LONG);
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        });
    }
}