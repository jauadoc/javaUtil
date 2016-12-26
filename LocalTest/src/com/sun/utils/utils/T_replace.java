package com.sun.utils.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


/**
 * 宏替换工具类，针对宏修改后影响其他page页面时使用<br>
 * 根据正则将宏进行替换，如需使用正则内分组，可如demo重写doCustomReplace方法即可<br>
 * 此工具会创建并占用C:/replaceTmpFile文件夹<br>
 * 可能出现换行问题
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 */

public abstract class T_replace {

    private static final String tempDir = "C:"+File.separator+"replaceTmpFile"+File.separator;

    /**
     * 将指定文件夹内所有以x结尾的文件中符合正则的内容替换
     * @param dirPath 需要替换的文件夹目录（该目录下所有符合规则的文件均被替换）
     * @param endWith 查找条件，以什么结尾的文件作为查找
     * @param regex 替换的正则
     * @param replacement 要替换成的内容
     */
    public void replace(String dirPath, String endWith, String regex, String replacement, boolean replace){

        File dir = new File(dirPath);
        //获取所有以x结尾的文件
        List<File> files = getEndWithFile(dir, endWith);
        //替换所有包含正则文件的内容，创建临时文件保存
        replaceFileContent(regex, replacement, files, replace);
    }

    /**
     * 将指定文件夹内所有以x结尾的文件中符合正则的内容替换<br>
     * 替换方式自定义，使用正则匹配到内容的分组
     * @param dirPath 需要替换的文件夹目录（该目录下所有符合规则的文件均被替换）
     * @param endWith 查找条件，以什么结尾的文件作为查找
     * @param regex 替换的正则
     * @param replace 是否生产临时文件后直接替换原文件
     */
    public void replace(String dirPath, String endWith, String regex, boolean replace){

        File dir = new File(dirPath);
        //获取所有以x结尾的文件
        List<File> files = getEndWithFile(dir, endWith);
        //替换所有包含正则文件的内容，创建临时文件保存
        replaceFileContent(regex, files, replace);
    }

    /**
     * 根据正则查找文件是否存在需要替换内容，存在则替换并保存为临时文件
     * @param regex
     * @param replacement
     * @param files
     * @param replace
     * @return
     */
    public void replaceFileContent(String regex, String replacement, List<File> files,  boolean replace) {

        Map<String, String> fileRelations = new HashMap<String, String>();

        for(File file : files){
            boolean isMatch = false;
            String renderPath = tempDir+file.getName();
            BufferedReader br = B_IOUtils.getFileBufferedReader(file);
            BufferedWriter bw = B_IOUtils.getFileBufferedWriter(renderPath, false, "UTF-8");
            String line = "";
            try {
                while((line = br.readLine()) != null){
                    String temp = line.replaceAll(regex, replacement);
                    if(line != temp){
                        isMatch = true;
                    }
                    bw.write(line+B_SystemUtils.newLine());
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "文件读取异常"+e);
            }
            if(replace){
                //如果匹配到内容，则将文件加入替换列表中，执行替换
                if(isMatch){
                    fileRelations.put(file.getAbsolutePath(), renderPath);
                }
            }
            if(!isMatch){
                new File(renderPath).delete();
            }
        }

        replaceFile(fileRelations);
    }

    /**
     * 根据正则查找文件是否存在需要替换内容，存在则替换并保存为临时文件
     * @param regex
     * @param replacement
     * @param files
     * @param replace
     * @return
     */
    public void replaceFileContent(String regex, List<File> files, boolean replace) {

        Map<String, String> fileRelations = new HashMap<String, String>();

        for(File file : files){
            boolean isMatch = false;
            String renderPath = tempDir+file.getName();
            BufferedReader br = B_IOUtils.getFileBufferedReader(file);
            BufferedWriter bw = B_IOUtils.getFileBufferedWriter(renderPath, false, "UTF-8");
            String line = "";
            try {
                while((line = br.readLine()) != null){
                    String tab = "";
                    for(int i=0; i<line.length(); i++){
                        if(line.charAt(i)==' '){
                            tab += " ";
                        }
                    }
                    String replaceStr = doCustomReplace(regex, line);
                    if(replaceStr != line){
                        isMatch = true;
                        bw.write(tab+replaceStr+B_SystemUtils.newLine());
                    }else{
                        bw.write(replaceStr+B_SystemUtils.newLine());
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "文件读取异常"+e);
            }
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                System.out.println("关闭文件流异常"+e);
            }
            if(replace){
                //如果匹配到内容，则将文件加入替换列表中，执行替换
                if(isMatch){
                    fileRelations.put(file.getAbsolutePath(), renderPath);
                }
            }
            if(!isMatch){
                new File(renderPath).delete();
            }
        }

        replaceFile(fileRelations);
    }

    /**
     * 自定义替换方式，需要使用正则分组的内容
     * @param regex
     * @param str
     * @return
     */
    public abstract String doCustomReplace(String regex, String str);

    /**
     * 获取指定目录下所有以endWith结尾的文件
     * @param dir
     * @param endWith
     * @return
     */
    private List<File> getEndWithFile(File dir, String endWith) {

        List<File> endWithFiles = new ArrayList<File>();

        File[] dirContainFiles = dir.listFiles();
        for(File file : dirContainFiles){
            if(!file.isDirectory() && file.getName().endsWith(endWith)){
                endWithFiles.add(file);
            }
            if(file.isDirectory()){
                endWithFiles.addAll(getEndWithFile(dir, endWith));
            }
        }
        return endWithFiles;
    }

    /**
     * 根据原文件和临时文件路径，将原文件替换
     * @param fileRelations
     * @return
     */
    private void replaceFile(Map<String, String> fileRelations){

        //遍历并替换文件
        for(Entry<String, String> fileRelation : fileRelations.entrySet()){
            //替换文件
            B_IOUtils.copyFile(fileRelation.getKey(), fileRelation.getValue(), true);
        }
    }

    /**
     * 示例：将原CustomBtn宏改为CriminalInfo宏<br>
     * 需要原宏内部分参数，当做新宏的参数<br>
     * 如不需要正则分组内容，直接调用replace(String dirPath, String endWith, String regex, String replacement, boolean replace)
     * 将需替换位置直接替换成指定内容即可。
     */
    public void demo(){
        new T_replace() {
            @Override
            public String doCustomReplace(String regex, String str) {

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(str);
                String bcName = "";
                String bcNum = "";
                boolean isMatch = false;
                while(matcher.find()){
                    bcName = matcher.group(1);
                    bcNum = matcher.group(2);
                    isMatch = true;
                }
                if(isMatch){
                    str = "#criminalInfo("+bcNum+","+bcName+")";
                }
                return str;
            }
        }.replace("C:\\Users\\Administrator\\Desktop\\test"
                , ".page"
                , "#customBtn\\(.*?criminal/common/initDetail.*?btnTitle=(.*?)\\+\"\\[\"\\+(.*?)\\+\"\\]\".*?\\)"
                , false);
    }

    public static void main(String[] args) {
        new T_replace() {
            @Override
            public String doCustomReplace(String regex, String str) {

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(str);
                String bcName = "";
                String bcNum = "";
                boolean isMatch = false;
                while(matcher.find()){
                    bcName = matcher.group(1);
                    bcNum = matcher.group(2);
                    isMatch = true;
                }
                if(isMatch){
                    str = "#criminalInfo("+bcNum+","+bcName+")";
                }
                return str;
            }
        }.replace("C:\\Users\\Administrator\\Desktop\\test"
                , ".page"
                , "#customBtn\\(.*?criminal/common/initDetail.*?btnTitle=(.*?)\\+\"\\[\"\\+(.*?)\\+\"\\]\".*?\\)"
                , false);
    }
}
