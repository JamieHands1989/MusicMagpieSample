package uk.co.jamiehands.musicmagpiesample.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import uk.co.jamiehands.musicmagpiesample.databinding.ViewItemBinding;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;
import uk.co.jamiehands.musicmagpiesample.viewModel.adapter.ItemViewModel;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.BindingHolder>{

    /**
     * Private variables
     */
    private final Context context;
    private final List<UPCLookup.LookupItem> items;

    /**
     * Default constructor
     * @param context - Context for the layout inflater
     * @param items - List of items to show the user
     */
    public ItemAdapter(Context context, List<UPCLookup.LookupItem> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * Creates a new holder
     * @param parent - Parent to use for the holder
     * @param viewType - The type of view to use (unused)
     * @return - New holder
     */
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewItemBinding binding = ViewItemBinding.inflate(layoutInflater, parent, false);
        return new BindingHolder(binding);
    }

    /**
     * Binds the item to the holder
     * @param holder - Holder to display the information
     * @param position - Position of the item to display
     */
    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ViewItemBinding binding = holder.binding;
        binding.setViewModel(new ItemViewModel(context, items.get(position)));
    }

    /**
     * Returns the count of items to be displayed
     * @return - Size of item list
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        final ViewItemBinding binding;

        public BindingHolder(ViewItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
