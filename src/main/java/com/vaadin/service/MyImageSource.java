package com.vaadin.service;

import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.colorpicker.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nik on 15.05.2017.
 */
public class MyImageSource implements StreamResource.StreamSource {

    int reloads = 0;
    ByteArrayOutputStream imagebuffer = null;

    @Override
    public InputStream getStream() {
        // Create an image
        BufferedImage image = new BufferedImage (400, 400,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D drawable = image.createGraphics();

        // Draw something static
        drawable.setStroke(new BasicStroke(5));
        drawable.setColor(java.awt.Color.white);
        drawable.fillRect(0, 0, 400, 400);
        drawable.setColor(java.awt.Color.black);
        drawable.drawOval(50, 50, 300, 300);

        // Draw something dynamic
        drawable.setFont(new Font("Montserrat", Font.PLAIN, 48));
        drawable.drawString("Reloads=" + reloads, 75, 216);
        drawable.setColor(new java.awt.Color(0, 165, 235));

        reloads++;
        int x= (int) (200-10 + 150*Math.sin(reloads * 0.3));
        int y= (int) (200-10 + 150*Math.cos(reloads * 0.3));
        drawable.fillOval(x, y, 20, 20);

        try {
            // Write the image to a buffer
            imagebuffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebuffer);

            // Return a stream from the buffer
            return new ByteArrayInputStream(imagebuffer.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
