# doc2hpo

doc2hpo is a java spring mvc based webapp to parse clinical note and get the HPO for phenolyzer analysis.

## Getting Started

### Prerequisites
- java version 1.8.0_191 (https://www.java.com/en/download/)
- apache-tomcat version 8.5.35 (https://tomcat.apache.org/download-90.cgi)
- apache-maven-3.6.0 (https://maven.apache.org/install.html)
- metamap-api-2.0.jar (Optional) (https://metamap.nlm.nih.gov/MainDownload.shtml)
- MetaMap 2016v2 Linux Version (https://metamap.nlm.nih.gov/MainDownload.shtml)
- Api key for ncbo annotator (http://data.bioontology.org/documentation)


### Configuration
- Install metamap and metamap java api (https://metamap.nlm.nih.gov/Installation.shtml and https://metamap.nlm.nih.gov/Docs/README_javaapi.shtml#Downloading,%20Extracting%20and%20Installing%20the%20API%20distribution)
  * You have to get a free UMLS license to install the software
- Starting supporting servers and running the MetaMap server
  * Follow the instruction here https://metamap.nlm.nih.gov/Docs/README_javaapi.shtml#Using%20the%20MetaMap%20server
- Change MetamapBinPath in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
- Please change Api key for ncbo annotator in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
  * You need to register a free account to get api key https://bioportal.bioontology.org/account
- Add proxy and port if necessary in `doc2hpo/src/main/webapp/WEB-INF/config.properties`
  * `Proxy=null` and `Port=null` if you don't need proxy.
- export the `doc2hpo.war` file for the project. You could do it using eclipse or by maven
  * You may find this link helpful https://www.baeldung.com/tomcat-deploy-war
- deploy the war file under `apache-tomcat-8.5.35/webapps`
  * Make sure privilege is correct.
- start the tomcat and browse the results.
  * You could check version requirement by calling api at `servername:8080/doc2hpo/version`

## Versioning
0.15.0

## New features under development
- Add context based annotation in backend
- Using different color to seperate the category (e.g. family, negation, education) in frontend
- Add more parsers
  - metamap Lite https://metamap.nlm.nih.gov/MetaMapLite.shtml
  - cTakes http://ctakes.apache.org/
  - ClinPhen http://bejerano.stanford.edu/clinphen/

## Authors
Cong Liu, Chi Yuan, Kai Wang, Chunhua Weng
stormliucong@gmail.com
