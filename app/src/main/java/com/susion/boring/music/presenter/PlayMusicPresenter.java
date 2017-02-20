package com.susion.boring.music.presenter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.susion.boring.music.presenter.itf.IPlayMusicPresenter;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.utils.FastBlurUtil;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/2/8.
 */
public class PlayMusicPresenter extends MediaPlayPresenter implements IPlayMusicPresenter {

    public PlayMusicPresenter(MediaPlayerContract.View mView) {
        super(mView);
    }

    @Override
    public Drawable getBackgroundBlurImage(Bitmap bitmap) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (UIUtils.getScreenWidth()
                * 1.0 / UIUtils.getScreenHeight() * 1.0);

        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }



}
