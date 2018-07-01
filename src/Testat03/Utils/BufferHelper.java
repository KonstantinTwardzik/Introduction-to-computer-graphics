package Testat03.Utils;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Einfuehrung in die Computergrafik
 * Hilfsroutinen um f�r LWJGL Puffer bereitzustellen
 *
 * @author Stefan Bodenschatz
 * 31.07.2015
 */
public class BufferHelper {

    public static IntBuffer convertToBuffer(int[][] array) {
        int cap = 0;
        for (int[] a : array) {
            cap += a.length;
        }
        IntBuffer buff = BufferUtils.createIntBuffer(cap);
        for (int[] a : array) {
            buff.put(a);
        }
        buff.flip();
        return buff;
    }

    /**
     * @param array Vektor mit ints
     * @return Puffer mit ints
     */
    public static IntBuffer convertToBuffer(int[] array) {
        IntBuffer buff = BufferUtils.createIntBuffer(array.length);
        buff.put(array);
        buff.flip();
        return buff;
    }

    /**
     * @param array Matrize mit floats
     * @return Floatbuffer f�r LWJGL
     */
    public static FloatBuffer convertToBuffer(float[][] array) {
        int cap = 0;
        for (float[] a : array) {
            cap += a.length;
        }
        FloatBuffer buff = BufferUtils.createFloatBuffer(cap);
        for (float[] a : array) {
            buff.put(a);
        }
        buff.flip();
        return buff;
    }

    /**
     * @param array Vektor mit floats
     * @return FloatBuffer fuer LWJGL
     */
    public static FloatBuffer convertToBuffer(float[] array) {
        FloatBuffer buff = BufferUtils.createFloatBuffer(array.length);
        buff.put(array);
        buff.flip();
        return buff;
    }
}
