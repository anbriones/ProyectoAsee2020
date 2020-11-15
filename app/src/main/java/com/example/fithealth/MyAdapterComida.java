
package com.example.fithealth;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;

        import android.widget.TextView;

        import androidx.recyclerview.widget.RecyclerView;


        import com.example.fithealth.model.AlimentosAna;
        import com.example.fithealth.ui.home.HomeFragment;

        import java.util.List;

public class MyAdapterComida extends RecyclerView.Adapter<MyAdapterComida.MyViewHolder> {
    private List<AlimentosAna> mDataset;
    Context context;
    public interface OnListInteractionListener{
        public void onListInteraction(String nombre, Integer calorias,Integer cantidad ,String unidad);
    }

    public OnListInteractionListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;

        public View mView;


        public AlimentosAna mItem;


        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mTextView = v.findViewById(R.id.prod);
            mTextView2 = v.findViewById(R.id.calorias);
            mTextView3 = v.findViewById(R.id.cantidad);
            mTextView4 = v.findViewById(R.id.unidad);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterComida(List<AlimentosAna> myDataset, OnListInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    @Override
    public MyAdapterComida.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado_comida, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
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

    public void swap(List<AlimentosAna> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }
}