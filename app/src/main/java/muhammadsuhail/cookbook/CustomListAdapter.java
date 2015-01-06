package muhammadsuhail.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammad Suhail on 03/01/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    static class ViewHolder{
        ImageView imageview;
        TextView title;
    }
    public ArrayList<Recipe> recipes;
    public LayoutInflater layoutInflater;
    public CustomListAdapter(Context context,ArrayList<Recipe>recipes)
    {

 this.recipes=recipes;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if(convertView==null)
        {
            convertView=layoutInflater.inflate(R.layout.list_row_layout,null);
            holder=new ViewHolder();
            holder.title=(TextView)convertView.findViewById(R.id.title);
            holder.imageview=(ImageView)convertView.findViewById(R.id.thumbImage);
            convertView.setTag(holder);
        }
else{
holder=(ViewHolder)convertView.getTag();
        }

Recipe obj=(Recipe)recipes.get(position);
holder.title.setText(obj.gettitle());
if(holder.imageview!=null)
{
new ImageDownloaderTask(holder.imageview).execute(obj.getimage_url());
}
return convertView;
    }
}
