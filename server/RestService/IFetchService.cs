using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace RestService
{
    //**************************************************************************************************
    //**  SERVICE CONTRACTS                                                                              **
    //**************************************************************************************************

    [ServiceContract]
    public interface IFetchService
    {
        // plumaingroup
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "plumaingroup/")]
        List<DCPluMainGroup> GetPluMainGroup();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plumaingroup/")]
        DCPluMainGroup InsertPluMainGroup(DCPluMainGroup plu);
        
        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plumaingroup/")]
        DCPluMainGroup UpdatePluMainGroup(DCPluMainGroup plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plumaingroup/{id}")]
        DCPluMainGroup DeletePluMainGroup(string id);

        // plugroup
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "plugroup/")]
        List<DCPluGroup> GetPluGroup();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plugroup/")]
        DCPluGroup InsertPluGroup(DCPluGroup plu);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plugroup/")]
        DCPluGroup UpdatePluGroup(DCPluGroup plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plugroup/{id}")]
        DCPluGroup DeletePluGroup(string id);

        // plu
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "plu/")]
        List<DCPlu> GetPlu();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plu/")]
        DCPlu InsertPlu(DCPlu plu);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plu/")]
        DCPlu UpdatePlu(DCPlu plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plu/{id}")]
        DCPlu DeletePlu(string id);
    }


    //**************************************************************************************************
    //**  DATA CONTRACTS                                                                              **
    //**************************************************************************************************

    // DCPluMainGroup
    //-------------------------------------------------------------------------------------------------
    [DataContract(Namespace = "", Name = "PluMainGroup")]
    public class DCPluMainGroup
    {
        [DataMember]
        public Guid id { get; set; }
        [DataMember]
        public string name { get; set; }
        [DataMember(IsRequired=false)]
        public DateTime timestamp { get; set; }
        [DataMember(IsRequired = false)]
        public Boolean deleted { get; set; }
    }

    // DCPluGroup
    //-------------------------------------------------------------------------------------------------
    [DataContract(Namespace = "", Name = "PluGroup")]
    public class DCPluGroup
    {
        [DataMember]
        public Guid id { get; set; }
        [DataMember]
        public string name { get; set; }
        [DataMember]
        public Guid plumaingroup_id { get; set; }
        [DataMember]
        public DateTime timestamp { get; set; }
        [DataMember(IsRequired = false)]
        public Boolean deleted { get; set; }
    }

    // DCPlu
    //-------------------------------------------------------------------------------------------------
    [DataContract(Namespace = "", Name = "Plu")]
    public class DCPlu
    {
        [DataMember]
        public Guid id { get; set; }
        [DataMember]
        public int ean { get; set; }
        [DataMember]
        public string name1 { get; set; }
        [DataMember]
        public string name2 { get; set; }
        [DataMember]
        public string name3 { get; set; }
        [DataMember]
        public decimal price1 { get; set; }
        [DataMember]
        public decimal? price2 { get; set; }
        [DataMember]
        public decimal? price3 { get; set; }
        [DataMember]
        public Guid plugroup_id { get; set; }
        [DataMember]
        public Guid? vat_id { get; set; }
        [DataMember]
        public Guid? department_id { get; set; }
        [DataMember]
        public string text { get; set; }
        [DataMember]
        public short? status1 { get; set; }
        [DataMember]
        public short? status2 { get; set; }
        [DataMember]
        public int? halolalo { get; set; }
        [DataMember]
        public Guid? linkplu1_id { get; set; }
        [DataMember]
        public int? linkplu1quantity { get; set; }
        [DataMember]
        public Guid? linkplu2_id { get; set; }
        [DataMember]
        public int? linkplu2quantity { get; set; }
        [DataMember]
        public decimal? coefficient { get; set; }
        [DataMember]
        public int? ordertype { get; set; }
        [DataMember]
        public short? menuvol { get; set; }
        [DataMember]
        public short? menupat { get; set; }
        [DataMember]
        public int? happyhour { get; set; }
        [DataMember]
        public Guid? pictogram_id { get; set; }
        [DataMember]
        public int? state { get; set; }
        [DataMember]
        public int? blocked { get; set; }
        [DataMember]
        public Guid? source_id { get; set; }
        [DataMember]
        public int? sourcenumstock { get; set; }
        [DataMember]
        public int? sourcestock { get; set; }
        [DataMember]
        public DateTime timestamp { get; set; }
        [DataMember(IsRequired = false)]
        public Boolean deleted { get; set; }
    } 
}
