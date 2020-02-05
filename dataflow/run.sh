#!/bin/bash

cd maven/development

mvn -Pdataflow-runner compile exec:java \
-Dexec.mainClass=com.development.beam.BeamSQLReadAvro \
-Dexec.args="--project=$PROJECT_ID \
--region=asia-east1 \
--tempLocation=gs://$PROJECT_ID/dataflow-temp \
--stagingLocation=gs://$PROJECT_ID/dataflow-staging \
--runner=DataflowRunner"
