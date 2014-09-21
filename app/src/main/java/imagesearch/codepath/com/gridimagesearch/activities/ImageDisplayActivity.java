package imagesearch.codepath.com.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import imagesearch.codepath.com.gridimagesearch.R;
import imagesearch.codepath.com.gridimagesearch.model.ImageResult;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        //remove action bar on display
        getActionBar().hide();
        // pull out URL from intent
        // find image view
        // load image url into image view using picassa
        //String url = getIntent().getStringExtra("url");
        ImageResult result= (ImageResult) getIntent().getSerializableExtra("result");
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResults);
        Picasso.with(this).load(result.getFullUrl()).into(ivImageResult);
        ivImageResult.setBackgroundColor(Color.RED);
        TextView tvTitle = (TextView) findViewById(R.id.tvFullImageTitle);
        tvTitle.setText(Html.fromHtml(result.getTitle()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
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

    public void onClickShare (View v) {
        // Get access to bitmap image from view
       ImageView ivImage = (ImageView) findViewById(R.id.ivImageResults);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(getApplicationContext(), "Sharing failed", Toast.LENGTH_SHORT).show();
        }
    }
    public Uri getLocalBitmapUri(ImageView imageView) {
        //ImageView siv = (ImageView) findViewById(R.id.ivResult);
        /*Drawable mDrawable = imageView.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                mBitmap, "Image Description", null);

        Uri uri = Uri.parse(path);
        return uri;*/
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
