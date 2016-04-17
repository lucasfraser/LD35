package au.com.ionprogramming.ld35;

import com.badlogic.gdx.graphics.Texture;

public class Images {

    public static Texture IPL;
    public static Texture bad;

    public static void loadImages(){
        IPL = new Texture("IonProgrammingLogo640.png");
        bad = new Texture("badlogic.jpg");
    }
}
