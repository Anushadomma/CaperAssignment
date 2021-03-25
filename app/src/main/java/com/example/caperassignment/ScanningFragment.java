package com.example.caperassignment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.Text;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ScanningFragment extends Fragment implements View.OnClickListener {
    public static String TAG = "scanningFragment";

    SurfaceView surfaceView= null;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private CameraSource cameraSource;
    Button btnGoToCart;
    Button btnAddItem;
    TextView barCode;
    private Class fragmentClass;
    private Fragment fragment = null;
    private String intentData="";
    private ArrayList<String> itemCodes=new ArrayList<String>();
    private boolean firstDetected=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.scanning_fragment, container, false);
         btnAddItem = view.findViewById(R.id.btnAddItem);
         btnGoToCart = view.findViewById(R.id.btnGoToCart);
         surfaceView = view.findViewById(R.id.surfaceView);
         barCode = view.findViewById(R.id.textBarcode);

        btnGoToCart.setOnClickListener(this);
        btnAddItem.setOnClickListener(this);
        initialiseDetectorsAndSources();

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v==btnGoToCart){
            Bundle bundle=new Bundle();
            bundle.putStringArrayList("qrUrl",itemCodes);

            fragmentClass=ShopFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);

            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.frament1, fragment).commit();

        }
        if(v==btnAddItem){
            firstDetected=true;
            initialiseDetectorsAndSources();

        }

    }
    private void initialiseDetectorsAndSources() {

        Toast.makeText(getContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                        Log.i(TAG, "surfaceCreated: bloop");

                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();


                if (barcodes.size() != 0 && firstDetected) {
                    firstDetected=false;
                    Log.i(TAG, "surfaceCreated:"+barcodes.size());


                    barCode.post(new Runnable() {

                        @Override
                        public void run() {


                            if (barcodes.valueAt(0).email!= null) {
                                barCode.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;

                                barCode.setText(intentData);
                                cameraSource.stop();

                            } else {

                                intentData = barcodes.valueAt(0).displayValue;
                                itemCodes.add(intentData);
                                barCode.setText(intentData);
                            }
                        }
                    });

                }


            }
        });
    }
}
