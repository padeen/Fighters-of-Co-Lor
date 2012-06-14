package com.project56;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.project56.R;

public class SFGameView extends GLSurfaceView {
	private SFGameRenderer renderer;
	
	public SFGameView(Context context) {
		super(context);
		
		renderer = new SFGameRenderer();
		
		this.setRenderer(renderer);
		

	}
	

}
