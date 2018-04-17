package com.example.barzi.application.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barzi.application.R;

import com.example.barzi.application.beans_DAO.Utilisateur;

import java.util.List;

/**
 * Created by Rokia on 01/04/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
    List<Utilisateur> users;
    public UserAdapter(List<Utilisateur> users) {
        this.users= users;
    }
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new UserAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {
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
        private TextView role_user;
        private TextView permission_user;
        private ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mId=(TextView)itemView.findViewById(R.id.ID_user);
            permission_user=(TextView)itemView.findViewById(R.id.permission_user);
            role_user=(TextView)itemView.findViewById(R.id.role_user);
            imageView=(ImageView)itemView.findViewById(R.id.icone_person);
        }
        void display(Utilisateur user){
            mId.setText(user.getId()+" "+user.getPseudo());
            if (user.getRole().equals("0")){
                user.setRole("Administrateur");

            }else
                user.setRole("utilisateur simple");
            if (user.getPermission().equals("0"))
                user.setPermission("activé");
            else
                user.setPermission("desactivé");
            role_user.setText(user.getRole().toString());
            permission_user.setText(user.getPermission().toString());
            imageView.setImageResource(R.drawable.personicon);
        }
    }
}
