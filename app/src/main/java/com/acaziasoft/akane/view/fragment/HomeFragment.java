package com.acaziasoft.akane.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.acaziasoft.akane.Adapter.DashboardAdapter;
import com.acaziasoft.akane.Manager.InsertDBManager;
import com.acaziasoft.akane.R;
import com.acaziasoft.akane.model.Item;
import com.acaziasoft.akane.presenter.EventAction;
import com.acaziasoft.akane.presenter.UploadImagePresenter;
import com.acaziasoft.akane.view.activity.BaseActivity;
import com.acaziasoft.akane.view.service.MyIntentService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements IFragment{

    public static final String TAG = HomeFragment.class.getSimpleName();

    private String imagePath;

    @BindView(R.id.recyclerDash)
    RecyclerView recyclerDash;

    DashboardAdapter adapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onChooseAsset(String type) {
        Log.e(TAG, type);
    }

    @OnClick(R.id.btn_local)
    public void onclickLocalFile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.btn_camera)
    public void onclickCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = null;
        try {
            photo = createImageFile();
            imagePath = photo.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photo != null) {
            startActivityForResult(intent, 2);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
        String fileName = "image_" + timeStamp;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(fileName, ".jpg", storageDir);
        imagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(getActivity(), MyIntentService.class);
            intent.putExtra("uriImage", data.getData().toString());
            getActivity().startService(intent);
        }
    }

    private void initView() {
        recyclerDash.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<Item> items = Item.listAll(Item.class);
        Log.e("size item", String.valueOf(items.size()));
        adapter = new DashboardAdapter((ArrayList<Item>) items, getContext());
        recyclerDash.setHasFixedSize(true);
        recyclerDash.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventAction.ReloadData data){
        adapter.setData(data.items);
    }
}
