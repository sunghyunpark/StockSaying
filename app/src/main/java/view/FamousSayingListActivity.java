package view;

import android.os.Bundle;

import com.investmentkorea.android.stocksaying.R;

import base.BaseActivity;
import butterknife.ButterKnife;

public class FamousSayingListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_saying_list);

        ButterKnife.bind(this);
    }
}
