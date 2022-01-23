package com.example.androidapis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update extends AppCompatActivity {
    private EditText name, job; private Button btn; private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //bind

        btn = findViewById(R.id.BtnReset);
        display = findViewById(R.id.displayResErr);
        job = findViewById(R.id.job);
        name = findViewById(R.id.name);

        //on click Listener

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(job.getText().toString().isEmpty()) {
                    job.setError("This field is required");
                    return;
                }else if(name.getText().toString().isEmpty()) {
                    name.setError("Name is required");
                    return;
                }
            //call method to update our data
            updateData(name.getText().toString(),job.getText().toString());

            }
        });

    }

    private void updateData(String name, String job) {
        //call Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating an instance of retrofit API
        RetrofitApiUpdate retrofitApi = retrofit.create(RetrofitApiUpdate.class);

        //Pass data to the Modal class
        DataModal dataModal = new DataModal(name,job);

        //call method to update and pass modal

        Call<DataModal> call = retrofitApi.updateData(dataModal);

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {

                Toast.makeText(getApplicationContext(), "Update success", Toast.LENGTH_SHORT).show();

                //Get response and pass it to the modal class
                DataModal responseAPI = response.body();

                //Display code, name, and job to client side
                display.setText("Response code: " + response.code() + "\nName: "+ responseAPI.getName() +"\n Job: " + responseAPI.getJob());
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
            display.setError("Error: \n" + t.getMessage());

            }
        });
    }
}