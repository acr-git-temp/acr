using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace RestService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    [ServiceContract]
    public interface IFetchService
    {
        [OperationContract]
        [WebInvoke(Method = "POST",
            UriTemplate = "DEBUG/")]
        String Debug(String s);
        
        // plumaingroup
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "plumaingroup/")]
        List<DCPluMainGroup> GetHLAVNISKUPINY_PLU();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plumaingroup/")]
        DCPluMainGroup InsertHLAVNISKUPINY_PLU(DCPluMainGroup plu);
        
        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plumaingroup/")]
        DCPluMainGroup UpdateHLAVNISKUPINY_PLU(DCPluMainGroup plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plumaingroup/{id}")]
        DCPluMainGroup DeleteHLAVNISKUPINY_PLU(string id);

        // SKUPINY_PLU
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "plugroup/")]
        List<DCPluGroup> GetSKUPINY_PLU();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plugroup/")]
        DCPluGroup InsertSKUPINY_PLUx(DCPluGroup plu);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plugroup/")]
        DCPluGroup UpdateSKUPINY_PLUx(DCPluGroup plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plugroup/{id}")]
        DCPluGroup DeleteSKUPINY_PLUx(string id);

        // PLU
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "plu/")]
        List<DCPlu> GetPLUs();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plu/")]
        DCPlu InsertPLUx(DCPlu plu);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plu/")]
        DCPlu UpdatePLUx(DCPlu plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "plu/{id}")]

        DCPlu DeletePLUx(string id);
    }

    // Use a data contract as illustrated in the sample below to add composite types to service operations.
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
