package com.serd.cashregister.rest.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by Admin on 20.10.14.
 */

/*
<HLAVNISKUPINY_PLUx>
<id>ff1d55f6-28ff-4f75-95c7-d3276335173f</id>
<nazev>Napoje</nazev>
<timestamp>2014-10-29T08:46:11.253</timestamp>
</HLAVNISKUPINY_PLUx>
*/
//@Root
@Root(name="HLAVNISKUPINY_PLUx", strict = false)
@NamespaceList
public class PLUMainGroup {

    public PLUMainGroup(@Element (name = "id") String pId, @Element (name = "nazev") String pNazev, @Element (name = "timestamp") String pTimestamp, @Element (name = "deleted") String pDeleted) {
        id = pId;
        nazev = pNazev;
        timestamp = pTimestamp;
        deleted = pDeleted;
    }

    @Element(required = true, name = "id")
    private String id;
    @Element(required = true, name = "nazev")
    private String nazev;
    @Element(required = true, name = "timestamp")
    private String timestamp;
    @Element(required = true, name = "deleted")
    private String deleted;

    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.nazev;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public Boolean getDeleted() {
        return this.deleted.equals("true");
    }
}
