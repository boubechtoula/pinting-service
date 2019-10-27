package com.abesoft.printingservice.utils;

import static com.abesoft.printingservice.utils.PSConst.CONFIG_FOLDER_NAME;
import static com.abesoft.printingservice.utils.PSConst.JRXML_FOLDER_NAME;
import static com.abesoft.printingservice.utils.PSConst.LOGGING_Extention;
import static com.abesoft.printingservice.utils.PSConst.RESOURCES_FOLDER_NAME;
import static com.abesoft.printingservice.utils.PSConst.USER_HOME_PATH;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author b.walid
 */
public class Util {

    public static void setLoggingFilePath(String pdfType) {
        String path = null;
        if (Util.checkMandatory(pdfType)) {
            path = getAppCOnfigFolder()
                    + File.separator + "principalLogFile" + LOGGING_Extention;
        } else {
            path = getPdfTypeConfigFolder(pdfType)
                    + File.separator + pdfType + LOGGING_Extention;
        }
        System.setProperty("loggingFile.path", path);
    }

    public static String getAppCOnfigFolder() {
        String pathFolder = USER_HOME_PATH + File.separator + CONFIG_FOLDER_NAME;
        File directory = new File(pathFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return pathFolder;
    }

    public static String getPdfTypeConfigFolder(String pdfType) {
        return getAppCOnfigFolder() + File.separator + pdfType;
    }

    public static String getJrxmlFolder(String pdfType) {
        return getAppCOnfigFolder() + File.separator + pdfType + File.separator + JRXML_FOLDER_NAME;
    }

    public static String getResourcesFolder(String pdfType) {
        return getAppCOnfigFolder() + File.separator + pdfType + File.separator + RESOURCES_FOLDER_NAME;
    }

    public static boolean checkMandatory(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean checkListMandatory(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean checkMAPMandatory(Map map) {
        return map == null || map.isEmpty();
    }
}
