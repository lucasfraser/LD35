package au.com.ionprogramming.ld35;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Images {

    public static Texture IPL;
    public static Texture bad;
    public static Texture forklift;
    public static Texture forks;
    public static Texture bg;

    public static TextureRegion forkliftRegion;
    public static TextureRegion forksRegion;

    public static void loadImages(){
        bad = new Texture("badlogic.jpg");
        forklift = new Texture("forklift.png");
        forks = new Texture("forks.png");
        bg = new Texture("ld35background.png");
        forksRegion= new TextureRegion(forks);
        IPL = new Texture("IonProgrammingLogo640.png");
        forkliftRegion = new TextureRegion(forklift);
    }
}
