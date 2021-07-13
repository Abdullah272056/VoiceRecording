package com.example.voicerecording.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicerecording.OnSelectListener;
import com.example.voicerecording.R;
import com.example.voicerecording.RecAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Recording extends Fragment implements OnSelectListener {



    RecyclerView recyclerView;
    List<File>fileList;
    RecAdapter recAdapter;
    File path=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Vrecorder");


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       view=inflater.inflate(R.layout.fragment_recording, container, false);
        askRunTimePermission();
       // displayFile();
        return view;
    }

    private void displayFile(){
        recyclerView=view.findViewById(R.id.recyclerView_record);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        fileList=new ArrayList<>();
        fileList.addAll(findFile(path));

        recAdapter=new RecAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(recAdapter);


    }
    public ArrayList<File> findFile(File file){

        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=file.listFiles();
        assert files != null;
        for (File singleFile: files ){

            if (singleFile.getName().toLowerCase().endsWith(".amr")){
                arrayList.add(singleFile);
            }
//            if (singleFile.isDirectory() && !singleFile.isHidden()){
//                arrayList.addAll(findFile(singleFile));
//
//            }else {
//                if (singleFile.getName().endsWith(".pdf")){
//                    arrayList.add(singleFile);
//                }
//
//            }
        }
        return arrayList;
    }


    @Override
    public void onSelected(File file) {
        Uri uri= FileProvider.getUriForFile(getContext(),getContext().getApplicationContext()
                .getPackageName() +".provider",file);
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"audio/x-wav");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      getContext().startActivity(intent);
    }

    // add new record
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            displayFile();
        }
    }

    private void askRunTimePermission() {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO).
                withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displayFile();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}