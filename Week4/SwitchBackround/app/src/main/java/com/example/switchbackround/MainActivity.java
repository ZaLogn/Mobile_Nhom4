package com.example.switchbackround;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private ImageView backgroundImage;
    private Switch switchBackground;

    // Hai ảnh sẽ thay đổi qua lại
    private final String image1 = "https://img.lovepik.com/background/20211030/medium/lovepik-sunrise-landscape-mobile-wallpaper-background-image_400415996.jpg";
    private final String image2 = "https://cdn.tgdd.vn//GameApp/1345434//trangdt9-800x1422.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Ánh xạ View
        backgroundImage = findViewById(R.id.backgroundImage);
        switchBackground = findViewById(R.id.switchBackground);

        // Set ảnh mặc định ban đầu
        Picasso.get().load(image1).into(backgroundImage);

        // Xử lý sự kiện khi bật/tắt Switch
        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Picasso.get().load(image2).into(backgroundImage); // Bật thì đổi sang image2
            } else {
                Picasso.get().load(image1).into(backgroundImage); // Tắt thì quay về image1
            }
        });
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}