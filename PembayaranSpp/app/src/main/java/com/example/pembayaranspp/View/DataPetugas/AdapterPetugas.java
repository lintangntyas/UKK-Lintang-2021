package com.example.pembayaranspp.View.DataPetugas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pembayaranspp.Common.Common;
import com.example.pembayaranspp.Model.Petugas.PetugasItem;
import com.example.pembayaranspp.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdapterPetugas extends RecyclerView.Adapter<AdapterPetugas.ViewHolder> {

    private Context c;
    private List<PetugasItem> petugasItem;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView usernameTxt, levelTxt, namapetugasTxt;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTxt = itemView.findViewById(R.id.tv_username_petugas);
            namapetugasTxt = itemView.findViewById(R.id.tv_nama_petugas);
            levelTxt = itemView.findViewById(R.id.tv_level_petugas);

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

    public AdapterPetugas(Context mContext, ArrayList<PetugasItem> petugasItem) {
        this.c = mContext;
        this.petugasItem = petugasItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.petugas_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final PetugasItem petugasItem1 = petugasItem.get(position);

        //bind data to widgets
        holder.usernameTxt.setText(petugasItem1.getUsername());
        holder.namapetugasTxt.setText(petugasItem1.getNamaPetugas());
        holder.levelTxt.setText(petugasItem1.getLevel());

        String username = petugasItem1.getUsername().toLowerCase(Locale.getDefault());
        String nama_petugas = petugasItem1.getNamaPetugas().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (username.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = username.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.usernameTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.usernameTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (nama_petugas.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = nama_petugas.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.namapetugasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.namapetugasTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Common.sendPetugasToActivity(c, petugasItem1,
                DetailPetugas.class));
    }
    @Override
    public int getItemCount() {
        return petugasItem.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end
