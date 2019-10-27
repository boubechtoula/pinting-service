package com.wsoft.printingservice.model.dto;

/**
 *
 * @author b.walid
 */
public class PdfTypeDto {

    private String name;
    private String printName;
    private String jrxmFile;
    private String ressourcesFolder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getJrxmFile() {
        return jrxmFile;
    }

    public void setJrxmFile(String jrxmFile) {
        this.jrxmFile = jrxmFile;
    }

    public String getRessourcesFolder() {
        return ressourcesFolder;
    }

    public void setRessourcesFolder(String ressourcesFolder) {
        this.ressourcesFolder = ressourcesFolder;
    }
}
