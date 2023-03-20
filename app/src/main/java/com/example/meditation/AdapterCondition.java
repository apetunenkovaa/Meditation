package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class AdapterCondition extends RecyclerView.Adapter<AdapterCondition.ViewHolder>  {

    protected Context mContext;
    List<MaskCondition> conditionList;

    public AdapterCondition(Context mContext, List<MaskCondition> conditionList) {
        this.mContext = mContext;
        this.conditionList = conditionList;
    }

    @NonNull
    @Override
    public AdapterCondition.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterCondition.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_condition, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCondition.ViewHolder holder, int position) {
        final MaskCondition modal = conditionList.get(position);
        holder.title.setText(modal.getTitle());
        new AdapterCondition.DownloadImageTask((ImageView) holder.img).execute(modal.getImage());
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return conditionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.imgFeeling);
        }
    }
}