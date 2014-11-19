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
        
        // HLAVNISKUPINY_PLU
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "HLAVNISKUPINY_PLU/")]
        List<HLAVNISKUPINY_PLUx> GetHLAVNISKUPINY_PLU();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "HLAVNISKUPINY_PLU/")]
        HLAVNISKUPINY_PLUx InsertHLAVNISKUPINY_PLU(HLAVNISKUPINY_PLUx plu);
        
        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "HLAVNISKUPINY_PLU/")]
        HLAVNISKUPINY_PLUx UpdateHLAVNISKUPINY_PLU(HLAVNISKUPINY_PLUx plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "HLAVNISKUPINY_PLU/{id}")]
        HLAVNISKUPINY_PLUx DeleteHLAVNISKUPINY_PLU(string id);

        // SKUPINY_PLU
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "SKUPINY_PLU/")]
        List<SKUPINY_PLUx> GetSKUPINY_PLU();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "SKUPINY_PLU/")]
        SKUPINY_PLUx InsertSKUPINY_PLUx(SKUPINY_PLUx plu);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "SKUPINY_PLU/")]
        SKUPINY_PLUx UpdateSKUPINY_PLUx(SKUPINY_PLUx plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "SKUPINY_PLU/{id}")]
        SKUPINY_PLUx DeleteSKUPINY_PLUx(string id);

        // PLU
        //-------------------------------------------------------------------------------------------------
        [OperationContract]
        [WebGet(UriTemplate = "PLU/")]
        List<PLUx> GetPLUs();

        [OperationContract]
        [WebInvoke(Method = "POST",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "PLU/")]
        PLUx InsertPLUx(PLUx plu);

        [OperationContract]
        [WebInvoke(Method = "PUT",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "PLU/")]
        PLUx UpdatePLUx(PLUx plu);

        [OperationContract]
        [WebInvoke(Method = "DELETE",
            RequestFormat = WebMessageFormat.Xml,
            ResponseFormat = WebMessageFormat.Xml,
            UriTemplate = "PLU/{id}")]

        PLUx DeletePLUx(string id);
    }

    // Use a data contract as illustrated in the sample below to add composite types to service operations.
    [DataContract(Namespace = "")]
    public class HLAVNISKUPINY_PLUx
    {
        [DataMember]
        public Guid id { get; set; }
        [DataMember]
        public string nazev { get; set; }
        [DataMember(IsRequired=false)]
        public DateTime timestamp { get; set; }
        [DataMember(IsRequired = false)]
        public Boolean deleted { get; set; }
    }

     [DataContract(Namespace = "")]
    public class SKUPINY_PLUx
    {
        [DataMember]
        public Guid id { get; set; }
        [DataMember]
        public string nazev { get; set; }
        [DataMember]
        public Guid hlavniskupina_id { get; set; }
        [DataMember]
        public DateTime timestamp { get; set; }
        [DataMember(IsRequired = false)]
        public Boolean deleted { get; set; }
    }

     [DataContract(Namespace = "")]
    public class PLUx
    {
        [DataMember]
        public Guid id { get; set; }
        [DataMember]
        public int ean { get; set; }
        [DataMember]
        public string nazev1 { get; set; }
        [DataMember]
        public string nazev2 { get; set; }
        [DataMember]
        public string nazev3 { get; set; }
        [DataMember]
        public decimal cena1 { get; set; }
        [DataMember]
        public decimal? cena2 { get; set; }
        [DataMember]
        public decimal? cena3 { get; set; }
        [DataMember]
        public Guid skupina_id { get; set; }
        [DataMember]
        public Guid? dph_id { get; set; }
        [DataMember]
        public Guid? zs_id { get; set; }
        [DataMember]
        public string text { get; set; }
        [DataMember]
        public short? status1 { get; set; }
        [DataMember]
        public short? status2 { get; set; }
        [DataMember]
        public int? halo_lalo { get; set; }
        [DataMember]
        public Guid? link_plu1_id { get; set; }
        [DataMember]
        public int? link_plu1_pocet { get; set; }
        [DataMember]
        public Guid? link_plu2_id { get; set; }
        [DataMember]
        public int? link_plu2_pocet { get; set; }
        [DataMember]
        public decimal? koeficient { get; set; }
        [DataMember]
        public int? objedvanka_typ { get; set; }
        [DataMember]
        public short? menu_vol { get; set; }
        [DataMember]
        public short? menu_pat { get; set; }
        [DataMember]
        public int? stastna_hodina { get; set; }
        [DataMember]
        public Guid? piktogram_id { get; set; }
        [DataMember]
        public int? stav { get; set; }
        [DataMember]
        public int? blokace { get; set; }
        [DataMember]
        public Guid? zdroj_id { get; set; }
        [DataMember]
        public int? zdroj_cislo { get; set; }
        [DataMember]
        public int? zdroj_sklad { get; set; }
        [DataMember]
        public DateTime timestamp { get; set; }
        [DataMember(IsRequired = false)]
        public Boolean deleted { get; set; }
    } 
}
