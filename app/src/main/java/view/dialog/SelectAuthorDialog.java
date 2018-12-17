package view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.investmentkorea.android.stocksaying.R;

import java.util.ArrayList;
import java.util.Collections;

import api.ApiClient;
import api.ApiInterface;
import api.response.AuthorListResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.AuthorModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.Util;
import util.adapter.SelectAuthorAdapter;

public class SelectAuthorDialog extends Dialog {

    private boolean isAll;
    private ArrayList<AuthorModel> authorModelArrayList;
    private SelectAuthorAdapter selectAuthorAdapter;
    private SelectAuthorListener selectAuthorListener;

    @BindView(R.id.author_recyclerView) RecyclerView authorRecyclerView;

    /*
    isAll -> true - '전체' 항목이 노출, false - '전체' 항목이 미노출
     */
    public SelectAuthorDialog(Context context, boolean isAll, SelectAuthorListener selectAuthorListener){
        super(context);
        this.isAll = isAll;
        this.selectAuthorListener = selectAuthorListener;
    }

    public interface SelectAuthorListener{
        void selectAuthor(String authorName);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.select_author_dialog);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        authorModelArrayList = new ArrayList<>();

        if(isAll){
            authorModelArrayList.add(new AuthorModel("전체보기"));
        }

        LinearLayoutManager lL = new LinearLayoutManager(getContext());
        selectAuthorAdapter = new SelectAuthorAdapter(authorModelArrayList, new SelectAuthorAdapter.SelectAuthorAdapterListener() {
            @Override
            public void selectAuthor(String authorName) {
                selectAuthorListener.selectAuthor(authorName);
                dismiss();
            }
        });
        authorRecyclerView.setLayoutManager(lL);
        authorRecyclerView.setAdapter(selectAuthorAdapter);

        getAuthorList();
    }

    private void getAuthorList(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AuthorListResponse> call = apiService.getAuthorList();
        call.enqueue(new Callback<AuthorListResponse>() {
            @Override
            public void onResponse(Call<AuthorListResponse> call, Response<AuthorListResponse> response) {
                AuthorListResponse authorListResponse = response.body();
                if(authorListResponse.getResult().size() > 0) {
                    for(AuthorModel am : authorListResponse.getResult()){
                        Collections.addAll(authorModelArrayList, am);
                    }
                }
                selectAuthorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AuthorListResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Util.showToast(getContext(), "네트워크 연결상태를 확인해주세요.");
            }
        });
    }

    @OnClick(R.id.cancel_btn) void cancelBtn(){
        dismiss();
    }
}
