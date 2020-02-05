#!/bin/bash

cd maven/development

mvn clean install

mvn -Pdataflow-runner compile exec:java \
-Dexec.mainClass=com.development.beam.BeamSQLReadAvro \
-Dexec.args="--project=linknet-poc \
--region=asia-east1 \
--tempLocation=gs://linknet-poc/dataflow-temp \
--stagingLocation=gs://linknet-poc/dataflow-staging \
--runner=DataflowRunner"
