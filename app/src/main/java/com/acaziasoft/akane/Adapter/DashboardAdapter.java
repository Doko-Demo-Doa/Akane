package com.acaziasoft.akane.Adapter;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acaziasoft.akane.R;
import com.acaziasoft.akane.model.Item;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duyth on 11/23/2017.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    ArrayList<Item> items;
    Context context;
    Dialog dialog;

    public DashboardAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setData(List<Item> items){
        this.items = (ArrayList<Item>) items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        @BindView(R.id.imgPicture)
        ImageView imgPicture;

        TextView tvNameFile, tvSize, tvDateUpload, tvURL;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Item item) {
            Glide.with(itemView.getContext())
                    .load(item.getUrl())
                    .asBitmap()
                    .placeholder(R.color.color_gray)
                    .error(R.color.color_gray)
                    .into(imgPicture);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            showDialog(items.get(getAdapterPosition()));
            return false;
        }

        @Override
        public void onClick(View view) {
            ((ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE)).setText(items.get(getAdapterPosition()).getUrl());
            Toast.makeText(context, "Copy clipboard", Toast.LENGTH_SHORT).show();
        }

        private void showDialog(Item item) {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_infomation_item);
            tvNameFile = dialog.findViewById(R.id.tvNameFile);
            tvSize = dialog.findViewById(R.id.tvSize);
            tvDateUpload = dialog.findViewById(R.id.tvDateUpload);
            tvURL = dialog.findViewById(R.id.tvURL);
            tvNameFile.setText(item.getName());
            double size = item.getSize() / 1024;
            Log.e("size mb", String.valueOf(size));
            DecimalFormat df = new DecimalFormat("0.00");
            tvSize.setText(String.format("%s kb", df.format(size)));
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(item.getUploadDate());
            tvDateUpload.setText(String.format("%d/%d/%d %d:%d:%d", calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
            tvURL.setText(item.getUrl());
            dialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.show();
        }
    }
}
