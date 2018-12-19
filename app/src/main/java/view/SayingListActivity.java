package view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.investmentkorea.android.stocksaying.R;

import java.util.ArrayList;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.SayingModel;
import presenter.SayingListPresenter;
import presenter.view.SayingListView;
import util.EndlessRecyclerOnScrollListener;
import util.adapter.SayingAdapter;
import view.dialog.AboutSayingDialog;
import view.dialog.SelectAuthorDialog;

public class SayingListActivity extends BaseActivity implements SayingListView{

    // 한번에 받아올 데이터 갯수
    private static final int LOAD_DATA_COUNT = 30;
    // 현재 필터 상태
    private String sortMode = "all";

    private SayingListPresenter sayingListPresenter;
    private ArrayList<SayingModel> sayingModelArrayList;
    private SayingAdapter sayingAdapter;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @BindView(R.id.saying_recyclerView) RecyclerView sayingRecyclerView;
    @BindView(R.id.empty_tv) TextView emptyTv;
    @BindView(R.id.sort_tv) TextView sortTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saying_list);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        sayingModelArrayList = new ArrayList<>();
        sayingListPresenter = new SayingListPresenter(getApplicationContext(), this, sayingModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        sayingAdapter = new SayingAdapter(sayingModelArrayList, new SayingAdapter.SayingAdapterListener() {
            @Override
            public void clickItem(String contents, String author, String created_at) {
                AboutSayingDialog aboutSayingDialog = new AboutSayingDialog(SayingListActivity.this, created_at, contents, author);
                aboutSayingDialog.show();
            }
        });

        // 최초로 한번 명언 리스트를 불러온다.
        sayingListPresenter.getSayingList(true, 0, "all");

        // LoadMore 리스너 등록
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager, LOAD_DATA_COUNT) {
            @Override
            public void onLoadMore(int current_page) {
                if(!sayingModelArrayList.isEmpty()){
                    sayingListPresenter.getSayingList(false, sayingModelArrayList.get(sayingModelArrayList.size()-1).getNo(), sortMode);
                }
            }
        };
        sayingRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

        sayingRecyclerView.setLayoutManager(linearLayoutManager);
        sayingRecyclerView.setAdapter(sayingAdapter);
    }

    @Override
    public void setSayingList(){
        if(sayingModelArrayList.size() > 0){
            emptyTv.setVisibility(View.GONE);
            sayingRecyclerView.setVisibility(View.VISIBLE);
            sayingAdapter.notifyDataSetChanged();
        }else{
            emptyTv.setVisibility(View.VISIBLE);
            sayingRecyclerView.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.sort_tv, R.id.back_btn}) void onClick(View v){
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.sort_tv:
                SelectAuthorDialog selectAuthorDialog = new SelectAuthorDialog(this, new SelectAuthorDialog.SelectAuthorListener() {
                    @Override
                    public void selectAuthor(String authorName) {
                        sortTv.setText(authorName);
                        sortMode = (authorName.equals("전체보기") ? "all" : authorName);
                        sayingListPresenter.getSayingList(true, 0, sortMode);
                        endlessRecyclerOnScrollListener.reset(0, true);
                    }
                });
                selectAuthorDialog.show();
                break;
        }
    }
}
