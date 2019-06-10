package cn.com.buyforyou.fund.ui.activity.user;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import cn.com.buyforyou.fund.App;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.model.user.PhotoResp;
import cn.com.buyforyou.fund.model.user.UserInformationResp;
import cn.com.buyforyou.fund.present.user.PhotoPresent;
import cn.com.buyforyou.fund.utils.BitmapUtils;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.utils.PhotoFromPhotoAlbum;
import cn.droidlover.xdroidmvp.widget.ToastUtils;
import io.reactivex.functions.Consumer;

/**
 * Created by guolonggang on 2019/4/12.
 */
public class PhotoActivity extends XActivity<PhotoPresent> implements View.OnClickListener {


    /**
     * 返回按钮
     */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;
    /**
     * 身份证正面
     */
    @BindView(R.id.iv_idcardZ)
    ImageView image_idCardZ;

    /**
     * 身份证反面
     */
    @BindView(R.id.iv_idcardF)
    ImageView image_idCardF;

    /**
     * 银行卡正面
     */
    @BindView(R.id.iv_bankcardZ)
    ImageView image_bankCradZ;

    /**
     * 银行卡反面
     */
    @BindView(R.id.iv_bankcardF)
    ImageView image_bankCardF;

    /**
     * 上传照片按钮
     *
     * @return
     */
    @BindView(R.id.btn_commit)
    Button button;
    private String token;
    private String userId;
    private HttpLoadingDialog httpLoadingDialog;

    private final int IMAGE_RESULT_CODE = 2;//相册
    private final int PICK = 1;//拍照
    //定义一个变量,为了区分是哪一张图片
    private int flag;
    private PopupWindow pop;

    private View bottomView;
    private String photoPath;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;

    //把图片途径放入到集合中去
    private HashMap<String, String> path = new HashMap<>();
    private String idCardF;
    private String idCardZ;
    private String bankCardZ;
    private String bankCardF;


    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public PhotoPresent newP() {
        return new PhotoPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("个人信息照片");
        httpLoadingDialog = new HttpLoadingDialog(context);
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        httpLoadingDialog.visible();
        getP().getImage(token, userId);
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        //申请动态权限
        requestPermissions();

    }


    private void showWaringDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->微动利基金->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                }).show();
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(context);
        rxPermission
                .request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //允许权限直接进入相册或者拍照
                        } else {
                            //提示跳转到设置界面，请求开启权限
                            showWaringDialog();
                        }
                    }

                });


    }

    @Override
    public void initEvents() {

        image_idCardZ.setOnClickListener(this);
        image_idCardF.setOnClickListener(this);
        image_bankCradZ.setOnClickListener(this);
        image_bankCardF.setOnClickListener(this);
        //请求网络
        button.setOnClickListener(new View.OnClickListener() {
            private String imagePths;

            @Override
            public void onClick(View v) {
                httpLoadingDialog.visible();
                getP().ftpData(token, userId, path);
            }
        });
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }


    /**
     * 弹框  是进相册还是进拍照
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_idcardZ:
                showPop();
                flag = 1;
                break;
            case R.id.iv_idcardF:
                showPop();
                flag = 2;
                break;
            case R.id.iv_bankcardZ:
                showPop();
                flag = 3;
                break;
            case R.id.iv_bankcardF:
                showPop();
                flag = 4;
                break;
            default:
                break;
        }
    }

    private void showPop() {
        bottomView = View.inflate(context, R.layout.layout_bottom_dialog, null);
        Button mAlbum = bottomView.findViewById(R.id.tv_album);
        Button mCamera = bottomView.findViewById(R.id.tv_camera);
        Button mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        goPhotoAlbum();
                        break;
                    case R.id.tv_camera:
                        goCamera();
                        break;
                    case R.id.tv_cancel:
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    //激活相机操作
    private void goCamera() {
        PackageManager packageManager = context.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            showToast("您的手机上没有拍照功能");
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, "cn.com.buyforyou.fund.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, PICK);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照
            case PICK:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (cameraSavePath != null) {
                        photoPath = String.valueOf(cameraSavePath);
                    }
                } else if (uri != null) {
                    photoPath = uri.getEncodedPath();
                }
                Log.d("拍照返回图片路径:", photoPath);
                if (flag == 1) {


                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_idCardZ);
                        path.put("idCardZ", newPath);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (flag == 2) {


                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_idCardF);
                        path.put("idCardF", newPath);
                        Log.e("拍照2", "onActivityResult: " + newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (flag == 3) {

                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_bankCradZ);
                        path.put("bankCardZ", newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (flag == 4) {
                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_bankCardF);
                        path.put("bankCardF", newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            //相册
            case IMAGE_RESULT_CODE:
                if (data == null) {
                    return;
                } else {
                    photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
                }
                if (flag == 1) {
                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Log.e("拍照", "onActivityResult: " + photoPath);
                        Log.e("新路径", "onActivityResult: " + newPath);
                        Glide.with(context).load(newPath).into(image_idCardZ);
                        path.put("idCardZ", newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (flag == 2) {

                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_idCardF);
                        path.put("idCardF", newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (flag == 3) {


                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_bankCradZ);
                        path.put("bankCardZ", newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (flag == 4) {

                    try {
                        String newPath = BitmapUtils.compressImageUpload(photoPath);
                        Glide.with(context).load(newPath).into(image_bankCardF);
                        path.put("bankCardF", newPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    /**
     * @param resp
     */
    public void requestSuccess(PhotoResp resp) {
        httpLoadingDialog.dismiss();
        if (resp.getStatus() == 200) {
            ToastUtils.show(context, "上传图片成功");

            finish();


        }

    }

    public void requestFailed() {

        httpLoadingDialog.dismiss();

    }

    public void getImageData(UserInformationResp resp) {
        httpLoadingDialog.dismiss();
        if (resp.getData() != null) {
            idCardF = resp.getData().getIdCardF();
            idCardZ = resp.getData().getIdCardZ();
            bankCardZ = resp.getData().getBankCardZ();
            bankCardF = resp.getData().getBankCardF();
            if (idCardF != null) {
                Glide.with(context).load(idCardF).placeholder(R.mipmap.truck_img_add)// 占位符
                        .error(R.mipmap.truck_img_add).into(image_idCardF);

            }
            if (idCardZ != null) {
                Glide.with(context).load(idCardZ).placeholder(R.mipmap.truck_img_add)// 占位符
                        .error(R.mipmap.truck_img_add).into(image_idCardZ);
            }
            if (bankCardZ != null) {
                Glide.with(context).load(bankCardZ).placeholder(R.mipmap.truck_img_add)// 占位符
                        .error(R.mipmap.truck_img_add).into(image_bankCradZ);
            }
            if (bankCardF != null) {
                Glide.with(context).load(bankCardF).placeholder(R.mipmap.truck_img_add)// 占位符
                        .error(R.mipmap.truck_img_add).into(image_bankCardF);
            }

        } else if (resp.getData() == null) {
            if (image_idCardZ == null) {
                Glide
                        .with(context)
                        .load(idCardZ)
                        .placeholder(R.mipmap.truck_img_add) // can also be a drawable
                        .error(R.mipmap.truck_img_add).into(image_idCardZ);
            } else if (image_idCardF == null) {
                Glide
                        .with(context)
                        .load(idCardF)
                        .placeholder(R.mipmap.truck_img_add).error(R.mipmap.truck_img_add) // can also be a drawable
                        .into(image_idCardF);
            } else if (image_bankCradZ == null) {
                Glide
                        .with(context)
                        .load(bankCardZ)
                        .placeholder(R.mipmap.truck_img_add).error(R.mipmap.truck_img_add) // can also be a drawable
                        .into(image_bankCradZ);
            } else if (image_bankCardF == null) {
                Glide
                        .with(context)
                        .load(bankCardF)
                        .placeholder(R.mipmap.truck_img_add).error(R.mipmap.truck_img_add) // can also be a drawable
                        .into(image_bankCardF);
            }


        }
    }

    public void getImageFail() {
        httpLoadingDialog.dismiss();
    }
}
