package IO;




import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Anchor;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;



public class PDFGenerator {

    //private String FILE = "c:/temp/FirstPdf.pdf";
    private final String FILE = "Report.pdf";
    private Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private Font subFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private Font italicFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC);

//    private ProjectInformation information;
//
//    public PDFGenerator(ProjectInformation information){
//        this.information = information;
//    }
    public PDFGenerator(){}

    public void writePDF(){
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
            writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
            // set header
            // HeaderFooter event = new HeaderFooter();
            // writer.setPageEvent(event);


            document.open();
            // metadata can be seen in the property-menu of the pdf file
            addMetaData(document);

            // add DMP Tool logo
            Image image1 = Image.getInstance("logo-uni-tuebingen.png");
            document.add(image1);
            // adding title page
            addTitlePage(document, writer);

            // Start a new page
            document.newPage();

            // adding all chapters
            addGeneralProjectInformation(document, 1);

            addRolesAndResponsibilities(document, 2);

            addContentManagement(document, 3);

            // meta data which are written in the report, not
            // just in menu bar
            addMetaData(document, 4);

            addDataStorageAndPreservation(document, 5);

            //addPoliciesForDataSharingAndPublicAccess(document, 4);
            //addDisseminationMethods(document, 5);
            //addDataFormatsAndStandards(document, 2);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void addContentManagement(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("Content Management", catFont);
        anchor.setName("Content");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        addEmptyLine(catPart, 2);
        catPart.add(new Paragraph("Datatype + Description for each setting"));



        // now add all this to the document
        document.add(catPart);

    }


    private void addMetaData(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("Metadata", catFont);
        anchor.setName("Content");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        addEmptyLine(catPart, 2);
        catPart.add(new Paragraph("Datatype + Description for each setting"));



        // now add all this to the document
        document.add(catPart);

    }


    // iText allows to add metadata to the PDF which can be viewed in your Adobe Reader
    // under File -> Properties
    private void addMetaData(Document document) {
        document.addTitle("Data Management Plan");
        document.addSubject("Using data management tool 'The better one'");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("...");
        document.addCreator("...");
    }




    private void addTitlePage(Document document, PdfWriter writer) throws DocumentException {

        Paragraph title = new Paragraph();
        // centered text
        //title.setAlignment(Element.ALIGN_CENTER);
        // We add one empty line
        addEmptyLine(title, 5);
        // Lets write a big header with project name
        title.add(new Paragraph("PROJECT TITLE", catFont));

        addEmptyLine(title, 1);

        PdfContentByte cb = writer.getDirectContent();

        cb.setLineWidth(2.0f); // Make a bit thicker than 1.0 default
        cb.setGrayStroke(0.5f); // 1 = black, 0 = white
        cb.moveTo(500, 670);
        cb.lineTo(20, 670);
        cb.stroke();

        addEmptyLine(title, 1);
        // Will create: Report generated by: _name,
        title.add(new Paragraph("A Data Management Plan generated by the 'The better DMP Tool'", smallBold));
        addEmptyLine(title, 1);

        PdfContentByte cb1 = writer.getDirectContent();

        cb1.setLineWidth(2.0f); // Make a bit thicker than 1.0 default
        cb1.setGrayStroke(0.5f); // 1 = black, 0 = white
        cb1.moveTo(500, 500);
        cb1.lineTo(20, 500);
        cb1.stroke();

        addEmptyLine(title, 2);

        Paragraph upperPartTitle = new Paragraph();
        upperPartTitle.add(new Paragraph("Responsible project manager: " + " Judith Neukamm ", smallBold));
        upperPartTitle.add(new Paragraph("Affiliation: University of Tuebingen", smallBold));
        upperPartTitle.add(new Paragraph("Last modified: " + new Date(), smallBold));
        upperPartTitle.add(new Paragraph("Copyright: The amazing THREE", smallBold));
        addEmptyLine(upperPartTitle, 3);
        upperPartTitle.add(new Paragraph("Project Description ", smallBold));

        addEmptyLine(upperPartTitle, 8);

        upperPartTitle.add(new Paragraph("This data management plan was produced by the Data Management Tool ......", italicFont));

        document.add(title);
        document.add(upperPartTitle);

    }

    private void addDataFormatsAndStandards(Document document, int chapterNumber) throws DocumentException {

        Anchor anchor = new Anchor("Data collected, Formats and Standards", catFont);
        anchor.setName("Data");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        //createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);


    }




    private void addRolesAndResponsibilities(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("Roles and Responsibilities", catFont);
        anchor.setName("Roles");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        Paragraph subPara = new Paragraph("Responsible persons", subFont);
        addEmptyLine(catPart, 1);
        Section subCatPart = catPart.addSection(subPara);
        addEmptyLine(subPara, 2);
        // create table with responsible persons
        subCatPart.add(new Paragraph("As table ...... "));
        addEmptyLine(subCatPart, 1);
        PdfPTable table = new PdfPTable(2);
        PdfPCell c1 = new PdfPCell(new Phrase("Role type"));
        // TODO
        // c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Person in charge"));
        // TODO
        // c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        // cell 1,1
        table.addCell("Role");
        // cell 1,2
        table.addCell("Person");
        // cell 2,1
        table.addCell("Role");
        // cell 2,2
        table.addCell("Person");
        // cell 3,1
        table.addCell("Role");
        // cell 3,2
        table.addCell("Person");

        subCatPart.add(table);

        addEmptyLine(subCatPart, 2);
        subCatPart.add(new Paragraph(".... or text: "));
        addEmptyLine(subCatPart, 1);
        subCatPart.add(new Paragraph("Data owners: "));
        subCatPart.add(new Paragraph("Data managers: "));
        subCatPart.add(new Paragraph("Data contributors: "));
        subCatPart.add(new Paragraph("Data researchers: "));



        // now add all this to the document

        document.add(catPart);


    }







    private void addPoliciesForDataSharingAndPublicAccess(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("Policies for Data Sharing and Public Access", catFont);
        anchor.setName("policies");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);


    }



    private void addDisseminationMethods(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("Dissemination Methods", catFont);
        anchor.setName("dissemination");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);


    }



    private void addDataStorageAndPreservation(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("Data Storage and Preservation", catFont);
        anchor.setName("dataStorage");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message blubb"));

        // now add all this to the document
        document.add(catPart);


    }

    private void addGeneralProjectInformation(Document document, int chapterNumber) throws DocumentException {
        Anchor anchor = new Anchor("General project information", catFont);
        anchor.setName("information");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), chapterNumber);
        addEmptyLine(catPart, 1);


        Paragraph subPara = new Paragraph("Contact", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Institute : \t" ));
        subCatPart.add(new Paragraph("Street : \t" ));
        subCatPart.add(new Paragraph("ZIP-Code : \t"));
        subCatPart.add(new Paragraph("City : \t"));
        subCatPart.add(new Paragraph("Country : \t" ));
        subCatPart.add(new Paragraph("Person in charge : \t" ));

        subPara = new Paragraph("Project information", subFont);
        addEmptyLine(catPart, 1);
        subCatPart = catPart.addSection(subPara);

        subCatPart.add(new Paragraph("Species: ...." + " (NCBI ID: " + "...." + " )"));
        subCatPart.add(new Paragraph("Number of individuals: "));
        subCatPart.add(new Paragraph("Sample types: "));


        // now add all this to the document
        document.add(catPart);


    }



    private void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }


    /**
     *  Add empty lines to given part of the document
     *
     * @param paragraph, chapter, sub chapter
     * @param number of empty lines
     */
    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addEmptyLine(Chapter chapter, int number) {
        for (int i = 0; i < number; i++) {
            chapter.add(new Paragraph(" "));
        }
    }

    private void addEmptyLine(Section subCatPart, int number) {
        for (int i = 0; i < number; i++) {
            subCatPart.add(new Paragraph(" "));
        }
    }


/*
    *//*
    static class HeaderFooter extends PdfPageEventHelper {

        public void onEndPage (PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            // Header
//            ColumnText.showTextAligned(writer.getDirectContent(),
//                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", writer.getPageNumber())),
//                    (rect.getLeft() + rect.getRight()) / 2, rect.getTop() - 18, 0);

            // Footer with page number
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", writer.getPageNumber())),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
        }
    }
   */

}