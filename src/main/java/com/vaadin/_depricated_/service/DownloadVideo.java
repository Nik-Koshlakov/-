package com.vaadin._depricated_.service;

import java.io.File;
import java.net.URL;

import com.github.axet.vget.VGet;
import org.apache.commons.io.FileUtils;


/**
 * Created by Nik on 07.07.2017.
 */
@Deprecated
public class DownloadVideo {
    public static final String pathForSave = "C:\\labs\\6_kurs\\kursovoi rsoi\\_client(copy)\\videoYouTube";

    public static void saveVideo(String youTubeURL) {
        VGet v = null;
        try {
            FileUtils.cleanDirectory(new File(DownloadVideo.pathForSave));
            v = new VGet(new URL(youTubeURL), new File(pathForSave));
            v.download();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //v.done(new AtomicBoolean(true));
            System.out.println(v);
        }
    }
}
