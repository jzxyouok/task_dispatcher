package com.hptpd.taskdispatcherserver.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FileUploadUtil {

    public static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public static String fileBaseUrl;
    public static String fileBasePath;
    public static String port = "8080";

    public static String getPort() {
        if (StringUtils.isEmpty(port)) {
            return "80";
        }

        return port;
    }

    public static void setPort(String port) {
        FileUploadUtil.port = port;
    }

    public static String getServerIpAdddress() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ip = "http://" + ip + ":" + getPort();
        return ip;
    }

    public static String pathToUrl(String path, String modelId) {

        return "/" + modelId + "/" + path.substring(path.lastIndexOf("\\") + 1);
    }

    private static String getSubPath(String modelId, String fileName) {
        return "\\" + modelId + "\\" + fileName;
    }

    /**
     * 将上传的文件存在服务器文件系统
     *
     * @param dirFile 上传文件对应的文件夹
     * @param file    上传的源文件
     * @return 文件存储的路径
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String saveFile(File dirFile, CommonsMultipartFile file,
                                  String modelId) throws IllegalStateException, IOException {
        // 判断文件是否存在
        String fileName = null;
        File finalFile = null;
        if (!file.isEmpty()) {
            fileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            // if (!ArrayUtils.contains(Contant.FILE_EXTENSION_PERMIT,
            // fileExtension)) {
            // throw new NoSupportExtensionException("No Support Extension.");
            // }

            // 将文件进行重命名之后然后进行存储
            fileName = StringUtil.getRandomString(32) + "." + fileExtension;
            finalFile = new File(dirFile, fileName);

            file.transferTo(finalFile);
            finalFile.getAbsolutePath();

        }
        return getSubPath(modelId, fileName);
        // return finalFile.getAbsolutePath();
    }

    /**
     * 删除一个文件或者目录
     *
     * @param targetPath 文件或者目录路径
     * @IOException 当操作发生异常时抛出
     */
    public static void deleteFile(String targetPath) throws IOException {
        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            FileUtils.deleteDirectory(targetFile);
        } else if (targetFile.isFile()) {
            targetFile.delete();
        }
    }

    /**
     * 读取文件或者目录的大小
     *
     * @param distFilePath 目标文件或者文件夹
     * @return 文件或者目录的大小，如果获取失败，则返回-1
     */
    public static long genFileSize(String distFilePath) {
        File distFile = new File(distFilePath);
        if (distFile.isFile()) {
            return distFile.length();
        } else if (distFile.isDirectory()) {
            return FileUtils.sizeOfDirectory(distFile);
        }
        return -1L;
    }

    /**
     * 判断一个文件是否存在
     *
     * @param filePath 文件路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isExist(String filePath) {
        return new File(filePath).exists();
    }

    public static byte[] readFileToByteArray(String filePath) {
        File file = new File(filePath);
        byte[] out = null;
        if (file.exists()) {
            try {
                out = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {

                e.printStackTrace();

            }
        }
        return out;
    }

    public static String getDownloadFileName(String fileName) {
        String downloadFileName = null;
        try {
            downloadFileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return downloadFileName;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static File writeFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;

        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + fileName);
           /* 使用以下2行代码时，不追加方式*/
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(bfile);

            /* 使用以下3行代码时，追加方式*/
//            bos = new BufferedOutputStream(new FileOutputStream(file, true));
//            bos.write(bfile);
//            bos.write("\r\n".getBytes());


            bos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }

        }

        return file;
    }

    public static ResponseEntity<byte[]> fileDownloadWarpper(byte[] bytes, String name, MediaType mimeType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mimeType);
        headers.setContentDispositionFormData("attachment",
                getDownloadFileName(name));

        ResponseEntity<byte[]> fileEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        return fileEntity;
    }

    public static ResponseEntity<String> strDownloadWarpper(String content, String name, MediaType mimeType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mimeType);
        headers.setContentDispositionFormData("attachment",
                getDownloadFileName(name));
        ResponseEntity<String> fileEntity = new ResponseEntity<>(content, headers, HttpStatus.OK);

        return fileEntity;
    }
}
