Gmail
acr.git.temp@gmail.com
acrgittemp



Github
==================================================================================================================================================
Username: acr-git-temp
Password acrgittemp1

Quick setup � if you've done this kind of thing before
https://github.com/acr-git-temp/acr.git
git@github.com:acr-git-temp/acr.git


We recommend every repository include a README, LICENSE, and .gitignore.
�or create a new repository on the command line


touch README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/acr-git-temp/acr.git
git push -u origin master
�or push an existing repository from the command line


git remote add origin https://github.com/acr-git-temp/acr.git
git push -u origin master
�or import code from another repository

You can initialize this repository with code from a Subversion, Mercurial, or TFS project.

Import code




Server
==================================================================================================================================================
Grant permissions
--------------------------------------------------------------
as admin run:
netsh http add urlacl url=http://+:8000/. user=DELL\tomas



Start VS2008 command line
D:\Program Files\Microsoft Visual Studio 9.0\VC>

Start wcfsvchost and wcftestclient
wcfsvchost /service:E:\Source\WCFTest\WcfTest\bin\Debug\WcfTest.dll /config:E:\Source\WCFTest\WcfTest\bin\Debug\WcfTest.dll.config /client:WcfTestClient.exe

REST server VS2008
http://blogs.msdn.com/b/kaevans/archive/2008/04/03/creating-restful-services-using-wcf.aspx

REST client Android
http://spring.io/guides/gs/consuming-rest-android/

RESTful
http://msdn.microsoft.com/en-us/magazine/dd315413.aspx










Error:duplicate files during packaging 	org.springframework.android\spring-android-core org.springframework.android\spring-android-rest-template

\2.0.0.M1\26b2d3262a0c5f8de663deeb6481d132d871fffa\spring-android-core-2.0.0.M1.jar

\2.0.0.M1\6abb1e22445a4e4571c74632804e4179b43d82b5\spring-android-rest-template-2.0.0.M1.jar


CREATE TABLE ABC ( '_id' INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, TIMESTAMP TEXT);


INSERT INTO ABC (_id, TITLE, TIMESTAMP) VALUES (0, 'Jidlo' , '-');
SELECT *  FROM ABC;

CREATE TRIGGER TR_ANC_A_UI AFTER INSERT ON ABC BEGIN UPDATE ABC SET TIMESTAMP = '1' WHERE _id = NEW._id; END;

DROP TRIGGER IF EXISTS TR_ANC_A_UI;


SvcTraceViewer.exe

T�lo souboru XML s ko�enov�m n�zvem HLAVNISKUPINY_PLUx a ko�enov�m oborem n�zv�  (pro operaci InsertHLAVNISKUPINY_PLU a kontrakt (IFetchService, http://tempuri.org/)) nelze rekonstruovat pomoc� DataContractSerializer. Zajist�te, aby typ odpov�daj�c� souboru XML byl p�id�n do kolekce zn�m�ch typ� ve slu�b�.


/data/data/som.serd.cashregister.androidcashregisterapplication/files/tftf-1754834784tftf

org.springframework.http.converter.HttpMessageNotReadableException: 
Could not read [class com.serd.restclient.data.PLUMainGroup2]; nested exception is 	: 
Constructor not matched for class com.serd.restclient.data.PLUMainGroup2

/*                try {
                    PLUMainGroup a = new PLUMainGroup("aaa","bbb","ccc");

                    Serializer serializer = new Persister();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream( baos );
                    serializer.write(a, oos);
                    oos.close();
                    String s1 = new String( baos.toByteArray()  );
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/


/*
ALTER TRIGGER [dbo].[TR_HLAVNISKUPINY_PLU_ON_IU]
ON [dbo].[HLAVNISKUPINY_PLU]
FOR INSERT, UPDATE
AS
	UPDATE dbo.HLAVNISKUPINY_PLU
  SET timestamp = GETDATE()
  FROM Inserted i
  WHERE dbo.HLAVNISKUPINY_PLU.id = i.id
*/

 HttpHeaders requestHeaders = new HttpHeaders();
        List<MediaType> acceptableMediaTypesXml = new ArrayList<MediaType>();
        acceptableMediaTypesXml.add(MediaType.APPLICATION_XML);
        requestHeaders.set("WsUsername", "username");
        requestHeaders.set("WsPassword", "password");
        requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        if (ticket != null)
        {
            requestHeaders.set("Ticket", ticket);
        }
        requestHeaders.set("TerminalID", UnoMobilApplication.getTerminalId());
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());     // since default doesn't work...
        requestHeaders.setAccept(acceptableMediaTypesXml);
        HttpEntity<?> requestEntity = new HttpEntity<String>(id, requestHeaders);
        ResponseEntity<DeliveryList> responseEntity = restTemplate.exchange(url + "deliveries/" + id, HttpMethod.GET, requestEntity, DeliveryList.class);
        return responseEntity.getBody();