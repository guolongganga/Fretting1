package cn.com.buyforyou.fund.ui.adapter.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.model.user.BankAddResp;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.ui.activity.user.AddBankCardListActivity;
import cn.com.buyforyou.fund.utils.Base64ImageUtil;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by guolonggang on 2018/12/10.
 * Description:银行卡adapter以及添加银行卡
 *
 */

public class AddbankcardListAdapter extends SimpleRecAdapter<BankAddResp, AddbankcardListAdapter.ViewHolder> {
    public static final int ITEM_CLICK = 0;    //点击标识
    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    public AddbankcardListAdapter.ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public AddbankcardListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_user_bank_list_add_item;
    }

    @Override
    public void onBindViewHolder(final AddbankcardListAdapter.ViewHolder holder, final int position) {
          final BankAddResp bankAddResp = data.get(position);
        //获取 图片Base64 字符串
        String strimage = bankAddResp.getBankLogo();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            holder.bankImage.setImageBitmap(bitmap);
        }
        holder.bankName.setText(bankAddResp.getBankName() + "（尾号" + bankAddResp.getBankNoTail() + "）");
        holder.tradeNumber.setText(bankAddResp.getTrade_acco());



        //item的点击事件
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecItemClick().onItemClick(position, bankAddResp, ITEM_CLICK, holder);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        /**
         * 银行图标
         */
        @BindView(R.id.image_banck)
        ImageView bankImage;
        /**
         * 银行名称
         */
        @BindView(R.id.banck_name)
        TextView bankName;

        /**
         * 内容
         */
        @BindView(R.id.rl_contents)
        LinearLayout rlContent;
        /**
         * 交易账号
         */
        @BindView(R.id.trade_number)
        TextView tradeNumber;


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
