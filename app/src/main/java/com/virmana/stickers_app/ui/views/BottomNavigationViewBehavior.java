package com.virmana.stickers_app.ui.views;

import android.app.Activity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.view.View;

import com.virmana.stickers_app.Manager.PrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import devlight.io.library.ntb.NavigationTabBar;

public class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<NavigationTabBar> {

    private int height;

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, NavigationTabBar child, int layoutDirection) {
        height = child.getHeight() + 20;
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, NavigationTabBar child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, NavigationTabBar child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0) {
            slideDown(child);
        } else if (dyConsumed < 0) {
            slideUp(child);
        }
    }

    private void slideUp(NavigationTabBar child) {
        child.clearAnimation();
        child.animate().translationY(0).setDuration(200);
    }

    private void slideDown(NavigationTabBar child) {
        child.clearAnimation();
        child.animate().translationY(height).setDuration(200);
    }
    public static boolean check(Activity activity){
        final PrefManager prf = new PrefManager(activity.getApplication());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        if (prf.getString("LAST_DATA_LOAD").equals("")) {
            prf.setString("LAST_DATA_LOAD", strDate);
        } else {
            String toyBornTime = prf.getString("LAST_DATA_LOAD");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date oldDate = dateFormat.parse(toyBornTime);
                System.out.println(oldDate);
                Date currentDate = new Date();
                long diff = currentDate.getTime() - oldDate.getTime();
                long seconds = diff / 1000;
                if (seconds >30) {
                    prf.setString("LAST_DATA_LOAD", strDate);
                    return  true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }
}