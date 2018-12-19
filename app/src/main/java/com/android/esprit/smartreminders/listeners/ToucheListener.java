package com.android.esprit.smartreminders.listeners;

import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ToucheListener implements View.OnTouchListener {
    private float mPrimStartTouchEventX = -1;
    private float mPrimStartTouchEventY = -1;
    private float mSecStartTouchEventX = -1;
    private float mSecStartTouchEventY = -1;
    private float mPrimSecStartTouchDistance = 0;
    private int mPtrCount = 0;
    private int mViewScaledTouchSlop = 0;
    private PinchCallBack pincher;
    public ToucheListener(PinchCallBack pincher){
        this.pincher=pincher;
    }
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        int action = (event.getAction() & MotionEvent.ACTION_MASK);
        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                mPtrCount++;
                if (mPtrCount == 1 && mPrimStartTouchEventY == -1 && mPrimStartTouchEventY == -1) {
                    mPrimStartTouchEventX = event.getX(0);
                    mPrimStartTouchEventY = event.getY(0);
                    Log.d("TAG", String.format("POINTER ONE X = %.5f, Y = %.5f", mPrimStartTouchEventX, mPrimStartTouchEventY));
                }
                if (mPtrCount == 2) {
                    // Starting distance between fingers
                    mSecStartTouchEventX = event.getX(1);
                    mSecStartTouchEventY = event.getY(1);
                    mPrimSecStartTouchDistance = distance(event, 0, 1);
                    Log.d("TAG", String.format("POINTER TWO X = %.5f, Y = %.5f", mSecStartTouchEventX, mSecStartTouchEventY));
                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                mPtrCount--;
                if (mPtrCount < 2) {
                    mSecStartTouchEventX = -1;
                    mSecStartTouchEventY = -1;
                }
                if (mPtrCount < 1) {
                    mPrimStartTouchEventX = -1;
                    mPrimStartTouchEventY = -1;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                boolean isPrimMoving = isScrollGesture(event, 0, mPrimStartTouchEventX, mPrimStartTouchEventY);
                boolean isSecMoving = (mPtrCount > 1 && isScrollGesture(event, 1, mSecStartTouchEventX, mSecStartTouchEventY));

                // There is a chance that the gesture may be a scroll
                if (mPtrCount > 1 && isPinchGesture(event)) {
                    Log.d("TAG", "PINCH! OUCH!");

                } else if (isPrimMoving || isSecMoving) {
                    // A 1 finger or 2 finger scroll.
                    if (isPrimMoving && isSecMoving) {
                        Log.d("TAG", "Two finger scroll");
                    } else {
                        Log.d("TAG", "One finger scroll");
                    }
                }
                break;
        }

        return true;
    }
    private boolean isScrollGesture(MotionEvent event, int ptrIndex, float originalX, float originalY){
        float moveX = Math.abs(event.getX(ptrIndex) - originalX);
        float moveY = Math.abs(event.getY(ptrIndex) - originalY);

        if (moveX > mViewScaledTouchSlop || moveY > mViewScaledTouchSlop) {
            return true;
        }
        return false;
    }
    private boolean isPinchGesture(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            final float distanceCurrent = distance(event, 0, 1);
            final float diffPrimX = mPrimStartTouchEventX - event.getX(0);
            final float diffPrimY = mPrimStartTouchEventY - event.getY(0);
            final float diffSecX = mSecStartTouchEventX - event.getX(1);
            final float diffSecY = mSecStartTouchEventY - event.getY(1);

            if (// if the distance between the two fingers has increased past
                // our threshold
                    Math.abs(distanceCurrent - mPrimSecStartTouchDistance) > mViewScaledTouchSlop
                            // and the fingers are moving in opposing directions
                            && (diffPrimY * diffSecY) <= 0
                            && (diffPrimX * diffSecX) <= 0) {
                if(distanceCurrent>mPrimSecStartTouchDistance)
                {
                    Log.d("Kind", "isPinchGesture: growing ");
                    pincher.onPinchWide();

                }else
                {
                    pincher.onPinchNarrow();
                    Log.d("Kind", "isPinchGesture: shrinking ");
                }

                // mPinchClamp = false; // don't clamp initially
                return true;
            }
        }
        return false;
    }

    private float distance(MotionEvent event, int first, int second) {
        if (event.getPointerCount() >= 2) {
            final float x = event.getX(first) - event.getX(second);
            final float y = event.getY(first) - event.getY(second);

            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }


}
