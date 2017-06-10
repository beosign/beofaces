Logging Filter for Wildfly for monitoring the nummber of sql statements during <b>development</b>. In order to make it work, create the following entries in
standalone.xml:

1. Create a logging handler. The path must match the init param of the filter, see below.


   <file-handler name="SQL" autoflush="true">
       <formatter>
           <named-formatter name="PATTERN"/>
       </formatter>
       <file relative-to="jboss.server.log.dir" path="sql.log"/>
       <append value="false"/>
   </file-handler>
</pre>

2. Create a logger and associate the above handler with it

   <logger category="org.hibernate.SQL">
       <level name="DEBUG"/>
       <handlers>
          <handler name="SQL"/>
       </handlers>
   </logger>


In addition, be sure that the path to the logfile is correct. To change the path to fit your needs, change the following in the web-fragment.xml file:

    <filter>
      <filter-name>WildflySqlLoggingFilter</filter-name>
      <init-param>
          <param-name>logfile</param-name>
          <param-value>../standalone/log/sql.log</param-value>
      </init-param>
  </filter>

Restrictions:
- Only works for a single user; if multiple users interact with the system at the same time (no concurrent users supported)</li>