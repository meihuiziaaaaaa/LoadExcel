package com.yiche;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.connection.Connection;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.SystemOutLogger;
import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App
    {
        //当mongodb有用户名和密码的时候为true
        private static boolean flag=false;

        private static String database;
        private static String host;
        private static int port;
        private static String username;
        private static String password;

    public static void main( String[] args ) {
         getProperties();

         MongoCollection<org.bson.Document> collection=getConnection();
         if (collection != null){
             insert(collection);
         }


         /* getProperties();
             MongoCollection<org.bson.Document> collection=getConnection();

           read(collection);*/

        /*

       List<UserImage> userImages=readExcel();
       System.out.println(userImages.size());
       for(int i=0;i<100;i++){

           //System.out.println(userImages.get(i));
       }*/

    }

    /*获取数据库的链接*/
     public  static MongoCollection<org.bson.Document>  getConnection(){
               MongoCollection<org.bson.Document> collection=null;
                  if (flag == false){

                      // 连接到 mongodb 服务
                      //System.out.println(port);
                      MongoClient mongoClient = new MongoClient(host,port);

                      // 连接到数据库
                     // System.out.println(database);
                      MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
                      System.out.println("Connect to database successfully");

                       //mongoDB中的文档集合
                       collection = mongoDatabase.getCollection("userImage");


                  }else{


                      //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
                      //ServerAddress()两个参数分别为 服务器地址 和 端口
                      ServerAddress serverAddress = new ServerAddress(host,new Integer(port));
                      List<ServerAddress> addrs = new ArrayList<ServerAddress>();
                      addrs.add(serverAddress);

                      //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
                      MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());
                      List<MongoCredential> credentials = new ArrayList<MongoCredential>();
                      credentials.add(credential);

                      //通过连接认证获取MongoDB连接
                      MongoClient mongoClient = new MongoClient(addrs,credentials);

                      //连接到数据库
                      MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
                       collection = mongoDatabase.getCollection("userImage");


                  }

                  return collection;
     }



    /*读取外边的配置文件*/
     public static void getProperties(){

                 Properties prop = new Properties();
                  try{
                          //读取属性文件a.properties
                         InputStream in =App.class.getClassLoader().getResourceAsStream("user.properties");//getClass().getResourceAsStream("user.properties"); ;

                        prop.load(in);     ///加载属性列表
                        //tem.out.println(prop.getProperty("port"));
                       Iterator<String> it=prop.stringPropertyNames().iterator();
                          database=prop.getProperty("database");
                          host=prop.getProperty("host");
                          port=Integer.valueOf(prop.getProperty("port"));

                          if (prop.getProperty("username") != null){

                               username=prop.getProperty("username");
                          }

                          if (prop.getProperty("password") != null){
                              password=prop.getProperty("password");
                              flag=true;
                          }
                          //System.out.println(prop.getProperty("database"));

                        in.close();


                     }catch(Exception e){
                          e.printStackTrace();
                     }

     }


      /*测试数据*/
       public static  void read( MongoCollection<org.bson.Document> collection){
         int i=0;
           FindIterable<org.bson.Document> findIterable = collection.find();
           MongoCursor<org.bson.Document> mongoCursor = findIterable.iterator();
           while(mongoCursor.hasNext()){
                i++;
               System.out.println(mongoCursor.next());
           }

            System.out.println(i);
       }


       /*拿到链接后插入数据*/
      public static void insert(MongoCollection<org.bson.Document> collection){

          try {

              List<UserImage> userImages=readExcel();
              List<org.bson.Document> list=new ArrayList<>();
              if (userImages.size()>0){

                  for(int i=0;i<userImages.size();i++){
                      org.bson.Document document=new org.bson.Document();
                      UserImage userImage=userImages.get(i);
                      document.append("_id",userImage.getId());
                      document.append("_class","com.yiche.Bean.UserImage");
                      document.append("cid",userImage.getCid());
                      document.append("phoneNumber",userImage.getPhoneNumber());
                      document.append("carType",userImage.getCarType());
                      document.append("credit",userImage.getCredit());
                      document.append("changeCar",userImage.getChangeCar());
                      document.append("local",userImage.getLocal());
                      document.append("recentlyCarType",userImage.getRecentlyCarType());
                      document.append("newClassify",userImage.getNewClassify());
                      document.append("newCarType",userImage.getNewCarType());
                      System.out.println(document.toJson()+".....");
                      list.add(document);

                  }
                   //如果不加的话 InsertManyOptions().ordered(false) 出现重复的key就会停止插入
                   collection.insertMany(list,new InsertManyOptions().ordered(false));
                   //collection.re
                   //collection.insertMany(list);

              }



          } catch (Exception e) {
             /* System.err.println( e.getClass().getName() + ": " + e.getMessage() );*/
          }

           System.out.println("excel 插入成功！");

      }

      /*读取excel表格里面的数据*/
    public static List<UserImage> readExcel(){

        List<UserImage> list=new ArrayList<>();
        HSSFWorkbook workbook=null;

        try {

            //读取文件  //new FileInputStream("D:/student.xls")
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("table.xls");
            //输入流作为参数
            workbook = new HSSFWorkbook(inputStream);

            //循环工作流
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                //每张工作表
                    HSSFSheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }

                String id=null;

                //循环行  注意从1开始   不要表头
                for (int row = 1; row <= sheet.getLastRowNum(); row++) {

                    //得到一行数据集
                    HSSFRow row1 = sheet.getRow(row);
                    if (row1 == null) {
                        continue;
                    }

                    UserImage userImage = new UserImage();
                    //得到单元格的值
                    HSSFCell cell = row1.getCell(0);

                    if (cell == null) {
                        continue;
                    }
                    //--------------------设置值
                    //设置得到的是String
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // cell.getStringCellValue()得到String
                    id=cell.getStringCellValue();

                    cell = row1.getCell(1);
                    cell.setCellType(Cell.CELL_TYPE_STRING);

                    //phone 和  cartType可以为空
                    userImage.setCid(id);

                    id=id+"_"+cell.getStringCellValue();


                    userImage.setId(id);

                    //如果读取的是数值的话  防止错误

                    cell = row1.getCell(2);

                    //先设置成String  在读取String
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!"null".equalsIgnoreCase(cell.getStringCellValue())){
                            userImage.getPhoneNumber().add(cell.getStringCellValue());
                        }

                    }


                    cell = row1.getCell(3);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (!"null".equalsIgnoreCase(cell.getStringCellValue())){

                            userImage.getCarType().add(cell.getStringCellValue());
                        }

                    }

                    list.add(userImage);
                }
            }

        }catch (Exception e){
             e.printStackTrace();
            // logger.error("failed! Excel 表格解析出错",e.fillInStackTrace());
        }
         return  list;
    }
}
