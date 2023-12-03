package algonquin.cst2335.newfinalproject.Sun.Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.newfinalproject.R;

public class LookupAdapter extends RecyclerView.Adapter<LookupViewHolder> {

    private List<Lookup> lookupList = new ArrayList<>();

    @NonNull
    @Override
    public LookupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lookup, parent, false);
        return new LookupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LookupViewHolder holder, int position) {
        Lookup lookup = lookupList.get(position);
        holder.bind(lookup);
    }

    @Override
    public int getItemCount() {
        return lookupList.size();
    }

    public void setLookups(List<Lookup> lookups) {
        this.lookupList = lookups;
        notifyDataSetChanged();
    }
}
