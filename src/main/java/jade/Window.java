package jade;

import jdk.swing.interop.SwingInterOpUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    int width , height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window(){
        this.height = 1080;
        this.width = 1920;
        this.title = "Mario";

    }

    public static Window get() {
        if (Window.window == null){
            Window.window = new Window();
        }

        return  Window.window;
    }

    public void run (){
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();

        //Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate  GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        //set up error callback where to print any of teh errors & looging info
        //System.err.println("We have an error");
        GLFWErrorCallback.createPrint(System.err).set();

        //initialize GLFW
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW ");
        }

        //Configure GLFWy
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // maximizes window size

        //create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException( "Failed to create GLFW window");
        }

        //Make the OpenGl Context Current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        //Create capabilities  makes sure we can use bindings
        GL.createCapabilities();


    }

    public void loop(){
        while(!glfwWindowShouldClose(glfwWindow)) {
            //pol events
            glfwPollEvents();;

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);


        }
    }
}
