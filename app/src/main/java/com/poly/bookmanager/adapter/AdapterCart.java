package com.poly.bookmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.TextView;
import com.poly.bookmanager.R;
import com.poly.bookmanager.model.Cart;
import com.poly.bookmanager.projectDAO.SachDAO;
import java.util.List;

public class AdapterCart extends BaseAdapter {
    private List<Cart> carts;
    SachDAO sachDAO;
    private LayoutInflater layoutInflater;
    private Context context;
    public AdapterCart(Context aContext, List<Cart> carts) {
        this.context = aContext;
        this.carts = carts;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int i) {
        return carts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup viewGroup)
    { ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_cart, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.nameItem);
            viewHolder.tvAmount = (TextView) convertView.findViewById(R.id.amountItem);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.priceItem);
            viewHolder.delItem = (Button) convertView.findViewById(R.id.delItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Cart cart = this.carts.get(position);
        viewHolder.tvName.setText(cart.getNameItem());
        viewHolder.tvAmount.setText("Số lượng mua: "+cart.getAmountItem());
        viewHolder.tvPrice.setText("Tổng: "+cart.getPriceItem());

        viewHolder.delItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sachDAO = new SachDAO(context);
                    carts.remove(position);
                    notifyDataSetChanged();

            }
        });
        return convertView;
    }
    class ViewHolder {
        Button delItem;
        TextView tvName, tvAmount, tvPrice;
    }
}