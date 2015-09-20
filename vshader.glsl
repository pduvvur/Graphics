attribute vec4 vPosition;	
attribute vec2 vTexCoord;	 	
varying	vec2 texCoord; // Texture coordinates
uniform vec3 theta;	// Angles of rotations
uniform vec3 scale; // Scaling values
uniform vec3 translate; // Translation Values

uniform float flag; // To apply transformations
attribute vec3 vNormal; // Normal
uniform vec4 lightPosition; // Position of the light source

varying vec3 lPos;
varying vec3 vPos;
varying vec3 vNorm;

void main()
{
	texCoord = vTexCoord;

	if(flag == 0.0){ // For all objects with just texture and no lighting
		// Compute the sines and cosines of each rotation about each axis.
		vec3 angles = radians (theta);
		vec3 c = cos (angles);
		vec3 s = sin (angles);

		// Scaling matrix                    
		mat4 scmat = mat4 ( scale.x, 0.0,     0.0,     0.0,
							0.0,     scale.y, 0.0,     0.0,
							0.0,     0.0,     scale.z, 0.0,
							0.0,     0.0,     0.0,     1.0);

		// Translation matrix
		mat4 transmat = mat4 (  1.0, 		 0.0, 		  0.0, 		   0.0,
								0.0, 		 1.0, 		  0.0, 		   0.0,
								0.0, 		 0.0, 		  1.0,         0.0,
								translate.x, translate.y, translate.z, 1.0);

		// rotation matrices
		mat4 rx = mat4 (1.0,  0.0,  0.0,  0.0, 
						0.0,  c.x,  s.x,  0.0,
						0.0, -s.x,  c.x,  0.0,
						0.0,  0.0,  0.0,  1.0);

		mat4 ry = mat4 (c.y,  0.0, -s.y,  0.0, 
						0.0,  1.0,  0.0,  0.0,
						s.y,  0.0,  c.y,  0.0,
						0.0,  0.0,  0.0,  1.0);

		mat4 rz = mat4 (c.z, s.z,  0.0,  0.0, 
						-s.z,  c.z,  0.0,  0.0,
						0.0,  0.0,  1.0,  0.0,
						0.0,  0.0,  0.0,  1.0);

		// The model matrix
		mat4 modelMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
				     			 0.0,  1.0,  0.0,  0.0,
								 0.0,  0.0,  1.0,  0.0,
								 0.0,  0.0,  -3.0,  1.0);

		// To apply zoom using camera
		mat4 viewMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
								0.0,  1.0,  0.0,  0.0,
								0.0,  0.0,  1.0,  0.0,
								0.0,  -0.3,  -1.0,  1.0);

		mat4 projMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
								0.0,  1.0,  0.0,  0.0,
								0.0,  0.0,  0.11,  0.0,
								0.0,  0.0,  0.0,  1.0);

		mat4 modelViewMatrix = viewMatrix * modelMatrix;

		// convert to clip space (like a vertex shader should)
		gl_Position =  projMatrix * viewMatrix * modelMatrix * transmat * scmat * rz * ry * rx * vPosition;
	} else if(flag == 1.0){
		gl_Position = vPosition; // For background - No transformations
	} else if (flag == 2.0 || flag == 3.0){ // For object with texture and lighting
		vec3 angles = radians (theta);
		vec3 c = cos (angles);
		vec3 s = sin (angles);

		// Scaling matrix                    
		mat4 scmat = mat4 ( scale.x, 0.0,     0.0,     0.0,
							0.0,     scale.y, 0.0,     0.0,
							0.0,     0.0,     scale.z, 0.0,
							0.0,     0.0,     0.0,     1.0);

		// Translation matrix
		mat4 transmat = mat4 (  1.0, 		 0.0, 		  0.0, 		   0.0,
								0.0, 		 1.0, 		  0.0, 		   0.0,
								0.0, 		 0.0, 		  1.0,         0.0,
								translate.x, translate.y, translate.z, 1.0);

		// rotation matricies
		mat4 rx = mat4 (1.0,  0.0,  0.0,  0.0, 
						0.0,  c.x,  s.x,  0.0,
						0.0, -s.x,  c.x,  0.0,
						0.0,  0.0,  0.0,  1.0);

		mat4 ry = mat4 (c.y,  0.0, -s.y,  0.0, 
						0.0,  1.0,  0.0,  0.0,
						s.y,  0.0,  c.y,  0.0,
						0.0,  0.0,  0.0,  1.0);

		mat4 rz = mat4 (c.z, s.z,  0.0,  0.0, 
						-s.z,  c.z,  0.0,  0.0,
						0.0,  0.0,  1.0,  0.0,
						0.0,  0.0,  0.0,  1.0);

		// The model matrix
		mat4 modelMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
								 0.0,  1.0,  0.0,  0.0,
								 0.0,  0.0,  1.0,  0.0,
								 0.0,  0.0,  -3.0,  1.0);

		// The view matrix.
		mat4 viewMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
								0.0,  1.0,  0.0,  0.0,
								0.0,  0.0,  1.0,  0.0,
								0.0,  -0.3,  -1.0,  1.0);

		// The projection Matrix.
		mat4 projMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
								0.0,  1.0,  0.0,  0.0,
								0.0,  0.0,  0.11,  0.0,
								0.0,  0.0,  0.0,  1.0);

		mat4 modelViewMatrix = viewMatrix * modelMatrix;

		// All vectors need to be converted to "eye" space
		// All vectors should also be normalized
		vec4 vertexInEye = modelViewMatrix * vPosition;
		vec4 lightInEye = viewMatrix * lightPosition;

		vec4 normalInEye = normalize(modelViewMatrix * vec4(vNormal, 0.0));

		// pass our vertex data to the fragment shader
		lPos = lightInEye.xyz;
		vPos = vertexInEye.xyz;
		vNorm = normalInEye.xyz;

		// convert to clip space (like a vertex shader should)
		gl_Position =  projMatrix * viewMatrix * modelMatrix * transmat * scmat * rz * ry * rx * vPosition;
	}
}