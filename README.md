TachoReader Workbench
==================================

Sources for the official [Thingtrack](http://tachoreader.thingtrack.com/) workbench application: http://web.tachoreader.thingtrack.com/

![TachoReader Workbench](http://tachoreader.thingtrack.com/assets/img/app-bg.png)

Running the App
==
Run the Maven 'install' for each project target and deploy the resulting WAR file to your Java application server.


The application uses: 
==

- [MySQL Connector v5.1.6](http://dev.mysql.com/downloads/connector/j/) framework by Oracle, which is released under GPL 2.0: http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
- [JPA Eclipse Link v2.4.2](http://www.eclipse.org/eclipselink/) framework by Eclipse, which is released under EPL v1.0 : http://www.eclipse.org/legal/epl-v10.html
- [Spring v4.1.0.RELEASE](https://spring.io/) framework by Spring, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [Google Guava Event Bus v18.0](https://code.google.com/p/guava-libraries/) framework by Google, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [Vaadin v7.4.6](https://vaadin.com/home) framework by Vaadin, which is released under Apache License 2.0: http://www.
apache.org/licenses/LICENSE-2.0.html
- [Vaadin Valo Theme](https://vaadin.com/valo) framework by Vaadin, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [Spring Vaadin Integration v3.1](https://vaadin.com/directory#!addon/vaadin-spring) add-on by Alexander Fedorov, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [I18N4Vaadin v1.0.0](https://vaadin.com/directory#!addon/i18n4vaadin) add-on by Alexander Fedorov, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [FilteringTable v0.9.12.v7](https://vaadin.com/directory#!addon/filteringtable) add-on by Teppo Kurki, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [Plupload wrapper for Vaadin 7.x v2.0.0](https://vaadin.com/directory#!addon/plupload-wrapper-for-vaadin-7x) add-on by Slawomir Dymitrow, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html
- [Exporter](https://vaadin.com/directory#!addon/exporter v0.0.5.5) add-on by Haijian Wang, which is released under Apache License 2.0: http://www.apache.org/licenses/LICENSE-2.0.html

Directory Structure
==

	  |-exporter/ ...................... Adaptation of the exporter Vaadin add-on to Tachoreader
	  |-tachoreader/ ................... Parent project pom with many common dependencies
	  |-tachoreader.dao.api/ ........... Persistence API
	  |-tachoreader.dao.impl/ .......... Persistence API Implementation
	  |-tachoreader.service.api/ ....... Service API
	  |-tachoreader.service.impl/ ...... Service API Implementation
	  |-tachoreader.parser/ ............ Library to interpret the bytes of the files in a tachograph card based on the REGLAMENTO (CE) No 1360/2002 DE LA COMISIÓN de 13 de junio de 2002
	  |-tachoreader.workbench/ ......... Web Application
	  |-pom.xml ........................ Project object model
	  |-license ........................ Commercial License
	  '-README.md ...................... Release readme

Licenses
== 
Copyright (c) 2010-2015, Thingtrack S.L.