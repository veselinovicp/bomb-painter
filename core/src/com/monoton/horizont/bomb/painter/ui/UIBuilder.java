package com.monoton.horizont.bomb.painter.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by monoton on 18.9.2017.
 */
public class UIBuilder extends AssetManager {




    public static final String DEFAULT_SKIN_ATLAS = "quantum-horizon-ui.atlas";//quantum-horizon-ui.atlas  uiskin.atlas
    public static final String DEFAULT_SKIN = "quantum-horizon-ui.json";//quantum-horizon-ui.json uiskin.json




    public static final String COURT = "court_2.jpg";//"explosion.png"
    public static final String EXPLOSION = "explosion.png";//"basketball_ball_2.png"
    public static final String BASKET_BALL = "basketball_ball_2.png";//"coin_spin_4.png"
    public static final String COIN_SPIN = "coin_spin_4.png";//""flame_4.png""
    public static final String FLAME = "flame_4.png";//"DeathFlash.ogg"
    public static final String EXPLOSION_SOUND = "DeathFlash.ogg";//"basket_ball_drop.wav"
    public static final String BASKET_BALL_DROP = "basket_ball_drop.wav";//"money_spin.wav"
    public static final String MONEY_SPIN_SOUND = "money_spin.wav";//"money_spin.wav"



    public UIBuilder() {
        super();
    }



    public void startLoading(){



        super.load(DEFAULT_SKIN_ATLAS, TextureAtlas.class);

        super.load(DEFAULT_SKIN, Skin.class, new SkinLoader.SkinParameter(DEFAULT_SKIN_ATLAS));

        super.load(COURT, Texture.class);
        super.load(EXPLOSION, Texture.class);
        super.load(BASKET_BALL, Texture.class);
        super.load(COIN_SPIN, Texture.class);
        super.load(FLAME, Texture.class);
        super.load(EXPLOSION_SOUND, Sound.class);
        super.load(BASKET_BALL_DROP, Sound.class);
        super.load(MONEY_SPIN_SOUND, Sound.class);





    }

    public void dispose(){
        super.dispose();
    }




}
