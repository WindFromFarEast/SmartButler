package com.studio.smartbutler.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.smartbutler.R;
import com.studio.smartbutler.entity.MyUser;
import com.studio.smartbutler.ui.CourierActivity;
import com.studio.smartbutler.ui.LoginActivity;
import com.studio.smartbutler.ui.PhoneActivity;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.fragment
 * file name: UserFragment
 * creator: WindFromFarEast
 * created time: 2017/7/11 12:03
 * description: 个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener
{
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private Button btn_change_userinfo;
    private Button btn_change_ok;
    private Button btn_exit;
    private Button btn_album;
    private Button btn_camera;
    private Button btn_cancel;
    private TextView tv_courier;
    private TextView tv_place;
    private CircleImageView circle_avatar;
    private Dialog avatarClickDialog;
    private View dialogView;
    private String avatarPath;
    private String avatarUrl;

    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_user,null);
        initView(view);
        return view;
    }

    //初始化控件
    private void initView(View view)
    {
        et_username= (EditText) view.findViewById(R.id.et_username);
        et_sex= (EditText) view.findViewById(R.id.et_sex);
        et_age= (EditText) view.findViewById(R.id.et_age);
        et_desc= (EditText) view.findViewById(R.id.et_desc);
        btn_change_userinfo= (Button) view.findViewById(R.id.btn_change_userinfo);
        btn_exit= (Button) view.findViewById(R.id.btn_exit);
        btn_change_ok= (Button) view.findViewById(R.id.btn_change_ok);
        circle_avatar= (CircleImageView) view.findViewById(R.id.circle_avatar);
        tv_courier= (TextView) view.findViewById(R.id.tv_courier);
        tv_place= (TextView) view.findViewById(R.id.tv_place);

        //添加监听器
        btn_change_userinfo.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_change_ok.setOnClickListener(this);
        circle_avatar.setOnClickListener(this);
        tv_courier.setOnClickListener(this);
        tv_place.setOnClickListener(this);

        //默认不允许在输入框中输入
        setEnabled(false);
        //在输入框中显示用户信息
        showUserInfo();
    }

    //为按钮设置监听事件
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_change_userinfo:
            {
                //允许输入框输入
                setEnabled(true);
                //显示确认修改按钮
                btn_change_ok.setVisibility(View.VISIBLE);
                //设置不允许再次点击该按钮
                btn_change_userinfo.setEnabled(false);
                break;
            }
            case R.id.btn_exit:
            {
                //退出登录
                MyUser.logOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;
            }
            case R.id.btn_change_ok:
            {

                if (!TextUtils.isEmpty(et_username.getText().toString().trim())&!TextUtils.isEmpty(et_sex.getText().toString().trim())
                        &!TextUtils.isEmpty(et_age.getText().toString().trim()))
                {
                    //若输入框不为空
                    //性别栏只允许输入男或女
                    if (et_sex.getText().toString().trim().equals("男")|et_sex.getText().toString().trim().equals("女"))
                    {
                        changeUserInfo();
                        //允许点击编辑资料按钮,隐藏确认修改按钮
                        btn_change_userinfo.setEnabled(true);
                        btn_change_ok.setVisibility(View.GONE);
                        //不允许输入框输入
                        setEnabled(false);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"性别栏只允许输入男或女",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.circle_avatar:
            {
                //点击圆形头像后进行编辑头像
                showDialogForAvatarOrDismiss(true);
                break;
            }
            case R.id.btn_album:
            {
                //运行时权限处理,获取读写SD卡的权限
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}
                            ,StaticClass.WRITE_EXTERNAL_STORAGE_CODE);
                }
                else
                {
                    toAlbum();
                    showDialogForAvatarOrDismiss(false);
                }
                break;
            }
            case R.id.btn_camera:
            {
                toCamera();
                showDialogForAvatarOrDismiss(false);
                break;
            }
            case R.id.btn_cancel:
            {
                showDialogForAvatarOrDismiss(false);
                break;
            }
            case R.id.tv_courier:
            {
                //点击物流查询跳转到物流查询页面
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            }
            case R.id.tv_place:
            {
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
            }
        }
    }


    //设置输入框能否输入
    private void setEnabled(boolean is)
    {
        et_username.setEnabled(is);
        et_desc.setEnabled(is);
        et_age.setEnabled(is);
        et_sex.setEnabled(is);
    }

    //显示用户最新的个人信息
    private void showUserInfo()
    {
        MyUser user= MyUser.getCurrentUser(MyUser.class);
        String username=user.getUsername();
        String sex=user.getSex()?"男":"女";
        String age=user.getAge().toString();
        String desc=user.getDesc();
        if (user.getAvatarUrl()!=null)
        {
            String avatarUrl=user.getAvatarUrl();
            L.i("头像文件Url:"+avatarUrl);
            //下载头像文件
            BmobFile file=new BmobFile("avatar.bmp","",avatarUrl);
            file.download(new DownloadFileListener()
            {
                @Override
                public void done(String savePath, BmobException e)
                {
                    if (e==null)
                    {
                        Toast.makeText(getActivity(),"头像下载成功",Toast.LENGTH_SHORT).show();
                        avatarPath=savePath;
                        //设置头像,由于download方法实质上单独开辟了一个线程,因此若是将设置头像方法放在外部的话很有可能造成还未下载完头像就执行了设置头像的步骤
                        Bitmap avatarBmp=BitmapFactory.decodeFile(avatarPath);
                        circle_avatar.setImageBitmap(avatarBmp);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"头像下载失败",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onProgress(Integer integer, long l)
                {

                }

                @Override
                public void onStart()
                {
                    Toast.makeText(getActivity(),"正在下载用户头像",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            L.i("没有头像文件!");
        }
        et_username.setText(username);
        et_sex.setText(sex);
        et_age.setText(age);
        et_desc.setText(desc);
    }

    //修改用户信息
    private void changeUserInfo()
    {
        //获取输入框的内容
        String username=et_username.getText().toString().trim();
        String sex_text=et_sex.getText().toString().trim();
        boolean sex = true;
        if (sex_text.equals("男"))
        {
            sex=true;
        }
        else
        {
            sex=false;
        }
        Integer age=Integer.parseInt(et_age.getText().toString().trim());
        String desc=et_desc.getText().toString();

        //更新用户信息
        MyUser user=new MyUser();
        user.setUsername(username);
        user.setSex(sex);
        user.setDesc(desc);
        user.setAge(age);
        BmobUser bmobUser=MyUser.getCurrentUser();
        user.update(bmobUser.getObjectId(), new UpdateListener()
        {
            @Override
            public void done(BmobException e)
            {
                if (e==null)
                {
                    //更新成功
                    Toast.makeText(getActivity(),"更新成功",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //更新失败
                    Toast.makeText(getActivity(),"更新失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //让用户抉择使用相册还是相机来选择头像
    private void showDialogForAvatarOrDismiss(boolean showOrNotShow)
    {
        if (showOrNotShow)
        {
            avatarClickDialog = new Dialog(getActivity(), R.style.Theme_Light_Dialog);
            dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog,null);
            //获得dialog的window窗口
            Window window = avatarClickDialog.getWindow();
            //设置dialog在屏幕底部
            window.setGravity(Gravity.BOTTOM);
            //设置dialog弹出时的动画效果，从屏幕底部向上弹出
            window.setWindowAnimations(R.style.dialogStyle);
            window.getDecorView().setPadding(0, 0, 0, 0);
            //获得window窗口的属性
            android.view.WindowManager.LayoutParams lp = window.getAttributes();
            //设置窗口宽度为充满全屏
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //设置窗口高度为包裹内容
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //将设置好的属性set回去
            window.setAttributes(lp);
            //将自定义布局加载到dialog上
            avatarClickDialog.setContentView(dialogView);
            //初始化Button
            btn_camera= (Button) dialogView.findViewById(R.id.btn_camera);
            btn_album= (Button) dialogView.findViewById(R.id.btn_album);
            btn_cancel= (Button) dialogView.findViewById(R.id.btn_cancel);
            //监听Button
            btn_camera.setOnClickListener(this);
            btn_album.setOnClickListener(this);
            btn_cancel.setOnClickListener(this);
            avatarClickDialog.show();
        }
        else
        {
            avatarClickDialog.dismiss();
        }
    }

    //相机拍摄获取头像
    private void toCamera()
    {
        //创建File对象,存储拍照得到的图片
        File outputImage=new File(getActivity().getExternalCacheDir(),"output_image.jpg");
        try
        {
            if (!outputImage.exists())
            {
                //若不存在则创建文件output_image.jpg
                outputImage.createNewFile();
            }
            else
            {
                //若存在则删除文件output_image.jpg后再创建
                outputImage.delete();
                outputImage.createNewFile();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //根据系统版本创建文件output_image.jpg的Uri
        if (Build.VERSION.SDK_INT>=24)
        {
            //版本大于Android7.0
            imageUri= FileProvider.getUriForFile(getActivity(),"com.studio.smartbutler.fileprovider",outputImage);
        }
        else
        {
            //版本小于Android7.0
            imageUri=Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);//指定照片输出Uri
        startActivityForResult(intent, StaticClass.TAKE_PHOTO);
        //关闭Dialog
        showDialogForAvatarOrDismiss(false);
    }

    //相册获取头像
    private void toAlbum()
    {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,StaticClass.CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            //相机
            case StaticClass.TAKE_PHOTO:
            {
                try
                {
                    if (resultCode==RESULT_OK)
                    {
                        cutPicture(imageUri);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            //相册
            case StaticClass.CHOOSE_PHOTO:
            {
                if (resultCode==RESULT_OK)
                {
                    //判断系统版本号，大于KitKat版本和小于KitKat版本需要用两种方法解析图片Uri
                    if (Build.VERSION.SDK_INT>=19)
                    {
                        //大于KitKat
                        cutPicture(data.getData());
                    }
                    else
                    {
                        //小于KitKat
                        cutPicture(data.getData());
                    }
                }
                break;
            }
            //裁剪图片
            case StaticClass.CUT_PHOTO:
            {
                setPictureToView(data);
                if (data!=null)
                {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Uri uri = getImageUri(getActivity(), bitmap);
                    //将头像保存到服务器
                    saveAvatarToBmob(getUriPath(uri, null));
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case StaticClass.WRITE_EXTERNAL_STORAGE_CODE:
            {
                if (grantResults.length>0 & grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    //同意获得读写SD卡的权限则进入相册
                    toAlbum();
                }
                else
                {
                    //拒绝获得读写SD卡的权限则提醒用户无法使用相册功能
                    Toast.makeText(getActivity(), getActivity().getString(R.string.deny_permission), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    //对图片进行裁剪
    private void cutPicture(Uri uri)
    {
        if (uri==null)
        {
            return;
        }
        else
        {
            Intent intent=new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri,"image/*");
            //设置裁剪属性
            intent.putExtra("crop","true");
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            intent.putExtra("outputX",320);
            intent.putExtra("outputY",320);
            intent.putExtra("return-data",true);
            startActivityForResult(intent,StaticClass.CUT_PHOTO);
        }
    }

    //将图片设置到头像
    private void setPictureToView(Intent data)
    {
        if (data!=null)
        {
            Bundle bundle=data.getExtras();
            if (bundle!=null)
            {
                Bitmap bitmap=bundle.getParcelable("data");
                circle_avatar.setImageBitmap(bitmap);
            }
        }
    }

    //保存用户头像至服务器
    private void saveAvatarToBmob(final String filePath)
    {
        final BmobFile bmobFile=new BmobFile(new File(filePath));
        //上传图片至Bmob服务器
        bmobFile.uploadblock(new UploadFileListener()
        {
            @Override
            public void done(BmobException e)
            {
                if (e==null)
                {
                    avatarUrl=bmobFile.getFileUrl();//必须在done回调方法里面实现上传成功后的逻辑,因此uploadblock方法开辟了子线程
                    L.i("图片路径:"+filePath);
                    MyUser myUser=new MyUser();
                    myUser.setAvatarUrl(avatarUrl);
                    BmobUser bmobUser=BmobUser.getCurrentUser(MyUser.class);
                    L.i("头像地址:"+avatarUrl);
                    myUser.update(bmobUser.getObjectId(), new UpdateListener()
                    {
                        @Override
                        public void done(BmobException e)
                        {

                            if (e==null)
                            {
                                Toast.makeText(getActivity(), "头像更新成功", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"头像更新失败",Toast.LENGTH_SHORT).show();
                                L.i("头像更新失败原因:"+e.toString());
                            }
                        }
                    });
                    Toast.makeText(getActivity(), "头像上传成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "头像上传失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String getUriPath(Uri uri,String selection)
    {
        String path=null;
        Cursor cursor=getActivity().getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //获得本地Bitmap图片的路径
    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
