package ma.eshop.usersapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImagesService {
    @Value("${imagesBaseLocation}")
    String imagesBaseLocation;
    public ByteArrayResource findByName(String imageName) {
        String path = imagesBaseLocation+imageName;
        File file = getFileFromPath(path);
        BufferedImage bufferedImage = getBufferedImage(path, file);
        ByteArrayOutputStream byteArrayOutputStream = getByteArrayOutputStream(imageName, bufferedImage);
        return getByteArrayResource(byteArrayOutputStream);
    }

        private File getFileFromPath(String path){
            return new File(path);
        }

        private ByteArrayResource getByteArrayResource(ByteArrayOutputStream byteArrayOutputStream) {
            byte [] imageBytes = byteArrayOutputStream.toByteArray();
            imageBytes =  Base64.getEncoder().encodeToString(imageBytes).getBytes();
            return new ByteArrayResource(imageBytes);
        }

        private ByteArrayOutputStream getByteArrayOutputStream(String imageName, BufferedImage bufferedImage) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                String formatName = getImageFileExtension(imageName);
                ImageIO.write(bufferedImage, formatName, byteArrayOutputStream );
            } catch (IOException e) {
                try{
                    writeAsPng(bufferedImage, byteArrayOutputStream);
                }catch(IOException IOe){
                    IOe.printStackTrace();
                }
            }
            return byteArrayOutputStream;
        }

        private void writeAsPng(BufferedImage bufferedImage, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
            ImageIO.write(bufferedImage,"png", byteArrayOutputStream );
        }

        private String getImageFileExtension(String imageName) {
            return imageName.split("\\.")[1];
        }

        private BufferedImage getBufferedImage(String path, File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("INVALID_FILE:"+path);
        }
        return bufferedImage;
    }

    public void deleteByName(String imageName) throws IOException {
        Path imagePath = Paths.get(imagesBaseLocation+imageName);
        Files.delete(imagePath);
    }
}

