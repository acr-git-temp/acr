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
            plumaingroup obj = context.PluMainGroup.Where(s => s.id == id).FirstOrDefault<plumaingroup>();
            return obj.timestamp;
        }

        public List<DCPluMainGroup> GetHLAVNISKUPINY_PLU()
        {
            Console.WriteLine(DateTime.Now.ToString() + " Get PluMainGroup");

            using (var context = new SERDEntities())
            {
                var data = (from t in context.PluMainGroup
                            select new DCPluMainGroup
                            {
                                id = t.id,
                                name = t.name,
                                timestamp = t.timestamp,
                                deleted = t.deleted
                            }).ToList();
                return data;
            }
        }

        public DCPluMainGroup InsertHLAVNISKUPINY_PLU(DCPluMainGroup src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Insert PluMainGroup\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.name).ToString());

            plumaingroup obj;
            {
                SERDEntities context = new SERDEntities();

                obj = new plumaingroup
                {
                    id = src.id,
                    name = src.name,
                    timestamp = src.timestamp,
                    deleted = src.deleted
                };

#if (!DO_NOT_UPDATE_DATABASE) 
                context.AddToPluMainGroup(obj);
                context.SaveChanges();
#endif
            }

            return src;
        }

        public DCPluMainGroup UpdateHLAVNISKUPINY_PLU(DCPluMainGroup src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Update PluMainGroup\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.name).ToString());

            plumaingroup obj;
            using (var context = new SERDEntities())
            {
                obj = context.PluMainGroup.Where(s => s.id == src.id).FirstOrDefault<plumaingroup>();
                if (obj != null)
                {
#if (!DO_NOT_UPDATE_DATABASE)
                    obj.name = src.name;
                    obj.timestamp = src.timestamp;
                    obj.deleted = src.deleted;
                    context.SaveChanges();
#endif
                }
                else
                {
                    obj = new plumaingroup
                    {
                        id = src.id,
                        name = src.name,
                        timestamp = src.timestamp,
                        deleted = src.deleted
                    };

#if (!DO_NOT_UPDATE_DATABASE)
                    context.AddToPluMainGroup(obj);
                    context.SaveChanges();
#endif
                }
            }

            return src;
        }

        public DCPluMainGroup DeleteHLAVNISKUPINY_PLU(string id)
        {
            DCPluMainGroup result = new DCPluMainGroup();

            plumaingroup obj;
            using (var context = new SERDEntities())
            {
                Guid idAsGuid = new Guid(id);
                obj = context.PluMainGroup.Where(s => s.id == idAsGuid).FirstOrDefault<plumaingroup>();
                if (obj != null)
                {
                    Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Delete PluMainGroup\n\t\t\tID: {0}\n\t\t\tTitle: {1}", obj.id.ToString(), obj.name).ToString());

#if (!DO_NOT_UPDATE_DATABASE) 
                    context.DeleteObject(obj);
                    context.SaveChanges();
#endif
                }
            }

            result.id = obj != null ? obj.id : Guid.Empty;
            result.name = obj != null ? obj.name : "";
            return result;
        }

        DateTime GetSKUPINY_PLUTimestamp(Guid id)
        {
            SERDEntities context = new SERDEntities();
            plugroup obj = context.PluGroup.Where(s => s.id == id).FirstOrDefault<plugroup>();
            return obj.timestamp;
        }

        public List<DCPluGroup> GetSKUPINY_PLU()
        {
            Console.WriteLine(DateTime.Now.ToString() + " Get PluGroup");

            using (var context = new SERDEntities())
            {
                var data = (from t in context.PluGroup
                            select new DCPluGroup
                            {
                                id = t.id,
                                name = t.name,
                                plumaingroup_id = t.plumaingroup.id,
                                timestamp = t.timestamp,
                                deleted = t.deleted
                            }).ToList();
                return data;
            }
        }

        public DCPluGroup InsertSKUPINY_PLUx(DCPluGroup src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Insert PluGroup\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.name).ToString());

            plugroup obj;
            {
                SERDEntities context = new SERDEntities();

                obj = new plugroup
                {
                    id = src.id,
                    name = src.name,
                    timestamp = src.timestamp,
                    deleted = src.deleted
                };

                obj.plumaingroupReference.EntityKey = new System.Data.EntityKey("SERDEntities.PluMainGroup", "id", src.plumaingroup_id);
#if (!DO_NOT_UPDATE_DATABASE)
                context.AddToPluGroup(obj);
                context.SaveChanges();
#endif
            }

            return src;
        }

        public DCPluGroup UpdateSKUPINY_PLUx(DCPluGroup src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Update PluGroup\n\t\t\tID: {0}\n\t\t\tTitle: {1}", src.id.ToString(), src.name).ToString());

            plugroup obj;
            using (var context = new SERDEntities())
            {
                obj = context.PluGroup.Where(s => s.id == src.id).FirstOrDefault<plugroup>();
                if (obj != null)
                {
#if (!DO_NOT_UPDATE_DATABASE)
                    obj.name = src.name;
                    obj.timestamp = src.timestamp;
                    obj.deleted = src.deleted;
                    obj.plumaingroupReference.EntityKey = new System.Data.EntityKey("SERDEntities.PluMainGroup", "id", src.plumaingroup_id);
                    context.SaveChanges();
#endif
                }
                else
                {
                    obj = new plugroup
                    {
                        id = src.id,
                        name = src.name,
                        timestamp = src.timestamp,
                        deleted = src.deleted
                    };

                    obj.plumaingroupReference.EntityKey = new System.Data.EntityKey("SERDEntities.PluMainGroup", "id", src.plumaingroup_id);
#if (!DO_NOT_UPDATE_DATABASE)
                    context.AddToPluGroup(obj);
                    context.SaveChanges();
#endif
                }

            }

            return src;
        }

        public DCPluGroup DeleteSKUPINY_PLUx(string id)
        {
            DCPluGroup result = new DCPluGroup();

            plugroup obj;
            using (var context = new SERDEntities())
            {
                Guid idAsGuid = new Guid(id);
                obj = context.PluGroup.Where(s => s.id == idAsGuid).FirstOrDefault<plugroup>();
                if (obj != null)
                {
                    Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Delete PluGroup\n\t\t\tID: {0}\n\t\t\tTitle: {1}", obj.id.ToString(), obj.name).ToString());

#if (!DO_NOT_UPDATE_DATABASE)
                    context.DeleteObject(obj);
                    context.SaveChanges();
#endif
                }
            }

            result.id = obj != null ? obj.id : Guid.Empty;
            result.name = obj != null ? obj.name : "";
            return result;
        }

        DateTime GetPLUTimestamp(Guid id)
        {
            SERDEntities context = new SERDEntities();
            plu obj = context.Plu.Where(s => s.id == id).FirstOrDefault<plu>();
            return obj.timestamp;
        }

        public List<DCPlu> GetPLUs()
        {
            Console.WriteLine(DateTime.Now.ToString() + " Get Plu");

            using (var context = new SERDEntities())
            {
                var data = (from t in context.Plu
                            select new DCPlu
                            {
                                id = t.id, ean = t.ean, name1 = t.name1, name2 = t.name2, name3 = t.name3,
                                price1 = t.price1, price2 = t.price2, price3 = t.price3, 
                                plugroup_id = t.plugroup.id, vat_id = t.vat_id, department_id = t.department_id, text = t.text,
                                status1 = t.status1, status2 = t.status2, halolalo = t.halolalo,
                                linkplu1_id = t.linkplu1_id, linkplu1quantity = t.linkplu1quantity,
                                linkplu2_id = t.linkplu2_id, linkplu2quantity = t.linkplu2quantity,
                                coefficient = t.coefficient, ordertype = t.ordertype,
                                menuvol = t.menuvol, menupat = t.menupat, happyhour = t.happyhour,
                                pictogram_id = t.pictogram_id, state = t.state, blocked = t.blocked,
                                source_id = t.source_id, sourcenumstock = t.sourcenumstock, sourcestock = t.sourcestock,
                                timestamp = t.timestamp, deleted = t.deleted
                            }).ToList();
                return data;
            }
        }

        public DCPlu InsertPLUx(DCPlu src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Insert Plu\n\tID: {0}\n\tTitle: {1}", src.id.ToString(), src.name1).ToString());
            
            plu obj;
            {
                SERDEntities context = new SERDEntities();

                obj = new plu
                {
                    id = src.id,
                    ean = src.ean,
                    name1 = src.name1, name2 = src.name2, name3 = src.name3,
                    price1 = src.price1, price2 = src.price2, price3 = src.price3, vat_id = src.vat_id, 
                    department_id = src.department_id, text = src.text, status1 = src.status1, status2 = src.status2, halolalo = src.halolalo, 
                    linkplu1_id = src.linkplu1_id, linkplu1quantity = src.linkplu1quantity, 
                    linkplu2_id = src.linkplu2_id, linkplu2quantity = src.linkplu2quantity,
                    coefficient = src.coefficient, ordertype = src.ordertype, menuvol = src.menuvol, menupat = src.menupat,
                    happyhour = src.happyhour, pictogram_id = src.pictogram_id, state = src.state, blocked = src.blocked,
                    source_id = src.source_id, sourcenumstock = src.sourcenumstock, sourcestock = src.sourcestock,
                    timestamp = src.timestamp, deleted = src.deleted
                };

                obj.plugroupReference.EntityKey = new System.Data.EntityKey("SERDEntities.PluGroup", "id", src.plugroup_id);
#if (!DO_NOT_UPDATE_DATABASE) 
                context.AddToPlu(obj);
                context.SaveChanges();
#endif
            }

            return src;
        }

        public DCPlu UpdatePLUx(DCPlu src)
        {
            Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Update Plu\n\tID: {0}\n\tTitle: {1}", src.id.ToString(), src.name1).ToString());

            plu obj;
            using (var context = new SERDEntities())
            {
                obj = context.Plu.Where(s => s.id == src.id).FirstOrDefault<plu>();
                if (obj != null)
                {
#if (!DO_NOT_UPDATE_DATABASE) 
                    obj.ean = src.ean;
                    obj.name1 = src.name1; obj.name2 = src.name2; obj.name3 = src.name3;
                    obj.price1 = src.price1; obj.price2 = src.price2; obj.price3 = src.price3; obj.vat_id = src.vat_id; 
                    obj.department_id = src.department_id; obj.text = src.text; obj.status1 = src.status1; obj.status2 = src.status2; obj.halolalo = src.halolalo;
                    obj.linkplu1_id = src.linkplu1_id; obj.linkplu1quantity = src.linkplu1quantity;
                    obj.linkplu2_id = src.linkplu2_id; obj.linkplu2quantity = src.linkplu2quantity;
                    obj.coefficient = src.coefficient; obj.ordertype = src.ordertype; obj.menuvol = src.menuvol; obj.menupat = src.menupat;
                    obj.happyhour = src.happyhour; obj.pictogram_id = src.pictogram_id; obj.state = src.state; obj.blocked = src.blocked;
                    obj.source_id = src.source_id; obj.sourcenumstock = src.sourcenumstock; obj.sourcestock = src.sourcestock;
                    obj.plugroupReference.EntityKey = new System.Data.EntityKey("SERDEntities.PluGroup", "id", src.plugroup_id);
                    context.SaveChanges();
#endif
                }
            }

            return src;
        }

        public DCPlu DeletePLUx(string id)
        {
            DCPlu result = new DCPlu();

            plu obj;
            using (var context = new SERDEntities())
            {
                Guid idAsGuid = new Guid(id);
                obj = context.Plu.Where(s => s.id == idAsGuid).FirstOrDefault<plu>();
                if (obj != null)
                {
                    Console.WriteLine(DateTime.Now.ToString() + builder.AppendFormat(" Delete Plu\n\tID: {0}\n\tTitle: {1}", obj.id.ToString(), obj.name1).ToString());

#if (!DO_NOT_UPDATE_DATABASE)
                    context.DeleteObject(obj);
                    context.SaveChanges();
#endif
                }
            }

            result.id = obj != null ? obj.id : Guid.Empty;
            result.name1 = obj != null ? obj.name1 : "";
            return result;
        }

    }
}
