package com.anilabhapro.demojobs;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    PhotoView ig;
    String sharejob;
    int index = 0;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchJobs();

    }


    public void fetchJobs() {
        ProgressDialog pro = new ProgressDialog(this);
        pro.setMessage("Loding....");
        pro.show();

        String url = "https://jobs.github.com/positions.json?";
        ig = findViewById(R.id.logo);
        txt = findViewById(R.id.cpname);

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        try {

                            int length = response.length();


                            JSONObject object = response.getJSONObject(index);

                            String ur = object.getString("company_logo");
                            String Applyurl = object.getString("url");
                            String company = object.getString("company");
                            String type = object.getString("type");


                            String location = object.getString("location");
                            String title = object.getString("title");
                            String time = object.getString("created_at");


                            Log.d("Url is:", ur);
                            Glide.with(MainActivity.this).load(ur)
                                    .placeholder(R.drawable.loading_pro).into(ig);


                            txt.setText("Company Name : " + company + "\nTitle : " + title + "\nType : " + type + "\nLocation : " + location + "\nPosted on : " + time);


                            sharejob = Applyurl;
                            index++;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            pro.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Loding Failed!!", Toast.LENGTH_SHORT).show();
                        pro.dismiss();


                    }
                });
//        queue.add(jsonObjectRequest);

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


    //for backjobs

    public void backjobs() {

        ProgressDialog pro = new ProgressDialog(this);
        pro.setMessage("Loding....");
        pro.show();

        String url = "https://jobs.github.com/positions.json?";
        ig = findViewById(R.id.logo);
        txt = findViewById(R.id.cpname);

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        index--;

                        try {

                            if (index>0) {
                                while (index >= 0) {

                                    int length = response.length();


                                    JSONObject object = response.getJSONObject(--index);
                                    index++;


                                    String ur = object.getString("company_logo");
                                    String Applyurl = object.getString("url");
                                    String company = object.getString("company");
                                    String type = object.getString("type");


                                    String location = object.getString("location");
                                    String title = object.getString("title");
                                    String time = object.getString("created_at");


                                    //Log.d("Url is:", ur);
                                    Log.d("Index :", String.valueOf(index));
                                    Glide.with(MainActivity.this).load(ur)
                                            .placeholder(R.drawable.loading_pro).into(ig);


                                    txt.setText("Company Name : " + company + "\nTitle : " + title + "\nType : " + type + "\nLocation : " + location + "\nPosted on : " + time);


                                    sharejob = Applyurl;
                                    break;
                                }
                            }
                            else{
                                int length = response.length();


                                JSONObject object = response.getJSONObject(0);
                                index++;
                                Toast.makeText(MainActivity.this, "Not Allowed To Back!!", Toast.LENGTH_LONG).show();



                                String ur = object.getString("company_logo");
                                String Applyurl = object.getString("url");
                                String company = object.getString("company");
                                String type = object.getString("type");


                                String location = object.getString("location");
                                String title = object.getString("title");
                                String time = object.getString("created_at");


                                //Log.d("Url is:", ur);
                                Log.d("Index :", String.valueOf(index));
                                Glide.with(MainActivity.this).load(ur)
                                        .placeholder(R.drawable.loading_pro).into(ig);


                                txt.setText("Company Name : " + company + "\nTitle : " + title + "\nType : " + type + "\nLocation : " + location + "\nPosted on : " + time);


                                sharejob = Applyurl;


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            pro.dismiss();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Loding Failed!!", Toast.LENGTH_SHORT).show();
                        pro.dismiss();


                    }
                });


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


    public void showNextMeme(View view) {

        fetchJobs();
    }

    public void backjob(View view) {


        backjobs();

    }


    public void Apply(View view) {
        try {
            Uri uri = Uri.parse("googlechrome://navigate?url=" + sharejob);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is probably not installed
            Log.d("error", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            Intent myintent = new Intent(Intent.ACTION_SEND);
            myintent.setType("text/plan");

            String shereBoday = "Your Apps are Here";

            String shereSub = sharejob;
            myintent.putExtra(Intent.EXTRA_SUBJECT, shereBoday);
            myintent.putExtra(Intent.EXTRA_TEXT, shereSub);
            startActivity(Intent.createChooser(myintent, "Share Using"));
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        builder.setMessage("Do you want to exit ?");


        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                finish();
                            }
                        });

        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}


