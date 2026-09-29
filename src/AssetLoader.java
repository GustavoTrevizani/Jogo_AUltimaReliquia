import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public final class AssetLoader {

    private AssetLoader() {}

    public static BufferedImage load(String name) {
        return loadFromFolder(name, "sprites");
    }

    public static BufferedImage loadBackground(String name) {
        return loadFromFolder(name, "backgrounds");
    }

    private static BufferedImage loadFromFolder(String name, String folder) {
        String[] resources = {
                "/assets/" + folder + "/" + name,
                "/src/assets/" + folder + "/" + name
        };

        for (String resource : resources) {
            try {
                InputStream input = AssetLoader.class.getResourceAsStream(resource);
                if (input != null) {
                    BufferedImage image = ImageIO.read(input);
                    input.close();
                    if (image != null) return image;
                }
            } catch (Exception ignored) { }
        }

        String userDir = System.getProperty("user.dir");
        String[] paths = {
                userDir + File.separator + "assets/" + folder + "/" + name,
                userDir + File.separator + "src/assets/" + folder + "/" + name,
                "assets/" + folder + "/" + name,
                "src/assets/" + folder + "/" + name,
                "../assets/" + folder + "/" + name,
                "../src/assets/" + folder + "/" + name,
                "../../assets/" + folder + "/" + name,
                "../../src/assets/" + folder + "/" + name
        };

        for (String path : paths) {
            try {
                File file = new File(path);
                if (file.isFile()) {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) return image;
                }
            } catch (Exception ignored) { }
        }

        return null;
    }
}