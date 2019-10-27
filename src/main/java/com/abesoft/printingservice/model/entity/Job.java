package com.abesoft.printingservice.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author b.walid
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "job")
public class Job extends BaseEntity {

    public enum Status {
        STARTED("En cours"), STOPPED("Arrêté"), FAILED("Echoué"), COMPLETED("Terminé avec succès"), COMPLETED_ERROR("Terminé avec erreurs"), IN_QUEUE("En file d'attente"), CANCELED("Annulé");
        private String name;

        private Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    /**
     * État du job
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    /**
     * Nom du job
     */
    @Column(name = "name", nullable = true)
    private String name;
    /**
     * contenu du fichier des paramètres de la génération TODO à sauvgarder
     * comme objet json (problème de taille de String)
     */

    @Column(columnDefinition = "TEXT", name = "json", nullable = true)
    private String json;
    /**
     * Nom/path du pdf/rapport qui sera produit dans la fin normal du job
     */
    @Column(name = "name_pdf", nullable = true)
    private String namePdf;

    /**
     * date de debut de lancement du job
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    /**
     * date de fin du job
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private PdfType pdfType;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getNamePdf() {
        return namePdf;
    }

    public void setNamePdf(String namePdf) {
        this.namePdf = namePdf;
    }

    public PdfType getPdfType() {
        return pdfType;
    }

    public void setPdfType(PdfType pdfType) {
        this.pdfType = pdfType;
    }

    @Override
    public String toString() {
        return "Job{" + "status=" + status + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }

}
