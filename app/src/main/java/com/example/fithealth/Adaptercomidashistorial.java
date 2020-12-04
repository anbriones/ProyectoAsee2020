package com.example.fithealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.datos.model.Alimento;

import java.util.ArrayList;
import java.util.List;




public class Adaptercomidashistorial extends RecyclerView.Adapter<Adaptercomidashistorial.ViewHolder> {
    private List<Alimento> mDataset = new ArrayList<Alimento>();
    Context context;

    public Adaptercomidashistorial(Context context) {
        context = context;
    }

    @NonNull
    @Override
    public Adaptercomidashistorial.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadocomidasfecha, parent, false);

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
    }


    @Override
    public int getItemCount() {
        return mDataset.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        private TextView mTextView2;
        private TextView mTextView3;
        private TextView mTextView4;
        private TextView mTextView5;
        private TextView mtotal;

        public View mView;


        public Alimento mItem;

        public ViewHolder(@NonNull View v) {
            super(v);
            mView = v;
            mTextView = v.findViewById(R.id.prodl);
            mTextView2 = v.findViewById(R.id.calorias);
            mTextView3 = v.findViewById(R.id.cantidad);
            mTextView4 = v.findViewById(R.id.unidad);
            mTextView5 = v.findViewById(R.id.kc);

        }
    }


    public void add(Alimento item) {
        mDataset.add(item);
        notifyDataSetChanged();

    }

    public void clear() {

        mDataset.clear();
        notifyDataSetChanged();

    }

    public void load(List<Alimento> items) {

        mDataset = items;
        notifyDataSetChanged();

    }
}