package ma.eshop.usersapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class UploadsService {
    @Value("${productsInputStorageLocation}")
    String productsInputStorageLocation;

    @Value("${imagesBaseLocation}")
    String imagesBaseLocation;

    public void uploadCsvFile(MultipartFile multipartProductsFile) throws IOException {
        File productsCsvFile = new File(productsInputStorageLocation);
        productsCsvFile.getParentFile().mkdirs();
        productsCsvFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(productsCsvFile);
        fileOutputStream.write(multipartProductsFile.getBytes());
        fileOutputStream.close();
    }

    public void uploadImage(MultipartFile multipartProductsFile,String name ) throws IOException {
        File imageFile = new File(imagesBaseLocation+name);
        imageFile.getParentFile().mkdirs();
        imageFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
        fileOutputStream.write(multipartProductsFile.getBytes());
        fileOutputStream.close();
    }
}
