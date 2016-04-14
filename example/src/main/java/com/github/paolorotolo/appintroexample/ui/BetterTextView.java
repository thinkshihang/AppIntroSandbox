package com.github.paolorotolo.appintroexample.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.github.paolorotolo.appintroexample.R;

// TODO - Noticed conflict between trimExcessPadding = true and autofit width
// TODO - Upper bound text size not working? Noticed on recipient layout
// TODO - Notice some cases where tracking is always the same amount regardless of value. Seen on t-log header rows with textAllCaps
public class BetterTextView extends TextView {
    public final static float DEFAULT_TRACKING = 0;
    public final static float DEFAULT_MIN_TEXT_SIZE = 1;
    public final static float DEFAULT_MAX_TEXT_SIZE = 1000;
    public final static int AUTOFIT_NONE = 0;
    public final static int AUTOFIT_WIDTH = 1;
    public final static int AUTOFIT_HEIGHT = 2;
    private float tracking = DEFAULT_TRACKING;
    private int fitType = AUTOFIT_NONE;
    private float textSizeLowerBound = DEFAULT_MIN_TEXT_SIZE;
    private float textSizeUpperBound = DEFAULT_MAX_TEXT_SIZE;
    private boolean trimExcessPadding = true;
    private CharSequence rawText;
    private CharSequence trackedText;
    private TextPaint paint;
    private Rect rect;

    public BetterTextView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public BetterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public BetterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs, defStyle);
    }

    public float getTracking() {
        return tracking;
    }

    public void setTracking(float tracking) {
        this.tracking = tracking;
        setTextWithTracking(rawText, null);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyle) {
        rect = new Rect();
        paint = new TextPaint();
        paint.set(this.getPaint());

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.BetterTextView,
                    defStyle,
                    0);
            tracking = ta.getFloat(R.styleable.BetterTextView_fontTracking, tracking);
            textSizeLowerBound = ta.getDimensionPixelSize(R.styleable.BetterTextView_textSizeLowerBound, (int) textSizeLowerBound);
            textSizeUpperBound = ta.getDimensionPixelSize(R.styleable.BetterTextView_textSizeUpperBound, (int) textSizeUpperBound);
            fitType = ta.getInteger(R.styleable.BetterTextView_autofit, AUTOFIT_NONE);
            trimExcessPadding = ta.getBoolean(R.styleable.BetterTextView_trimExcessPadding, false);
            ta.recycle();

            setTracking(tracking);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (fitType != AUTOFIT_NONE) {
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            refitText(super.getText().toString(), parentWidth, parentHeight);
            this.setMeasuredDimension((fitType == AUTOFIT_WIDTH) ? parentWidth : width, (fitType == AUTOFIT_WIDTH) ? height : parentHeight);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        if (fitType != AUTOFIT_NONE)
            refitText(text.toString(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        if (fitType != AUTOFIT_NONE && (w != oldw || h != oldh)) {
            refitText(super.getText().toString(), w, h);
        }
    }

    @Override
    public CharSequence getText() {
        return rawText;
    }

    public CharSequence getTrackedText() {
        return tracking == 0 ? rawText : trackedText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        rawText = text;
        setTextWithTracking(text, type);
    }

    private void refitText(String text, int textWidth, int textHeight)
    {
        if (textWidth <= 0 || textHeight <= 0) return;
        int target = (fitType == AUTOFIT_WIDTH) ?
                (textWidth - this.getPaddingLeft() - this.getPaddingRight()) : (textHeight - this.getPaddingTop() - this.getPaddingBottom());
        float hi = 1000;
        float lo = 2;
        final float threshold = 0.5f; // How close we have to be

        paint.set(this.getPaint());

        while((hi - lo) > threshold) {
            float size = (hi+lo)/2;
            paint.setTextSize(size);
            float measured = (fitType == AUTOFIT_WIDTH) ?
                    paint.measureText(text) : new StaticLayout(text, paint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getHeight();
            if(measured >= target)
                hi = size; // too big
            else
                lo = size; // too small
        }
        // Use lo so that we undershoot rather than overshoot
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, lo);
    }

    private void setTextWithTracking(CharSequence src, BufferType type) {
        if (src == null) return;
        if (tracking == 0f || src.length() < 2) {
            super.setText(src, type != null ? type : BufferType.NORMAL);
            return;
        }

        final String nonBreakingSpace = "\u00A0";
        final SpannableStringBuilder builder = (src instanceof SpannableStringBuilder ? (SpannableStringBuilder)src : new SpannableStringBuilder(src));
        for (int i = src.length() - 1; i >= 1; i--) {
            builder.insert(i, nonBreakingSpace);
            builder.setSpan(new ScaleXSpan(tracking), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        trackedText = builder.toString();
        super.setText(builder, BufferType.SPANNABLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (trimExcessPadding) {
            paint = this.getPaint();
            paint.setTextSize(getTextSize());
            paint.setColor(getCurrentTextColor());
            paint.setTextScaleX(getScaleX());
            paint.setTypeface(getTypeface());
            paint.setFlags(getPaintFlags());
            String trackedText = getTrackedText().toString();
            paint.getTextBounds(trackedText, 0, trackedText.length(), rect);
            canvas.drawText(trackedText, -rect.left, -rect.top, paint);
        } else {
            super.onDraw(canvas);
        }
    }
}
