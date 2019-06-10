package android.jian.com.runingmodel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.filament.Material;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {
    private ArFragment arFragment;
    private Button cube;
    private Button sphere;
    private Button cylinder;
    private enum ShapeType {
        CURE,
        SPHERE,
        CYLINOER
    }
    private  ShapeType shapeType = ShapeType.CURE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        cube = findViewById(R.id.Cube);
        sphere = findViewById(R.id.Sphere);
        cylinder = findViewById(R.id.cylinder);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            if (shapeType==ShapeType.CURE){
                createCure(hitResult.createAnchor(),shapeType);
            }else if(shapeType==ShapeType.SPHERE){
                createSphere(hitResult.createAnchor(),shapeType);
            }else {
                createClinder(hitResult.createAnchor(),shapeType);
            }
        });

        cube.setOnClickListener(v -> shapeType = ShapeType.CURE);
        sphere.setOnClickListener(v -> shapeType = ShapeType.SPHERE);
        cylinder.setOnClickListener(v -> shapeType = ShapeType.CYLINOER);
    }

    private void createClinder(Anchor anchor, ShapeType shapeType) {
        MaterialFactory
                .makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                .thenAccept(material -> {
                    ModelRenderable model = ShapeFactory.makeCylinder(0.1f,0.2f,new Vector3(0f, 0.2f, 0f),  material);
                    placeModel(anchor, model);
                });
    }

    private void createSphere(Anchor anchor, ShapeType shapeType) {
        MaterialFactory
                .makeOpaqueWithColor(this, new Color(android.graphics.Color.WHITE))
                .thenAccept(material -> {
                    ModelRenderable model = ShapeFactory.makeSphere(0.1f,new Vector3(0f, 0.1f, 0f), material);
                    placeModel(anchor, model);
                });
    }

    private void placeModel(Anchor anchor, ModelRenderable model) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(model);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }

    private void createCure(Anchor anchor,ShapeType shapeType) {

        MaterialFactory
                .makeOpaqueWithColor(this, new Color(android.graphics.Color.BLACK))
                .thenAccept(material -> {
                    ModelRenderable model = ShapeFactory.makeCube(new Vector3(0.1f, 0.1f, 0.1f), new Vector3(0f, 0.1f, 0f), material);
                    placeModel(anchor, model);
                });
    }



}
