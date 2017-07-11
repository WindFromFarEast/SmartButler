package com.studio.smartbutler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.smartbutler.R;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.fragment
 * file name: WechatFragment
 * creator: WindFromFarEast
 * created time: 2017/7/11 12:02
 * description: 智能管家
 */

public class WechatFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_wechat,null);
        return view;
    }
}
