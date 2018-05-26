package com.example.sergeyvankovich.letsdoit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergeyvankovich.letsdoit.R;
import com.vansuita.materialabout.builder.AboutBuilder;

public class AboutMeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AboutBuilder builder = AboutBuilder.with(getContext())
                .setAppIcon(R.drawable.titlemain)
                .setAppName(R.string.app_name)
                .setPhoto(R.drawable.titlemain)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Миша")
                .setSubTitle("About you")
                .setLinksColumnsCount(3)
                .setBrief("About app")
                .addEmailLink("afinievskym@gmail.com")
                .addWhatsappLink("Михаил", "+79247301350")
                .addGitHubLink("afinievskym")
                .setVersionNameAsAppSubTitle()
                .setActionsColumnsCount(2)
                .setShowAsCard(false)
                .addShareAction(R.string.app_name)
                .addFeedbackAction("afinievskym@gmail.com")
                .setWrapScrollView(true);

        return builder.build();
    }


}
