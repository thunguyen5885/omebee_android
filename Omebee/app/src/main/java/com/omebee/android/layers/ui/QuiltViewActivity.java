package com.omebee.android.layers.ui;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.omebee.android.R;
import com.omebee.android.layers.ui.components.views.quiltview.QuiltView;

public class QuiltViewActivity extends Activity {
	public QuiltView quiltView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		quiltView = (QuiltView) findViewById(R.id.quilt);
		quiltView.setChildPadding(5);
		addTestQuilts(1000);
	}
	
	public void addTestQuilts(int num){
		ArrayList<ImageView> images = new ArrayList<ImageView>();
		for(int i = 0; i < num; i++){
			ImageView image = new ImageView(this.getApplicationContext());
			image.setScaleType(ScaleType.CENTER_CROP);
			if(i % 2 == 0)
				image.setImageResource(R.drawable.mayer);
			else 
				image.setImageResource(R.drawable.mayer1);
			images.add(image);
		}
		quiltView.addPatchImages(images);
	}

}

