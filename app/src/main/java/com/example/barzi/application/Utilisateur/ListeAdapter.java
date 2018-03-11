package com.example.barzi.application.Utilisateur;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barzi.application.R;

import java.util.List;

/**
 * Created by Rokia on 10/03/2018.
 */

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.MyViewHolder>{

    List<MaListe> mesListes;
    public ListeAdapter(List<MaListe> mesListes) {
        this.mesListes= mesListes;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_liste_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(mesListes.get(position));

    }

    @Override
    public int getItemCount() {
        return mesListes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mtitreListe;
        private TextView mdescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            mtitreListe=(TextView)itemView.findViewById(R.id.Titreliste);
            mdescription=(TextView)itemView.findViewById(R.id.description);
        }

        void display(MaListe Maliste){
            mtitreListe.setText(Maliste.getTitre());
            mdescription.setText(Maliste.getDescription());
        }
    }
}
