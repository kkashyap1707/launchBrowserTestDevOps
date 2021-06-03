package webLoadTest.utilities;

import com.aventstack.extentreports.Status;
import com.jcraft.jsch.*;
import webLoadTest.test.TestBaseBrowser;

import java.util.Vector;

public class SFTPClient extends TestBaseBrowser {

    private static Session session = null;
    private static Channel channel = null;

    private String privateKeyPath;

    public static String filePath = "/home/sftpusers/home/"+GlobalVars.SFTP_AUTH_USERNAME+"/job-reqs/";

    public SFTPClient() {

    }

    public static void SFTP_Connect(String Username, String Password, String Host, Integer Port) throws JSchException {
        JSch jsch = new JSch();

        // jsch.addIdentity("private-key-path);

        session = jsch.getSession(Username,Host,Port);
        session.setPassword(Password);

        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        // test.log(Status.PASS, "Establishing Connection...");

        session.connect();

        System.out.println("Connection established.");
        // test.log(Status.PASS, "SFTP Connection Established.");
        System.out.println("Creating SFTP Channel.");
        // test.log(Status.PASS, "Creating SFTP Channel.");
    }

    public static Session SSH_Connect_UsingPrivateKey(String Username, String Password, String Host, Integer Port, String privateKeyPath) throws Exception {
        java.util.Properties config = new java.util.Properties();
        JSch jsch = new JSch();
        session = jsch.getSession(Username,Host,Port);
        jsch.addIdentity(privateKeyPath, Password);
        config.put("StrictHostKeyChecking", "no");
        config.put("ConnectionAttempts", "3");
        session.setConfig(config);
        session.connect();

        System.out.println("SSH Connected");

        return session;
    }

    public static Session SFTP_Connect1(String Username, String Password, String Host, Integer Port) throws Exception {
        JSch jsch = new JSch();

        // jsch.addIdentity("private-key-path);

        session = jsch.getSession(Username,Host,Port);
        session.setPassword(Password);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing SFTP Connection...");
        session.connect();
        System.out.println("SFTP Connection established.");

        return session;
    }

    public static Channel SFTP_ChannelConnect(Session session) throws Exception{
        channel = session.openChannel("sftp");
        channel.connect();
        System.out.println("Channel Connected");

        return channel;
    }

    public static void SFTP_ChannelDisConnect(Session session) throws Exception{

        channel.disconnect();
        System.out.println("Channel Dis-Connected");

        //return channel;
    }

    public static String SFTP_LatestUploadedFile(Channel channel) throws Exception{
        String copyFrom = filePath + "*.csv";
        String latestUploadedFileName = null;

        try {

            ChannelSftp sftpChannel = (ChannelSftp) channel;
            Vector<ChannelSftp.LsEntry> vector = (Vector<ChannelSftp.LsEntry>) sftpChannel.ls(copyFrom);
            ChannelSftp.LsEntry list = vector.get(0);
            String oldestFile =list.getFilename();
            System.out.println("Oldest File is :: "+oldestFile);

            SftpATTRS attrs=list.getAttrs();
            int currentOldestTime =attrs.getMTime();
            String nextName=null;
            ChannelSftp.LsEntry lsEntry=null;
            int nextTime;

            for (Object sftpFile : vector) {
                lsEntry = (ChannelSftp.LsEntry) sftpFile;
                nextName = lsEntry.getFilename();
                attrs = lsEntry.getAttrs();
                nextTime = attrs.getMTime();
                if (nextTime > currentOldestTime) {
                    oldestFile = nextName;
                    currentOldestTime = nextTime;
                }
            }
            latestUploadedFileName = oldestFile;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+latestUploadedFileName);

            sftpChannel.exit();

        } catch (SftpException e) {
            e.printStackTrace();
        }

        return latestUploadedFileName;
    }

    public static void SFTP_Upload(String source, String destination) throws JSchException, SftpException, InterruptedException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.put(source, destination);
        System.out.println(source+" CSV File uploaded successfully to "+ destination);
        Reporter.test.log(Status.INFO,"File uploaded to SFTP successfully to "+ destination);
        Thread.sleep(40000);
        sftpChannel.exit();
    }

    public static void SFTP_Download(String source, String destination) throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.get(source, destination);
        System.out.println("File downloaded successfully");
        Reporter.test.log(Status.PASS,"File downloaded successfully");

        sftpChannel.exit();
    }

    public static void SFTP_Disconnect() throws Exception {
        if (session != null) {
            Thread.sleep(20000);
            session.disconnect();
            System.out.println("SFTP Connection Disconnected.");
            //test.log(Status.PASS, "SFTP Connection Disconnected.");
        }
    }

    public static String SFTP_LatestFile() throws JSchException, SftpException{
        String lastFileName = null;
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;

        System.out.println("Entered >>>>>>>>");

        Vector<ChannelSftp.LsEntry> files = channelSftp.ls(filePath + "*.csv");
        ChannelSftp.LsEntry newestEntry = null;
        for (ChannelSftp.LsEntry entry : files)
        {
            if (!entry.getFilename().equals(".") && !entry.getFilename().equals(".."))
            {
                if ((newestEntry == null) ||
                        (newestEntry.getAttrs().getMTime() < entry.getAttrs().getMTime()))
                {
                    newestEntry = entry;
                }
            }
        }

        if (newestEntry != null)
        {
            lastFileName = newestEntry.getFilename();
            //test.log(Status.INFO,"Newest file is :: " + newestEntry.getFilename());
            System.out.println("Newest file is " + newestEntry.getFilename());
            System.out.println("Newest file is " + newestEntry.getFilename() + " with timestamp " + newestEntry.getAttrs().getMtimeString());


        }
        return lastFileName;
    }

    public static String newFileNameToBeUploaded1(String lastFileNameUploadedOnSFTP) throws Exception{

        String a = lastFileNameUploadedOnSFTP.substring(0,24);
        System.out.println("??????????"+a);
        String c = lastFileNameUploadedOnSFTP.substring(24, 27);

        System.out.println("File Count :: "+c);

        Integer d = Integer.parseInt(c)+1;

        System.out.println(">>>>>>>>>"+d);

        String newFileName =  a+d+".csv";
        System.out.println("Updated New File name is :: "+newFileName);

        return newFileName;
    }



}
