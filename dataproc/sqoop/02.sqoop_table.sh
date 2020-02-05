#!/bin/bash
JDBC_ORACLE=jdbc:oracle:thin:@<HOSTNAME>:<PORT>/<SERVICE_NAME>
USERNAME=<USERNAME>
PASSWORD_FILE=gs://$PROJECT_ID/path/to/password/text/file.txt
TABLE=<TABLE NAME>
TARGET_DIR=gs://$PROJECT_ID/path/to/target/dir/$TABLE
JARS_DIR=gs://$PROJECT_ID/share/lib/sqoop

gcloud dataproc jobs submit hadoop \
	--region asia-southeast1 \
	--cluster sqoop-ingest-01 \
	--class org.apache.sqoop.Sqoop \
	--jars $JARS_DIR/sqoop-1.4.7-hadoop260.jar,$JARS_DIR/avro-tools-1.8.2.jar,$JARS_DIR/java-json.jar,$JARS_DIR/ojdbc6.jar \
	-- \
	import \
	-Dorg.apache.sqoop.splitter.allow_text_splitter=true \
	-Dmapreduce.job.user.classpath.first=true \
	--connect $JDBC_ORACLE \
	--username $USERNAME \
	--password-file $PASSWORD_FILE \
	--target-dir $TARGET_DIR \
	--delete-target-dir \
	--table $TABLE \
	--as-avrodatafile \
        -m 1
