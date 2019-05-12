package Helper;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;

public class BottomNavigationBehaviour extends CoordinatorLayout.Behavior {

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        if (dependency instanceof Snackbar.SnackbarLayout)
        {
            updateSnackbar(child,dependency);
        }

        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        child.setTranslationY(Math.max(0f,
                Math.min(Float.parseFloat(String.valueOf(child.getHeight())),child.getTranslationX()+dyConsumed)
                ));
    }

    private void updateSnackbar(View child, View dependency)
    {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)dependency.getLayoutParams();

        params.setAnchorId(child.getId());
        params.anchorGravity = Gravity.TOP;
        params.gravity = Gravity.TOP;
        dependency.setLayoutParams(params);

    }
}
