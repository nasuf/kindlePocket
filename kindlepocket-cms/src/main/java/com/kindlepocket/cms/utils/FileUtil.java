package com.kindlepocket.cms.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import static java.lang.System.out;


public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 
     * 单个文件上传
     * 
     * @param is
     * 
     * @param fileName
     * 
     * @param filePath
     */

    public static void upFile(InputStream is, String fileName, String filePath) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File f = new File(filePath + "/" + fileName);
        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            byte[] bt = new byte[4096];
            int len;
            while ((len = bis.read(bt)) > 0) {
                bos.write(bt, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != fos) {
                    fos.close();
                    fos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解决浏览器附件名乱码问题
     * @param filename
     * @param browserAgent
     * @return
     */
    public static String getFileNameFromBrowser(String filename, String browserAgent) {

        if (browserAgent.contains("Firefox")) {
            // base64
            BASE64Encoder encoder = new BASE64Encoder();
            try {
                return "=?utf-8?B?" + encoder.encode(filename.getBytes("utf-8")) + "?=";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // urlencoder ie chrome safari
        try {
            return URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("附件名错误");
        }
    }

    public static long getFileSize(File file) throws IOException {
        /*
         * String filePath = request .getSession() .getServletContext() .getRealPath( "/WEB-INF/upload/0acf7b008de64551b45e40dd7bac5ac4-uploadedIn-20151224160511-uploadedBy-nasuf-named-eclipse黑色主题插件.zip");
         */
        FileChannel fc = null;
        try {
            if (file.exists() && file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
            } else {
                logger.info("file doesn't exist or is not a file");
            }
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    logger.error(e.toString());
                }
            }
        }
        return fc.size();
    }

    public static List<File> listFiles(String folderPath, List<File> fileList ){
       // List<File> fileList = new ArrayList<File>();
        File rootPath = new File(folderPath);
        File[] files = rootPath.listFiles();
        if(null != files){
            for(File file: files){
                if(file.isDirectory()){
                    listFiles(file.getAbsolutePath(),fileList);
                } else {
                    fileList.add(file);
                    if(logger.isInfoEnabled()){
                        logger.info("scanned files: " + file.getName() + "; path: " + file.getPath());
                    }
                }
            }
        }
        return fileList;
    }

    public static void copySingleFile(String oldPath, String newPath){

        try {
            File oldFile = new File(oldPath);
            new File(newPath).mkdirs();
            File newFile = new File(newPath+File.separator+oldFile.getName());
            //newFile.createNewFile();
            int bytesum = 0;
            int byteread = 0;
            if(oldFile.exists()){
                InputStream in = new FileInputStream(oldFile);
                FileOutputStream out = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                int length;
                while((byteread = in.read(buffer)) != -1){
                    bytesum += byteread;
                    out.write(buffer, 0, byteread);
                }
            }
            if(logger.isInfoEnabled()){
                logger.info("file: [" + oldPath + "] has been copy to [" + newFile.getPath());
            }
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("copy single file failed!", e);
            }
        }
    }

    public static void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs();
            File oldFolder = new File(oldPath);
            String[] oldFiles = oldFolder.list();
            File temp = null;
            for(int i=0; i<oldFiles.length; i++){
                if(oldPath.endsWith(File.separator)){
                    temp = new File(oldPath + oldFiles[i]);
                } else {
                    temp = new File(oldPath + File.separator + oldFiles[i]);
                }

                if(temp.isFile()){
                    FileInputStream in = new FileInputStream(temp);
                    FileOutputStream out = new FileOutputStream(newPath + Constants.SLASH + (temp.getName().toString()));
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while((len=in.read(b)) != -1){
                        out.write(b, 0, len);
                    }
                    out.flush();
                    out.close();
                    in.close();
                } else if(temp.isDirectory()){
                    copyFolder(oldPath + Constants.SLASH + oldFiles[i], newPath + Constants.SLASH + oldFiles[i]);
                }
            }
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("copy folder failed!", e);
            }
        }
    }

    public static void deleteSingleFile(String filePath) {
        File file = new File(filePath);
        file.delete();
        if(logger.isInfoEnabled()){
            logger.info("file: [" + filePath + "] has been deleted!");
        }
    }

    public static void deleteFolder(String folderPath){
        deleteAllFiles(folderPath);
        new File(folderPath).delete();
        if(logger.isInfoEnabled()){
            logger.info("folder: [" + folderPath + "] has been deleted!");
        }
    }

    public static void deleteAllFiles(String path){
        File file = new File(path);
        if(!file.exists()){
            if(logger.isWarnEnabled()){
                logger.warn("path not exists!");
            }
            return ;
        }
        if(!file.isDirectory()){
            if(logger.isWarnEnabled()){
                logger.warn("path is not a directory!");
            }
        }
        String[] tempList = file.list();
        File temp = null;
        for(int i=0; i<tempList.length; i++){
            if(path.endsWith(File.separator)){
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if(temp.isFile()){
                temp.delete();
                if(logger.isInfoEnabled()){
                    logger.info("file: [" + temp.getPath() + "] has been deleted!");
                }
            }
            if(temp.isDirectory()){
                deleteAllFiles(path + Constants.SLASH + tempList[i]);
               // deleteFolder(path + Constants.SLASH + tempList[i]);
            }
        }
    }

    public static void moveFile(String oldPath, String newPath) {
        copySingleFile(oldPath,newPath);
        deleteSingleFile(oldPath);
        if(logger.isInfoEnabled()){
            logger.info("file:[" + oldPath + "] has been moved to [" + newPath + "]");
        }
    }

    public static void moveFolder(String oldPath, String newPath){
        copyFolder(oldPath,newPath);
        deleteFolder(oldPath);
        if(logger.isInfoEnabled()){
            logger.info("folder:[" + oldPath + "] has been moved to [" + newPath + "]");
        }
    }

    /*public static void main(String args[]){
        new FileUtil().moveFolder("src/main/css7","src/main/css8");
    }*/
}
