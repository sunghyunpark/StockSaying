package api;

import api.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("api/users/{uid}")
    Call<LoginResponse> loginApi(@Path("uid") String uid);

}
