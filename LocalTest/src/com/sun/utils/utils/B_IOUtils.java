package com.sun.utils.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

/**
 * io操作utils
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class B_IOUtils {

    /**
     * 级联删除，当前路径如果是文件则删除文件，目录则级联删除目录
     * @param path
     * @return
     */
    public boolean delete(String path){

        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(path);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(path);
            }
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   path 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String path) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取FileReader的bufferedreader
     * @param path 文件路径
     * @return
     */
    public static BufferedReader getFileBufferedReader(String path){

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(path)));
        } catch (FileNotFoundException e) {
            System.out.println("文件读取时异常，检查文件是否存在");
            System.out.println(e);
        }

        return br;
    }

    /**
     * 获取FileReader的bufferedreader
     * @param file 文件
     * @return
     */
    public static BufferedReader getFileBufferedReader(File file){

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("文件读取时异常，检查文件是否存在");
            System.out.println(e);
        }

        return br;
    }

    /**
     * 通过buffered包装的OutputStreamWriter将字符串一次性写入文件，不可续写，续写则获取writer进行操作<br>
     * example:B_IOUtils.write2File("path", "str", true, "UTF-8");
     * @param path 文件的路径
     * @param str 要写入的字符串
     * @return
     */
    public static boolean write2File(String path, String str, boolean push, String code){

        boolean isWriten = false;

        BufferedWriter bw = null;

        try {

            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path), push), code));
            bw.write(str);
            bw.flush();
            isWriten = true;
            bw.close();
        } catch (FileNotFoundException e) {

            System.out.println("文件读取时异常，检查文件是否存在");
            System.out.println(e);
        } catch (IOException e) {

            System.out.println("文件写入IO异常");
        };

        return isWriten;
    }

    /**
     *
     * 获取FileOutputStream的bufferedwriter
     * @param path 文件路径
     * @param push 是否在文件尾续写
     * @param code 编码格式
     * @return
     */
    public static BufferedWriter getFileBufferedWriter(String path, boolean append, String code){

        BufferedWriter bw = null;

        try {
            checkAndCreateFile(new File(path));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path), append),code));
        } catch (FileNotFoundException e) {
            System.out.println("文件读取时异常，检查文件是否存在");
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("不支持的编码异常");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("文件不存在，不存在时自动创建时异常");
            System.out.println(e);
        };

        return bw;
    }

    /**
    *
    * 获取FileOutputStream的bufferedwriter
    * @param path 文件路径
    * @param push 是否在文件尾续写
    * @param code 编码格式
    * @return
    */
   public static BufferedWriter getFileBufferedWriter(File file, boolean append, String code){

       BufferedWriter bw = null;

       try {

           checkAndCreateFile(file);
           new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append),code));
       } catch (FileNotFoundException e) {

           System.out.println("文件读取时异常，检查文件是否存在");
           System.out.println(e);
       } catch (UnsupportedEncodingException e) {

           System.out.println("不支持的编码异常");
           System.out.println(e);
       } catch (IOException e) {
           System.out.println("文件不存在，不存在时自动创建时异常");
           System.out.println(e);

       };

       return bw;
   }

    /**
     * 获取文件中的内容<br>
     * example:StringBuffer sb = B_IOUtils.getFileContent("path", "UTF-8");
     * @param path 路径
     * @param code 编码
     * @return
     */
    public static StringBuffer getFileContent(String path, String code){

        StringBuffer fileContent = new StringBuffer();

        BufferedReader br = null;
        String line = "";

        try {

             br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), code));

             while((line = br.readLine()) != null){

                 fileContent.append(line);
                 fileContent.append(B_SystemUtils.newLine());
             }
             br.close();
        } catch (FileNotFoundException e) {

            System.out.println("文件读取时异常，检查文件是否存在");
            System.out.println(e);
        } catch (IOException e) {

            System.out.println("文件读取时IO异常");
            System.out.println(e);
        }

        return fileContent;
    }

    private static String MESSAGE = "";

    /**
     * 复制单个文件
     *
     * @param srcFileName
     *            待复制的文件名
     * @param descFileName
     *            目标文件名
     * @param overlay
     *            如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName,
            boolean overlay) {
        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            MESSAGE = "源文件：" + srcFileName + "不存在！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else if (!srcFile.isFile()) {
            MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName
     *            待复制目录的目录名
     * @param destDirName
     *            目标目录名
     * @param overlay
     *            如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String destDirName,
            boolean overlay) {
        // 判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            MESSAGE = "复制目录失败：源目录" + srcDirName + "不存在！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else if (!srcDir.isDirectory()) {
            MESSAGE = "复制目录失败：" + srcDirName + "不是目录！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        }

        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                new File(destDirName).delete();
            } else {
                MESSAGE = "复制目录失败：目的目录" + destDirName + "已存在！";
                JOptionPane.showMessageDialog(null, MESSAGE);
                return false;
            }
        } else {
            // 创建目的目录
            System.out.println("目的目录不存在，准备创建。。。");
            if (!destDir.mkdirs()) {
                System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {
                flag = B_IOUtils.copyFile(files[i].getAbsolutePath(),
                        destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag = B_IOUtils.copyDirectory(files[i].getAbsolutePath(),
                        destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            MESSAGE = "复制目录" + srcDirName + "至" + destDirName + "失败！";
            JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查文件是否存在，若不存在则创建该文件
     * @param file 文件对象
     * @return true 文件存在或创建成功
     * @throws IOException
     */
    public static boolean checkAndCreateFile(File file) throws IOException {
        boolean flag = true;
        if (!file.exists()) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                flag = folder.mkdirs();
            }
            if (flag) {
                flag = file.createNewFile();
            }
        }
        return flag;
    }
}
