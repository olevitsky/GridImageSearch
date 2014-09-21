package imagesearch.codepath.com.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import imagesearch.codepath.com.gridimagesearch.R;
import imagesearch.codepath.com.gridimagesearch.model.ImageResult;

/**
 * Created by oleg on 9/20/2014.
 */
public class imageResultsAdapter extends ArrayAdapter<ImageResult> {

    public imageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get image result
        ImageResult imageInfo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        // clear out the image fromlast time for recycled view
        ivImage.setImageResource(0);
        //populate title and remote download of image URL
        tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        // remotely download image data
        Picasso.with(getContext()).load(imageInfo.getThumbUrl()).into(ivImage);
        // Return the completed view to render on screen
        return convertView;
    }
}
