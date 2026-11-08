package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos , lastX, lastY;
    private boolean mouseButtonPressed [] = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static  MouseListener get (){
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    //Mouse Callback
    public  static  void mousePosCallback(long window, double xPos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    //Mouse Button callbacks
    public static void mouseButtonCallBack(long window, int button, int action, int mods) {
        if (action ==GLFW_PRESS) {
            if ( button < get().mouseButtonPressed.length) {  //check that we do not exceed the number of button inputs
                get().mouseButtonPressed[button] = true;
            }
        } else if ( action == GLFW_RELEASE) {
            if ( button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    //Mouse Scroll back that  sends notification when a user scrolls with a mouse or touchpad
    public static void mouseScrollCallback( long window, double xOffset, double yOffset ){
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    //end the key frames
    public static void endFrame(){
        get().scrollY = 0;
        get().scrollY = 0;
        get().lastY = get().yPos;
        get().lastX  = get().xPos;
    }

    public static float  getX() {
        return  (float)get().xPos;
    }

    public static float  getY() {
        return  (float)get().yPos;
    }

    //Get the last  displacement in the x position
    public static float getDx() {
        return (float)(get().lastX - get().xPos);
    }

    //Get the last  displacement in the y position
    public static float getDy() {
        return (float)(get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float)get().scrollX;
    }

    public static  float getScrollY() {
        return (float)get().scrollY;
    }

    //check if mouse is dragging
    public static boolean isDragging() {
        return get().isDragging;
    }

    //check if mouse button is down
    public static  boolean mouseButtonDown (int button) {
        if (button < get().mouseButtonPressed.length ) {
            return  get().mouseButtonPressed[button];
        }
        else {
            return false;
        }
    }
}
