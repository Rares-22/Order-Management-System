package bll;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import dao.AbstractDAO;


public class AbstractBLL<Ty> {

    private final Class<Ty> type;
    private AbstractDAO<Ty> abstractDAO;

    /**
     * Uses parameterized class to execute generic methods.
     * @param type - type of the class
     */
    public AbstractBLL(Class<Ty> type) {
        this.type = type;
        abstractDAO = new AbstractDAO<Ty>(type);
    }

    /**
     * Searches for the entry with the given id in the corresponding database table.
     * @param id - searched id
     * @return reference to the found object
     * @throws NoSuchElementException if no entry is found
     */
    public Ty findById(int id) {
        Ty obj = abstractDAO.findById(id);
        if (obj == null) {
            throw new NoSuchElementException("The " + type.getSimpleName() + " with id=" + id + " was not found.");
        }
        return obj;
    }

    /**
     * Searches for all the entries in the corresponding database table.
     * @return List of all the table object entries
     * @throws NoSuchElementException if no entry is found
     */
    public List<Ty> findAll() {
        List<Ty> l = abstractDAO.findAll();
        if(l == null) {
            throw new NoSuchElementException("The " + type.getSimpleName() + " table is empty.");
        }
        return l;
    }

    /**
     * Inserts element into corresponding table.
     * @param ty - element to be inserted
     * @return - id of the inserted element, or -1 in case of failure
     */
    public int insert(Ty ty){
        return abstractDAO.insert(ty);
    }

    /**
     * Updates a given element into the corresponding table.
     * @param ty - element to be updated
     * @return - id of the updated element, or -1 in case of failure
     */
    public int update(Ty ty) {
        Ty searched = null;
        int id = -1;
        try {
            Field f = type.getDeclaredFields()[0];
            f.setAccessible(true);
            id = (int)f.get(ty);
            searched = this.findById(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if(searched == null) {
            throw new NoSuchElementException("The " + type.getSimpleName() + " with id=" + id + " was not found");
        }
        return abstractDAO.update(ty);
    }

    /**
     * Deletes all the entries from the corresponding table.
     * @return 0 if successful; -1 if unsuccessful
     */
    public int deleteEntries() {
        return abstractDAO.deleteEntries();
    }


    public void reportOrder() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        //String[][] tOrder = abstractDAO.findAll();
        //int rows = o.countRowsOrder();
        //counterreportO++;
        //String nameR = "ReportOrder" + counterreportO + ".pdf";
       // PdfWriter.getInstance(document, new FileOutputStream(nameR));
        document.open();
        PdfPTable table = new PdfPTable(4);
        //addTableHeaderProduct(table);
        //for (int i = 0; i < rows; i++) {
//            table.addCell(tOrder[i][0]);
//            table.addCell(tOrder[i][1]);
//            table.addCell(tOrder[i][2]);
//            table.addCell(tOrder[i][3]);
//            table.addCell(tOrder[i][4]);
//        }
//        document.add(table);
//        document.close();

    }

    private void addTableHeaderOrder(PdfPTable table) {
        String[] title = new String[10];
        title[0] = "idO";
        title[1] = "lName";
        title[2] = "fName";
        title[3] = "product";
        title[3] = "quantity";
        for (int i = 0; i < title.length - 2; i++) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(title[i]));
            table.addCell(header);
        }
    }

}
