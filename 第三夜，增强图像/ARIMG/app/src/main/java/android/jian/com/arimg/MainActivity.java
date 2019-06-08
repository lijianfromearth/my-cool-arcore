package android.jian.com.arimg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements Scene.OnUpdateListener {
    private customArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (customArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this);
    }
    public void setupDatabase(Config config, Session session){
        Bitmap catBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cat);
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
        aid.addImage("lovely cat",catBitmap);
        config.setAugmentedImageDatabase(aid);
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> Images = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage image : Images){
            if (image.getTrackingState() == TrackingState.TRACKING){
                if (image.getName().equals("lovely cat")){
                    Anchor anchor = image.createAnchor(image.getCenterPose());

                    creatModul(anchor);
                }
            }
        }
    }

    private void creatModul(Anchor anchor) {
        ModelRenderable.builder()
                       .setSource(this,R.raw.cat)
                       .build()
                       .thenAccept(modelRenderable -> {
                           AnchorNode anchorNode = new AnchorNode(anchor);
                           anchorNode.setRenderable(modelRenderable);
                           arFragment.getArSceneView().getScene().addChild(anchorNode);
                       });
    }
}
