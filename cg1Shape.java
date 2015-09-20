/**
 * cg1Shape.java
 *
 * Tesselates the given shapes.
 */

import java.awt.*;
import java.nio.*;
import java.awt.event.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import java.io.*;


public class cg1Shape extends simpleShape
{
	/**
	 * constructor
	 */
	public cg1Shape()
	{
	}


	/**
	 * makeDefaultShape - creates a "unit" shape of your choice using your tesselation routines.
	 * 
	 *
	 */
	public void makeDefaultShape ()
	{
		// tessellates the shape 

		// Draws front face of the cube
		float x = 0.5f, y = 0.5f, z = 0.5f;

		// front face
		addTriangle(-x,-y,z,0f,1f,x,-y,z,1f,1f,x,y,z,1f,0f);
		addTriangle(-x,-y,z,0f,1f,x,y,z,1f,0f,-x,y,z,0f,0f);

		// top face
		addTriangle(-x,y,z,0f,1f,x,y,z,1f,1f,x,y,-z,1f,0f);
		addTriangle(-x,y,z,0f,1f,x,y,-z,1f,0f,-x,y,-z,0f,0f);

		// left face
		addTriangle(-x,-y,-z,0f,1f,-x,-y,z,1f,1f,-x,y,z,1f,0f);
		addTriangle(-x,-y,-z,0f,1f,-x,y,z,1f,0f,-x,y,-z,0f,0f);

	}

	// This method creates the Background.

	public void makeBackground ()
	{

		// Draws the background
		float x2 = 1.0f, y2 = 1.0f, z2 = 1.0f;
		addTriangle(-x2,-y2,z2, 0f,0f,x2,-y2,z2,1f,0f,x2,y2,z2,1f,1f);
		addTriangle(-x2,-y2,z2, 0f,0f,x2,y2,z2,1f,1f,-x2,y2,z2,0f,1f);

	}

	// This method creates the Cereal box.

	public void makeBook ()
	{

		float x = 0.2f, y = 0.2f, z = 0.2f;

		// front face
		addTriangle(-x,-y,z,1f,1f,x,-y,z,0f,1f,x,y,z,0f,0f);
		addTriangle(-x,-y,z,1f,1f,x,y,z,0f,0f,-x,y,z,1f,0f);

		// top face
		addTriangle(-x,y,z,1f,1f,x,y,z,0f,1f,x,y,-z,0f,0f);
		addTriangle(-x,y,z,1f,1f,x,y,-z,0f,0f,-x,y,-z,1f,0f);

		// left face
		addTriangle(-x,-y,-z,1f,1f,-x,-y,z,0f,1f,-x,y,z,0f,0f);
		addTriangle(-x,-y,-z,1f,1f,-x,y,z,0f,0f,-x,y,-z,1f,0f);
	}

	// This method creates the basketball.

	public void makeBulbCover ()
	{
		float PI = (float) Math.PI;;
		float twoPi = 2 * PI;
		double theta = 6.0;
		double pi = Math.PI;
		double fi = 12.0;
		double latitude = pi / 360;
		double longitude = pi / 180;

		float radius = 0.25f;
		for(int i = 0; i <= 720 ; i += fi)
		{
			for(int j = 0 ; j <= 360 ; j += theta)
			{
				float x1 = (float)(radius * Math.sin(j * latitude) * Math.cos(i * longitude));
				float y1 = (float)(radius * Math.cos(j * latitude));
				float z1 = (float)(radius * Math.sin(i * longitude) * Math.sin(j * latitude)); 

				float x2 = (float)(radius * Math.sin((j + theta) * latitude) * Math.cos(i * longitude));
				float y2 = (float)(radius * Math.cos((j + theta) * latitude));
				float z2 = (float)(radius * Math.sin((j + theta) * latitude) * Math.sin(i * longitude)); 

				float x3 = (float)(radius * Math.sin((j + theta) * latitude) * Math.cos((i + fi) * longitude));
				float y3 = (float)(radius * Math.cos((j + theta) * latitude));
				float z3 = (float)(radius * Math.sin((i + fi) * longitude) * Math.sin((j + theta) * latitude));

				float x4 = (float)(radius * Math.sin(j * latitude) * Math.cos((i + fi) * longitude));
				float y4 = (float)(radius * Math.cos(j * latitude));
				float z4 = (float)(radius * Math.sin(j * latitude) * Math.sin((i + fi) * longitude));

				float t1 = (float) (Math.acos(y1 / radius) / PI);
				float s1 = (float) (Math.acos(x1 / (radius * Math.sin(PI * (t1)))) / twoPi);

				float t2 = (float) (Math.acos(y2 / radius) / PI);
				float s2 = (float) (Math.acos(x2 / (radius * Math.sin(PI * (t2)))) / twoPi);

				float t3 = (float) (Math.acos(y3 / radius) / PI);
				float s3 = (float) (Math.acos(x3 / (radius * Math.sin(PI * (t3)))) / twoPi);

				float t4 = (float) (Math.acos(y4 / radius) / PI);
				float s4 = (float) (Math.acos(x4 / (radius * Math.sin(PI * (t4)))) / twoPi);

				addTriangle(x3, y3, z3, s3, t3, x2, y2, z2, s2, t2, x1, y1, z1, s1, t1);
				addTriangle(x3, y3, z3, s3, t3, x1, y1, z1, s1, t1, x4, y4, z4, s4, t4);
			}
		}
	}
	
	// Creates the Tennis ball.

	public void makeCylinder ()
	{
		float PI = (float) Math.PI;;
		float twoPi = 2*PI;
		double theta = 6.0;
		double pi = Math.PI;
		double fi = 12.0;
		double latitude = pi / 360;
		double longitude = pi / 180;

		float radius = 0.25f;
		for(int i = 0; i <= 720 ; i += fi)
		{
			for(int j = 0 ; j <= 360 ; j += theta)
			{
				float x1 = (float)(radius * Math.sin(j * latitude) * Math.cos(i * longitude));
				float y1 = (float)(radius * Math.cos(j * latitude));
				float z1 = (float)(radius * Math.sin(i * longitude) * Math.sin(j * latitude)); 

				float x2 = (float)(radius * Math.sin((j + theta) * latitude) * Math.cos(i * longitude));
				float y2 = (float)(radius * Math.cos((j + theta) * latitude));
				float z2 = (float)(radius * Math.sin((j + theta) * latitude) * Math.sin(i * longitude)); 

				float x3 = (float)(radius * Math.sin((j + theta) * latitude) * Math.cos((i + fi) * longitude));
				float y3 = (float)(radius * Math.cos((j + theta) * latitude));
				float z3 = (float)(radius * Math.sin((i + fi) * longitude) * Math.sin((j + theta) * latitude));

				float x4 = (float)(radius * Math.sin(j * latitude) * Math.cos((i + fi) * longitude));
				float y4 = (float)(radius * Math.cos(j * latitude));
				float z4 = (float)(radius * Math.sin(j * latitude) * Math.sin((i + fi) * longitude));

				float t1 = (float) (Math.acos(y1 / radius) / PI);
				float s1 = (float) (Math.acos(x1 / (radius * Math.sin(PI * (t1)))) / twoPi);

				float t2 = (float) (Math.acos(y2 / radius) / PI);
				float s2 = (float) (Math.acos(x2 / (radius * Math.sin(PI * (t2)))) / twoPi);

				float t3 = (float) (Math.acos(y3 / radius) / PI);
				float s3 = (float) (Math.acos(x3 / (radius * Math.sin(PI * (t3)))) / twoPi);

				float t4 = (float) (Math.acos(y4 / radius) / PI);
				float s4 = (float) (Math.acos(x4 / (radius * Math.sin(PI * (t4)))) / twoPi);

				addTriangle(x3, y3, z3, s3, t3, x2, y2, z2, s2, t2, x1, y1, z1, s1, t1);
				addTriangle(x3, y3, z3, s3, t3, x1, y1, z1, s1, t1, x4, y4, z4, s4, t4);
			}
		}
	}
	
	// Creates the bulb cover

	public void makeShade()
	{
		float PI = (float) Math.PI;;
		float twoPi = 2*PI;
		double theta = 6.0;
		double pi = Math.PI;
		double fi = 12.0;
		double latitude = pi / 360;
		double longitude = pi / 180;

		float radius = 0.25f;
		for(int i = 0; i <= 720 ; i += fi)
		{
			for(int j = 0 ; j <= 360 ; j += theta)
			{
				float x1 = (float)(radius * Math.sin(j * latitude) * Math.cos(i * longitude));
				float y1 = (float)(radius * Math.cos(j * latitude));
				float z1 = (float)(radius * Math.sin(i * longitude) * Math.sin(j * latitude)); 

				float x2 = (float)(radius * Math.sin((j + theta) * latitude) * Math.cos(i * longitude));
				float y2 = (float)(radius * Math.cos((j + theta) * latitude));
				float z2 = (float)(radius * Math.sin((j + theta) * latitude) * Math.sin(i * longitude)); 

				float x3 = (float)(radius * Math.sin((j + theta) * latitude) * Math.cos((i + fi) * longitude));
				float y3 = (float)(radius * Math.cos((j + theta) * latitude));
				float z3 = (float)(radius * Math.sin((i + fi) * longitude) * Math.sin((j + theta) * latitude));

				float x4 = (float)(radius * Math.sin(j * latitude) * Math.cos((i + fi) * longitude));
				float y4 = (float)(radius * Math.cos(j * latitude));
				float z4 = (float)(radius * Math.sin(j * latitude) * Math.sin((i + fi) * longitude));

				float t1 = (float) (Math.acos(y1 / radius) / PI);
				float s1 = (float) (Math.acos(x1 / (radius * Math.sin(PI * (t1)))) / twoPi);

				float t2 = (float) (Math.acos(y2 / radius) / PI);
				float s2 = (float) (Math.acos(x2 / (radius * Math.sin(PI * (t2)))) / twoPi);

				float t3 = (float) (Math.acos(y3 / radius) / PI);
				float s3 = (float) (Math.acos(x3 / (radius * Math.sin(PI * (t3)))) / twoPi);

				float t4 = (float) (Math.acos(y4 / radius) / PI);
				float s4 = (float) (Math.acos(x4 / (radius * Math.sin(PI * (t4)))) / twoPi);

				addTriangle(x3, y3, z3, s3, t3, x2, y2, z2, s2, t2, x1, y1, z1, s1, t1);
				addTriangle(x3, y3, z3, s3, t3, x1, y1, z1, s1, t1, x4, y4, z4, s4, t4);
			}
		}
	}
}
