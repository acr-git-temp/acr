package com.serd.cashregister.rest.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Tomas on 12. 11. 2014.
 */
@Root(name="ArrayOfPlu", strict=false)
public class PLUs {
    @ElementList(required = false, inline=true, entry="Plu")
    public List<PLU> item;

}