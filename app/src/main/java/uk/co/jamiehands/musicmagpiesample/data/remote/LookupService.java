package uk.co.jamiehands.musicmagpiesample.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;

public interface LookupService {

    String ENDPOINT = "https://api.upcitemdb.com/prod/trial/";

    /**
     * Returns information on the lookup of a barcode
     * @param upc - upc code
     * @return - Results of the lookup
     */
    @GET("lookup")
    Observable<UPCLookup> lookupUPC(@Query("upc") String upc);
}
