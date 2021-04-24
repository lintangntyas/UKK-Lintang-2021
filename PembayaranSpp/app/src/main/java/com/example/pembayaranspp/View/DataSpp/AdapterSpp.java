package com.example.pembayaranspp.View.DataSpp;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Spp.SppItem;
import com.example.pembayaranspp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterSpp extends RecyclerView.Adapter<AdapterSpp.ViewHolder> {
    private Context c;
    private List<SppItem> sppItem;
    public String searchString = "";

    /**
     * Our ViewHolder class. It's responsibilities include:
     * 1. Hold all the widgets which will be recycled and reference them.
     * 2. Implement click event.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView tahunTxt, nominalTxt;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        public ViewHolder(View itemView) {
            super(itemView);
            tahunTxt = itemView.findViewById(R.id.tv_tahun);
            nominalTxt = itemView.findViewById(R.id.tv_nominal);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    /**
     * Our MyAdapter's costructor
     */
    public AdapterSpp(Context mContext, ArrayList<SppItem> sppItem) {
        this.c = mContext;
        this.sppItem = sppItem;
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.spp_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final SppItem s = sppItem.get(position);

        //bind data to widgets
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.tahunTxt.setText(s.getTahun());
        holder.nominalTxt.setText(s.getNominal());

        //get name and galaxy
        String tahun = s.getTahun().toLowerCase(Locale.getDefault());
        String nominal = s.getNominal().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (tahun.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = tahun.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.tahunTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tahunTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Common.sendSppToActivity(c, s,
                Detailspp.class));
    }
    @Override
    public int getItemCount() {
        return sppItem.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end