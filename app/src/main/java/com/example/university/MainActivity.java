package com.example.university;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView domains, name, state_province, country, alpha_two_code, website;
    private WebView webView;
    private List<UniversityData> universityList;
    private String University = "JNU";

    private Handler handler = new Handler();
    private static final int REFRESH_INTERVAL = 10000; // 10 seconds

    private Runnable fetchDataRunnable = new Runnable() {
        @Override
        public void run() {
            // Fetch data from the API here
            fetchDataFromApi(); // Call the API data fetch method

            // Show a toast for demonstration purposes
            Toast.makeText(getApplicationContext(), "Data refreshed!", Toast.LENGTH_SHORT).show();

            // Schedule the next refresh
            handler.postDelayed(this, REFRESH_INTERVAL);
        }
    };

    //open
    public void openWebView(View view) {
        // Get the URL from the "Website" TextView
        Log.d("MainActivity", "openWebView called");
        TextView websiteTextView = findViewById(R.id.website);
        String url = websiteTextView.getText().toString();

        // Find your WebView by its ID
        WebView webView = findViewById(R.id.webView);

        // Load the URL in your WebView
        webView.loadUrl(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        universityList = new ArrayList<>();

        if (getIntent().getStringExtra("university") != null)
            University = getIntent().getStringExtra("university");

//        website = findViewById(R.id.website);
//        webView = findViewById(R.id.webView);
//
//        website.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the URL from the TextView
//                String url = website.getText().toString();
//
//                // Load the URL in your WebView
//                webView.loadUrl(url);
//            }
//        });
//
//        String url = getIntent().getStringExtra("url");
//        if (url != null) {
//            webView.loadUrl(url);
//        }
//        else{
//            String urlToLoad = "https://example.com"; // Replace with your URL
//            webView.loadUrl(urlToLoad);
//        }

//        webView.setVisibility(View.VISIBLE);
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true); // Enable zoom controls
//        webView.getSettings().setDisplayZoomControls(false); // Hide zoom controls

//        String urlToLoad = "https://example.com"; // Replace with your URL
//        webView.loadUrl(urlToLoad);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // Load the URL in the WebView
//                view.loadUrl(url);
//                return true;
//            }
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                Log.e("WebView", "Error: " + error.getDescription());
//            }
//
//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
//                Log.e("WebView", "HTTP Error: " + errorResponse.getStatusCode());
//            }
//        });


        init(); // all initialization

        TextView name = findViewById(R.id.name);
        name.setText(University);
        name.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, UniversityListActivity.class)));

        // Schedule the data refresh task
        handler.postDelayed(fetchDataRunnable, REFRESH_INTERVAL);
    }

    private void fetchDataFromApi() {
        ApiUtilities.getUniversityApi().getUniversityData().enqueue(new Callback<List<UniversityData>>() {
            @Override
            public void onResponse(Call<List<UniversityData>> call, Response<List<UniversityData>> response) {
                universityList.addAll(response.body());
                for (int i = 0; i < universityList.size(); i++) {
                    if (universityList.get(i).getName().equals(University)) {
                        Vector<String> domain = universityList.get(i).getDomains();
                        String nam = universityList.get(i).getName();
                        String stat_province = universityList.get(i).getState_province();
                        Vector<String> web_pag = universityList.get(i).getWeb_pages();
                        String country1 = universityList.get(i).getCountry();
                        String alpha_two_cod = universityList.get(i).getAlpha_two_code();

                        StringBuilder domainBuilder = new StringBuilder();

                        for (String dmn : domain) {
                            domainBuilder.append(dmn).append(", ");
                        }
                        String domainText = domainBuilder.toString();
                        if (domainText.endsWith(", ")) {
                            domainText = domainText.substring(0, domainText.length() - 2);
                        }
                        domains.setText(domainText);

                        StringBuilder webpageBuilder = new StringBuilder();

                        for (String wbg : web_pag) {
                            webpageBuilder.append(wbg).append(", ");
                        }
                        String webpageText = webpageBuilder.toString();
                        if (webpageText.endsWith(", ")) {
                            webpageText = webpageText.substring(0, webpageText.length() - 2);
                        }
                        website.setText(webpageText);
                        name.setText(nam);
                        state_province.setText(stat_province);
                        country.setText(country1);
                        alpha_two_code.setText(alpha_two_cod);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UniversityData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        domains = findViewById(R.id.domains);
        name = findViewById(R.id.name);
        state_province = findViewById(R.id.state_province);
        website = findViewById(R.id.website);
        country = findViewById(R.id.country);
        alpha_two_code = findViewById(R.id.alpha_two_code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the callback to stop the data refresh task when the activity is destroyed
        handler.removeCallbacks(fetchDataRunnable);
    }
}
