package Testat03.Shaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ShaderReader {

    /***
     * Lie�t eine Datei ein und gibt dessen Inhalt (byteweise) als
     * String zur�ck. Soll benutzt werden, um shader-Dateien einzulesen.
     * @param path
     * Der Pfad vom Projekt-Ordner zur Datei. Bsp.: "./src/package/shadername.vert"
     * @return
     * Inhalt der Datei (byteweise) als String. Oder null, falls ein Fehler
     * aufgetreten ist
     */
    public static String readFile(String path) {
        byte[] byteContent;
        String content = "";
        try {
            byteContent = Files.readAllBytes(Paths.get(path));
            content = new String(byteContent);
            return content;
        } catch (IOException e) {
            System.err.print(e.getMessage());
        } catch (OutOfMemoryError e) {
            System.err.print(e.getMessage());
        } catch (SecurityException e) {
            System.err.print(e.getMessage());
        }
        return null;
    }

}
