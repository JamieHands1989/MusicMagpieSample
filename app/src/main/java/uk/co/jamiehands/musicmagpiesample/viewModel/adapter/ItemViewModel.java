package uk.co.jamiehands.musicmagpiesample.viewModel.adapter;

import android.content.Context;
import android.databinding.BaseObservable;

import uk.co.jamiehands.musicmagpiesample.R;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;

public class ItemViewModel extends BaseObservable {

    /**
     * Private variables
     */
    private final Context context;
    private final UPCLookup.LookupItem lookupItem;

    /**
     * Default constructor
     * @param context - Context to use
     * @param lookupItem - Lookup item to be displayed
     */
    public ItemViewModel(Context context, UPCLookup.LookupItem lookupItem) {
        this.context = context;
        this.lookupItem = lookupItem;
    }

    /**
     * Returns the name of the item
     * @return - Name of the item
     */
    public String getName() {
        return lookupItem.title;
    }

    /**
     * Returns the brand of the item
     * @return - Brand of the item
     */
    public String getBrand() {
        return lookupItem.brand;
    }

    /**
     * Returns the ASIN of the item
     * @return - ASIN of the item
     */
    public String getAsin() {
        return context.getString(R.string.asin, lookupItem.ean);
    }
}
