package com.development.beam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.io.AvroIO;
import org.apache.beam.sdk.schemas.utils.AvroUtils;
import org.apache.beam.sdk.extensions.sql.SqlTransform;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.CreateDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.WriteDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryUtils;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import java.io.File;
import java.io.IOException;

import com.development.schemas.*;

public class BeamSQLReadAvro {
	public static void main(String[] args) throws IOException {
		Pipeline p = Pipeline.create(PipelineOptionsFactory.fromArgs(args).withValidation().create());
		
		/* Define source schema */
		Schema schema_in_CODE36 = new Schema.Parser().parse(new File("schemas/CODE36.avsc"));
		
		/* Load the data */
		PCollection<GenericRecord> avro_CODE36 = p.apply("Load the data",AvroIO.readGenericRecords(schema_in_CODE36).withBeamSchemas(true).from("gs://[PROJECT_ID]/dwh/staging01/CODE36/*.avro"));
		
		/* SQL Transform, produces Beam Row */
	    PCollection<Row> sql01 = avro_CODE36.apply("SQL Transform 01 A",SqlTransform.query("SELECT TABL,COUNT(*) CNT_CODE FROM PCOLLECTION GROUP BY TABL"));
	       
	    /* Write to BQ */
	    sql01.apply("Write to BQ",BigQueryIO.<Row>write()
    	  			.withSchema(new TestSchemas.CODE36().getTableSchema())
    	   			.to(new TestSchemas.CODE36().getTableName())
    	   			.withFormatFunction(BigQueryUtils.toTableRow())
    	   			.withCreateDisposition(CreateDisposition.CREATE_IF_NEEDED)
    	   			.withWriteDisposition(WriteDisposition.WRITE_TRUNCATE));
	    
		/* Print output */
	    sql01.apply("Print Output",
 		      MapElements.via(
                 new SimpleFunction<Row, Row>() {
                   @Override
                   public Row apply(Row input) {
                     System.out.println("PCOLLECTION: " + input.getValues());
                     return input;
                   }
                 }
               )
		   );
	    
	    /* Run 
	     * Don't use waitUntilFinish() if you want to run another job immediately in Airflow
	     * */
		//p.run().waitUntilFinish();
	    p.run();
	}
}
