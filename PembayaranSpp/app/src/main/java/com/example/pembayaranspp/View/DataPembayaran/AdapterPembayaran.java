package com.example.pembayaranspp.View.DataPembayaran;

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
import com.example.pembayaranspp.Model.Pembayaran.PembayaranItem;
import com.example.pembayaranspp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdapterPembayaran extends RecyclerView.Adapter<AdapterPembayaran.ViewHolder> {

    private Context c;
    private List<PembayaranItem> pembayaranItem;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nisnTxt, tanggalTxt, bulanTxt, tahunTxt;
        private AdapterPembayaran.ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            nisnTxt = itemView.findViewById(R.id.tv_nisn_pembayaran);
            tanggalTxt = itemView.findViewById(R.id.tv_tgl_pembayaran);
            bulanTxt = itemView.findViewById(R.id.tv_bulan_pembayaran);
            tahunTxt = itemView.findViewById(R.id.tv_tahun_pembayaran);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        public void setItemClickListener(AdapterPembayaran.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    public AdapterPembayaran(Context mContext, ArrayList<PembayaranItem> pembayaranItem) {
        this.c = mContext;
        this.pembayaranItem = pembayaranItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.pembayaran_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPembayaran.ViewHolder holder, int position) {
        //get current scientist
        final PembayaranItem pembayaranItem1 = pembayaranItem.get(position);

        //bind data to widgets
        holder.nisnTxt.setText(pembayaranItem1.getNisn());
        holder.tanggalTxt.setText(pembayaranItem1.getTglBayar());
        holder.bulanTxt.setText(pembayaranItem1.getBulanDibayar());
        holder.tahunTxt.setText(pembayaranItem1.getTahunDibayar());

        String nisn = pembayaranItem1.getNisn().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (nisn.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nisn.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nisnTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nisnTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Common.sendPembayaranToActivity(c, pembayaranItem1,
                DetailPembayaran.class));
    }

    @Override
    public int getItemCount() {
        return pembayaranItem.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end