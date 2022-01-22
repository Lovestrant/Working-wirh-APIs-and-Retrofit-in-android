package com.example.androidapis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostToApi extends AppCompatActivity {
    private EditText nameEdt, jobEdt;
    private TextView displayRes;
    private Button postBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_api);

        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postBtn = findViewById(R.id.idBtnPost);
        displayRes = findViewById(R.id.idTVResponse);
        progressBar = findViewById(R.id.idLoadingPB);

        //set onclick listerner
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEdt.getText().toString().isEmpty()){
                   nameEdt.setError("Fill this field");
                   return;
                }else if(jobEdt.getText().toString().isEmpty()) {
                    jobEdt.setError("Fill this field");
                    return;
                }
                //post data to Api
                postData(nameEdt.getText().toString(), jobEdt.getText().toString());
            }

            private void postData(String name, String job) {
                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://reqres.in/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                //create instance for new API class
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                //Passing data from our textviews to dtamodal class
                DataModal dataModal = new DataModal(name, job);

                //call method to create a post and passing our DataModal class
                Call<DataModal> call = retrofitAPI.createPost(dataModal);

                //executing our method
                call.enqueue(new Callback<DataModal>() {
                    @Override
                    public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                    //Display Toast message if success
                        Toast.makeText(getApplicationContext(),"Inserted successfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        //set edittexts to null
                        nameEdt.setText("");
                        jobEdt.setText("");

                        //get response from body and pass to our modal class
                        DataModal responseFromAPI = response.body();
                        String res = "job: " + responseFromAPI.getJob() + "/n" + "Name: " + responseFromAPI.getName();

                        //Set response to textView
                        displayRes.setText(res);
                    }

                    @Override
                    public void onFailure(Call<DataModal> call, Throwable t) {
                        displayRes.setText("Error found is: " + t.getMessage());
                    }
                });
            }
        });

    }
}