package Sphere;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

/**
 * Einfuehrung in die Computergrafik
 * Bild zu Textur Konverter f�r LWJGL
 *@author Stefan Bodenschatz
 *31.07.2015
 */
public class TextureConverter {
	public static ByteBuffer convertToTextureData(BufferedImage img) {
		int[] pixelArray = new int[img.getWidth() * img.getHeight()];
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixelArray, 0,
				img.getWidth());
		ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(img.getWidth()
				* img.getHeight() * 4);

		for (int y = img.getHeight()-1; y >=0 ; y--) {
			for (int x = 0; x < img.getWidth(); x++) {
				int p = pixelArray[img.getWidth()*y + x];
				pixelBuffer.put((byte)((p>>0x10)&0xFF));
				pixelBuffer.put((byte)((p>>0x8 )&0xFF));
				pixelBuffer.put((byte)((p>>0x0 )&0xFF));
				pixelBuffer.put((byte)((p>>0x18)&0xFF));
			}
		}
		pixelBuffer.flip();
		return pixelBuffer;
	}
	public static int makeTextureFromBufferedImage(BufferedImage img){
		int texID=glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(),
				img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
				convertToTextureData(img));
        glGenerateMipmap(GL_TEXTURE_2D);
        return texID;
	}
}












