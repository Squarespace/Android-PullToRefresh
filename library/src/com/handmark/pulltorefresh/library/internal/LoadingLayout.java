/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.R;

public class LoadingLayout extends FrameLayout {

  static final int DEFAULT_ROTATION_ANIMATION_DURATION = 600;

  private Drawable mHeaderImage_pull;
  private Drawable mHeaderImage_release;
  private Drawable mHeaderImage_refreshing;
  private OnClickListener onLeftSideIndicatorClicked;
  private OnClickListener onRightSideIndicatorClicked;

  private final Matrix mHeaderImageMatrix;

  private final TextView mHeaderText;
  private final TextView mSubHeaderText;

  private String mPullLabel;
  private String mRefreshingLabel;
  private String mReleaseLabel;

  private float mRotationPivotX, mRotationPivotY;

  private final Animation mRotateAnimation;

  private boolean rotationMode;

  private ImageView mHeaderImage;

  public LoadingLayout(Context context){
    super(context);
    ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);
    mHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
    mSubHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_sub_text);
    mHeaderImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
    mHeaderImage.setScaleType(ScaleType.MATRIX);
    mHeaderImageMatrix = new Matrix();
    mHeaderImage.setImageMatrix(mHeaderImageMatrix);
    final Interpolator interpolator = new LinearInterpolator();
    mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        0.5f);
    mRotateAnimation.setInterpolator(interpolator);
    mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
    mRotateAnimation.setRepeatCount(Animation.INFINITE);
    mRotateAnimation.setRepeatMode(Animation.RESTART);
  }
  public Drawable getScaledDrawable(float scale,Drawable d){
    
    BitmapDrawable bd = (BitmapDrawable)d;
    Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(),
                         (int) (bd.getIntrinsicHeight() *scale),
                         (int) (bd.getIntrinsicWidth() *scale),
                         false);
    return new BitmapDrawable(getResources(),b);
  }
  public LoadingLayout(Context context, final Mode mode, TypedArray attrs,OnClickListener onLeftSideIndicatorClicked,OnClickListener onRightSideIndicatorClicked) {
    super(context);
    rotationMode=attrs.getBoolean(R.styleable.PullToRefresh_rotation_mode,true);
    float scale=attrs.getFloat(R.styleable.PullToRefresh_pull_to_refresh_image_scale,1);
    ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);
    mHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
    mSubHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_sub_text);
    mHeaderImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);

    mHeaderImageMatrix = new Matrix();
    if(rotationMode){
      mHeaderImage.setScaleType(ScaleType.MATRIX);
      mHeaderImage.setImageMatrix(mHeaderImageMatrix);
    }
    
    final Interpolator interpolator = new LinearInterpolator();
    mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
        0.5f);
    mRotateAnimation.setInterpolator(interpolator);
    mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
    mRotateAnimation.setRepeatCount(Animation.INFINITE);
    mRotateAnimation.setRepeatMode(Animation.RESTART);


    switch (mode) {
      case PULL_UP_TO_REFRESH:
        // Load in labels
        if(attrs.hasValue(R.styleable.PullToRefresh_pull_to_refresh_from_bottom_pull_label)){
          mPullLabel=attrs.getString(R.styleable.PullToRefresh_pull_to_refresh_from_bottom_pull_label);
        }else{
          mPullLabel=context.getString(R.string.pull_to_refresh_from_bottom_pull_label);
        }
        if(attrs.hasValue(R.styleable.PullToRefresh_pull_to_refresh_from_bottom_refreshing_label)){
          mRefreshingLabel=attrs.getString(R.styleable.PullToRefresh_pull_to_refresh_from_bottom_refreshing_label);
        }else{
          mRefreshingLabel=context.getString(R.string.pull_to_refresh_from_bottom_refreshing_label);
        }
        if(attrs.hasValue(R.styleable.PullToRefresh_pull_to_refresh_from_bottom_release_label)){
          mReleaseLabel=attrs.getString(R.styleable.PullToRefresh_pull_to_refresh_from_bottom_release_label);
        }else{
          mReleaseLabel=context.getString(R.string.pull_to_refresh_from_bottom_release_label);
        }
        if(!rotationMode){
          mHeaderImage_pull=getScaledDrawable(scale,attrs.getDrawable(R.styleable.PullToRefresh_pull_to_refresh_release_image));
          mHeaderImage_refreshing=getScaledDrawable(scale,attrs.getDrawable(R.styleable.PullToRefresh_pull_to_refresh_refreshing_image));
          mHeaderImage_release=getScaledDrawable(scale,attrs.getDrawable(R.styleable.PullToRefresh_pull_to_refresh_pull_image));
        }
        break;

      case PULL_DOWN_TO_REFRESH:
      default:
        // Load in labels
        if(attrs.hasValue(R.styleable.PullToRefresh_pull_to_refresh_pull_label)){
          mPullLabel=attrs.getString(R.styleable.PullToRefresh_pull_to_refresh_pull_label);
        }else{
          mPullLabel=context.getString(R.string.pull_to_refresh_pull_label_string);
        }
        if(attrs.hasValue(R.styleable.PullToRefresh_pull_to_refresh_refreshing_label)){
          mRefreshingLabel=attrs.getString(R.styleable.PullToRefresh_pull_to_refresh_refreshing_label);
        }else{
          mRefreshingLabel=context.getString(R.string.pull_to_refresh_refreshing_label_string);
        }
        if(attrs.hasValue(R.styleable.PullToRefresh_pull_to_refresh_release_label)){
          mReleaseLabel=attrs.getString(R.styleable.PullToRefresh_pull_to_refresh_release_label);
        }else{
          mReleaseLabel=context.getString(R.string.pull_to_refresh_release_label_string);
        } 

        if(!rotationMode){
          mHeaderImage_pull=getScaledDrawable(scale,attrs.getDrawable(R.styleable.PullToRefresh_pull_to_refresh_pull_image));
          mHeaderImage_refreshing=getScaledDrawable(scale,attrs.getDrawable(R.styleable.PullToRefresh_pull_to_refresh_refreshing_image));
          mHeaderImage_release=getScaledDrawable(scale,attrs.getDrawable(R.styleable.PullToRefresh_pull_to_refresh_release_image));
        }
        
        break;
    }

    if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderTextColor)) {
      ColorStateList colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderTextColor);
      setTextColor(null != colors ? colors : ColorStateList.valueOf(0xFF000000));
    }
    if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderSubTextColor)) {
      ColorStateList colors = attrs.getColorStateList(R.styleable.PullToRefresh_ptrHeaderSubTextColor);
      setSubTextColor(null != colors ? colors : ColorStateList.valueOf(0xFF000000));
    }
    if (attrs.hasValue(R.styleable.PullToRefresh_ptrHeaderBackground)) {
      Drawable background = attrs.getDrawable(R.styleable.PullToRefresh_ptrHeaderBackground);
      if (null != background) {
        setBackgroundDrawable(background);
      }
    }

    // Try and get defined drawable from Attrs
    Drawable imageDrawable = null;
    if (attrs.hasValue(R.styleable.PullToRefresh_ptrDrawable)) {
      imageDrawable = attrs.getDrawable(R.styleable.PullToRefresh_ptrDrawable);
    }

    // If we don't have a user defined drawable, load the default
    if (null == imageDrawable) {
      imageDrawable = context.getResources().getDrawable(R.drawable.default_ptr_drawable);
    }

    // Set Drawable, and save width/height
    setLoadingDrawable(imageDrawable);

    reset();
  }

  public void reset() {
    mHeaderText.setText(Html.fromHtml(mPullLabel));
    mHeaderImage.setVisibility(View.VISIBLE);
    mHeaderImage.clearAnimation();

    resetImageRotation();

    if (TextUtils.isEmpty(mSubHeaderText.getText())) {
      mSubHeaderText.setVisibility(View.GONE);
    } else {
      mSubHeaderText.setVisibility(View.VISIBLE);
    }
  }

  public void releaseToRefresh() {
    Log.i("LoadingLayout","Releasing:"+rotationMode);
    mHeaderText.setText(Html.fromHtml(mReleaseLabel));
    if(!rotationMode){
      mHeaderImage.setImageDrawable(mHeaderImage_release);
    }
  }

  public void setPullLabel(String pullLabel) {
    mPullLabel = pullLabel;
  }

  public void refreshing() {
    Log.i("LoadingLayout","Refreshing: "+rotationMode);
    mHeaderText.setText(Html.fromHtml(mRefreshingLabel));
    if(rotationMode){
      mHeaderImage.startAnimation(mRotateAnimation);
    }else{
      mHeaderImage.setImageDrawable(mHeaderImage_refreshing);
    }

    mSubHeaderText.setVisibility(View.GONE);
  }

  public void setRefreshingLabel(String refreshingLabel) {
    mRefreshingLabel = refreshingLabel;
  }

  public void setReleaseLabel(String releaseLabel) {
    mReleaseLabel = releaseLabel;
  }

  public void pullToRefresh() {
    Log.i("LoadingLayout","PullToRefresh:"+rotationMode);
    mHeaderText.setText(Html.fromHtml(mPullLabel));
    if(!rotationMode){
      mHeaderImage.setImageDrawable(mHeaderImage_pull);
      mHeaderImage.invalidate();
    }
  }

  public void setTextColor(ColorStateList color) {
    mHeaderText.setTextColor(color);
    mSubHeaderText.setTextColor(color);
  }

  public void setSubTextColor(ColorStateList color) {
    mSubHeaderText.setTextColor(color);
  }

  public void setTextColor(int color) {
    setTextColor(ColorStateList.valueOf(color));
  }

  public void setLoadingDrawable(Drawable imageDrawable) {
    // Set Drawable, and save width/height
    mHeaderImage.setImageDrawable(imageDrawable);
    mRotationPivotX = imageDrawable.getIntrinsicWidth() / 2f;
    mRotationPivotY = imageDrawable.getIntrinsicHeight() / 2f;
  }

  public void setSubTextColor(int color) {
    setSubTextColor(ColorStateList.valueOf(color));
  }

  public void setSubHeaderText(CharSequence label) {
    if (TextUtils.isEmpty(label)) {
      mSubHeaderText.setVisibility(View.GONE);
    } else {
      mSubHeaderText.setText(label);
      mSubHeaderText.setVisibility(View.VISIBLE);
    }
  }

  public void onPullY(float scaleOfHeight) {
    if(rotationMode){
      mHeaderImageMatrix.setRotate(scaleOfHeight * 90, mRotationPivotX, mRotationPivotY);
      mHeaderImage.setImageMatrix(mHeaderImageMatrix);
    }
  }

  private void resetImageRotation() {
    if(rotationMode){
      mHeaderImageMatrix.reset();
      mHeaderImage.setImageMatrix(mHeaderImageMatrix);
    }
  }
}
