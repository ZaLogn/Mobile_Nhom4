package com.example.mobile_week4;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Tìm ImageView
        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        // Danh sách các URL ảnh
        List<String> backgrounds = Arrays.asList(
                "https://e0.pxfuel.com/wallpapers/393/207/desktop-wallpaper-honeycombs-honey-pattern.jpg",
                "https://i.pinimg.com/736x/db/85/32/db8532f5bc1933d6fc00735f154a0a8d.jpg",
                "https://iphoneswallpapers.com/wp-content/uploads/2021/07/3D-Hexagon-Metallic-Texture.jpg",
                "https://wallpapercave.com/wp/wp13731423.jpg"
        );

        // Chọn ảnh ngẫu nhiên
        String randomImage = backgrounds.get(new Random().nextInt(backgrounds.size()));

        // Tải ảnh vào ImageView bằng Picasso
        Picasso.get()
                .load(randomImage)
                .into(backgroundImage);
    }
}
