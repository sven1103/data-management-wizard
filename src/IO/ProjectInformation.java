package IO;

/**
 * Created by neukamm on 6/5/15.
 */

import com.userSlides.AUserSlide;

import java.util.List;

/**
 * This class contains all information of the project, set by user or filtered out
 * of the tsv file at the beginning
 *
 */
public class ProjectInformation {


    private final List<AUserSlide> all_slides;
    private String projectName;
    private String species;
    private int numberOfIndividuals;
    private List<String> conditionTreatment;
    private List<String> sampleType;
    private List<String> primaryTissue;
    private List<String> tissueDetailed;
    private int ncbiOrganismID;
    private List<String> identifier;
    private List<String> experiment;
    private List<String> secondaryName;
    private List<String> parent;
    private List<String> externalDB_ID;
    private List<String> conditionTissue;
    private List<String> qsampleType;
    // most responsible person
    private String projectOwner;
    // short project description
    private String description;


    public ProjectInformation(List<AUserSlide> all_slides){
        
        this.all_slides = all_slides;

    }



    // getter and setter

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public List<String> getQsampleType() {
        return qsampleType;
    }

    public void setQsampleType(List<String> qsampleType) {
        this.qsampleType = qsampleType;
    }

    public List<String> getConditionTissue() {
        return conditionTissue;
    }

    public void setConditionTissue(List<String> conditionTissue) {
        this.conditionTissue = conditionTissue;
    }

    public List<String> getExternalDB_ID() {
        return externalDB_ID;
    }

    public void setExternalDB_ID(List<String> externalDB_ID) {
        this.externalDB_ID = externalDB_ID;
    }

    public List<String> getParent() {
        return parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public List<String> getSecondaryName() {
        return secondaryName;
    }

    public void setSecondaryName(List<String> secondaryName) {
        this.secondaryName = secondaryName;
    }

    public List<String> getExperiment() {
        return experiment;
    }

    public void setExperiment(List<String> experiment) {
        this.experiment = experiment;
    }

    public List<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<String> identifier) {
        this.identifier = identifier;
    }

    public int getNcbiOrganismID() {
        return ncbiOrganismID;
    }

    public void setNcbiOrganismID(int ncbiOrganismID) {
        this.ncbiOrganismID = ncbiOrganismID;
    }

    public List<String> getTissueDetailed() {
        return tissueDetailed;
    }

    public void setTissueDetailed(List<String> tissueDetailed) {
        this.tissueDetailed = tissueDetailed;
    }

    public List<String> getPrimaryTissue() {
        return primaryTissue;
    }

    public void setPrimaryTissue(List<String> primaryTissue) {
        this.primaryTissue = primaryTissue;
    }

    public List<String> getSampleType() {
        return sampleType;
    }

    public void setSampleType(List<String> sampleType) {
        this.sampleType = sampleType;
    }

    public List<String> getConditionTreatment() {
        return conditionTreatment;
    }

    public void setConditionTreatment(List<String> conditionTreatment) {
        this.conditionTreatment = conditionTreatment;
    }

    public int getNumberOfIndividuals() {
        return numberOfIndividuals;
    }

    public void setNumberOfIndividuals(int numberOfIndividuals) {
        this.numberOfIndividuals = numberOfIndividuals;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


}
