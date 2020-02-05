#!/bin/bash
CLASS=co.id.test.sparksql
JARS=gs://$PROJECT_ID/path/to/linknet-test-1.0.0.jar
OUTPUT=gs://$PROJECT_ID/path/to/output

gcloud dataproc jobs submit spark --cluster=spark-batch-01 \
	--region asia-southeast1 \
	--class $CLASS \ 
	--jars $JARS \ 
	-- $OUTPUT
