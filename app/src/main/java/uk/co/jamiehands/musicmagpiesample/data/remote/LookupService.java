package uk.co.jamiehands.musicmagpiesample.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;

public interface LookupService {

    String ENDPOINT = "https://api.upcitemdb.com/prod/trial/";

    @GET("lookup")
    Observable<UPCLookup> lookupUPC(@Query("upc") String upc);
}
