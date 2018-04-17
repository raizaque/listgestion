package com.example.barzi.application.adapters;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Liste;

import java.util.List;
/**
 * Created by Rokia on 10/03/2018.
 */
public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.MyViewHolder>{
    private List<Liste> mesListes;
    public ListeAdapter(List<Liste> Listes) {
        this.mesListes= Listes;
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
    public Liste get_list_numero(int position){
        return mesListes.get(position);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mtitreListe;
        private TextView mdescription;
        public MyViewHolder(View itemView) {
            super(itemView);
            mtitreListe=(TextView)itemView.findViewById(R.id.ID_user);
            mdescription=(TextView)itemView.findViewById(R.id.Pseudo_user);
        }
        void display(Liste Maliste){
            Log.d("description_liste",Maliste.getTitre());

                mtitreListe.setText(Maliste.getTitre());
            mdescription.setText(Maliste.getDescription());
        }
    }
}
