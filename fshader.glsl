
// The tex coordinates
varying	vec2 texCoord;	 	
uniform	sampler2D finalTexture;	
uniform float flag;

// Light color
uniform vec4 lightColor;
uniform vec4 specularColor;
uniform float sExponent;

// Diffuse reflection color
uniform vec4 diffuseColor;

// Vectors "attached" to vertex and get sent to fragment shader
varying vec3 lPos;
varying vec3 vPos;
varying vec3 vNorm;

void main()		
{	
	// Only for objects that do not need lighting.
	if(flag != 2.0 ){
		gl_FragColor = texture2D(finalTexture, texCoord );
	}
	
	vec3 L = normalize (lPos - vPos);
    vec3 N = normalize (vNorm);

    vec3 ViewingDirection = normalize (vPos);
    vec3 rReflection = reflect(L,N);
    vec3 ReflectionVec = normalize(rReflection);
    
    // calculates the components
    vec4 diffuse = lightColor * diffuseColor * (dot(N, L));
    vec4 specular = lightColor * specularColor * pow(max(0.0, dot(ViewingDirection, ReflectionVec)), sExponent);

    // For objects with texture and lighting.	
	if(flag == 2.0){
      gl_FragColor =  texture2D(finalTexture, texCoord) + (specular);
	}
}	
