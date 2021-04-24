package com.example.pembayaranspp.View.DataKelas;

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
import com.example.pembayaranspp.Model.Kelas.KelasItem;
import com.example.pembayaranspp.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdapterKelas extends RecyclerView.Adapter<AdapterKelas.ViewHolder> {

    private Context c;
    private List<KelasItem> kelasItem;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nama_kelasTxt, kompetensi_kelasTxt;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        public ViewHolder(View itemView) {
            super(itemView);

            nama_kelasTxt = itemView.findViewById(R.id.tv_namakelas);
            kompetensi_kelasTxt = itemView.findViewById(R.id.tv_Kompetensi);
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
    public AdapterKelas(Context mContext, ArrayList<KelasItem> kelasItem) {
        this.c = mContext;
        this.kelasItem = kelasItem;
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.kelas_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final KelasItem k = kelasItem.get(position);

        //bind data to widgets
        holder.nama_kelasTxt.setText(k.getNamaKelas());
        holder.kompetensi_kelasTxt.setText(k.getKompetensiKeahlian());

        //get name and galaxy
        String nama_kelas = k.getNamaKelas().toLowerCase(Locale.getDefault());
        String kompetensi_keahlian = k.getKompetensiKeahlian().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (nama_kelas.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nama_kelas.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nama_kelasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nama_kelasTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (kompetensi_keahlian.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = kompetensi_keahlian.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.kompetensi_kelasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.kompetensi_kelasTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Common.sendKelasToActivity(c, k,
                DetailKelas.class));
    }
    @Override
    public int getItemCount() {
        return kelasItem.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end