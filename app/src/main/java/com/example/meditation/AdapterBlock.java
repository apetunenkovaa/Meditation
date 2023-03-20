package com.example.meditation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AdapterBlock extends BaseAdapter {

    protected Context mContext;
    List<MaskBlock> blockList;

    public AdapterBlock(Context mContext, List<MaskBlock> blockList) {
        this.mContext = mContext;
        this.blockList = blockList;
    }

    @Override
    public int getCount() {
        return blockList.size();
    }

    @Override
    public Object getItem(int i) {
        return blockList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return blockList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.item_block,null);

        TextView title = v.findViewById(R.id.tvTitle);
        ImageView Image = v.findViewById(R.id.image);
        TextView description = v.findViewById(R.id.tvDescription);

        MaskBlock maskBlock = blockList.get(position);
        title.setText(maskBlock.getTitle());
        new DownloadImageTask((ImageView) Image).execute(maskBlock.getImage());
        description.setText(maskBlock.getDescription());
        return v;
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}