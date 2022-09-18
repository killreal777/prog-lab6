package data.xml.model;

import subject.model.Organization;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.PriorityQueue;

@XmlRootElement(name = "collection")
public class CollectionRoot {
    private PriorityQueue<Organization> collection = new PriorityQueue<>();

    public CollectionRoot() {
    }

    @XmlElement(name = "organization")
    public PriorityQueue<Organization> getCollection() {
        return collection;
    }

    public void setCollection(PriorityQueue<Organization> collection) {
        this.collection = collection;
    }
}
