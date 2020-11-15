package com.example.fithealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.database.Alimento;

import java.util.ArrayList;
import java.util.List;




public class AdapterBAseDatosCena extends RecyclerView.Adapter<AdapterBAseDatosCena.ViewHolder> {
    private List<Alimento> mDataset= new ArrayList<Alimento>();
    Context context;

    public AdapterBAseDatosCena(Context context ) {
        context = context;
    }

    @NonNull
    @Override
    public AdapterBAseDatosCena.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadoroom_cena, parent, false);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mItem = mDataset.get(position);
        holder.mTextView.setText(mDataset.get(position).getNombre());
        holder.mTextView2.setText(mDataset.get(position).getCalorias().toString());
        holder.mTextView3.setText(mDataset.get(position).getGramos().toString());
        holder.mTextView4.setText(mDataset.get(position).getUnidad());
        holder.mTextView5.setText("calorias");
       // holder.mfec.setText(mDataset.get(position).FORMAT.format(mDataset.get(position).getDate()));
          }





    @Override
    public int getItemCount() {
        return   mDataset.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        private TextView mTextView2;
        private TextView mTextView3;
        private TextView mTextView4;
        private TextView mTextView5;
        private TextView mfec;


        public View mView;


        public Alimento mItem;

        public ViewHolder(@NonNull View v) {
            super(v);
            mView=v;
            mTextView = v.findViewById(R.id.prod);
            mTextView2 = v.findViewById(R.id.calorias);
            mTextView3 = v.findViewById(R.id.cantidad);
            mTextView4 = v.findViewById(R.id.unidad);
            mTextView5 = v.findViewById(R.id.calorias_texto);
             mfec= v.findViewById(R.id.fechacena);
        }
    }



    public void add(Alimento item) {
        mDataset.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mDataset.clear();
        notifyDataSetChanged();

    }

    public void load(List<Alimento> items){
        mDataset = items;
        notifyDataSetChanged();

    }



}
