package cn.com.buyforyou.fund.ui.adapter.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import javax.annotation.Nullable;

import butterknife.BindView;
import cn.com.buyforyou.fund.R;

import cn.com.buyforyou.fund.model.fund.BankCard;

import cn.com.buyforyou.fund.model.fund.BuyFundResp;
import cn.com.buyforyou.fund.utils.Base64ImageUtil;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by guolonggang on 2018/12/18.
 * Description:
 */

public class BuyFundAdapter extends SimpleRecAdapter<BankCard, BuyFundAdapter.ViewHolder> {

    public static final int ITEM_CLICK = 0;    //点击标识
    private ImageLoader imageLoader = ImageLoader.getInstance();


    public BuyFundAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.buyfundadapter_buy_bank_card;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       final  BankCard bankCard = data.get(position);
        //获取 图片Base64 字符串
            String strimage = bankCard.getLogo();
            if (!TextUtils.isEmpty(strimage)) {
                //将Base64图片串转换成Bitmap
                Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
                holder.bankImage.setImageBitmap(bitmap);
                holder.bankName.setText(bankCard.getName());
                holder.bankLimit.setText(bankCard.getInfo());

            }

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, bankCard, ITEM_CLICK, holder);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 银行图标
         */
        @Nullable
        @BindView(R.id.bank_image)
        ImageView bankImage;
        /**
         * 银行名称
         */
        @BindView(R.id.bank_name)
        TextView bankName;
        /**
         * 银行限额
         */
        @BindView(R.id.bank_limit)
        TextView bankLimit;

        /**
         * 内容
         */
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }


}
