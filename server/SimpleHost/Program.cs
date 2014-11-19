using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using System.ServiceModel.Web;

namespace SimpleHost
{
    class Program
    {
        static void Main(string[] args)
        {
            Uri baseAddress = new Uri("http://127.0.0.1:8000");

            using (WebServiceHost host = new WebServiceHost(typeof(RestService.FetchService), baseAddress))
            {
                host.Open();
                Console.WriteLine("Press any key to terminate");
                Console.ReadLine();
            }
        }
    }
}
