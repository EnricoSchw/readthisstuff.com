package com.readthisstuff.rts.service.util;


import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final String THUMB_TYPE = "jpg";


    public byte[] resizeThumb(byte[] image, int width, int height, String imageType) throws IOException {

        if (ArrayUtils.isEmpty(image)) {
            log.debug("RESIZE IMAGE Image was empty!");
            return image;
        }

        // Get a BufferedImage object from a byte array
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // Get image dimensions
        int orgHeight = originalImage.getHeight();
        int orgWidth = originalImage.getWidth();

        // The image is already a square
        if (height == orgHeight && width == orgWidth) {
            log.debug("RESIZE IMAGE Image has the right values! Nothing to do!");
            return image;
        }

        BufferedImage resizeImage = Thumbnails
                .of(originalImage)
                .forceSize(width, height)
                .asBufferedImage();

        return convertToByte(resizeImage, imageType);
    }

    private byte[] convertToByte(BufferedImage image, String imageType) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //Example:  String imageType == new String("image/png").substring(6); -> imageType == "png"
        ImageIO.write(image, imageType.substring(6), baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }
}
