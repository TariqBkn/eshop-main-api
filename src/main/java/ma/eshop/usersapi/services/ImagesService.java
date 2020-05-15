package ma.eshop.usersapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
@Service
public class ImagesService {
    @Value("${imagesBaseLocation}")
    String imagesBaseLocation;
    public ByteArrayResource findImageByName(String imageName) {
        String path=imagesBaseLocation+imageName;
        File file = new File(path);
        BufferedImage bImage = null;
        try {
            System.out.println("FILE Path :"+path);
            bImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("INVALID_FILE:"+path);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage,"jpg", bos );
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte [] data = bos.toByteArray();
        data =  Base64.getEncoder().encodeToString(data).getBytes();
        return new ByteArrayResource(data);
    }
    }

