package imagesearch.codepath.com.gridimagesearch.model;

import java.io.Serializable;

/**
 * Created by oleg on 9/21/2014.
 */
public class ImageFilter implements Serializable{
    private static final long serialVersionUID = -8505467626159083197L;
    private int ImageType;
    private int ImageSize;
    private int ImageColorFilter;
    private String ImageSite;

    public ImageFilter(int imageType, int imageSize,
                       int imageColorFilter, String imageSite) {
        super();
        ImageType = imageType;
        ImageSize = imageSize;
        ImageColorFilter = imageColorFilter;
        ImageSite = imageSite;
    }

    public ImageFilter(ImageFilter f) {
        super();
        this.ImageType = f.getImageType();
        this.ImageSite = f.getImageSite();
        this.ImageColorFilter = f.getImageColorFilter();
        this.ImageSize = f.getImageSize();
    }


    public void SetFilter(ImageFilter f) {
        this.ImageType = f.getImageType();
        this.ImageSite = f.getImageSite();
        this.ImageColorFilter = f.getImageColorFilter();
        this.ImageSize = f.getImageSize();
    }
    public int getImageType() {
        return ImageType;
    }

    public String getImageTypeStr() {
        String imageType;
        switch (ImageType) {
            case 0:
                imageType = "";
                break;
            case 1:
                imageType = "face";
                break;
            case 2:
                imageType = "photo";
                break;
            case 3:
                imageType = "clipart";
                break;
            case 4:
                imageType = "lineart";
                break;
            default:
                imageType = "";
                break;
        }
        return imageType;
    }


    public void setImageType(int imageType) {
        ImageType = imageType;
    }
    public int getImageSize() {
        return ImageSize;
    }

    public String getImageSizeStr () {
        String imageSize;
        switch(ImageSize) {
            case 0: imageSize=""; break;
            case 1: imageSize="icon"; break;
            case 2: imageSize="medium"; break;
            case 3: imageSize="xxlarge"; break;
            case 4: imageSize="huge"; break;
            default: imageSize=""; break;
        }
        return imageSize;
    }
    public void setImageSize(int imageSize) {
        ImageSize = imageSize;
    }
    public int getImageColorFilter() {
        return ImageColorFilter;
    }

    public String getImageColorFilterStr() {
        String imageColor;
        switch (ImageColorFilter) {
            case 0: imageColor=""; break;
            case 1: imageColor="black"; break;
            case 2: imageColor="blue"; break;
            case 3: imageColor="brown"; break;
            case 4: imageColor="gray"; break;
            case 5: imageColor="green"; break;
            case 6: imageColor="orange"; break;
            case 7: imageColor="pink"; break;
            case 8: imageColor="purple"; break;
            case 9: imageColor="red"; break;
            case 10: imageColor="teal"; break;
            case 11: imageColor="white"; break;
            case 12: imageColor="yellow"; break;
            default: imageColor=""; break;
        }
        return imageColor;
    }
    public void setImageColorFilter(int imageColorFilter) {
        ImageColorFilter = imageColorFilter;
    }
    public String getImageSite() {
        return ImageSite;
    }
    public void setImageSite(String imageSite) {
        ImageSite = imageSite;
    }

}
