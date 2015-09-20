/**
 *
 * textureParams.java
 *
 * Simple class for setting up the textures for the textures
 * Assignment.
 *
 * Students are to complete this class.
 */

import java.io.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;

import com.jogamp.opengl.util.texture.*;

public class textureParams
{
    Texture finalTexture;
	/**
	 * constructor
	 */
	public textureParams()
	{
	}
    
    /**
     * This functions loads texture data to the GPU.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param filename - The name of the texture file.
     *
     */
    public void loadTexture (String filename)
    {
    	InputStream stream = getClass().getResourceAsStream(filename);
    	try {
			TextureData texData = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
			finalTexture = TextureIO.newTexture(texData); 
		} catch (IOException e) {
			System.err.println("Problem with loading the image.");
		}
    }

    
    /**
     * This functions sets up the parameters
     * for texture use.
     *
     * You will need to write this function, and maintain all of the values needed
     * to be sent to the various shaders.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpTextures (int program, GL2 gl2)
    {
    	finalTexture.bind(gl2); 
    	finalTexture.enable(gl2);
    	gl2.glUniform1i(gl2.glGetUniformLocation(program, "finalTexture"), 0);
    }
    
    public void setUpPhong (int program, GL2 gl2)
    {
        // Sets up the values for the light position, colour and diffuse component.
    	
    	float lightpos[] = {0.0f, 5.0f, 2.0f, 1.0f};
        float lightColor[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
        
        int light = gl2.glGetUniformLocation( program , "lightPosition");
        int lightc = gl2.glGetUniformLocation( program , "lightColor");
        int diff = gl2.glGetUniformLocation( program , "diffuseColor");
        
        gl2.glUniform4fv( light , 1 , lightpos, 0 );
        gl2.glUniform4fv( lightc , 1 , lightColor, 0 );
        gl2.glUniform4fv( diff , 1 , diffuse, 0 );
        
        // Code for the specular component
        
        int spec, specExp; 
        float[] specValues = {1.0f, 1.0f, 1.0f, 1.0f};
        
        spec = gl2.glGetUniformLocation( program , "specularColor" );
        gl2.glUniform4fv( spec , 1 , specValues, 0 );
        
        specExp = gl2.glGetUniformLocation( program , "sExponent" );
        gl2.glUniform1f( specExp, 30.0f );

    }
}
