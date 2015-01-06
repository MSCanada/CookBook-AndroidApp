package muhammadsuhail.cookbook;

import java.io.Serializable;

/**
 * Created by Muhammad Suhail on 03/01/2015.
 */
public class Recipe implements Serializable {
    String title;
    String source_url;
    String image_url;

    public void settitle(String title)
    {
        this.title=title;
    }
    public String gettitle()
    {
        return title;
    }

    public void setsource_url(String source_url)
    {
        this.source_url=source_url;
    }
    public String getsource_url()
    {
        return source_url;
    }

    public void setimage_url(String image_url)
    {
        this.image_url=image_url;
    }
    public String getimage_url()
    {
        return image_url;
    }
}



