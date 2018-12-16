package util.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.investmentkorea.android.stocksaying.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.SayingModel;

public class SayingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_ITEM = 1;
    private ArrayList<SayingModel> sayingModelArrayList;

    public SayingAdapter(ArrayList<SayingModel> sayingModelArrayList){
        this.sayingModelArrayList = sayingModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_saying, parent, false);
            return new Saying_VH(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    private SayingModel getItem(int position) {
        return sayingModelArrayList.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Saying_VH) {
            final SayingModel currentItem = getItem(position);
            final Saying_VH VHitem = (Saying_VH)holder;

            VHitem.monthTv.setText(currentItem.getCreatedAt().split("-")[1]);
            VHitem.dayTv.setText(currentItem.getCreatedAt().split("-")[2]);

            VHitem.contentsTv.setText(currentItem.getContents());
            VHitem.authorTv.setText("- "+currentItem.getAuthorName()+" -");


        }
    }

    public class Saying_VH extends RecyclerView.ViewHolder{

        @BindView(R.id.month_tv) TextView monthTv;
        @BindView(R.id.day_tv) TextView dayTv;
        @BindView(R.id.contents_tv) TextView contentsTv;
        @BindView(R.id.author_tv) TextView authorTv;

        private Saying_VH(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    //increasing getItemcount to 1. This will be the row of header.
    @Override
    public int getItemCount() {
        return sayingModelArrayList.size();
    }
}

