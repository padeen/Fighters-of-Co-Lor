package com.proandroidgames;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import com.proandroidgames.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class SFGoodGuy {
	   
	private FloatBuffer vertexBuffer;
   private FloatBuffer textureBuffer;
   private ByteBuffer indexBuffer;
   
   private int[] textures = new int[1];

   private float vertices[] = {
                   0.0f, 0.0f, 0.0f, 
                   1.0f, 0.0f, 0.0f,  
                   1.0f, 1.0f, 0.0f,  
                   0.0f, 1.0f, 0.0f,
                                 };
   
    private float texture[] = {          
                   0.0f, 0.0f,
                   0.25f, 0.0f,
                   0.25f, 0.25f,
                   0.0f, 0.25f, 
                                  };
        
    private byte indices[] = {
                   0,1,2, 
                   0,2,3, 
                                  };
    
    private final int playerNumber;
    private final int color;
    //0 = red, 1 = blue, 2 = yellow, 3 = green, 4 = purple
	public boolean isDestroyed = false;

	   public SFGoodGuy(int playerNumber, int color) {
		   this.playerNumber = playerNumber;
		   this.color = color;
	      ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
	      byteBuf.order(ByteOrder.nativeOrder());
	      vertexBuffer = byteBuf.asFloatBuffer();
	      vertexBuffer.put(vertices);
	      vertexBuffer.position(0);

	      byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
	      byteBuf.order(ByteOrder.nativeOrder());
	      textureBuffer = byteBuf.asFloatBuffer();
	      textureBuffer.put(texture);
	      textureBuffer.position(0);

	      indexBuffer = ByteBuffer.allocateDirect(indices.length);
	      indexBuffer.put(indices);
	      indexBuffer.position(0);
	   }

	   public void draw(GL10 gl, int[] spriteSheet) {
		      gl.glBindTexture(GL10.GL_TEXTURE_2D, spriteSheet[0]);
		      
		      gl.glFrontFace(GL10.GL_CCW);
		      gl.glEnable(GL10.GL_CULL_FACE);
		      gl.glCullFace(GL10.GL_BACK);
		           
		      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		      gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		      gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		      
		      gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);      
		      
		      gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		      gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		      gl.glDisable(GL10.GL_CULL_FACE);
		   }
	   
	   
	     
	   
	}
