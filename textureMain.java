//
//  textureMain.java
//  
// This class has methods that interact with the shaders to render the scene.
//

import java.awt.*;
import java.nio.*;
import java.io.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*; 


public class textureMain implements GLEventListener, KeyListener
{

	/**
	 * buffer info 
	 */
	private int vbuffer[];
	private int ebuffer[];
	private int numVerts[];

	// Transformation values for the desk
	public float[] angles = {5.0f,6.5f,0.0f};
	public float[] transArr = {0.5f,-0.1f,0.0f};
	public float[] scaleArr = {1.3f,0.8f,1.0f};

	// Transformation values for the Kelloggs cereal box.
	public float[] anglesBook = {11.0f,-10.0f,0.0f};
	public float[] transArrBook = {0.85f,0.55f,0.0f};
	public float[] scaleArrBook = {0.8f,1.1f,1.0f};
	
	// Transformation values for the basketball
	public float[] anglesBulb = {0.0f,0.0f,40.0f};
	public float[] transArrBulb = {-0.05f,0.53f,0.0f};
	public float[] scaleArrBulb = {0.8f,0.8f,0.8f};
	
	// Transformation values for the tennis ball
	public float[] anglesCyl = {0.0f,10.0f,0.0f};
	public float[] transArrCyl = {0.25f,0.40f,0.0f};
	public float[] scaleArrCyl = {0.3f,0.3f,0.3f};
	
	// Transformation values for bulb 
	public float[] anglesShade = {0.0f,0.0f,40.0f};
	public float[] transArrShade = {0.5f,0.46f,0.0f};
	public float[] scaleArrShade = {0.5f,0.5f,0.5f};

	/**
	 * program and variable IDs
	 */
	public int shaderProgID;

	// texture values
	public textureParams tex;
	public textureParams tex2;
	public textureParams tex3;
	public textureParams tex4;
	public textureParams tex5;
	public textureParams tex6;

	/**
	 * shape info
	 */
	cg1Shape myShape;
	cg1Shape myShapeBG;
	cg1Shape myShapeBook;
	cg1Shape myShapeBulb;
	cg1Shape myShapeCyl;
	cg1Shape myShapeShade;

	/**
	 * my canvas
	 */
	GLCanvas myCanvas;

	/**
	 * constructor
	 */
	public textureMain(GLCanvas G)
	{
		vbuffer = new int[6];  
		ebuffer = new int[6];  
		numVerts = new int[6];  

		myCanvas = G;
		tex = new textureParams();
		tex2 = new textureParams();
		tex3 = new textureParams();
		tex4 = new textureParams();
		tex5 = new textureParams();
		tex6 = new textureParams();

		G.addGLEventListener (this);
		G.addKeyListener (this);
	}

	private void errorCheck (GL2 gl2)
	{
		int code = gl2.glGetError();
		if (code == GL.GL_NO_ERROR) 
			System.err.println ("All is well");
		else
			System.err.println ("Problem - error code : " + code);

	}


	/**
	 * Called by the drawable to initiate OpenGL rendering by the client. 
	 */
	public void display(GLAutoDrawable drawable)
	{
		// get GL
		GL2 gl2 = (drawable.getGL()).getGL2();

		// clear your frame buffers
		gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );

		// Allocates buffer space for all objects. This is for the desk.
		// bind your vertex buffer
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[0]);

		// bind your element array buffer
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0]);

		// set up your attribute variables
		gl2.glUseProgram (shaderProgID);
		long dataSize = myShape.getNVerts() * 4l * 4l;
		int  vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
		gl2.glEnableVertexAttribArray ( vPosition );
		gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
				0, 0l);
		
		int  vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
		gl2.glEnableVertexAttribArray ( vTex );
		gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,
				0, dataSize);

		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "theta"), 1, angles, 0);  
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "translate"), 1, transArr, 0); 
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "scale"), 1, scaleArr, 0);  
		gl2.glUniform1f(gl2.glGetUniformLocation (shaderProgID, "flag"), 0.0f);
		tex.setUpTextures (shaderProgID, gl2);

		// draws the shape
		gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[0],  GL.GL_UNSIGNED_SHORT, 0l);

		// Allocates buffer space and draws the background 
		// bind your vertex buffer
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[1]);

		// bind your element array buffer
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[1]);

		// set up your attribute variables
		gl2.glUseProgram (shaderProgID);
		long dataSize2 = myShapeBG.getNVerts() * 4l * 4l;
		vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
		gl2.glEnableVertexAttribArray ( vPosition );
		gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
				0, 0l);
		
		vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
		gl2.glEnableVertexAttribArray ( vTex );
		gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,
				0, dataSize2);

		// setup uniform variables for texture
		gl2.glUniform1f(gl2.glGetUniformLocation (shaderProgID, "flag"), 1.0f); // for background
		tex2.setUpTextures (shaderProgID, gl2);

		// draws the background
		gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[1],  GL.GL_UNSIGNED_SHORT, 0l);

		// Binds the buffers  and draws the kellogs cereal box. 
		// bind your vertex buffer
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[2]);

		// bind your element array buffer
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[2]);

		// set up your attribute variables
		gl2.glUseProgram (shaderProgID);
		long dataSize3 = myShape.getNVerts() * 4l * 4l;
		vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
		gl2.glEnableVertexAttribArray ( vPosition );
		gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
				0, 0l);
		
		vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
		gl2.glEnableVertexAttribArray ( vTex );
		gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,
				0, dataSize3);

		// setup uniform variables for texture
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "theta"), 1, angles, 0);  
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "translate"), 1, transArrBook, 0); 
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "scale"), 1, scaleArrBook, 0);  
		gl2.glUniform1f  (gl2.glGetUniformLocation (shaderProgID, "flag"), 0.0f); // To apply transformations
		tex3.setUpTextures (shaderProgID, gl2);

		// draws the cereal box.
		gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[2],  GL.GL_UNSIGNED_SHORT, 0l);

		// Binds the buffer and draws the basketball 
		// bind your vertex buffer
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[3]);

		// bind your element array buffer
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[3]);

		// set up your attribute variables
		gl2.glUseProgram (shaderProgID);
		long dataSize4 = myShapeBulb.getNVerts() * 4l * 4l;
		vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
		gl2.glEnableVertexAttribArray ( vPosition );
		gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
				0, 0l);
		
		vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
		gl2.glEnableVertexAttribArray ( vTex );
		gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,
				0, dataSize4);

		// setup uniform variables for texture
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "theta"), 1, anglesBulb, 0);  
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "translate"), 1, transArrBulb, 0); 
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "scale"), 1, scaleArrBulb, 0);  
		gl2.glUniform1f  (gl2.glGetUniformLocation (shaderProgID, "flag"), 0.0f); // Flag to apply transformations
		tex4.setUpTextures (shaderProgID, gl2);

		// draws the basketball.
		gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[3],  GL.GL_UNSIGNED_SHORT, 0l);
		
		// Binds the buffer and draws the tennis ball 
		// bind your vertex buffer
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[4]);

		// bind your element array buffer
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[4]);

		// set up your attribute variables
		gl2.glUseProgram (shaderProgID);
		long dataSize5 = myShapeBulb.getNVerts() * 4l * 4l;
		vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
		gl2.glEnableVertexAttribArray ( vPosition );
		gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
				0, 0l);
		
		vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
		gl2.glEnableVertexAttribArray ( vTex );
		gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,
				0, dataSize5);

		// setup uniform variables for texture
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "theta"), 1, anglesCyl, 0); 
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "translate"), 1, transArrCyl, 0); 
		gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "scale"), 1, scaleArrCyl, 0); 
		gl2.glUniform1f  (gl2.glGetUniformLocation (shaderProgID, "flag"), 0.0f); // To apply transformations
		tex5.setUpTextures (shaderProgID, gl2);

		// draws the tennis ball.
		gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[4],  GL.GL_UNSIGNED_SHORT, 0l);
		
		// Binds the buffer and creates the Bulb cover 
		// bind your vertex buffer
				gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[5]);

				// bind your element array buffer
				gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[5]);

				// set up your attribute variables
				gl2.glUseProgram (shaderProgID);
				long dataSize6 = myShapeShade.getNVerts() * 4l * 4l;
				vPosition = gl2.glGetAttribLocation (shaderProgID, "vPosition");
				gl2.glEnableVertexAttribArray ( vPosition );
				gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false,
						0, 0l);
				
		        int  vNormal = gl2.glGetAttribLocation (shaderProgID, "vNormal");
		        gl2.glEnableVertexAttribArray ( vNormal );
		        gl2.glVertexAttribPointer (vNormal, 3, GL.GL_FLOAT, false,
		                                   0, dataSize6);
		        
				vTex = gl2.glGetAttribLocation (shaderProgID, "vTexCoord");
				gl2.glEnableVertexAttribArray ( vTex );
				gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false,
						0, dataSize6);

				// setup uniform variables for texture
				gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "theta"), 1, anglesShade, 0);  
				gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "translate"), 1, transArrShade, 0);  
				gl2.glUniform3fv (gl2.glGetUniformLocation (shaderProgID, "scale"), 1, scaleArrShade, 0);
				// To apply lighting and transformations
				gl2.glUniform1f  (gl2.glGetUniformLocation (shaderProgID, "flag"), 2.0f); 
				tex6.setUpTextures (shaderProgID, gl2);
				tex6.setUpPhong(shaderProgID, gl2);

				// draws the Bulb cover
				gl2.glDrawElements ( GL.GL_TRIANGLES, numVerts[5],  GL.GL_UNSIGNED_SHORT, 0l);
	}


	/**
	 * Notifies the listener to perform the release of all OpenGL 
	 * resources per GLContext, such as memory buffers and GLSL 
	 * programs.
	 */
	public void dispose(GLAutoDrawable drawable)
	{

	}

	/**
	 * Called by the drawable immediately after the OpenGL context is
	 * initialized. 
	 */
	public void init(GLAutoDrawable drawable)
	{
		// get the gl object
		GL2 gl2 = drawable.getGL().getGL2();

		// Load shaders
		shaderProgram myShaders = new shaderProgram();
		shaderProgID = myShaders.readAndCompile (gl2, "vshader.glsl", "fshader.glsl");
		if (shaderProgID == 0) {
			System.err.println ("Error setting up shaders");
			System.exit (1);
		}

		// Other GL initialization
		gl2.glEnable (GL.GL_DEPTH_TEST);
		gl2.glEnable (GL.GL_CULL_FACE);
		gl2.glCullFace ( GL.GL_BACK );
		gl2.glFrontFace(GL.GL_CCW);
		gl2.glClearColor (1.0f, 1.0f, 1.0f, 1.0f);
		gl2.glDepthFunc (GL.GL_LEQUAL);
		gl2.glClearDepth (1.0f);

		// initially create a new Shape
		createShapes(gl2);

		// Load textures
		tex.loadTexture ("texture.jpg");
		tex2.loadTexture ("texture2.jpg");
		tex3.loadTexture ("texture3.jpg");
		tex4.loadTexture ("texture4.jpg");
		tex5.loadTexture ("texture5.jpg");
		tex6.loadTexture ("texture6.jpg");

	}

	/**
	 * Called by the drawable during the first repaint after the component
	 * has been resized. 
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height)
	{

	}



	/**
	 * creates a new shape
	 */
	public void createShapes(GL2 gl2)
	{
		// creates a new shape
		myShape = new cg1Shape();
		myShapeBG = new cg1Shape();    
		myShapeBook = new cg1Shape();    
		myShapeBulb = new cg1Shape();    
		myShapeCyl = new cg1Shape();    
		myShapeShade = new cg1Shape();   

		// clears the old shape
		myShape.clear();
		myShapeBG.clear();    
		myShapeBook.clear();   
		myShapeBulb.clear();    
		myShapeCyl.clear();    
		myShapeShade.clear();   

		// Tesselates the required shape
		myShape.makeDefaultShape();
		myShapeBG.makeBackground();      
		myShapeBook.makeBook();         
		myShapeBulb.makeBulbCover();      
		myShapeCyl.makeCylinder();        
		myShapeShade.makeShade();         

		// get your verticies and elements
		Buffer points = myShape.getVerticies();
		Buffer elements = myShape.getElements();
		Buffer texCoords = myShape.getUV();

		Buffer points2 = myShapeBG.getVerticies(); 
		Buffer elements2 = myShapeBG.getElements(); 
		Buffer texCoords2 = myShapeBG.getUV();    

		Buffer points3 = myShapeBook.getVerticies();  
		Buffer elements3 = myShapeBook.getElements();  
		Buffer texCoords3 = myShapeBook.getUV();   

		Buffer points4 = myShapeBulb.getVerticies();  
		Buffer elements4 = myShapeBulb.getElements();  
		Buffer texCoords4 = myShapeBulb.getUV();   
		
		Buffer points5 = myShapeCyl.getVerticies();  
		Buffer elements5 = myShapeCyl.getElements();  
		Buffer texCoords5 = myShapeCyl.getUV();  
		
		Buffer points6 = myShapeShade.getVerticies(); 
		Buffer elements6 = myShapeShade.getElements(); 
		Buffer texCoords6 = myShapeShade.getUV();  
		Buffer normals6 = myShapeShade.getNormals();

		// set up the vertex buffer
		int bf[] = new int[6];  
		gl2.glGenBuffers (6, bf, 0);
		vbuffer[0] = bf[0];
		vbuffer[1] = bf[1];
		vbuffer[2] = bf[2];
		vbuffer[3] = bf[3];
		vbuffer[4] = bf[4];
		vbuffer[5] = bf[5];

		long vertBsize = myShape.getNVerts() * 4l * 4l;
		long tdataSize = myShape.getNVerts() * 2l * 4l;
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[0]);
		gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize + tdataSize , null, GL.GL_STATIC_DRAW);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize, points);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize, tdataSize, texCoords);

		// For the background 
		long vertBsize2 = myShapeBG.getNVerts() * 4l * 4l;
		long tdataSize2 = myShapeBG.getNVerts() * 2l * 4l;
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[1]);
		gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize2 + tdataSize2 , null, GL.GL_STATIC_DRAW);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize2, points2);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize2, tdataSize2, texCoords2);

		// For the cereal box. 
		long vertBsize3 = myShapeBook.getNVerts() * 4l * 4l;
		long tdataSize3 = myShapeBook.getNVerts() * 2l * 4l;
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[2]);
		gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize3 + tdataSize3 , null, GL.GL_STATIC_DRAW);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize3, points3);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize3, tdataSize3, texCoords3);

		// For the basketball. 
		long vertBsize4 = myShapeBulb.getNVerts() * 4l * 4l;
		long tdataSize4 = myShapeBulb.getNVerts() * 2l * 4l;
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[3]);
		gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize4 + tdataSize4 , null, GL.GL_STATIC_DRAW);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize4, points4);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize4, tdataSize4, texCoords4);
		
		// For the tennis ball 
		long vertBsize5 = myShapeCyl.getNVerts() * 4l * 4l;
		long tdataSize5 = myShapeCyl.getNVerts() * 2l * 4l;
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[4]);
		gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize5 + tdataSize5 , null, GL.GL_STATIC_DRAW);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize5, points5);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize5, tdataSize5, texCoords5);
		
		// For the bulb cover 
		long vertBsize6 = myShapeShade.getNVerts() * 4l * 4l;
		long tdataSize6 = myShapeCyl.getNVerts() * 2l * 4l;
		long ndataSize6 = myShapeShade.getNVerts() * 3l * 4l;
		gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vbuffer[5]);
		gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize6 + ndataSize6 , null, GL.GL_STATIC_DRAW);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize6, points6);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize6, tdataSize6, texCoords6);
		gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize6, ndataSize6, normals6);

		gl2.glGenBuffers (6, bf, 0);  
		ebuffer[0] = bf[0];
		ebuffer[1] = bf[1];
		ebuffer[2] = bf[2];
		ebuffer[3] = bf[3];
		ebuffer[4] = bf[4];
		ebuffer[5] = bf[5];

		long eBuffSize = myShape.getNVerts() * 2l;
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[0]);
		gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize,elements, 
				GL.GL_STATIC_DRAW);

		numVerts[0] = myShape.getNVerts();

		// For background 
		long eBuffSize2 = myShapeBG.getNVerts() * 2l;
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[1]);
		gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize2,elements2, 
				GL.GL_STATIC_DRAW);

		numVerts[1] = myShapeBG.getNVerts();

		// For the cereal box 
		long eBuffSize3 = myShapeBook.getNVerts() * 2l;
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[2]);
		gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize3,elements3, 
				GL.GL_STATIC_DRAW);

		numVerts[2] = myShapeBook.getNVerts();

		// For the basketball 
		long eBuffSize4 = myShapeBulb.getNVerts() * 2l;
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[3]);
		gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize4,elements4, 
				GL.GL_STATIC_DRAW);

		numVerts[3] = myShapeBulb.getNVerts();

		// For the tennis ball. 
		long eBuffSize5 = myShapeCyl.getNVerts() * 2l;
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[4]);
		gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize5,elements5, 
				GL.GL_STATIC_DRAW);

		numVerts[4] = myShapeCyl.getNVerts();
		
		// For the bulb cover.
		long eBuffSize6 = myShapeShade.getNVerts() * 2l;
		gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, ebuffer[5]);
		gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize6,elements6, 
				GL.GL_STATIC_DRAW);

		numVerts[5] = myShapeShade.getNVerts();
	}

	/**
	 * Because I am a Key Listener...we'll only respond to key presses
	 */
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

	/** 
	 * Invoked when a key has been pressed.
	 */
	public void keyPressed(KeyEvent e)
	{
		// Get the key that was pressed
		char key = e.getKeyChar();

		// Respond appropriately
		switch( key ) {
		case 'q': case 'Q':
			System.exit( 0 );
			break;
		}

		// do a redraw
		myCanvas.display();
	}


	/**
	 * main program
	 */
	public static void main(String [] args)
	{
		// GL setup
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);

		// create your tessMain
		textureMain myMain = new textureMain(canvas);


		Frame frame = new Frame("CG - Final Project");
		frame.setSize(512, 512);
		frame.add(canvas);
		frame.setVisible(true);

		// by default, an AWT Frame doesn't do anything when you click
		// the close button; this bit of code will terminate the program when
		// the window is asked to close
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
