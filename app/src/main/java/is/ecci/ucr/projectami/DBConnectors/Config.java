package is.ecci.ucr.projectami.DBConnectors;

/**
 * Created by alaincruzcasanova on 5/20/17.
 */

public class Config {

    //DNS público del servidor Linux en la nube de Amazon AWS
    public static String PUBLIC_DNS = "http://ec2-35-164-117-191.us-west-2.compute.amazonaws.com";
    //Puerto de ingreso, para realizar una consulta de HTTP
    public static String REST_PORT = "8080";
    //Nombre de la base de datos en MongoDB
    public static String DATABASE_NAME = "LifeFinder";
    //Nombre de la base de datos de usuarios en MongoDB
    public static String DATABASE_NAME_AUTH = "auth";
    //URL completo para realizar consultas HTTP a la base de datos
    public static String CONNECTION_STRING_USERS = PUBLIC_DNS+":"+REST_PORT+"/"+DATABASE_NAME_AUTH;
    //URL completo para realizar consultas HTTP a la base de datos
    public static String CONNECTION_STRING = PUBLIC_DNS+":"+REST_PORT+"/"+DATABASE_NAME;
    //Parametro JSON para usar autenticación en las consultas
    public static String AUTH_KEY = "Authorization";
    //Parametro JSON para definir el tipo de contenido que usaremos
    public static String JSON_CONTENT_TYPE_KEY = "Content-Type";
    //Parametro JSON para definir que la consulta nos devolvera un valor de tipo JSON
    public static String JSON_CONTENT_TYPE = "application/json";
    //External directory where the CSV file with the keys are stored
    public static String KEYS_DIR = "KeysData";
    //CSV file with the keys
    public static String CSV_KEYS = "keys.csv";
    //Dummy user object id
    public static String DUMMY_USER_ID = "alain.cruzc@gmail.com";
}
