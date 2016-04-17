package au.com.ionprogramming.ld35;

import java.util.Random;

public class SimplexNoise {

//    static int octaves = 3;
//    static float roughness = 0.5f;
//    static float scale = 0.008f;

    private int octaves = 4;
    private float roughness = 0.5f;
    private float scale = 0.002f;

    private static int grad3[][] = 	{{1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
            {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
            {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1}};
    private int[] p;
    // To remove the need for index wrapping, double the permutation table length

    public static int[] noiseSeed(){
        Random r = new Random();
        int[] seed = new int[256];
        boolean done;
        for(int n = 0; n < seed.length; n++){
            done = false;
            while(!done) {
                seed[n] = r.nextInt(256);
                done = true;
                for (int i = 0; i < n; i++){
                    if(seed[n] == seed[i]){
                        done = false;
                        break;
                    }
                }
            }
        }
        return seed;
    }

    public SimplexNoise(int octaves, float roughness, float scale, int[] p){
        this.octaves = octaves;
        this.roughness = roughness;
        this.scale = scale;
        this.p = p;
        perm =  new int[512];
        for(int i=0; i<512; i++) perm[i]=p[i & 255];
    }

    public SimplexNoise(int octaves, float roughness, float scale){
        this.octaves = octaves;
        this.roughness = roughness;
        this.scale = scale;
        this.p = noiseSeed();
        perm =  new int[512];
        for(int i=0; i<512; i++) perm[i]=p[i & 255];
    }

    private int perm[];
    // A lookup table to traverse the simplex around a given point in 4D.
    // Details can be found where this table is used, in the 4D noise method.

    // This method is a *lot* faster than using (int)Math.floor(x)
    private static int fastfloor(double x) {
        return x>0 ? (int)x : (int)x-1;
    }
    private static double dot(int g[], double x, double y) {
        return g[0]*x + g[1]*y; }

    private final double F2 = 0.5*(Math.sqrt(3.0)-1.0);
    private final double G2 = (3.0-Math.sqrt(3.0))/6.0;

    // 2D simplex noise
    public double noise(float xin, float yin) {
        double n0, n1, n2; // Noise contributions from the three corners
        // Skew the input space to determine which simplex cell we're in
        double s = (xin+yin)*F2; // Hairy factor for 2D
        int i = fastfloor(xin+s);
        int j = fastfloor(yin+s);
        double t = (i+j)*G2;
        double X0 = i-t; // Unskew the cell origin back to (x,y) space
        double Y0 = j-t;
        double x0 = xin-X0; // The x,y distances from the cell origin
        double y0 = yin-Y0;
        // For the 2D case, the simplex shape is an equilateral triangle.
        // Determine which simplex we are in.
        int i1, j1; // Offsets for second (middle) corner of simplex in (i,j) coords
        if(x0>y0) {i1=1; j1=0;} // lower triangle, XY order: (0,0)->(1,0)->(1,1)
        else {i1=0; j1=1;} // upper triangle, YX order: (0,0)->(0,1)->(1,1)
        // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
        // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
        // c = (3-sqrt(3))/6
        double x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) unskewed coords
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2; // Offsets for last corner in (x,y) unskewed coords
        double y2 = y0 - 1.0 + 2.0 * G2;
        // Work out the hashed gradient indices of the three simplex corners
        int ii = i & 255;
        int jj = j & 255;
        int gi0 = perm[ii+perm[jj]] % 12;
        int gi1 = perm[ii+i1+perm[jj+j1]] % 12;
        int gi2 = perm[ii+1+perm[jj+1]] % 12;
        // Calculate the contribution from the three corners
        double t0 = 0.5 - x0*x0-y0*y0;
        if(t0<0) n0 = 0.0;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient
        }
        double t1 = 0.5 - x1*x1-y1*y1;
        if(t1<0) n1 = 0.0;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }
        double t2 = 0.5 - x2*x2-y2*y2;
        if(t2<0) n2 = 0.0;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }
        // Add contributions from each corner to get the final noise value.
        // The result is scaled to return values in the interval [-1,1].
        return 70.0 * (n0 + n1 + n2);
    }

    public void generateOctavedSimplexNoise(int xoff, int yoff, int width, int height, int octaves, float roughness, float scale, float[] map){

        for(int x = 0; x < width; x++){
                float layerFrequency = scale;
                float layerWeight = 1;
                float weightSum = 0;
                map[x] = 0;
                for(int octave = 0; octave < octaves; octave++){
                    map[x] += (float) noise((x+xoff) * layerFrequency, (yoff) * layerFrequency) * layerWeight;
                    layerFrequency *= 2;
                    weightSum += layerWeight;
                    layerWeight *= roughness;
                }
                map[x] /= weightSum;
        }
    }

    public float generateOctavedSimplexNoise(int xoff, int yoff, int octaves, float roughness, float scale){
        float totalNoise = 0;
        float layerFrequency = scale;
        float layerWeight = 1;
        float weightSum = 0;

        for (int octave = 0; octave < octaves; octave++) {
            //Calculate single layer/octave of simplex noise, then add it to total noise
            totalNoise += (float) noise(xoff * layerFrequency, yoff * layerFrequency) * layerWeight;

            //Increase variables with each incrementing octave
            layerFrequency *= 2;
            weightSum += layerWeight;
            layerWeight *= roughness;

        }
        totalNoise = totalNoise / weightSum;

        return totalNoise;
    }

    public float generateNoise(int xoff, int yoff){
        return generateOctavedSimplexNoise(xoff, yoff, octaves, roughness, scale);
    }

    public float[] generateLevelPoints(int numPoints, float width, float height){
        float[] data = new float[numPoints];
        float[] levelPoints = new float[numPoints*2 + 4];
        generateOctavedSimplexNoise(0, 0, numPoints, 1, 8, 0.5f, 0.01f, data);
        for(int x = 0; x < numPoints; x++){
            levelPoints[x*2] = (float)x/(numPoints - 1)*width;
            levelPoints[x*2 + 1] = (data[x] + 1)/2*height;
        }
        levelPoints[numPoints*2] = width;
        levelPoints[numPoints*2 + 1] = 0;
        levelPoints[numPoints*2 + 2] = 0;
        levelPoints[numPoints*2 + 3] = 0;
        return levelPoints;
    }
}