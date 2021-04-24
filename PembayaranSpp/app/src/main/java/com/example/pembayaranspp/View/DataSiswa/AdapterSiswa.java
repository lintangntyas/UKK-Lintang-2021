package com.example.pembayaranspp.View.DataSiswa;

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
import com.example.pembayaranspp.Model.Siswa.SiswaItem;
import com.example.pembayaranspp.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterSiswa extends RecyclerView.Adapter<AdapterSiswa.ViewHolder> {
    private Context c;
    private List<SiswaItem> siswaItem;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView nisTxt, namasiswaTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        public ViewHolder(View itemView) {
            super(itemView);

            nisTxt = itemView.findViewById(R.id.tv_nis_siswa);
            namasiswaTxt = itemView.findViewById(R.id.tv_nama_siswa);
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
    public AdapterSiswa(Context mContext, ArrayList<SiswaItem> siswaItem) {
        this.c = mContext;
        this.siswaItem = siswaItem;
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.siswa_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final SiswaItem s = siswaItem.get(position);

        //bind data to widgets
        holder.nisTxt.setText(s.getNis());
        holder.namasiswaTxt.setText(s.getNama());

        //get name and galaxy
        String nis = s.getNis().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (nis.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nis.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nisTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nisTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Common.sendSiswaToActivity(c, s,
                Detailsiswa.class));
    }
    @Override
    public int getItemCount() {
        return siswaItem.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end
