package example.jackf.testfresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.FileOutputStream;

/**
 * Created by jackf on 2015/12/16.
 */
public class MySimpleDraweeView extends SimpleDraweeView {
    public MySimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySimpleDraweeView(Context context) {
        super(context);
    }

    public void setImageUriPhoto(Uri uri, int customRotate) {

        Log.d("JackTest", "customRotate = " + customRotate);
//        ImageRequestBuilder imgBuilder = ImageRequestBuilder.newBuilderWithSource(uri).setAutoRotateEnabled(true)
//                .setCustomAutoRotate(customRotate);
        ImageRequestBuilder imgBuilder = ImageRequestBuilder.newBuilderWithSource(uri).setAutoRotateEnabled(true);

        ImageRequest request = imgBuilder.build();
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder()
                .setOldController(getController())
                .setImageRequest(request);

        setController(builder.build());
    }
}
