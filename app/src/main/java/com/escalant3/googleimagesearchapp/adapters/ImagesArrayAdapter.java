package com.escalant3.googleimagesearchapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.escalant3.googleimagesearchapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public abstract class ImagesArrayAdapter extends ArrayAdapter<String> {

    private static class ViewHolder {
        ImageView imageView;
    }

    public ImagesArrayAdapter(Context context, List<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        String item = getItem(position);
        ViewHolder viewHolder;

        if (position == getCount() - 1) {
            onLastElementVisible();
        }

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item, viewGroup, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);

            // The GridView automatically resizes width to fill columns.
            // Use that width as the height to have perfect dynamic squares
            GridView gv = (GridView) viewGroup;
            int columnWidth = gv.getWidth() / gv.getNumColumns();
            convertView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, columnWidth));
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Reset the current image if any before recycling
        viewHolder.imageView.setImageBitmap(null);

        Picasso.with(viewHolder.imageView.getContext())
                .load(item)
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.imageView);

        return convertView;
    }

    public abstract void onLastElementVisible();
}
