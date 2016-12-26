package com.sun.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sun.utils.utils.B_SystemUtils;

/**
 * 文件下载器，只能下载资源文件，无法下载网页！
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class DownloadDemo {

    public static void main(String[] args) {
        String uri = "http://61.130.25.167/m10.music.126.net/20161221143731/a8fe824b90a32cdcd5d1db990302864f/ymusic/f46f/16a0/4e95/2c81e8a505d62d0dcc1aaa303fedb9f8.mp3?wshc_tag=0&wsts_tag=585a1d53&wsid_tag=7ae08c92&wsiphost=ipdbm";
        new DownloadDemo(uri)
            .setDownloadPath("C:\\Users\\Administrator\\Desktop","童话镇")
                .download();
    }

    /**
     * 执行下载操作
     */
    private void download() {
        connection();
        creatFile();
        writeResult();
    }


    public DownloadDemo(String source) {
        this.source = source;
    }


    private String source = "";
    private String downloadFileUri = null;
    private File file = null;
    private URLConnection con = null;

    /**
     *  连接
     */
    public DownloadDemo connection() {
        URL url = null;
        try {
            url = new URL(source);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            con = url.openConnection();
            // con.setConnectTimeout(1000 * 60 *30);
            // con.setReadTimeout(1000 * 60 *30);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     *  创建本地文件
     */
    public DownloadDemo creatFile() {
        file = new File(downloadFileUri);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     *  将从网络获取的文件的InputStream并写入到本地
     */
    @SuppressWarnings({ "null" })
    private DownloadDemo writeResult() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        // BufferedOutputStream bos = new BufferedOutputStream(fos, 300 * 1024);
        DataOutputStream dos =  new DataOutputStream(bos);

        InputStream inputStream = null;
        try {
            // is = con.getOutputStream();
            inputStream = con.getInputStream();
            // System.out.println(inputStream.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e1) {
            }
        }
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        // BufferedInputStream bis = new BufferedInputStream(inputStream,
        // 300 * 1024);
        DataInputStream dis = new DataInputStream(bis);
        try {
            while (true) {
                dos.write(dis.readByte());
            }
        } catch (EOFException e) {
            System.out.println("下载完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                dos.close();
                bis.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return this;
    }

    /**
     * 设置文件保存路径
     * @param downloadPath
     * @return
     */
    public DownloadDemo setDownloadPath(String downloadPath) {

        downloadFileUri = source.substring(source.lastIndexOf("/") + 1, source.contains("\\?")?source.length():source.indexOf("?")).trim();

        //如果路径最后不包含/或\\则添加系统文件分隔符，保证路径正确
        downloadPath = B_SystemUtils.fileSepartor(downloadPath);
        if(!downloadPath.endsWith("\\") || !downloadPath.endsWith("/")){
            downloadPath += File.separator;
        }

        downloadFileUri = downloadPath + downloadFileUri;

        System.out.println(downloadFileUri);
        return this;
    }

    /**
     * 设置文件保存路径及文件名
     * @param downloadPath
     * @return
     */
    public DownloadDemo setDownloadPath(String downloadPath, String fileName) {

        //截取文件格式，添加到自定义文件名后
        String endSfx = source.substring(source.lastIndexOf("/") + 1, source.contains("\\?")?source.length():source.indexOf("?")).trim();
        endSfx = endSfx.substring(endSfx.indexOf("."), endSfx.length());
        fileName += endSfx;

        //如果路径最后不包含/或\\则添加系统文件分隔符，保证路径正确
        downloadPath = B_SystemUtils.fileSepartor(downloadPath);
        if(!downloadPath.endsWith("\\") || !downloadPath.endsWith("/")){
            downloadPath = downloadPath += File.separator;
        }

        downloadFileUri = downloadPath + fileName;
        System.out.println(downloadFileUri);
        return this;
    }

}
