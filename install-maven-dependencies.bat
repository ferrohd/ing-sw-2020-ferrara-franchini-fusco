@ECHO OFF
mvn install:install-file -Dfile=".\lib\jimObjModelImporterJFX.jar" -DgroupId=com.interactivemesh.jfx -DartifactId=importer -Dversion=1.0.0 -Dpackaging=jar
PAUSE
