package com.ovenbits.quickactionview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * View that shows the action
 */
public class ActionView extends View {

    private Paint mBackgroundPaint;

    private int mActionCircleRadius;
    private float mActionCircleRadiusExpanded;
    private int mIconPadding;
    private float mInterpolation;

    private Action mAction;
    private ConfigHelper mConfigHelper;

    public ActionView(Context context, Action action, ConfigHelper configHelper) {
        super(context);
        mAction = action;
        mConfigHelper = configHelper;
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mActionCircleRadius = getResources().getDimensionPixelSize(R.dimen.qav_action_view_radius);
        mActionCircleRadiusExpanded = getResources().getDimensionPixelSize(R.dimen.qav_action_view_radius_expanded);
        mIconPadding = getResources().getDimensionPixelSize(R.dimen.qav_action_view_icon_padding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (mActionCircleRadiusExpanded * 2 + getMaxShadowRadius() * 2), (int) (mActionCircleRadius * 2 + getMaxShadowRadius()*2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = mActionCircleRadiusExpanded+getMaxShadowRadius();
        float y = mActionCircleRadius+getMaxShadowRadius()-getShadowOffsetY();
        //TODO 5.0+ = elevation?
        mBackgroundPaint.setShadowLayer(getCurrentShadowRadius(), 0, getShadowOffsetY(), Color.parseColor("#50000000"));
        mBackgroundPaint.setColor(mConfigHelper.getBackgroundColorStateList().getColorForState(getDrawableState(), Color.GRAY));

        canvas.drawCircle(x, y, getInterpolatedRadius(), mBackgroundPaint);

        Drawable icon = mAction.getIcon();
//        if (isPressed) {
//            icon.setColorFilter(config.getPressedColorFilter());
//        } else {
//            icon.setColorFilter(config.getNormalColorFilter());
//        }
        Rect bounds = getRectInsideCircle(new Point((int) x, (int) y), getInterpolatedRadius());
        bounds.inset(mIconPadding, mIconPadding);

        float aspect = icon.getIntrinsicWidth() / (float) (icon.getIntrinsicHeight());
        int desiredWidth = (int) Math.min(bounds.width(), bounds.height() * aspect);
        int desiredHeight = (int) Math.min(bounds.height(), bounds.width() / aspect);

        bounds.inset((bounds.width() - desiredWidth) / 2, (bounds.height() - desiredHeight) / 2);
        icon.setBounds(bounds);
        mAction.getIcon().draw(canvas);

    }

    private Rect getRectInsideCircle(Point center, float radius) {
        Rect rect = new Rect(0, 0, (int) ((radius * 2) / Math.sqrt(2)), (int) ((radius * 2) / Math.sqrt(2)));
        rect.offsetTo(center.x - rect.width() / 2, center.y - rect.width() / 2);
        return rect;
    }

    private float getMaxShadowRadius() {
        return mActionCircleRadiusExpanded/5;
    }

    public void setInterpolation(float interpolation) {
        mInterpolation = interpolation;
    }

    public float getInterpolation() {
        return mInterpolation;
    }

    private float getInterpolatedRadius() {
        return mActionCircleRadius + (mActionCircleRadiusExpanded - mActionCircleRadius) * mInterpolation;
    }

    private float getCurrentShadowRadius() {
        return getInterpolatedRadius()/5;
    }

    private float getShadowOffsetY()
    {
        return 6;
    }
}
