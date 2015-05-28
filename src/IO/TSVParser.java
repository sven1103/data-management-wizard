package IO;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by neukamm on 5/26/15.
 */
public class TSVParser {

    ArrayList<String[]> tsvContent;
    String[][] content;
    String[][] tsvContentTransposed;
    Communicator communicator;

    public TSVParser(String filepath, Communicator communicator) throws IOException{
        this.communicator = communicator;
        readTSV(filepath);
        makeAndTransposeMatrix();

    }



    /**
     * read the tsv file and store content in array
     *
     * @param filepath
     * @throws IOException
     */

    private void readTSV(String filepath) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String currLine = "";

        // arrayList to collect content of tsv file
        // initialized with first row (column names)
        this.tsvContent = new ArrayList<>();
        String[] columnNames = br.readLine().split("\t");
        this.tsvContent.add(columnNames);


        try {
            while ((currLine = br.readLine()) != null) {

                String[] currLineSplit = currLine.split("\t");
                String[] lineSplitExtended = new String[columnNames.length];

                for(int i = 0; i < currLineSplit.length; i++){
                    lineSplitExtended[i] = currLineSplit[i];
                }

                this.tsvContent.add(lineSplitExtended);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * create 2D matrix of tsv file content and transpose this matrix,
     * such that each row contains the content of one column of the
     * tsv file
     */
    private void makeAndTransposeMatrix() {

        this.content = new String[this.tsvContent.size()][this.tsvContent.get(0).length];

        for (int i = 0; i < this.tsvContent.size(); i++) {
            for (int j = 0; j < this.tsvContent.get(0).length; j++) {
                this.content[i][j] = this.tsvContent.get(i)[j];
            }
        }

        int n = this.tsvContent.size();
        int m = this.tsvContent.get(0).length;
        System.out.println(n);
        System.out.println(m);

        this.tsvContentTransposed = new String[m][n];

        for (int c = 0; c < n; c++) {
            for (int d = 0; d < m; d++) {
                this.tsvContentTransposed[d][c] = this.content[c][d];
            }
        }
    }

    /**
     * set information of ths tsv file
     * @throws IOException
     */

    public void setInformation() throws IOException {

        // set identifier
        this.communicator.setIdentifier(Arrays.asList(this.tsvContentTransposed[0]));
        // set sample type
        this.communicator.setSampleType(Arrays.asList(this.tsvContentTransposed[1]));
        //set experiment
        this.communicator.setExperiment(Arrays.asList(this.tsvContentTransposed[3]));
        // set secondary name
        this.communicator.setSecondaryName(Arrays.asList(this.tsvContentTransposed[4]));
        //set parent
        this.communicator.setParent(Arrays.asList(this.tsvContentTransposed[5]));
        // set primary tissue
        this.communicator.setPrimaryTissue(Arrays.asList(this.tsvContentTransposed[6]));
        // set tissue detailed
        this.communicator.setTissueDetailed(Arrays.asList(this.tsvContentTransposed[7]));
        // set ncbi id
        this.communicator.setNcbiOrganismID(Integer.parseInt(this.tsvContentTransposed[9][1]));
        // set species 
        this.communicator.setSpecies(getSpeciesByID(this.communicator.getNcbiOrganismID()));
        // set Q sample type
        this.communicator.setQsampleType(deleteDuplicates(Arrays.asList(this.tsvContentTransposed[10])));
        // set externamDB id
        this.communicator.setExternalDB_ID(Arrays.asList(this.tsvContentTransposed[11]));
        // set condition treatment
        this.communicator.setConditionTreatment(deleteDuplicates(Arrays.asList(this.tsvContentTransposed[12])));
        // set condition tissue
        this.communicator.setConditionTissue(deleteDuplicates(Arrays.asList(this.tsvContentTransposed[13])));
        // set number if individuals
        getNumberOfIndividuals(this.tsvContentTransposed[0]);


    }

    /**
     * get the number of individuals.
     * Parse array with identifier. Last part
     * of the id contains the mouse - number -> last 
     * one = total number of mice
     *
     * @param names
     */

    private void getNumberOfIndividuals(String[] names){
        String[] namesSplitted = names[1].split("-");
        String name = namesSplitted[0];

        for(int i = 2; i < names.length; i++){
            String[] namesSplitted2 = names[i].split("-");
            String name2 = namesSplitted2[0];
            if (!name2.equals(name)){
                communicator.setNumberOfIndividuals(Integer.parseInt(names[i-1].split("-")[1]));
                break;
            }
        }


    }

    /**
     * This method removes all double entries from a 
     * given list
     *
     * @param
     * @return list with just single entries
     */
    private List<String> deleteDuplicates(List<String> listWithDuplicates){

        List<String> noDublicates = new LinkedList<>();

        for(int i = 1; i < listWithDuplicates.size(); i++){
            if(!noDublicates.contains(listWithDuplicates.get(i))){
                noDublicates.add(listWithDuplicates.get(i));
            }
        }

        return noDublicates;

    }



    /**
     * This method runs the command line program "curl" to get 
     * the information of the species from ncbi DB.
     * Then, it writes the output into a xml file and 
     * parses this to get the species name of the ncbi ID 
     *
     * @param
     * @return species name as string
     * @throws IOException
     */
    private String getSpeciesByID(int id) throws IOException{
        String species=null;

        // run command line call to get XML file from
        // ncbi with information about the ncbiID of our species
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("curl http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=taxonomy&id=" + id);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedWriter xmlOutput = new BufferedWriter(new FileWriter("ncbiID.xml"));

        // read the output from the command and write it in xml file
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            xmlOutput.write(s);
        }
        xmlOutput.close();

        DOMParser domparser = new DOMParser();
        species = domparser.parse("ncbiID.xml");

        return species;
    }
}