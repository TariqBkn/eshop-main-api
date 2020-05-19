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
        File productsCsvFile = makeFile(productsInputStorageLocation);
        saveFileToLocalStorage(multipartProductsFile, productsCsvFile);
        return;
    }

    public void uploadImage(MultipartFile multipartProductsFile,String name ) throws IOException {
        File imageFile = makeFile(name, imagesBaseLocation);
        saveFileToLocalStorage(multipartProductsFile, imageFile);
    }

    private File makeFile(String name, String location) throws IOException {
        File file = new File(location+name);
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
    private File makeFile(String location) throws IOException {
        File file = new File(location);
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
    private void saveFileToLocalStorage(MultipartFile multipartProductsFile, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartProductsFile.getBytes());
        fileOutputStream.close();
    }
}
