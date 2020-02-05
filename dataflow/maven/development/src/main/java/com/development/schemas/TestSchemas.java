package com.development.schemas;

import org.apache.beam.vendor.guava.v26_0_jre.com.google.common.collect.ImmutableList;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.api.services.bigquery.model.TableFieldSchema;

public class TestSchemas {
    static String p = "[PROJECT_ID]";
    static String d = "TEST";
    
    /* table sales_move_step_01*/
	public static class CODE36{
        String n = "CODE36";
        
        public String getTableName(){
        	String full_n = p + ":" + d + "." + n;
            return full_n;
        }
        
        public TableSchema getTableSchema() {
        	TableSchema schema = new TableSchema().setFields(
	    		    ImmutableList.of(
	    		    			new TableFieldSchema().setName("tabl").setType("STRING").setMode("NULLABLE"),
	    		    			new TableFieldSchema().setName("cnt_code").setType("INTEGER").setMode("NULLABLE")
	    		    		)
	    		);
        	
        	return schema;
        }
    }
	
	/* table sales_move_step_02*/
}
