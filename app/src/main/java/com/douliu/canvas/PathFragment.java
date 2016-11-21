package com.douliu.canvas;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.douliu.canvas.view.PointFTypeEvaluator;
import com.douliu.canvas.view.TestView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PathFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PathFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TestView mView;
    private FrameLayout mContentFrame;
    private Button mBtStart;

    private Handler mHandler= new Handler();
    private Button mBtStop;

    public PathFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PathFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PathFragment newInstance(String param1, String param2) {
        PathFragment fragment = new PathFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_path, container, false);
        initView(inflate);

        return inflate;
    }

    private void initView(View inflate) {
        mView = (TestView) inflate.findViewById(R.id.myview);
        mContentFrame = (FrameLayout) inflate.findViewById(R.id.content_frame);
        mBtStart = (Button) inflate.findViewById(R.id.bt_start);
        mBtStop = (Button) inflate.findViewById(R.id.bt_stop);

        mBtStart.setOnClickListener(this);
        mBtStop.setOnClickListener(this);
    }

    private void fly(){

   /*     PointF start = new PointF(0,mView.getHeight()/3 * 2);
        PointF end = new PointF(mView.getWidth(),mView.getHeight()/3);
        PointF control = new PointF(start.x,end.y);*/

        final ImageView fakeAddImageView = new ImageView(getContext());
        mContentFrame.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.small_circle);
        FrameLayout.LayoutParams layoutParams = new FrameLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) mView.getStartPoint().y;
        layoutParams.height = 100;
        layoutParams.width = 100;
        fakeAddImageView.setLayoutParams(layoutParams);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(mView.getControlPoint()), mView.getStartPoint(), mView.getEndPoint());

        addAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
                fakeAddImageView.setX(point.x - 50);
                fakeAddImageView.setY(point.y - 50);
            }
        });
        addAnimator.setInterpolator(new LinearInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(fakeAddImageView, "scaleX", 1f, 2f,1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(fakeAddImageView, "scaleY", 1f, 2f,1f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(scaleX).with(scaleY);
                animatorSet.setDuration(400);
                animatorSet.setStartDelay(900);
                animatorSet.start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                mContentFrame.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        addAnimator.setDuration(2000);
//        addAnimator.setRepeatCount(3);
//        addAnimator.setRepeatMode(ValueAnimator.RESTART);
        addAnimator.start();



    }


    int count;
    @Override
    public void onClick(View view) {
        if (view == mBtStart){
            count = 0;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fly();
                    count++;
                    if (count % 4 == 0){
                        mHandler.postDelayed(this,800);
                    }else {
                        mHandler.postDelayed(this,500);
                    }
                }
            },0);
        }else if (view == mBtStop){
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
