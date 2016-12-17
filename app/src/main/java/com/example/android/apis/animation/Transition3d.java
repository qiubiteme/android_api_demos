package com.example.android.apis.animation;

import com.example.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 *这个示例应用程序展示了如何使用动画和各种布局
 *转换视图。结果是一个3 d之间的过渡
 *列表视图和一个ImageView。当用户单击列表,它会翻转
 *显示图片。当用户单击图片,显示了翻转
 *列表。动画是由两个小动画:上半年
 *旋转90度在Y轴上,下半年旋转
 * 90度在Y轴上的照片。上半场结束时,
 *列表是由看不见的和图片是可见的。
 */
public class Transition3d extends Activity implements
        AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mPhotosList;
    private ViewGroup mContainer;
    private ImageView mImageView;

    /**
     * 照片的名字显示在列表中
     */
    private static final String[] PHOTOS_NAMES = new String[]{
            "Lyon-3D左右翻转动画",
            "Livermore-3D左右翻转动画",
            "Tahoe Pier-3D左右翻转动画",
            "Lake Tahoe-3D左右翻转动画",
            "Grand Canyon-3D左右翻转动画",
            "Bodie-3D左右翻转动画"
    };

    // Resource identifiers for the photos we want to display
    private static final int[] PHOTOS_RESOURCES = new int[]{
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5,
            R.drawable.photo6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animations_main_screen);

        mPhotosList = (ListView) findViewById(android.R.id.list);
        mImageView = (ImageView) findViewById(R.id.picture);
        mContainer = (ViewGroup) findViewById(R.id.container);

        // Prepare the ListView 准备ListView
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, PHOTOS_NAMES);

        mPhotosList.setAdapter(adapter);
        mPhotosList.setOnItemClickListener(this);

        // 准备ImageView
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setOnClickListener(this);

        //因为我们缓存大观点,我们想保持他们的缓存
        //每个动画之间
        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

    /**
     * 设置一个新的3 d视图旋转的容器。
     *
     * @param position 点击显示的项目图片,或1显示列表
     * @param start    一开始旋转的角度必须开始
     * @param end      旋转的角度
     */
    private void applyRotation(int position, float start, float end) {
        // 找到的中心容器
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        //创建一个新的三维旋转的参数提供
        //动画侦听器是用来触发下一个动画
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        mContainer.startAnimation(rotation);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // 预先加载图像然后开始动画
        mImageView.setImageResource(PHOTOS_RESOURCES[position]);
        applyRotation(position, 0, 90);
    }

    public void onClick(View v) {
        applyRotation(-1, 180, 90);
    }

    /**
     *这个类监听动画上半年的结束。
     *然后发布一个新的行动,当容器有效地交换意见
     *是旋转了90度,因此看不见。
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mContainer.post(new SwapViews(mPosition));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * 该类负责交换观点,开始第二个
     *一半的动画。
     */
    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;

            if (mPosition > -1) {
                mPhotosList.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.requestFocus();

                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                mImageView.setVisibility(View.GONE);
                mPhotosList.setVisibility(View.VISIBLE);
                mPhotosList.requestFocus();

                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            mContainer.startAnimation(rotation);
        }
    }

}
