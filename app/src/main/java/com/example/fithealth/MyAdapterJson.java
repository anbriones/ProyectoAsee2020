package com.example.fithealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;



public class MyAdapterJson extends RecyclerView.Adapter<MyAdapterJson.MyViewHolder> {
    private List<AlimentosFinales> mDataset;
    Context context;
    Integer gramos;



    public interface OnListInteractionListener{
        public void onListInteraction(String nombre, Integer calorias,Integer cantidad ,String unidad);

    }
    public interface OnListInteractionListenerdetalle{
        public void onListInteraction2(AlimentosFinales alim);

    }

    public MyAdapterJson.OnListInteractionListener mListener;
    public MyAdapterJson.OnListInteractionListenerdetalle mListener2;


    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public Button mbutonmas;
        public Button mbotonmenos;
        public Button maniadir;
        public View mView;

        public AlimentosFinales mItem;

        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mTextView = v.findViewById(R.id.prodl);
            mTextView2 = v.findViewById(R.id.calorias);
            mTextView3 = v.findViewById(R.id.cantidada);
            mTextView4 = v.findViewById(R.id.unidad);
            mbutonmas = v.findViewById(R.id.mas);
            mbotonmenos= v.findViewById(R.id.menos);
            maniadir= v.findViewById(R.id.aniadir);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterJson(List<AlimentosFinales> myDataset, MyAdapterJson.OnListInteractionListener listener,MyAdapterJson.OnListInteractionListenerdetalle listenerdetalle) {
        mDataset = myDataset;
        mListener = listener;
        mListener2=listenerdetalle;
    }

    @Override
    public MyAdapterJson.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado, parent, false);

        return new MyAdapterJson.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyAdapterJson.MyViewHolder holder, int position) {
        holder.mItem = mDataset.get(position);
        holder.mTextView.setText(mDataset.get(position).getNombreprod());
        holder.mTextView2.setText(mDataset.get(position).getCalorias().toString());
        holder.mTextView3.setText(mDataset.get(position).getCantidad().toString());
        holder.mTextView4.setText(mDataset.get(position).getUnidad());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener2.onListInteraction2( mDataset.get(position));
                }
            }
        });

        holder.mbutonmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gramos=Integer.parseInt(holder.mTextView3.getText().toString())+1;
                holder.mTextView3.setText(gramos.toString());
            }
        });

        holder.mbotonmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(holder.mTextView3.getText().toString()) >= 1) {
                    gramos = Integer.parseInt(holder.mTextView3.getText().toString()) - 1;
                    holder.mTextView3.setText(gramos.toString());
                }
            }
        });
        holder.maniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    int calorias=(holder.mItem.getCalorias()*Integer.parseInt(holder.mTextView3.getText().toString())/holder.mItem.getCantidad());
                    mListener.onListInteraction(holder.mItem.getNombreprod(),calorias,Integer.parseInt(holder.mTextView3.getText().toString()),holder.mItem.getUnidad());

                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<AlimentosFinales> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }
    public void clear(){
        mDataset.clear();
        notifyDataSetChanged();
    }
}