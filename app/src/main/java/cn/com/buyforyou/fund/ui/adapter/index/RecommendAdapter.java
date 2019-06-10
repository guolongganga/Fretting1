package cn.com.buyforyou.fund.ui.adapter.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.model.index.ProductModel;
import cn.com.buyforyou.fund.utils.RateStyleUtil;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by guolonggang on 2019/5/22.
 * Description:
 */

public class RecommendAdapter  extends SimpleRecAdapter<ProductModel, RecommendAdapter.ViewHolder> {

    public static final int ITEM_CLICK = 0;   //点击标识

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_index_week_recommend_item;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ProductModel model = data.get(position);
//        holder.tvValue.setText(BigDecimalUtil.bigdecimalToString(model.getFund_rose()) + "%");
        holder.tvValue.setText(data.get(position).getFund_name());
      //  RateStyleUtil.rateStyle(context, holder.tvValue, model.getFund_rose());
       // holder.tvDescribe.setText(model.getRiseTermDesc());
        RateStyleUtil.rateStyle(context, holder.tvDescribe, model.getFund_rose());

        holder.tvTitle.setText(model.getRiseTermDesc());
//        holder.tvLocationOne.setText(model.getFeatureone());
//        holder.tvLocationTwo.setText(model.getFeaturetwo());

//        if (data.size() - 1 == position) {
//            holder.viewLine.setVisibility(View.GONE);
//        } else {
//            holder.viewLine.setVisibility(View.VISIBLE);
//        }

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, model, ITEM_CLICK, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //基金指数1
        @BindView(R.id.rl_finger_one)
        RelativeLayout rlContent;
        //基金名称
        @BindView(R.id.tv_nvshen)
        TextView tvValue;
        //利率
        @BindView(R.id.tv_nvshen_shouyi)
        TextView tvDescribe;
        //近一年收益率
        @BindView(R.id.tv_finger_one_desc)
        TextView tvTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
