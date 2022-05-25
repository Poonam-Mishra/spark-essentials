package part2dataframes

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DateType, DoubleType, FloatType, LongType, StringType, StructField, StructType, TimestampType}
import org.slf4j.Logger

object DataFramesBasics1 extends App{

  //creating a spark session
  val spark = SparkSession.builder()
    .appName("DataFrameBasics1")
    .config("spark.master","local")
    .getOrCreate()


  //read a df
  val firstDf = spark.read.
    format("json")
    .option("inferSchema","true")
    .load("src/main/resources/data/cars.json")

  firstDf.show()// show a dataframe in a tabular format
  /*
      a dataframe is a distributed collection of rows with a schema of attributes with specific datatype
   */
  firstDf.printSchema()

  firstDf.take(10).foreach(println)//displays a collection of rows

  //spark  types - are defined by some case objects internally which are inferred at runtime rather than compile time
  // when spark evaluates the data
  val longType = LongType//LongType is an example of singleton case object that spark uses internally to describe  schemas
  //Other similar case objects - DoubleType,TimestampType, FloatType, StringType,DateType,
  /**
    * A Spark schema structure that describes a small cars DataFrame.
    * as best practice, we should always define the schema ourselves ,
    * instead of inferSchema ,as sometimes , spark my infer wrong schema, for e.g: dates which
    * are not in ISO standard format
    */
  val carsSchema = StructType(Array(
    StructField("Name", StringType),
    StructField("Miles_per_Gallon", DoubleType),
    StructField("Cylinders", LongType),
    StructField("Displacement", DoubleType),
    StructField("Horsepower", LongType),
    StructField("Weight_in_lbs", LongType),
    StructField("Acceleration", DoubleType),
    StructField("Year", StringType),
    StructField("Origin", StringType)
  ))

  val carsDfSchema = firstDf.schema
  val carsDfWithSchema = spark.read.format("json").schema(carsSchema).load("src/main/resources/data/cars.json")

  //create a rows by hand
  val myRow = Row("chevrolet chevelle malibu",18.0,8L,307.0,130L,3504L,12.0,"1970-01-01","USA")

  //create df from tuples
  val cars = Seq(("chevrolet chevelle malibu",18.0,8L,307.0,130L,3504L,12.0,"1970-01-01","USA"),
    ("buick skylark 320",15.0,8L,350.0,165L,3693L,11.5,"1970-01-01","USA"))

  val manualCardsDF = spark.createDataFrame(cars)
  //note: dfs have schema , rows do not

  //create df with implicits
  import spark.implicits._

  val manualCarsDfWithImplicits = cars.toDF("Name","MPG","Cylinders","Displacement","HP","Weight","Acceleration","Year","Origin")

  manualCardsDF.printSchema()
  manualCarsDfWithImplicits.printSchema()

  /**
    * Exercise
    * 1. create a manual df desribing smartphones - make, model, screen dimension, camera megapixels
    * 2. read another file from data folder - movies.json - print schema and count the number of rows
    */

  val smartphones = Seq(
    ("nokia","t20hk","5x2",5),
    ("samsung","m31","3x2",2),
    ("samsung","m32","3x2",3)
  )

  val smartphonesDf = spark.createDataFrame(smartphones).toDF("make","model","screen dimension","camera megapixels")
  smartphonesDf.show(false)

  val moviesDf = spark.read.format("json").load("src/main/resources/data/movies.json")
  moviesDf.printSchema()
  println(moviesDf.count())
}
