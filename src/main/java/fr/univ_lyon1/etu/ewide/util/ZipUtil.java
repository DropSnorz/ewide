package fr.univ_lyon1.etu.ewide.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    /**
     * Do the zipping job for directory param
     * @param dirName to be zipped directory string
     * @param nameZipFile final zip name string
     * @throws IOException if both params are Null, or if dirName link to a non existent directory
     */
    public static void zipDir(String dirName, String nameZipFile) throws IOException {
        File file = new File(nameZipFile);
        if(file.createNewFile())
            System.out.println(nameZipFile + " already exists, replacing...");
        FileOutputStream fW = new FileOutputStream(file, false);
        ZipOutputStream zip = new ZipOutputStream(fW);
        addFolderToZip("", dirName, zip);
        zip.close();
        fW.close();
    }

    /**
     * Add folder to wanted zip (called by zipDir method)
     * @param path
     * @param srcFolder
     * @param zip
     * @throws IOException
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
        File folder = new File(srcFolder);
        if (folder.list().length == 0) {
            addFileToZip(path , srcFolder, zip, true);
        }
        else {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
                else {
                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }

    /**
     * Add file to wanted zip (called by zipDir method)
     * @param path
     * @param srcFile
     * @param zip
     * @param flag
     * @throws IOException
     */
    private static void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws IOException {
        File folder = new File(srcFile);
        if (flag) {
            zip.putNextEntry(new ZipEntry(path + "/" +folder.getName() + "/"));
        }
        else {
            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            }
            else {
                byte[] buf = new byte[1024];
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
                in.close();
            }
        }
    }

}
