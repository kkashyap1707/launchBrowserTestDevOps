package webLoadTest.utilities;


public final class GlobalVars {

    private static GlobalVars globalVars;

    public static GlobalVars getInstance() {
        if (globalVars == null) {
            globalVars = new GlobalVars();
        }
        return globalVars;
    }

    public String REPORT_FOLDER_ROOT_NAME = "Hello-JobReq";
    public String EXTENT_CONFIG_FILE = "extent-config.xml";
    public String CONTENT_TYPE = "application/json";


    public String getREPORT_FOLDER_ROOT_NAME() {
        return REPORT_FOLDER_ROOT_NAME;
    }

    public String getEXTENT_CONFIG_FILE() {
        return EXTENT_CONFIG_FILE;
    }

    public String getCONTENT_TYPE() {
        return CONTENT_TYPE;
    }


    public static String ORIGIN;
    public static String GET_LOGIN;
    public static String POST_LOGIN;
    public static String AUTH_USERNAME;
    public static String AUTH_PASSWORD;
    public static String OLD_ETAG_HEADER;
    public static String DASHBOARD_LIST;

    public static String REMOTE_SSH_HOST;
    public static Integer REMOTE_SSH_PORT;
    public static String REMOTE_SSH_USERNAME;
    public static String REMOTE_SSH_PASSWORD;
    public static String REMOTE_MYSQL_HOST;
    public static Integer REMOTE_MYSQL_PORT;
    public static String REMOTE_MYSQL_USERNAME;
    public static String REMOTE_MYSQL_PASSWORD;

    public static String BASE_URL;
    public static String BASE_URL_DEV;
    public static String BASE_URL_STAGE;
    public static String PROJECT_NAME;
    public static String MOBILE_NUMBER;
    public static String HOST;
    public static Integer PORT;
    public static String SFTP_AUTH_USERNAME;
    public static String SFTP_AUTH_PASSWORD;

    public static String DATABASE_NAME;
    public static String DATABASE_URL;
    public static String DATABASE_USERNAME;
    public static String DATABASE_PASSWORD;

    public static String UPDATE_DATABASE_URL;
    public static String UPDATE_DATABASE_USERNAME;
    public static String UPDATE_DATABASE_PASSWORD;


    public static String SPREADSHEETURL;
    public static String SPREADSHEET_ID;
    public static String UPLOAD_PROGRESS_CHECK_URL;

    public static String SHEET_NAME;

    public static String USERNAME;
    public static String PASSWORD;

    public static String SFTP_LOCATION;

    public static String EMAIL_ID;

    public static String RECEIVER;
    public static String SENDER;
    public static String SUBJECT;
    public static String BODY;
    public static String fullFilePath;

    public static String UPLOAD_PROGRESS_URL;
    public static String UPLOAD_PROGRESS_USERNAME;
    public static String UPLOAD_PROGRESS_PASSWORD;
    public static String UPLOAD_PROGRESS_SFTP_USERNAME;
    public static String FIRST_INTERVIEWER_EMAIL;

    public static class v1 {

    }

    public static class V2 {

    }


    public static String SystemId;
    public static String createdDate;
    public static String updatedDate;
    public static String applyURL;
    public static String noOfPeople;
    public static String noOfPeopleNeededRemaining;
    public static String category;
    public static String additionalRequirements;
    public static String additionalLicensesNotListed;
    public static String employmentType;
    public static String jobFolderStatus;
    public static String licensesCertification;
    public static String orientationLocation;
    public static String otherSpecialRequirements;
    public static String physicalRequirements;
    public static String positionId;
    public static String postingTitle;
    public static String winTeamPositionTitle;
    public static String requisitionId;
    public static String securityClearance;
    public static String shiftsAvailable;
    public static String hiringManagerSystemID;
    public static String hiringManageFullName;
    public static String hiringManagerEmail;
    public static String hiringManagerPhone;
    public static String hiringManagerAddress;
    public static String hiringManagerLoginGroup;
    public static String additionalBranchReviewRequired;
    public static String additionalReqReviewerSystemId;
    public static String additionalReqReviewerFullName;
    public static String additionalReqReviewerEmail;
    public static String additionalReqReviewerPhone;
    public static String additionalReqReviewerHomeAddress;
    public static String primaryRecruiterSystemID;
    public static String primaryRecruiterFullName;
    public static String primaryRecruiterEmail;
    public static String primaryRecruiterPhone;
    public static String primaryRecruiterHomeAddress;
    public static String interviewLocationAddress;
    public static String administrativeSupportSystemID;
    public static String administrativeSupportFullName;
    public static String administrativeSupportEmail;
    public static String administrativeSupportPhone;
    public static String administrativeSupportHomeAddress;
    public static String postingLocationLocation;
    public static String postingLocationAddress;
    public static String postingLocationAddress2;
    public static String postingLocationCity;
    public static String postingLocationState;
    public static String postingLocationPostalCode;
    public static String postingLocationCountry;
    public static String requisitionPriority;
    public static String videoInterviewingPackage;
    public static String firstInterviewerSystemID;
    public static String firstInterviewerFullName;
    public static String firstInterviewerEmail;
    public static String firstInterviewerPhone;
    public static String firstInterviewerHomeAddress;
    public static String preEmploymentRequirements;
    public static String additionalQualifications;
    public static String jobOfficeAddress1;
    public static String jobOfficeAddress2;
    public static String jobOfficeAddressCity;
    public static String jobOfficeAddressState;
    public static String jobOfficeAddressZip;
    public static String jobOfficeAddressLocationName;
    public static String jobOfficeAddressEmail;
    public static String BranchOfficeLocationHireVueEnabled;


}