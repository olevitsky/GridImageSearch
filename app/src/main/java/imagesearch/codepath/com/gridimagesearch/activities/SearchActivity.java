package imagesearch.codepath.com.gridimagesearch.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import imagesearch.codepath.com.gridimagesearch.EndlessScrollListener;
import imagesearch.codepath.com.gridimagesearch.adapters.imageResultsAdapter;
import imagesearch.codepath.com.gridimagesearch.model.ImageFilter;
import imagesearch.codepath.com.gridimagesearch.model.ImageResult;
import imagesearch.codepath.com.gridimagesearch.R;


public class SearchActivity extends Activity {
    private final int REQUEST_CODE = 20;
    private EditText edQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private imageResultsAdapter aImageResults;
    private String searchQuery;
    private ImageFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        // create the data source
        imageResults = new ArrayList<ImageResult>();
        // attaches data source to adapter
        aImageResults = new imageResultsAdapter(this, imageResults);
        filter = new ImageFilter(0,0,0,"");
        // ling adapter to view
        gvResults.setAdapter(aImageResults);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if (isNetworkAvailable() & (totalItemsCount < 64) & (totalItemsCount != 0)) {
                    customLoadMoreDataFromApi(totalItemsCount);
                }
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });

    }

    private void setupViews() {
        edQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // launch image display activity
                // create intent
                // can't path just 'this' here because 'this' is anonymous class in this context
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                //get image results to display
                ImageResult result = imageResults.get(position);
                //i.putExtra("url", results.getFullUrl());
                i.putExtra("result", result);
                // pass image results to intent
                // launch new activity
                startActivity(i);
            }
        });
    }

    // old interface
    public void onImageSearch(View v) {

        // NOT USED - backup function ONLY!!!!!!!!!!!!!!!!!!



        searchQuery = edQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=andorid&rsz=8
        String searchString = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + searchQuery + "&rsz=8";
        client.get(searchString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageJsonResults = null;
                //Log.d("DEBUG", response.toString());
                try {
                    imageJsonResults = response.getJSONObject(
                            "responseData").getJSONArray("results");
                    aImageResults.clear();
                    imageResults.clear(); // clear on new search only
                    // when make changes to adapter it will modify underlining data automatcially
                    // two lines below can be combined into one
                    // does update and notify when applied to adapterdddddd
                    aImageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                    //aImageResults.notifyDataSetChanged(););
                    //imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                    //aImageResults.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("ImageResults", imageResults.toString());
            }
        });
        //Toast.makeText(this, "search text " + query, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                searchQuery = query;
                AsyncHttpClient client = new AsyncHttpClient();
                //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=andorid&rsz=8
                //String searchString = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
                String searchString = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8" +
                        "&start=" + 0 + "&v=1.0&q=" + Uri.encode(query) +
                        "&imgtype=" + filter.getImageTypeStr() +
                        "&imgsz=" + filter.getImageSizeStr() +
                        "&imgcolor=" + filter.getImageColorFilterStr() +
                        "&as_sitesearch=" + filter.getImageSite();
                if (isNetworkAvailable() && isOnline()) {
                    client.get(searchString, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            JSONArray imageJsonResults = null;
                            //Log.d("DEBUG", response.toString());
                            try {
                                imageJsonResults = response.getJSONObject(
                                        "responseData").getJSONArray("results");
                                aImageResults.clear();
                                imageResults.clear(); // clear on new search only
                                // when make changes to adapter it will modify underlining data automatcially
                                // two lines below can be combined into one
                                // does update and notify when applied to adapterdddddd
                                aImageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                                //aImageResults.notifyDataSetChanged(););
                                //imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                                //aImageResults.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("ImageResults", imageResults.toString());
                        }
                    });
                } else {
                    // TBD make into nice dialog.
                    Toast.makeText(getApplicationContext(), "Network is Down", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, FilterActivity.class);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            filter.SetFilter((ImageFilter) data.getExtras().getSerializable("filter"));
            //Log.d("FIlter", "DONE WIth filter settings");
            // Toast the name to display temporarily on screen
        }
        //Log.d("FIlter", "DONE WIth filter settings");
    }
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public Boolean isOnline() {
        // makes upp hanging.  Disabled for now
        return true;
        /*try {
            //Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            Process p1 = java.lang.Runtime.getRuntime().exec("ping  www.google.com");
            int returnVal = p1.waitFor();
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;*/
    }

    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        AsyncHttpClient client = new AsyncHttpClient();
        //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=andorid&rsz=8
        //String searchString = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + searchQuery + "&rsz=8" + "&start = " + offset;

        String searchString = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8" +
                "&start=" + offset + "&v=1.0&q=" + Uri.encode(searchQuery) +
                "&imgtype=" + filter.getImageTypeStr() +
                "&imgsz=" + filter.getImageSizeStr() +
                "&imgcolor=" + filter.getImageColorFilterStr() +
                "&as_sitesearch=" + filter.getImageSite();
        if (isNetworkAvailable() && isOnline()) {
            client.get(searchString, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray imageJsonResults = null;
                    //Log.d("DEBUG", response.toString());
                    try {
                        imageJsonResults = response.getJSONObject(
                                "responseData").getJSONArray("results");
                        //imageResults.clear(); // clear on new search only
                        // when make changes to adapter it will modify underlining data automatcially
                        // two lines below can be combined into one
                        // does update and notify when applied to adapterdddddd
                        aImageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                        //aImageResults.notifyDataSetChanged(););
                        //imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
                        //aImageResults.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.i("ImageResults", imageResults.toString());
                }
            });
        } else {
            // TBD make into nice dialog.
            Toast.makeText(getApplicationContext(), "Network is Down", Toast.LENGTH_SHORT).show();
        }
    }
}

