package IO;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by neukamm on 5/26/15.
 */

public class Communicator {

    // Attributes
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


    public Communicator(String filepath) throws IOException{

        if(filepath==""){
            generateDefaultCommunicator();
        } else {
            // generate communicator with parsed values from tsv file
            TSVParser tsvParser = new TSVParser(filepath, this);
            tsvParser.setInformation();


        }
    }


    /**
     * generate communicator with default values
     */
    private void generateDefaultCommunicator(){



    }



    /**
     * Getter and Setter
     */


    public int getNumberOfIndividuals() {
        return numberOfIndividuals;
    }

    public void setNumberOfIndividuals(int numberOfIndividuals) {
        this.numberOfIndividuals = numberOfIndividuals;
    }

    public List<String> getConditionTreatment() {
        return conditionTreatment;
    }

    public void setConditionTreatment(List<String> conditionTreatment) {
        this.conditionTreatment = conditionTreatment;
    }

    public List<String> getSampleType() {
        return sampleType;
    }

    public void setSampleType(List<String> sampleType) {
        this.sampleType = sampleType;
    }

    public List<String> getPrimaryTissue() {
        return primaryTissue;
    }

    public void setPrimaryTissue(List<String> primaryTissue) {
        this.primaryTissue = primaryTissue;
    }

    public List<String> getTissueDetailed() {
        return tissueDetailed;
    }

    public void setTissueDetailed(List<String> tissueDetailed) {
        this.tissueDetailed = tissueDetailed;
    }

    public int getNcbiOrganismID() {
        return ncbiOrganismID;
    }

    public void setNcbiOrganismID(int ncbiOrganismID) {
        this.ncbiOrganismID = ncbiOrganismID;
    }

    public List<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<String> identifier) {
        this.identifier = identifier;
    }

    public List<String> getExperiment() {
        return experiment;
    }

    public void setExperiment(List<String> experiment) {
        this.experiment = experiment;
    }

    public List<String> getSecondaryName() {
        return secondaryName;
    }

    public void setSecondaryName(List<String> secondaryName) {
        this.secondaryName = secondaryName;
    }

    public List<String> getParent() {
        return parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public List<String> getExternalDB_ID() {
        return externalDB_ID;
    }

    public void setExternalDB_ID(List<String> externalDB_ID) {
        this.externalDB_ID = externalDB_ID;
    }

    public List<String> getConditionTissue() {
        return conditionTissue;
    }

    public void setConditionTissue(List<String> conditionTissue) {
        this.conditionTissue = conditionTissue;
    }

    public List<String> getQsampleType() {
        return qsampleType;
    }

    public void setQsampleType(List<String> qsampleType) {
        this.qsampleType = qsampleType;
    }
}