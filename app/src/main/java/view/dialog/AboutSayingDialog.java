package view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.investmentkorea.android.stocksaying.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutSayingDialog extends Dialog {

    private String author, contents, created_at;

    @BindView(R.id.created_at_tv) TextView createdAtTv;
    @BindView(R.id.contents_tv) TextView contentsTv;
    @BindView(R.id.author_tv) TextView authorTv;

    public AboutSayingDialog(Context context, String created_at, String contents, String author){
        super(context);
        this.author = author;
        this.contents = contents;
        this.created_at = created_at;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.about_saying_dialog);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        createdAtTv.setText(created_at);
        contentsTv.setText(contents);
        authorTv.setText(author);
    }

}
