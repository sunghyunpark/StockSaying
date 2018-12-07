package util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.investmentkorea.android.stocksaying.R;
import com.skydoves.powermenu.MenuBaseAdapter;

import model.ColorPowerMenuModel;

public class ColorMenuAdapter extends MenuBaseAdapter<ColorPowerMenuModel> {

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.color_menu_list, viewGroup, false);
        }

        ColorPowerMenuModel item = (ColorPowerMenuModel) getItem(index);
        final ImageView icon = view.findViewById(R.id.color_iv);
        icon.setImageDrawable(item.getColorDrawable());

        final TextView title = view.findViewById(R.id.color_name_tv);
        title.setText(item.getColorName());
        return super.getView(index, view, viewGroup);
    }
}
