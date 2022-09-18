package data.management;


import io.Terminal;
import subject.model.Organization;
import data.xml.model.CollectionInfo;
import data.xml.model.DataRoot;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;


/**
 * Class for managing data collection in object data.model form

 * Provides data collection and some tools for working with it
 */


public class DataManager {
    private final DataJaxbConverter jaxbConverter;
    private final IdGenerator idGenerator;
    private DataRoot dataRoot;


    public DataManager(Terminal terminal) {
        this.jaxbConverter = new DataJaxbConverter(terminal);
        this.idGenerator = new IdGenerator();
        loadData();
    }

    private void loadData() {
        try {
            this.dataRoot = jaxbConverter.readXml();
            idGenerator.loadIdInfo(dataRoot.getCollectionRoot().getCollection());
        } catch (IOException ignored) {}
    }


    /**
     * Save collection to data file
     */
    public void saveData() throws FileNotFoundException {
        try {
            jaxbConverter.writeXml(dataRoot);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    /**
     * Provide data collection
     */
    public PriorityQueue<Organization> getCollection() {
        return dataRoot.getCollectionRoot().getCollection();
    }

    /**
     * Provide collection information
     */
    public CollectionInfo getCollectionInfo() {
        return dataRoot.getCollectionInfo();
    }

    /**
     * Provide IdGenerator
     */
    public IdGenerator getIdGenerator() {
        return idGenerator;
    }
}
