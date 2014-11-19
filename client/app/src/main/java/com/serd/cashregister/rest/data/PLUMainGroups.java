package com.serd.cashregister.rest.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Tomas on 12. 11. 2014.
 */
@Root(name="ArrayOfHLAVNISKUPINY_PLUx", strict=false)
public class PLUMainGroups {
    @ElementList(required = false, inline=true, entry="HLAVNISKUPINY_PLUx")
    public List<PLUMainGroup> item;

}