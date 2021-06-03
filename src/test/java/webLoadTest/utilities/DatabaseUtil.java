package webLoadTest.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import webLoadTest.test.TestBaseBrowser;

import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtil extends TestBaseBrowser {

    private static Statement statement;
    private static ResultSet rs;


    public static String queryData = "select data from hello_dev.question_jobreq where external_job_id = SystemId";
    public static String jobReqInfo = "select * from sample.question_jobreq where external_job_id = ";

    public static int roleID;
    public static String jobId,emailID,positionTitle,interviewLocation,jobStatus;



    public static int getRoleID(){

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqInfo);

            while(rs.next()){
                roleID = rs.getInt("role_id");
                //System.out.println("Role ID :: "+roleID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleID;
    }

    public static int getRoleIDAfterJobCreated(String jobReqQuery){

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqQuery);

            while(rs.next()){
                roleID = rs.getInt("role_id");
                //System.out.println("Role ID :: "+roleID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleID;
    }

    public static String getJobStatus(){

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqInfo);

            while(rs.next()){
                jobStatus = rs.getString("status");
                System.out.println("Role ID :: "+jobStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobStatus;
    }

    public static String getJobStatusAfterJobCreated(String jobReqQuery){

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqQuery);

            while(rs.next()){
                jobStatus = rs.getString("status");
                System.out.println("Role ID :: "+jobStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobStatus;
    }

    public static String getJobId(){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqInfo);

            while(rs.next()){

                String jsonOutput = rs.getString("data");
                System.out.println(jsonOutput);

                Object obj = new JSONParser().parse(jsonOutput);
                JSONObject jo = (JSONObject) obj;
                jobId = (String) jo.get("jobId");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobId;
    }

    public static String getJobIdAfterJobCreated(String jobReqQuery){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqQuery);

            while(rs.next()){

                String jsonOutput = rs.getString("data");
                System.out.println(jsonOutput);

                Object obj = new JSONParser().parse(jsonOutput);
                JSONObject jo = (JSONObject) obj;
                jobId = (String) jo.get("jobId");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobId;
    }

    public static String getEmailId(){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqInfo);

            while(rs.next()){

                String jsonOutput = rs.getString("data");
                System.out.println(jsonOutput);

                Object obj = new JSONParser().parse(jsonOutput);
                JSONObject jo = (JSONObject) obj;
                emailID = (String) jo.get("store_email");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailID;
    }

    public static String getEmailIdAfterJobCreated(String jobReqQuery){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqQuery);

            while(rs.next()){

                String jsonOutput = rs.getString("data");
                System.out.println(jsonOutput);

                Object obj = new JSONParser().parse(jsonOutput);
                JSONObject jo = (JSONObject) obj;
                emailID = (String) jo.get("store_email");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailID;
    }

    public static String getInterviewLocation(){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqInfo);

            while(rs.next()){

                String jsonOutput = rs.getString("data");
                System.out.println(jsonOutput);

                Object obj = new JSONParser().parse(jsonOutput);
                JSONObject jo = (JSONObject) obj;
                interviewLocation = (String) jo.get("interview_location");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return interviewLocation;
    }

    public static String getInterviewLocationAfterJobCreated(String jobReqQuery){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery(jobReqQuery);

            while(rs.next()){

                String jsonOutput = rs.getString("data");
                System.out.println(jsonOutput);

                Object obj = new JSONParser().parse(jsonOutput);
                JSONObject jo = (JSONObject) obj;
                interviewLocation = (String) jo.get("interview_location");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return interviewLocation;
    }

    public static String getPositionTitle(int roleID){
        try {

            statement = connection.createStatement();
            rs = statement.executeQuery("select title from question_role where id ="+roleID+";");

            while(rs.next()){
                positionTitle = rs.getString("title");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return positionTitle;
    }




}
