package com.example.projet_dupen;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ImageButton;
import android.widget.TextView;

public class PopupTemplate extends Dialog {

    private String title, subTitle;
    private TextView titleView, subTitleView;
    private ImageButton homeButton, restartButton;

    public PopupTemplate(Activity activity) {
        super(activity, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.endgame_popup_template);
        this.title = "Titre";
        this.subTitle = "Sous-titre";
        this.titleView = findViewById(R.id.title);
        this.subTitleView = findViewById(R.id.subtitle);
        this.homeButton = findViewById(R.id.homeButton);
        this.restartButton = findViewById(R.id.restartButton);
    }

    public void setTitle(String title) { this.title = title; }

    public void setSubTitle(String subTitle) { this.subTitle = subTitle; }

    public ImageButton getHomeButton() { return homeButton; }

    public ImageButton getRestartButton() { return restartButton; }

    public TextView getTitleView() { return titleView; }

    public void build() {
        show();
        titleView.setText(title);
        subTitleView.setText(subTitle);
    }
}
