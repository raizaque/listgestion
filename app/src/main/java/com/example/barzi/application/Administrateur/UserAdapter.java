package com.example.barzi.application.Administrateur;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.barzi.application.R;

import com.example.barzi.application.beans_DAO.Utilisateur;

import java.util.List;

/**
 * Created by Rokia on 01/04/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<com.example.barzi.application.Administrateur.UserAdapter.MyViewHolder>{

    List<Utilisateur> users;
    public UserAdapter(List<Utilisateur> users) {
        this.users= users;
    }

    @Override
    public com.example.barzi.application.Administrateur.UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_liste_item, parent, false);
        return new com.example.barzi.application.Administrateur.UserAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.barzi.application.Administrateur.UserAdapter.MyViewHolder holder, int position) {
        holder.display(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public Utilisateur get_list_numero(int position){

        return users.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mId;
        private TextView mPseudo;

        public MyViewHolder(View itemView) {
            super(itemView);
            mId=(TextView)itemView.findViewById(R.id.ID_user);
            mPseudo=(TextView)itemView.findViewById(R.id.Pseudo_user);
        }

        void display(Utilisateur user){
            mId.setText(user.getId());
            mPseudo.setText(user.getPseudo());
        }
    }
}
