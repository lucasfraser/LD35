package au.com.ionprogramming.ld35;

import com.badlogic.gdx.graphics.Texture;

public class Images {

    public static Texture IPL;
    public static Texture bad;
    public static Texture forklift;

    public static void loadImages(){
        bad = new Texture("badlogic.jpg");
        forklift = new Texture("forklift.png");
        IPL = new Texture("IonProgrammingLogo640.png");
    }
}
