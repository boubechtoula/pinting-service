package com.abesoft.printingservice.managers;

import com.abesoft.printingservice.model.entity.Job;
import com.abesoft.printingservice.services.JobsService;
import com.abesoft.printingservice.utils.PSConst;
import com.abesoft.printingservice.utils.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author b.walid
 */
@Named
@ViewScoped
public class JobManager implements Serializable {

    @Autowired
    JobsService jobsService;

    private Job selectedJob;

    public File hasFile(Job job) {
        if (Util.checkMandatory(job.getNamePdf())) {
            String pathToPdf = job.getPdfType().getRessourcesFolderName() + File.separator + PSConst.PDFS_FOLDER_NAME + File.separator + job.getNamePdf();
            return new File(pathToPdf);
        }
        return null;
    }

    public StreamedContent downloadFile() throws FileNotFoundException {
        File f = hasFile(selectedJob);
        if (f != null) {
            InputStream stream = new FileInputStream(f);
            StreamedContent file = new DefaultStreamedContent(stream, "application/pdf", f.getName());
            return file;
        }
        return null;
    }

    public List<Job> getAllJobs() {
        return jobsService.listJobs();
    }

    public Job getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Job selectedJob) {
        this.selectedJob = selectedJob;
    }
}
