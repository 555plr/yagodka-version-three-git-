package com.example.yagodka_test_3001.service;

import com.example.yagodka_test_3001.controller.IndexController;
import com.example.yagodka_test_3001.controller.InfoUserRepository;
import com.example.yagodka_test_3001.persist.Image;
import com.example.yagodka_test_3001.persist.ImageRepository;
import com.example.yagodka_test_3001.persist.InfoUser;
import com.example.yagodka_test_3001.persist.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class InfoUserService {

    private static final Logger logger =
            LoggerFactory.getLogger(InfoUserService.class);

    private final InfoUserRepository infoUserRepository;

    private final ImageRepository imageRepository;

    @Autowired
    public InfoUserService(InfoUserRepository infoUserRepository, ImageRepository imageRepository) {
        this.infoUserRepository = infoUserRepository;
        this.imageRepository = imageRepository;
    }

    public void create(InfoUser infoUser, MultipartFile file1) throws IOException {
        Image image1;

        if(file1.getSize() != 0){
            image1 = toImageEntity(file1);
            imageRepository.save(image1);
            infoUser.setImage(image1);
        }

        logger.info("Saving new InfoUser. Nickname: {}", infoUser.getNickname());
        infoUserRepository.save(infoUser);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());

        return image;
    }
}
