package com.serd.cashregister.rest.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;


//@Root
@Root(name="Plu", strict = false)
@NamespaceList
public class PLU {

    public PLU(
            @Element(required = true, name = "id") String pId,
            @Element(required = true, name = "name1") String pName1,
            @Element(required = false, name = "name2") String pName2,
            @Element(required = false, name = "name3") String pName3,
            @Element(required = true, name = "plugroup_id") String pGroupId,
            @Element(required = false, name = "vat_id") String pVatId,
            @Element(required = false, name = "department_id") String pDepartmentId,
            @Element(required = true, name = "ean") Integer pEan,
            @Element(required = true, name = "price1") Float pPrice1,
            @Element(required = false, name = "price2") Float pPrice2,
            @Element(required = false, name = "price3") Float pPrice3,
            @Element(required = false, name = "note") String pNote,
            @Element(required = false, name = "status1") Integer pStatus1,
            @Element(required = false, name = "status2") Integer pStatus2,
            @Element(required = false, name = "halolalo") Integer pHalolalo,
            @Element(required = false, name = "linkplu1_id") String pLinkplu1Id,
            @Element(required = false, name = "linkplu1quantity") Integer pLinkplu1quantity,
            @Element(required = false, name = "linkplu2_id") String pLinkplu2Id,
            @Element(required = false, name = "linkplu2quantity") Integer pLinkplu2quantity,
            @Element(required = false, name = "coefficient") Float pCoefficient,
            @Element(required = false, name = "ordertype") Integer pOrdertype,
            @Element(required = false, name = "menuvol") Integer pMenuvol,
            @Element(required = false, name = "menupat") Integer pMenupat,
            @Element(required = false, name = "happyhour") Integer pHappyhour,
            @Element(required = false, name = "pictogram_id") String pPictogramId,
            @Element(required = false, name = "state") Integer pState,
            @Element(required = false, name = "blocked") Integer pBlocked,
            @Element(required = false, name = "source_id") String pSourceId,
            @Element(required = false, name = "sourcenumstock") Integer pSourcenumstock,
            @Element(required = false, name = "sourcestock") Integer pSourcestock,
            @Element(required = true, name = "timestamp") String pTimestamp,
            @Element(required = true, name = "deleted") String pDeleted) {
        id = pId;
        name1 = pName1;
        name2 = pName2;
        name3 = pName3;
        price1 = pPrice1;
        price2 = pPrice2;
        price3 = pPrice3;
        plugroup_id = pGroupId;
        vat_id = pVatId;
        department_id = pDepartmentId;
        ean = pEan;
        note = pNote;
        status1 = pStatus1;
        status2 = pStatus2;
        halolalo = pHalolalo;
        linkplu1_id = pLinkplu1Id;
        linkplu1quantity = pLinkplu1quantity;
        linkplu2_id = pLinkplu2Id;
        linkplu2quantity = pLinkplu2quantity;
        coefficient = pCoefficient;
        ordertype = pOrdertype;
        menuvol = pMenuvol;
        menupat = pMenupat;
        happyhour = pHappyhour;
        pictogram_id = pPictogramId;
        state = pState;
        blocked = pBlocked;
        source_id = pSourceId;
        sourcenumstock = pSourcenumstock;
        sourcestock = pSourcestock;
        timestamp = pTimestamp;
        deleted = pDeleted;
    }

    @Element(required = true, name = "id")
    private String id;
    @Element(required = true, name = "name1")
    private String name1;
    @Element(required = false, name = "name2")
    private String name2;
    @Element(required = false, name = "name3")
    private String name3;
    @Element(required = true, name = "plugroup_id")
    private String plugroup_id;
    @Element(required = false, name = "vat_id")
    private String vat_id;
    @Element(required = false, name = "department_id")
    private String department_id;
    @Element(required = true, name = "ean")
    private Integer ean;
    @Element(required = true, name = "price1")
    private Float price1;
    @Element(required = false, name = "price2")
    private Float price2;
    @Element(required = false, name = "price3")
    private Float price3;
    @Element(required = false, name = "note")
    private String note;
    @Element(required = false, name = "status1")
    private Integer status1;
    @Element(required = false, name = "status2")
    private Integer status2;
    @Element(required = false, name = "halolalo")
    private Integer halolalo;
    @Element(required = false, name = "linkplu1_id")
    private String linkplu1_id;
    @Element(required = false, name = "linkplu1quantity")
    private Integer linkplu1quantity;
    @Element(required = false, name = "linkplu2_id")
    private String linkplu2_id;
    @Element(required = false, name = "linkplu2quantity")
    private Integer linkplu2quantity;
    @Element(required = false, name = "coefficient")
    private Float coefficient;
    @Element(required = false, name = "ordertype")
    private Integer ordertype;
    @Element(required = false, name = "menuvol")
    private Integer menuvol;
    @Element(required = false, name = "menupat")
    private Integer menupat;
    @Element(required = false, name = "happyhour")
    private Integer happyhour;
    @Element(required = false, name = "pictogram_id")
    private String pictogram_id;
    @Element(required = false, name = "state")
    private Integer state;
    @Element(required = false, name = "blocked")
    private Integer blocked;
    @Element(required = false, name = "source_id")
    private String source_id;
    @Element(required = false, name = "sourcenumstock")
    private Integer sourcenumstock;
    @Element(required = false, name = "sourcestock")
    private Integer sourcestock;
    @Element(required = true, name = "timestamp")
    private String timestamp;
    @Element(required = true, name = "deleted")
    private String deleted;

    public String getId() {
        return this.id;
    }
    public String getPLUGroupId() {
        return this.plugroup_id;
    }
    public String getVatId() {
        return this.vat_id;
    }
    public String getDepartmentId() {
        return this.department_id;
    }
    public String getName1() {
        return this.name1;
    }
    public String getName2() {
        return this.name2;
    }
    public String getName3() {
        return this.name3;
    }
    public Float getPrice1() {
        return this.price1;
    }
    public Float getPrice2() {
        return this.price2;
    }
    public Float getPrice3() {
        return this.price3;
    }
    public Integer getEan() {
        return this.ean;
    }
    public String getNote(){
        return this.note;
    }
    public Integer getStatus1(){
        return this.status1;
    }
    public Integer getStatus2(){
        return this.status2;
    }
    public Integer getHalolalo(){
        return this.halolalo;
    }
    public String getLinkPlu1Id(){
        return this.linkplu1_id;
    }
    public Integer getLinkPlu1Quantity(){
        return this.linkplu1quantity;
    }
    public String getLinkPlu2Id(){
        return this.linkplu2_id;
    }
    public Integer getLinkPlu2Quantity(){
        return this.linkplu2quantity;
    }
    public Float getCoefficient(){
        return this.coefficient;
    }
    public Integer getOrderType(){
        return this.ordertype;
    }
    public Integer getMenuVol(){
        return this.menuvol;
    }
    public Integer getMenuPat(){
        return this.menupat;
    }
    public Integer getHappyHour(){
        return this.happyhour;
    }
    public String getPictogramId(){
        return this.pictogram_id;
    }
    public Integer getState(){
        return this.state;
    }
    public Integer getBlocked(){
        return this.blocked;
    }
    public String getSourceId(){
        return this.source_id;
    }
    public Integer getSourceNumStock(){
        return this.sourcenumstock;
    }
    public Integer getSourceStock(){
        return this.sourcestock;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public Boolean getDeleted() {
        return this.deleted.equals("true");
    }
}
