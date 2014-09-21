package imagesearch.codepath.com.gridimagesearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by oleg on 9/20/2014.
 */
public class ImageResult implements Serializable{
    private static final long serialVersionUID = -9128908099099576466L;
    private String fullUrl;
    private String thumbUrl;
    private int imgHeight;
    private int imgWidth;
    private String title;

    //new Imagersult (..raw jason)
    public ImageResult(JSONObject json)  {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.imgHeight = json.getInt("tbHeight");
            this.imgWidth = json.getInt("tbWidth");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // take an array of JSON images and return array or JSON results
    public static ArrayList<ImageResult> fromJSONArray (JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for(int i = 0; i < array.length(); i++) {
            try {
                results.add (new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }
}
