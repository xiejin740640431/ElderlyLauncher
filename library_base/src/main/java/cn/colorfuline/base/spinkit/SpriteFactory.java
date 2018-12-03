package cn.colorfuline.base.spinkit;


import cn.colorfuline.base.spinkit.sprite.Sprite;
import cn.colorfuline.base.spinkit.style.ChasingDots;
import cn.colorfuline.base.spinkit.style.Circle;
import cn.colorfuline.base.spinkit.style.CubeGrid;
import cn.colorfuline.base.spinkit.style.DoubleBounce;
import cn.colorfuline.base.spinkit.style.FadingCircle;
import cn.colorfuline.base.spinkit.style.FoldingCube;
import cn.colorfuline.base.spinkit.style.MultiplePulse;
import cn.colorfuline.base.spinkit.style.MultiplePulseRing;
import cn.colorfuline.base.spinkit.style.Pulse;
import cn.colorfuline.base.spinkit.style.PulseRing;
import cn.colorfuline.base.spinkit.style.RotatingCircle;
import cn.colorfuline.base.spinkit.style.RotatingPlane;
import cn.colorfuline.base.spinkit.style.ThreeBounce;
import cn.colorfuline.base.spinkit.style.WanderingCubes;
import cn.colorfuline.base.spinkit.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
