package com.example.fithealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.roomdatabase.Alimento;
import com.example.fithealth.roomdatabase.AlimentoEnComida;
import com.example.fithealth.roomdatabase.Comida;

import java.util.ArrayList;
import java.util.List;

public class AdapterBaseDatos extends RecyclerView.Adapter<AdapterBaseDatos.ViewHolder> {
    private List<Alimento> mDataset= new ArrayList<Alimento>();
    private List<Comida> mDatasetcom=  new ArrayList<Comida>();
    private List<AlimentoEnComida> mDatasetAlimcomida=  new ArrayList<AlimentoEnComida>();
    Context context;

    public interface OnListInteractionListener{
        public void onListInteractionBD(Alimento alim);
    }

    public AdapterBaseDatos.OnListInteractionListener mListener;



    public AdapterBaseDatos(Context context,AdapterBaseDatos.OnListInteractionListener listener) {
        mListener = listener;
        context = context;
    }

    @NonNull
    @Override
    public AdapterBaseDatos.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado_room, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem = mDataset.get(position);
        holder.mTextView.setText(mDataset.get(position).getNombre());
        holder.mTextView2.setText(mDataset.get(position).getCalorias().toString());
        holder.mTextView5.setText("Kc");
        holder.mTextView3.setText(mDataset.get(position).getGramos().toString());
        holder.mTextView4.setText(mDataset.get(position).getUnidad());

        holder.mbotoneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Alimento alim=mDataset.get(position);
                    mListener.onListInteractionBD(alim);

                }
            }
        });
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

        public Button mbotoneliminar;
        public Alimento mItem;

        public ViewHolder(@NonNull View v) {
            super(v);
            mTextView = v.findViewById(R.id.prodl);
            mTextView2 = v.findViewById(R.id.calorias);
            mTextView3 = v.findViewById(R.id.cantidad);
            mTextView4 = v.findViewById(R.id.unidad);
            mTextView5 = v.findViewById(R.id.kc);
            mbotoneliminar = v.findViewById(R.id.botonelim);
        }
    }



    public void add(Alimento item) {
        mDataset.add(item);
        notifyDataSetChanged();
    }

    public void eliminaralimento(Alimento alim){
        mDataset.remove(alim);
        notifyDataSetChanged();
    }

    public void load(List<Alimento> items){
        mDataset = items;
        notifyDataSetChanged();

    }



}
