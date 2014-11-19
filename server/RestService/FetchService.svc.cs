//#define DO_NOT_UPDATE_DATABASE

using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data;

namespace RestService
{

    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    public class FetchService : IFetchService
    {
        StringBuilder builder = new StringBuilder();

        public String Debug(String s)
        {
            Console.WriteLine(s);
            return "abc";
        }

        DateTime GetHLAVNISKUPINY_PLUTimestamp(Guid id)
        {
            SERDEntities context = new SERDEntities();
            HLAVNISKUPINY_PLU obj = context.HLAVNISKUPINY_PLU.Where(s => s.id == id).FirstOrDefault<HLAVNISKUPINY_PLU>();
            return obj.timestamp;
        }

        public List<HLAVNISKUPINY_PLUx> GetHLAVNISKUPINY_PLU()
        {
            Console.WriteLine(DateTime.Now.ToString() + " Get HLAVNISKUPINY_PLU");

            using (var context = new SERDEntities())
            {
                var data = (from t in context.HLAVNISKUPINY_PLU
                            select new HLAVNISKUPINY_PLUx
                            {
                                id = t.id,
                                nazev = t.nazev,
                                timestamp = t.timestamp,
                                deleted = t.deleted
                            }).ToList();
                return data;
            }
        }

        public HLAVNISKUPINY_PLUx InsertHLAVNISKUPINY_PLU(HLAVNISKUPINY_PLUx src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Insert HLAVNISKUPINY_PLU\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.nazev).ToString());

            HLAVNISKUPINY_PLU obj;
            {
                SERDEntities context = new SERDEntities();

                obj = new HLAVNISKUPINY_PLU
                {
                    id = src.id,
                    nazev = src.nazev,
                    timestamp = src.timestamp,
                    deleted = src.deleted
                };

#if (!DO_NOT_UPDATE_DATABASE) 
                context.AddToHLAVNISKUPINY_PLU(obj);
                context.SaveChanges();
#endif
            }

            return src;
        }

        public HLAVNISKUPINY_PLUx UpdateHLAVNISKUPINY_PLU(HLAVNISKUPINY_PLUx src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Update HLAVNISKUPINY_PLU\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.nazev).ToString());

            HLAVNISKUPINY_PLU obj;
            using (var context = new SERDEntities())
            {
                obj = context.HLAVNISKUPINY_PLU.Where(s => s.id == src.id).FirstOrDefault<HLAVNISKUPINY_PLU>();
                if (obj != null)
                {
#if (!DO_NOT_UPDATE_DATABASE)
                    obj.nazev = src.nazev;
                    obj.timestamp = src.timestamp;
                    obj.deleted = src.deleted;
                    context.SaveChanges();
#endif
                }
                else
                {
                    obj = new HLAVNISKUPINY_PLU
                    {
                        id = src.id,
                        nazev = src.nazev,
                        timestamp = src.timestamp,
                        deleted = src.deleted
                    };

#if (!DO_NOT_UPDATE_DATABASE)
                    context.AddToHLAVNISKUPINY_PLU(obj);
                    context.SaveChanges();
#endif
                }
            }

            return src;
        }

        public HLAVNISKUPINY_PLUx DeleteHLAVNISKUPINY_PLU(string id)
        {
            HLAVNISKUPINY_PLUx result = new HLAVNISKUPINY_PLUx();
            
            HLAVNISKUPINY_PLU obj;
            using (var context = new SERDEntities())
            {
                Guid idAsGuid = new Guid(id);
                obj = context.HLAVNISKUPINY_PLU.Where(s => s.id == idAsGuid).FirstOrDefault<HLAVNISKUPINY_PLU>();
                if (obj != null)
                {
                    Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Delete HLAVNISKUPINY_PLU\n\t\t\tID: {0}\n\t\t\tTitle: {1}", obj.id.ToString(), obj.nazev).ToString());

#if (!DO_NOT_UPDATE_DATABASE) 
                    context.DeleteObject(obj);
                    context.SaveChanges();
#endif
                }
            }

            result.id = obj != null ? obj.id : Guid.Empty;
            result.nazev = obj != null ? obj.nazev : "";
            return result;
        }

        DateTime GetSKUPINY_PLUTimestamp(Guid id)
        {
            SERDEntities context = new SERDEntities();
            SKUPINY_PLU obj = context.SKUPINY_PLU.Where(s => s.id == id).FirstOrDefault<SKUPINY_PLU>();
            return obj.timestamp;
        }

        public List<SKUPINY_PLUx> GetSKUPINY_PLU()
        {
            Console.WriteLine(DateTime.Now.ToString() + " Get SKUPINY_PLU");

            using (var context = new SERDEntities())
            {
                var data = (from t in context.SKUPINY_PLU
                            select new SKUPINY_PLUx
                            {
                                id = t.id,
                                nazev = t.nazev,
                                hlavniskupina_id = t.HLAVNISKUPINY_PLU.id,
                                timestamp = t.timestamp,
                                deleted = t.deleted
                            }).ToList();
                return data;
            }
        }

        public SKUPINY_PLUx InsertSKUPINY_PLUx(SKUPINY_PLUx src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Insert SKUPINY_PLU\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.nazev).ToString());

            SKUPINY_PLU obj;
            {
                SERDEntities context = new SERDEntities();

                obj = new SKUPINY_PLU
                {
                    id = src.id,
                    nazev = src.nazev,
                    timestamp = src.timestamp,
                    deleted = src.deleted
                };

                obj.HLAVNISKUPINY_PLUReference.EntityKey = new System.Data.EntityKey("SERDEntities.HLAVNISKUPINY_PLU", "id", src.hlavniskupina_id);
#if (!DO_NOT_UPDATE_DATABASE)
                context.AddToSKUPINY_PLU(obj);
                context.SaveChanges();
#endif
            }

            return src;
        }

        public SKUPINY_PLUx UpdateSKUPINY_PLUx(SKUPINY_PLUx src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Update SKUPINY_PLU\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.nazev).ToString());

            SKUPINY_PLU obj;
            using (var context = new SERDEntities())
            {
                obj = context.SKUPINY_PLU.Where(s => s.id == src.id).FirstOrDefault<SKUPINY_PLU>();
                if (obj != null)
                {
#if (!DO_NOT_UPDATE_DATABASE)
                    obj.nazev = src.nazev;
                    obj.timestamp = src.timestamp;
                    obj.deleted = src.deleted;
                    obj.HLAVNISKUPINY_PLUReference.EntityKey = new System.Data.EntityKey("SERDEntities.HLAVNISKUPINY_PLU", "id", src.hlavniskupina_id);
                    context.SaveChanges();
#endif
                }
                else
                {
                    obj = new SKUPINY_PLU
                    {
                        id = src.id,
                        nazev = src.nazev,
                        timestamp = src.timestamp,
                        deleted = src.deleted
                    };

                    obj.HLAVNISKUPINY_PLUReference.EntityKey = new System.Data.EntityKey("SERDEntities.HLAVNISKUPINY_PLU", "id", src.hlavniskupina_id);
#if (!DO_NOT_UPDATE_DATABASE)
                    context.AddToSKUPINY_PLU(obj);
                    context.SaveChanges();
#endif
                }

            }

            return src;
        }

        public SKUPINY_PLUx DeleteSKUPINY_PLUx(string id)
        {
            SKUPINY_PLUx result = new SKUPINY_PLUx();

            SKUPINY_PLU obj;
            using (var context = new SERDEntities())
            {
                Guid idAsGuid = new Guid(id);
                obj = context.SKUPINY_PLU.Where(s => s.id == idAsGuid).FirstOrDefault<SKUPINY_PLU>();
                if (obj != null)
                {
                    Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Delete SKUPINY_PLU\n\t\t\tID: {0}\n\t\t\tTitle: {1}", obj.id.ToString(), obj.nazev).ToString());

#if (!DO_NOT_UPDATE_DATABASE)
                    context.DeleteObject(obj);
                    context.SaveChanges();
#endif
                }
            }

            result.id = obj != null ? obj.id : Guid.Empty;
            result.nazev = obj != null ? obj.nazev : "";
            return result;
        }

        DateTime GetPLUTimestamp(Guid id)
        {
            SERDEntities context = new SERDEntities();
            PLU obj = context.PLU.Where(s => s.id == id).FirstOrDefault<PLU>();
            return obj.timestamp;
        }

        public List<PLUx> GetPLUs()
        {
            Console.WriteLine(DateTime.Now.ToString() + " Get PLU");

            using (var context = new SERDEntities())
            {
                var data = (from t in context.PLU
                            select new PLUx
                            {
                                id = t.id, ean = t.ean, nazev1 = t.nazev1, nazev2 = t.nazev2, nazev3 = t.nazev3,
                                cena1 = t.cena1, cena2 = t.cena2, cena3 = t.cena3, 
                                skupina_id = t.SKUPINY_PLU.id, dph_id = t.dph_id, zs_id = t.zs_id, text = t.text,
                                status1 = t.status1, status2 = t.status2, halo_lalo = t.halo_lalo,
                                link_plu1_id = t.link_plu1_id, link_plu1_pocet = t.link_plu1_pocet,
                                link_plu2_id = t.link_plu2_id, link_plu2_pocet = t.link_plu2_pocet,
                                koeficient = t.koeficient, objedvanka_typ = t.objednavka_typ,
                                menu_vol = t.menu_vol, menu_pat = t.menu_pat, stastna_hodina = t.stastna_hodina,
                                piktogram_id = t.piktogram_id, stav = t.stav, blokace = t.blokace,
                                zdroj_id = t.zdroj_id, zdroj_cislo = t.zdroj_cislo, zdroj_sklad = t.zdroj_sklad,
                                timestamp = t.timestamp, deleted = t.deleted
                            }).ToList();
                return data;
            }
        }

        public PLUx InsertPLUx(PLUx src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Insert PLU\n\tID: {0}\n\tTitle: {1}", src.id.ToString(), src.nazev1).ToString());
            
            PLU obj;
            {
                SERDEntities context = new SERDEntities();

                obj = new PLU
                {
                    id = src.id,
                    ean = src.ean,
                    nazev1 = src.nazev1, nazev2 = src.nazev2, nazev3 = src.nazev3,
                    cena1 = src.cena1, cena2 = src.cena2, cena3 = src.cena3, dph_id = src.dph_id, 
                    zs_id = src.zs_id, text = src.text, status1 = src.status1, status2 = src.status2, halo_lalo = src.halo_lalo, 
                    link_plu1_id = src.link_plu1_id, link_plu1_pocet = src.link_plu1_pocet, 
                    link_plu2_id = src.link_plu2_id, link_plu2_pocet = src.link_plu2_pocet,
                    koeficient = src.koeficient, objednavka_typ = src.objedvanka_typ, menu_vol = src.menu_vol, menu_pat = src.menu_pat,
                    stastna_hodina = src.stastna_hodina, piktogram_id = src.piktogram_id, stav = src.stav, blokace = src.blokace,
                    zdroj_id = src.zdroj_id, zdroj_cislo = src.zdroj_cislo, zdroj_sklad = src.zdroj_sklad,
                    timestamp = src.timestamp, deleted = src.deleted
                };

                obj.SKUPINY_PLUReference.EntityKey = new System.Data.EntityKey("SERDEntities.SKUPINY_PLU", "id", src.skupina_id);
#if (!DO_NOT_UPDATE_DATABASE) 
                context.AddToPLU(obj);
                context.SaveChanges();
#endif
            }

            return src;
        }

        public PLUx UpdatePLUx(PLUx src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Update PLU\n\tID: {0}\n\tTitle: {1}", src.id.ToString(), src.nazev1).ToString());

            PLU obj;
            using (var context = new SERDEntities())
            {
                obj = context.PLU.Where(s => s.id == src.id).FirstOrDefault<PLU>();
                if (obj != null)
                {
#if (!DO_NOT_UPDATE_DATABASE) 
                    obj.ean = src.ean;
                    obj.nazev1 = src.nazev1; obj.nazev2 = src.nazev2; obj.nazev3 = src.nazev3;
                    obj.cena1 = src.cena1; obj.cena2 = src.cena2; obj.cena3 = src.cena3; obj.dph_id = src.dph_id; 
                    obj.zs_id = src.zs_id; obj.text = src.text; obj.status1 = src.status1; obj.status2 = src.status2; obj.halo_lalo = src.halo_lalo; 
                    obj.link_plu1_id = src.link_plu1_id; obj.link_plu1_pocet = src.link_plu1_pocet; 
                    obj.link_plu2_id = src.link_plu2_id; obj.link_plu2_pocet = src.link_plu2_pocet;
                    obj.koeficient = src.koeficient; obj.objednavka_typ = src.objedvanka_typ; obj.menu_vol = src.menu_vol; obj.menu_pat = src.menu_pat;
                    obj.stastna_hodina = src.stastna_hodina; obj.piktogram_id = src.piktogram_id; obj.stav = src.stav; obj.blokace = src.blokace;
                    obj.zdroj_id = src.zdroj_id; obj.zdroj_cislo = src.zdroj_cislo; obj.zdroj_sklad = src.zdroj_sklad;
                    obj.SKUPINY_PLUReference.EntityKey = new System.Data.EntityKey("SERDEntities.SKUPINY_PLU", "id", src.skupina_id);
                    context.SaveChanges();
#endif
                }
            }

            return src;
        }

        public PLUx DeletePLUx(string id)
        {
            PLUx result = new PLUx();

            PLU obj;
            using (var context = new SERDEntities())
            {
                Guid idAsGuid = new Guid(id);
                obj = context.PLU.Where(s => s.id == idAsGuid).FirstOrDefault<PLU>();
                if (obj != null)
                {
                    Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Delete PLU\n\tID: {0}\n\tTitle: {1}", obj.id.ToString(), obj.nazev1).ToString());

#if (!DO_NOT_UPDATE_DATABASE)
                    context.DeleteObject(obj);
                    context.SaveChanges();
#endif
                }
            }

            result.id = obj != null ? obj.id : Guid.Empty;
            result.nazev1 = obj != null ? obj.nazev1 : "";
            return result;
        }

    }
}
