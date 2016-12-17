/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.apis.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.android.apis.R;

import static android.view.animation.Animation.RESTART;

/**
 * This application demonstrates the use of Path animation.
 * 此应用程序演示了使用路径动画
 */
public class PathAnimations extends Activity implements
        RadioGroup.OnCheckedChangeListener, View.OnLayoutChangeListener {
    //遍历的路径;
    final static Path sTraversalPath = new Path();
    // 遍历路径大小;
    final static float TRAVERSE_PATH_SIZE = 7.0f;
    //用来表示可变值的类
    final static Property<PathAnimations, Point> POINT_PROPERTY
            = new Property<PathAnimations, Point>(Point.class, "point") {

        @Override
        public Point get(PathAnimations object) {
            View v = object.findViewById(R.id.moved_item);
            return new Point(Math.round(v.getX()), Math.round(v.getY()));
        }

        @Override
        public void set(PathAnimations object, Point value) {
            object.setCoordinates(value.x, value.y);
        }
    };

    static {
        float inverse_sqrt8 = (float) Math.sqrt(0.125);
        RectF bounds = new RectF(1, 1, 3, 3);
        sTraversalPath.addArc(bounds, 45, 180);
        sTraversalPath.addArc(bounds, 225, 180);

        bounds.set(1.5f + inverse_sqrt8, 1.5f + inverse_sqrt8, 2.5f + inverse_sqrt8,
                2.5f + inverse_sqrt8);
        sTraversalPath.addArc(bounds, 45, 180);
        sTraversalPath.addArc(bounds, 225, 180);

        bounds.set(4, 1, 6, 3);
        sTraversalPath.addArc(bounds, 135, -180);
        sTraversalPath.addArc(bounds, -45, -180);

        bounds.set(4.5f - inverse_sqrt8, 1.5f + inverse_sqrt8, 5.5f - inverse_sqrt8, 2.5f +
                inverse_sqrt8);
        sTraversalPath.addArc(bounds, 135, -180);
        sTraversalPath.addArc(bounds, -45, -180);

        sTraversalPath.addCircle(3.5f, 3.5f, 0.5f, Path.Direction.CCW);

        sTraversalPath.addArc(new RectF(1, 2, 6, 6), 0, 180);
    }

    private CanvasView mCanvasView;

    private ObjectAnimator mAnimator;

    /**
     * Called when the activity is first created.:第一次被创建时调用活动。;
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_animations);
        mCanvasView = (CanvasView) findViewById(R.id.canvas);
        mCanvasView.addOnLayoutChangeListener(this);
        ((RadioGroup) findViewById(R.id.path_animation_type)).setOnCheckedChangeListener(this);
    }

    /**
     * 设置坐标;
     * @param x
     * @param y
     */
    public void setCoordinates(int x, int y) {
        changeCoordinates((float) x, (float) y);
    }

    /**
     * 改变坐标;
     * @param x
     * @param y
     */
    public void changeCoordinates(float x, float y) {
        View v = findViewById(R.id.moved_item);
        v.setX(x);
        v.setY(y);
    }

    /**
     * 设置点;
     * @param point
     */
    public void setPoint(PointF point) {
        changeCoordinates(point.x, point.y);
    }

    @Override //RadioGroup监听回调
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        startAnimator(checkedId);
    }

    @Override //布局变化监听
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        int checkedId = ((RadioGroup) findViewById(R.id.path_animation_type))
                .getCheckedRadioButtonId();
        if (checkedId != RadioGroup.NO_ID) {
            startAnimator(checkedId);
        }
    }

    /**
     * 开始动画
     * @param checkedId
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimator(int checkedId) {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        View view = findViewById(R.id.moved_item);
        Path path = mCanvasView.getPath();
        if (path.isEmpty()) {
            return;
        }

        switch (checkedId) {
            case R.id.named_components:
                // Use the named "x" and "y" properties for individual (x, y)
                // coordinates of the Path and set them on the view object.
                //使用名为“x”和“y”个人的属性(x,y);/ /坐标路径和设置它们的视图对象。;
                // The setX(float) and setY(float) methods are called on view.
                // An int version of this method also exists for animating
                // int Properties.
                //集合X(浮动),Y(浮动)方法被称为视图。;/ /整数版本的动画这种方法也存在;/ /整数属性。;
                mAnimator = ObjectAnimator.ofFloat(view, "x", "y", path);
                break;
            case R.id.property_components:
                // Use two Properties for individual (x, y) coordinates of the Path
                // and set them on the view object.
                //:使用两个属性对个人(x,y)坐标的路径;/ /并设置它们在视图对象。;
                // An int version of this method also exists for animating
                // int Properties.
                //int版本,该方法也为动画的存在;/ /整数属性。;
                mAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
                break;
            case R.id.multi_int:
                // Use a multi-int setter to animate along a Path. The method
                // setCoordinates(int x, int y) is called on this during the animation.
                // Either "setCoordinates" or "coordinates" are acceptable parameters
                // because the "set" can be implied.
                //.:使用multi-int setter沿着路径动画。该方法;/ /设置坐标(int x,inty)在动画//“坐标”或“坐标”是可以接受的参数;/ /可以暗示,因为“套”。;
                mAnimator = ObjectAnimator.ofMultiInt(this, "setCoordinates", path);
                break;
            case R.id.multi_float:
                // Use a multi-float setter to animate along a Path. The method
                // changeCoordinates(float x, float y) is called on this during the animation.
                //:使用multi-float setter沿着路径动画。该方法;/ /改变坐标(x浮动,浮动y)在动画。;
                mAnimator = ObjectAnimator.ofMultiFloat(this, "changeCoordinates", path);
                break;
            case R.id.named_setter:
                // Use the named "point" property to animate along the Path.
                // There must be a method setPoint(PointF) on the animated object.
                //:使用名为“点”属性动画沿着路径。;/ /必须有一个方法设置点(点)的动画对象。;
                // Because setPoint takes a PointF parameter, no TypeConverter is necessary.
                // In this case, the animated object is PathAnimations.
                //因为F参数设定值需要点,没有类型转换器是必要的。;/ /在这种情况下,动画的动画对象路径。;
                mAnimator = ObjectAnimator.ofObject(this, "point", null, path);
                break;
            case R.id.property_setter:
                // Use the POINT_PROPERTY property to animate along the Path.
                // POINT_PROPERTY takes a Point, not a PointF, so the TypeConverter
                // PointFToPointConverter is necessary.
                //:使用P O我N T P R O P E R T Y属性动画沿着路径。;
                // / O / P I N T P R O P E R T Y点,而不是一个点,所以类型转换器;/ / F转换器是必要的。;
                mAnimator = ObjectAnimator.ofObject(this, POINT_PROPERTY,
                        new PointFToPointConverter(), path);
                break;
        }

        mAnimator.setDuration(10000);
        mAnimator.setRepeatMode(Animation.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    public static class CanvasView extends FrameLayout {

        Path mPath = new Path();

        Paint mPathPaint = new Paint();

        public CanvasView(Context context) {
            super(context);
            init();
        }

        public CanvasView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public CanvasView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        private void init() {
            setWillNotDraw(false);
            mPathPaint.setColor(0xFFFF0000);
            mPathPaint.setStrokeWidth(2.0f);
            mPathPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            if (changed) {
                Matrix scale = new Matrix();
                float scaleWidth = (right - left) / TRAVERSE_PATH_SIZE;
                float scaleHeight = (bottom - top) / TRAVERSE_PATH_SIZE;
                scale.setScale(scaleWidth, scaleHeight);
                sTraversalPath.transform(scale, mPath);
            }
        }

        public Path getPath() {
            return mPath;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawPath(mPath, mPathPaint);
            super.draw(canvas);
        }
    }

    /**
     * 轨迹转换器;
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class PointFToPointConverter extends TypeConverter<PointF, Point> {
        Point mPoint = new Point();

        public PointFToPointConverter() {
            super(PointF.class, Point.class);
        }

        @Override
        public Point convert(PointF value) {
            mPoint.set(Math.round(value.x), Math.round(value.y));
            return mPoint;
        }
    }
}
