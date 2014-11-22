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
@Root(name="PluGroup", strict = false)
@NamespaceList
public class PLUGroup {

    public PLUGroup(@Element (name = "id") String pId, @Element (name = "plumaingroup_id") String pMainGroupId, @Element (name = "name") String pName, @Element (name = "timestamp") String pTimestamp, @Element (name = "deleted") String pDeleted) {
        id = pId;
        name = pName;
        timestamp = pTimestamp;
        deleted = pDeleted;
        plumaingroup_id = pMainGroupId;
    }

    @Element(required = true, name = "id")
    private String id;
    @Element(required = true, name = "name")
    private String name;
    @Element(required = true, name = "plumaingroup_id")
    private String plumaingroup_id;
    @Element(required = true, name = "timestamp")
    private String timestamp;
    @Element(required = true, name = "deleted")
    private String deleted;

    public String getId() {
        return this.id;
    }

    public String getPLUMainGroupId() {
        return this.plumaingroup_id;
    }

    public String getContent() {
        return this.name;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public Boolean getDeleted() {
        return this.deleted.equals("true");
    }
}
