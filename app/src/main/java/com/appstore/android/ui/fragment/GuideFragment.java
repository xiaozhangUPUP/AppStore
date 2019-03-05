package com.appstore.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstore.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangqi on 2019/2/25
 */
public class GuideFragment extends Fragment {

    private static final String IMG_ID = "IMG_ID";
    private static final String BGCOLOR_ID = "BGCOLOR_ID";
    private static final String TEXT_ID = "TEXT_ID";
    @BindView(R.id.imgView)
    ImageView imgView;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    Unbinder unbinder;
    private View view;


    public static GuideFragment newInstance(int imgResId, int bgColorResId, int textResId) {
        GuideFragment fragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMG_ID, imgResId);
        bundle.putInt(BGCOLOR_ID, bgColorResId);
        bundle.putInt(TEXT_ID, textResId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guide, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        Bundle args = getArguments();
        int imgId = args.getInt(IMG_ID);
        int bgColorId = args.getInt(BGCOLOR_ID);
        int textId = args.getInt(TEXT_ID);

        rootView.setBackgroundColor(getResources().getColor(bgColorId));
        imgView.setBackgroundResource(imgId);
        text.setText(textId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
