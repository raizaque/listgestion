package com.example.barzi.application.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barzi.application.R;
import com.example.barzi.application.beans_DAO.Element;

import java.util.List;

/**
 * Created by Rokia on 13/03/2018.
 */

public class ElementAdapter  extends RecyclerView.Adapter<ElementAdapter.MyViewHolderElt> {
    List<Element> mesElements;
    public ElementAdapter(List<Element> mesElements) {
        this.mesElements= mesElements;
    }
    @Override
    public MyViewHolderElt onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_element_item, parent, false);
        return new ElementAdapter.MyViewHolderElt(view);
    }
    @Override
    public void onBindViewHolder(ElementAdapter.MyViewHolderElt holder, int position) {
        holder.display(mesElements.get(position));
    }
    @Override
    public int getItemCount() {
        return mesElements.size();
    }
    public class MyViewHolderElt extends RecyclerView.ViewHolder{
        private TextView mtitreElt;
        private TextView mdescription;
        private ImageView status;
        public MyViewHolderElt(View itemView) {
            super(itemView);
            status=(ImageView) itemView.findViewById(R.id.status_image);
            mtitreElt=(TextView)itemView.findViewById(R.id.titreList);
            mdescription=(TextView)itemView.findViewById(R.id.description_ajout_element);
        }
        void display(Element mElement){
            switch(mElement.getStatut_optionnel()) {
                case "1":
                    status.setImageResource(R.drawable.greenball);
                    break;
                case "0":
                    status.setImageResource(R.drawable.redball);
                    break;
                default:
                    status.setImageResource(R.drawable.yellowball);
                    break;
            }
            mtitreElt.setText(mElement.getTitre_element());
            mdescription.setText(mElement.getDescription_element());
        }
    }
}
