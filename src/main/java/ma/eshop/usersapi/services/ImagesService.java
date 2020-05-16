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
        String path=imagesBaseLocation+imageName;
        File file = new File(path);
        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("INVALID_FILE:"+path);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            String formatName = imageName.split("\\.")[1];
            ImageIO.write(bImage,formatName, bos );
        } catch (IOException e) {
            try{
                ImageIO.write(bImage,"png", bos );
            }catch(IOException IOe){
                IOe.printStackTrace();
            }
        }
        byte [] data = bos.toByteArray();
        data =  Base64.getEncoder().encodeToString(data).getBytes();
        return new ByteArrayResource(data);
    }

    public void deleteByName(String imageName) throws IOException {
        Path imagePath = Paths.get(imagesBaseLocation+imageName);
        Files.delete(imagePath);
    }
}

