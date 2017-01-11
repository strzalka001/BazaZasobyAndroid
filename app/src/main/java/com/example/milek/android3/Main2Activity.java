package com.example.milek.android3;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Main2Activity extends AppCompatActivity{
    private static final String QUERY_URL = "http://openlibrary.org/search.json?q=";
    JSONAdapter mJSONAdapter;
    Button back;
    Button szukaj;
    EditText editText;
    ListView listView;
    ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        back = (Button) findViewById(R.id.powrot);
        listView = (ListView) findViewById(R.id.lista);
        szukaj = (Button) findViewById(R.id.szukaj);
        editText = (EditText) findViewById(R.id.editText);
        addListenerOnBack();
        addListenerOnButton();
        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());
        listView.setAdapter(mJSONAdapter);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Searching for Book");
        mDialog.setCancelable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 12. Now that the user's chosen a book, grab the cover data
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
                String coverID = jsonObject.optString("cover_i","");

// create an Intent to take you over to a new DetailActivity
                Intent detailIntent = new Intent(getApplicationContext(),DetailActivity.class);

// pack away the data about the cover
// into your Intent before you head out
                detailIntent.putExtra("coverID", coverID);

// TODO: add any other data you'd like as Extras

// start the next Activity using your prepared Intent
                startActivity(detailIntent);
            }
        });
    }
    public void addListenerOnBack() {

        final Context context = this;


        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();

            }

        });
    }
    public void addListenerOnButton() {

        final Context context = this;


        szukaj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                queryBooks(editText.getText().toString());

            }

        });
    }
    private void queryBooks(String searchString) {

        // Prepare your search string to be put in a URL
        // It might have reserved characters or something
        String urlString = "";
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            // if this fails for some reason, let the user know why
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();
// Show ProgressDialog to inform user that a task in the background is occurring
        mDialog.show();
        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(QUERY_URL + urlString,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // 11. Dismiss the ProgressDialog
                        mDialog.dismiss();
                        // Display a "Toast" message
                        // to announce your success
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();

                        // 8. For now, just log results
                        Log.d("MAMY TO : ", jsonObject.toString());

                        // I WYSWITLAMY
                        mJSONAdapter.updateData(jsonObject.optJSONArray("docs"));
                    }
                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        // 11. Dismiss the ProgressDialog
                        mDialog.dismiss();
                        // Display a "Toast" message
                        // to announce the failure
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        // Log error message
                        // to help solve any problems
                        Log.e("MAMY TO : ", statusCode + " " + throwable.getMessage());
                    }
                });
    }

}
