package imagesearch.codepath.com.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import imagesearch.codepath.com.gridimagesearch.R;
import imagesearch.codepath.com.gridimagesearch.model.ImageFilter;

public class FilterActivity extends Activity {
    private Spinner spnImageType;
    private Spinner spnImageSize;
    private Spinner spnImageColorFilter;
    private EditText etImageSite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
        spnImageType = (Spinner) findViewById(R.id.f_type);
        spnImageSize = (Spinner) findViewById(R.id.f_imgsize);
        spnImageColorFilter = (Spinner) findViewById(R.id.f_imgcolor);
        etImageSite = (EditText) findViewById(R.id.it_site);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnClickOKButton(View v) {
        //Toast.makeText(this, "TEXT", Toast.LENGTH_SHORT).show();
        ImageFilter filter = new ImageFilter(0,0,0,"");
        filter.setImageType((int) spnImageType.getSelectedItemId());
        filter.setImageSize((int) spnImageSize.getSelectedItemId());
        filter.setImageColorFilter((int) spnImageColorFilter.getSelectedItemId());
        filter.setImageSite(etImageSite.getText().toString());
        Intent data = new Intent();
        data.putExtra("filter", filter);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
        Log.d("DEBUG", "done with OK Filter");
    }
}
