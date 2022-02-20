package net.ivanvega.peticionesredhttpa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txt ;
    RequestQueue queue;
    ImageView miImg;
    RecyclerView recyclerView;
    ArrayList<Heroe> listaAlbum;
    FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAlbum = new ArrayList<>();
        recyclerView = findViewById(R.id.RecyclerView);
        btn = findViewById(R.id.btnRequest);
        //miImg = findViewById(R.id.imgVoll);
        /*findViewById(R.id.btnStringReq).setOnClickListener(v->stringRequest());
        findViewById(R.id.btnJsonReq).setOnClickListener(v->jsonRequest());
        findViewById(R.id.btnImgReq).setOnClickListener(v->requestImageMethod());*/

        queue = Volley.newRequestQueue(this);
        stringRequest();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), listaAlbum);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });

    }


    private void stringRequest() {
        // Instantiate the RequestQueue.

        String url ="https://simplifiedcoding.net/demos/view-flipper/heroes.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Heroe> lista = new ArrayList<>();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("JSONOBJECT", obj.getString("heroes"));
                            JSONArray obj2 = new JSONArray(obj.getString("heroes"));
                            Log.d("objeto", obj2.toString());
                            for (int i=0; i<obj2.length();i++) {
                                JSONObject e = obj2.getJSONObject(i);
                                Heroe heroe = new Heroe();
                                heroe.setName( e.getString("name"));
                                heroe.setUrl( e.getString("imageurl"));
                                lista.add(heroe);
                                Log.d("JSONREQUESTMAPA", "name: " + e.getString("name") + " url: " +
                                        e.getString("imageurl") );
                            }
                            listaAlbum = lista;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("That didn't work!" + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void jsonRequest() {
        JsonRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://simplifiedcoding.net/demos/view-flipper/heroes.php",
                null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Log.d("array", response.toString());
                    }
        },
                error -> {
                    error.printStackTrace();
                }
        );

        queue.add(jsonRequest);
        }

    void requestImageMethod(){
        ImageRequest imgReq = new
                ImageRequest("https://via.placeholder.com/150/771796",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        miImg.setImageBitmap(response);
                    }

                    },200,200,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                error -> {
                    error.printStackTrace();
                }
                );
        MySingleton.getInstance(this).addToRequestQueue(imgReq);
    }

}