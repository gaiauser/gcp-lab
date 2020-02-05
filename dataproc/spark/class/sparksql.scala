package co.id.test

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql._

object sparksql {
  def main(args: Array[String]){
    val APPNAME = "Spark SQL Lab"
    val conf = new SparkConf().setAppName(APPNAME)
    
    val spark = SparkSession.builder.config(conf).enableHiveSupport().getOrCreate()
    val sc = spark.sparkContext
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    sqlContext.clearCache()
    
    import spark.implicits._
    
    /* Parameter */
    val OUTPUT = args(0)
    
    /* Data */
    val df01 = Seq(
      (1, "Alpha", "IT", 120000),
      (2, "Beta", "IT", 95000),
      (3, "Charlie", "Finance", 80000),
      (3, "Charlie", "Sales", 100000)
    ).toDF("id", "name", "dept", "salary")
    
    df01.createOrReplaceTempView("vdf01")
    
    /* Processing */
    val df02 = spark.sql("select dept,max(salary) max_salary from vdf01 group by dept order by max_salary desc")
    
    /* Write output */
    df02.write.mode("overwrite").parquet(OUTPUT)
    
    /* Stop spark job */
    spark.stop()
  }
}