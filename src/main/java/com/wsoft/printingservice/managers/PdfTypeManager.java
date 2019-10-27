package com.wsoft.printingservice.managers;

import com.wsoft.printingservice.model.dto.PdfTypeDto;
import com.wsoft.printingservice.model.entity.PdfType;
import com.wsoft.printingservice.services.PdfTypeService;
import com.wsoft.printingservice.utils.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
public class PdfTypeManager {

    private final Logger logger = LoggerFactory.getLogger(PdfTypeManager.class);
    @Autowired
    PdfTypeService pdfTypeService;
    private PdfTypeDto pdfType;
    private PdfType selectedPdfType;
    private int etape;

    public PdfTypeManager() {
        etape = 0;
        pdfType = new PdfTypeDto();
    }

    public void createPdfType() {
        if (Util.checkMandatory(pdfType.getName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Type pdf obligatoire."));
            return;
        }
        if (Util.checkMandatory(pdfType.getPrintName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Type imprimante obligatoire."));
            return;
        }
        Util.setLoggingFilePath("");
        logger.info("dfdsfffsdfsdfdsfdsf");
        etape = 1;
    }

    public void uploadJrxmlFiles(FileUploadEvent event) {
        if (event.getFile() != null) {
            System.setProperty("java.awt.headless", "true");
            long nb = pdfTypeService.getMaxId();

            UploadedFile file = event.getFile();
            String folderPath = Util.getJrxmlFolder(pdfType.getName() + (nb + 1));
            saveUploadedFile(folderPath, file);
            //save pdf type in the data base
            PdfType pt = new PdfType();
            pt.setJrxmlName(file.getFileName());
            pt.setName(pdfType.getName());
            pt.setPrintName(pdfType.getPrintName());
            pt.setRessourcesFolderName(Util.getPdfTypeConfigFolder(pdfType.getName() + (nb + 1)));
            pdfTypeService.savePdfType(pt);
            pdfType.setName(pdfType.getName() + (nb + 1));
            //messages
            FacesMessage message = new FacesMessage("Succès", file.getFileName() + "est téléchargé.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().update("form");
            etape = 2;
        }
    }

    public void uploadRessourceFiles(FileUploadEvent event) {
        if (event.getFile() != null) {
            System.setProperty("java.awt.headless", "true");
            UploadedFile file = event.getFile();
            String folderPath = Util.getResourcesFolder(pdfType.getName());
            saveUploadedFile(folderPath, file);
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().update("form");
            PrimeFaces.current().ajax().update("listType");
            PrimeFaces.current().executeScript("PF('dlg2').hide();");
            etape = 0;
        }
    }

    public Path saveUploadedFile(String path, UploadedFile uploadedFile) {
        Path fileToSavePath = null;
        try {
            //First, Generate file to make directories
            String savedFileName = path + File.separator + uploadedFile.getFileName();
            File fileToSave = new File(savedFileName);
            fileToSave.getParentFile().mkdirs();
            fileToSave.delete();
            //Generate path file to copy file
            Path folder = Paths.get(savedFileName);
            fileToSavePath = Files.createFile(folder);
            //Copy file to server
            InputStream input = uploadedFile.getInputstream();
            Files.copy(input, fileToSavePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("IOException lors de l'upload du fichier " + uploadedFile.getFileName());
        } finally {
            return fileToSavePath;
        }
    }

    public List<PdfType> getListPdfTypes() {
        return pdfTypeService.listPdfTypes();
    }

    public void reset() {
        etape = 0;
        pdfType = new PdfTypeDto();
    }

    public PdfTypeDto getPdfType() {
        return pdfType;
    }

    public void setPdfType(PdfTypeDto pdfType) {
        this.pdfType = pdfType;
    }

    public int getEtape() {
        return etape;
    }

    public void setEtape(int etape) {
        this.etape = etape;
    }

    public PdfType getSelectedPdfType() {
        return selectedPdfType;
    }

    public void setSelectedPdfType(PdfType selectedPdfType) {
        this.selectedPdfType = selectedPdfType;
    }
}
