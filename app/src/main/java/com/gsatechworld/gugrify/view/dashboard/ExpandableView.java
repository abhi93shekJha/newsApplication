package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;


public class ExpandableView extends FrameLayout {

    /**
     * Interface for callbacks and listener
     */
    public interface ExpandableButtonListener {
        void onViewExpanded();
        void onViewCollapsed();
    }

    Context context;            //context
    TextView textView;          //text view
    ImageView imageArrow;       //image arrow
    View viewColor;             //strip color

    int childViewResId = 0;     //child view id
    View childView;             //child view

    ExpandableButtonListener expandableButtonListener = null;


    public ExpandableView(@NonNull Context context) {
        super(context);
        initButton(context, null);
    }

    public ExpandableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initButton(context, attrs);
    }

    public ExpandableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExpandableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initButton(context, attrs);
    }

    /**
     * Initialize button on every constructor call.
     * Used to reference views and set attributes to the view.
     * @param context context
     * @param attrs attribute set for setting some properties
     */
    private void initButton(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expandable_view, this);
        textView = view.findViewById(R.id.tv_text);
        imageArrow = view.findViewById(R.id.iv_arrow);
//        viewColor = view.findViewById(R.id.view_color);

        setClickListener();

        if (attrs == null) return;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ExpandableView, 0, 0);

        try {
            childViewResId = typedArray.getResourceId(R.styleable.ExpandableView_childView, 0);
            setBarColor(typedArray.getColor(R.styleable.ExpandableView_color, 0));
            setText(typedArray.getString(R.styleable.ExpandableView_text));
            setIcon(typedArray.getDrawable(R.styleable.ExpandableView_icon));
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Set text to expandable button
     * @param text string text
     */
    public void setText(String text) {
        textView.setText(text);
    }

    /**
     * Set color to bar on left side of the button
     * User can set color anytime to this view as indicators
     * @param color color of bar
     */
    public void setBarColor(int color) {
        if (color != 0) viewColor.setBackgroundColor(color);
    }

    /**
     * User can set any icon as arrow
     * @param icon drawable
     */
    public void setIcon(Drawable icon) {
        if (icon != null) imageArrow.setImageDrawable(icon);
    }

    /**
     * Set child view programmatically
     * @param view child view
     */
    public void setChildView(View view) {
        this.childView = view;
    }

    /**
     * Setting up event listeners for expandable button
     * @param expandableButtonListener listener
     */
    public void setCallbackListener(ExpandableButtonListener expandableButtonListener) {
        this.expandableButtonListener = expandableButtonListener;
    }

    /**
     * Setting up the click listener
     */
    private void setClickListener() {
        this.setClickable(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked();
            }
        });
    }

    /**
     * Toggle view visibility on click listener and fire callbacks
     */
    private void onClicked() {
        rotateArrow();
        if (childView.getVisibility() == GONE) {
            childView.setVisibility(VISIBLE);
            if (expandableButtonListener != null) expandableButtonListener.onViewExpanded();
        } else {
            childView.setVisibility(GONE);
            if (expandableButtonListener != null) expandableButtonListener.onViewCollapsed();
        }
    }

    /**
     * Rotate arrow view on expanding and collapsing
     */
    private void rotateArrow() {
        if (imageArrow.getRotation() == 0) imageArrow.animate().rotation(180).start();
        else imageArrow.animate().rotation(0).start();
    }

    /**
     * Get child view on this method
     * Have to wait until the view is attached to window
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (childViewResId != 0) {
            childView = getRootView().findViewById(childViewResId);
            childView.setVisibility(GONE);
        }
    }
}
