package com.mrprona.botronmanhinh;

/**
 * Created by BinhTran on 8/14/17.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.view.View;

public final class RoundView extends View {
    private String f5193a;
    private boolean[] round4Coner;
    private boolean isFullConer;
    private float conerRadius;
    private int conerColor;

    public RoundView(Context context) {
        this(context, (byte) 0);
        m5846a();
    }

    private RoundView(Context context, byte b) {
        super(context, null, 0);
        this.f5193a = "RoundedCornerLayout";
        this.round4Coner = new boolean[]{true, true, true, true};
        this.conerRadius = 66.0f;
        this.conerColor = -16777216;
        this.isFullConer = true;
        m5846a();
    }

    private void m5846a() {
        setWillNotDraw(false);
        setLayerType(1, null);
    }

    public final void draw(Canvas canvas) {
        Paint paint = new Paint(1);

        for (int i = 0; i < 4; i++) {
            if (!round4Coner[i]) {
                isFullConer = false;
                break;
            }
        }

        if (this.isFullConer) {
            try {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                new StringBuilder("canvas width = ").append(width).append(", height = ").append(height);
                paint.setColor(this.conerColor);
                canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, paint);
                paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) width, (float) height), this.conerRadius, this.conerRadius, paint);
                return;
            } catch (Throwable th) {
                super.draw(canvas);
                return;
            }
        }
        try {
            int save = canvas.save();
            float width2 = (float) canvas.getWidth();
            float height2 = (float) canvas.getHeight();
            float f = this.conerRadius;
            float f2 = this.conerRadius;

            boolean z = this.round4Coner[1];
            boolean z2 = this.round4Coner[0];
            boolean z3 = this.round4Coner[3];
            boolean z4 = this.round4Coner[2];

            Path path = new Path();
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            float f3 = width2 - 0.0f;
            height2 -= 0.0f;
            if (f > f3 / 2.0f) {
                f = f3 / 2.0f;
            }
            if (f2 > height2 / 2.0f) {
                f2 = height2 / 2.0f;
            }
            f3 -= 2.0f * f;
            height2 -= 2.0f * f2;
            path.moveTo(width2, 0.0f + f2);
            if (z2) {
                path.rQuadTo(0.0f, -f2, -f, -f2);
            } else {
                path.rLineTo(0.0f, -f2);
                path.rLineTo(-f, 0.0f);
            }
            path.rLineTo(-f3, 0.0f);
            if (z) {
                path.rQuadTo(-f, 0.0f, -f, f2);
            } else {
                path.rLineTo(-f, 0.0f);
                path.rLineTo(0.0f, f2);
            }
            path.rLineTo(0.0f, height2);
            if (z3) {
                path.rQuadTo(0.0f, f2, f, f2);
            } else {
                path.rLineTo(0.0f, f2);
                path.rLineTo(f, 0.0f);
            }
            path.rLineTo(f3, 0.0f);
            if (z4) {
                path.rQuadTo(f, 0.0f, f, -f2);
            } else {
                path.rLineTo(f, 0.0f);
                path.rLineTo(0.0f, -f2);
            }
            path.rLineTo(0.0f, -height2);
            path.close();
            canvas.clipPath(path, Op.DIFFERENCE);
            paint.setColor(this.conerColor);
            canvas.drawPaint(paint);
            super.draw(canvas);
            canvas.restoreToCount(save);
        } catch (Throwable th2) {
            super.draw(canvas);
        }
    }

    public final int getColor() {
        return this.conerColor;
    }

    public final float getCornerRadius() {
        return this.conerRadius;
    }

    public final void setColor(int i) {
        this.conerColor = i;
        invalidate();
    }

    public final void setCornerRadius(float f) {
        this.conerRadius = 2.0f * f;
        invalidate();
    }

    public final void setFullRoundedCorner(boolean z) {
        this.isFullConer = z;
        invalidate();
    }

    public final void setRoundedCorner(boolean[] zArr) {
        this.round4Coner = zArr;
        invalidate();
    }
}
