package com.zhsoft.fretting.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;


/**
 * @author ZengSuWa
 * @Description：
 * @Company：众鑫贷
 * @Created time：2016/7/14 13:37
 */
public class FundBuyDialog extends Dialog {

    public FundBuyDialog(Context context) {
        super(context);
    }

    public FundBuyDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnTextFinishListener {
        void onFinish(String str);
    }

    private static PayPwdEditText ppePwd;

    public static class Builder {
        private Context context;
        private String fundName;
        private String fundAmount;
        private FundBuyDialog.OnTextFinishListener onTextFinishListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置基金名称
         * @param fundName
         * @return
         */
        public Builder setFundName(String fundName) {
            this.fundName = fundName;
            return this;
        }

        /**
         * 设置基金金额
         * @param fundAmount
         * @return
         */
        public Builder setFundAmount(String fundAmount) {
            this.fundAmount = fundAmount;
            return this;
        }

        /**
         * 设置密码输入完成监听
         * @param onTextFinishListener
         * @return
         */
        public Builder setOnTextFinishListener(FundBuyDialog.OnTextFinishListener onTextFinishListener) {
            this.onTextFinishListener = onTextFinishListener;
            return this;
        }

        public FundBuyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final FundBuyDialog dialog = new FundBuyDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_fund_buy_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            //购买的基金名称
            ((TextView) layout.findViewById(R.id.tv_fund_name)).setText(fundName);
            //购买的基金金额
            ((TextView) layout.findViewById(R.id.tv_fund_amount)).setText(fundAmount);
            layout.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            ppePwd = (PayPwdEditText) layout.findViewById(R.id.ppe_pwd);
            //初始化交易密码输入框的样式
            ppePwd.initStyle(R.drawable.edit_num_bg, 6, 0.66f, R.color.color_444444, R.color.color_444444, 20);
            ppePwd.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
                @Override
                public void onFinish(String str) {//密码输入完后的回调
                    onTextFinishListener.onFinish(str);
                }
            });
            dialog.setContentView(layout);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.7f;
            return dialog;
        }

    }

    /**
     * dialog重新显示时，清空上次输入的密码
     */
    @Override
    public void show() {
        super.show();
        ppePwd.clearText();
    }
}
