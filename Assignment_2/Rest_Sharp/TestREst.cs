using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp; 

namespace Rest_Sharp
{
  
    [TestFixture]
    public class TestREst
    {
        private string geturl = "http://localhost:8888/beer/79";

        [Test]
        public void TestGetInRest() {
            IDisposable restClient = new RestClient();
            RestRequest request = new RestRequest(geturl);
            restClient.Get(restClient);
        }

    }
}
