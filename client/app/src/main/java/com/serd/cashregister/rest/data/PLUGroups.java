package com.serd.cashregister.rest.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Tomas on 12. 11. 2014.
 */
@Root(name="ArrayOfSKUPINY_PLUx", strict=false)
public class PLUGroups {
    @ElementList(required = false, inline=true, entry="SKUPINY_PLUx")
    public List<PLUGroup> item;

}