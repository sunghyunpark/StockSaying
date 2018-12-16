package view;

import android.content.Intent;
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
import view.dialog.SelectAuthorDialog;

public class SayingListActivity extends BaseActivity implements SayingListView{

    private static final int LOAD_DATA_COUNT = 10;
    private String sortMode = "all";
    private SayingListPresenter sayingListPresenter;
    private ArrayList<SayingModel> sayingModelArrayList;
    private SayingAdapter sayingAdapter;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    @BindView(R.id.saying_recyclerView) RecyclerView sayingRecyclerView;
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

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        sayingAdapter = new SayingAdapter(sayingModelArrayList);

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
        sayingAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.sort_tv}) void onClick(View v){
        switch (v.getId()){
            case R.id.sort_tv:
                SelectAuthorDialog selectAuthorDialog = new SelectAuthorDialog(this, true, new SelectAuthorDialog.SelectAuthorListener() {
                    @Override
                    public void selectAuthor(String authorName) {
                        sortTv.setText(authorName);
                        authorName = (authorName.equals("전체") ? "all" : authorName);
                        sayingListPresenter.getSayingList(true, 0, authorName);
                        endlessRecyclerOnScrollListener.reset(0, true);
                    }
                });
                selectAuthorDialog.show();
                break;
        }
    }
}
