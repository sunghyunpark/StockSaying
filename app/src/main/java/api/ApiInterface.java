package api;

import api.response.AuthorListResponse;
import api.response.SayingListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    /*
    명언 리스트 받기
     */
    @GET("api/saying/list/saying/{no}/{sort}")
    Call<SayingListResponse> getSayingList(@Path("no") int no,
                                           @Path("sort") String sort);

    /*
    명언 저자 리스트 API
     */
    @GET("api/author/authorList")
    Call<AuthorListResponse> getAuthorList();

    @GET("api/saying/recent")
    Call<SayingListResponse> getSaying();

}
