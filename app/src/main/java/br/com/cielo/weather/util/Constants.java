package br.com.cielo.weather.util;

/**
 * Created by Gabriel Aguido Fraga on 31/08/15.
 */
public class Constants {


    private Constants() {
    }

    public static class Mongo {
        public static final String MONGO_IP = "10.82.252.152";
        public static final String MONGO_USERNAME = "developer";
        public static final String MONGO_DATABASE_NAME = "dev-test";
        public static final String MONGO_PASSWORD = "C!elo_dev";
        public static final int MONGO_PORT = 27017;
        public static final String MONGO_APPLICATIONS_COLLECTION = "score";
        public static final String MONGO_TESTRUNS_COLLECTION = "testRuns";
        public static final String MONGO_ID_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00";
        public static final String MONGO_APP_LIST = "MONGO APPLICATION LIST";
        public static final String MONGO_ACCESS = "MONGO DB ACCESS";
        public static final String IMPOSSIBLE_CONNECT_MONGO = "Não foi possível se conectar ao servidor do mongo. Tente novamente.";
        public static final String ERROR_MONGO_DOCUMENT = "ERROR ON MONGO DOCUMENT";
        public static final String GTE = "$gte";
        public static final String _ID = "_id";
        public static final String DATE = "date";
        public static final int systems = 17;
        public static final int systemsHistory = 10;
    }

    public static class SharedPreferences {
        public static final String USER_PREFERENCES = "user_preferences";
        public static final String JIRA_USER = "JIRA_USER";
        public static final String LAST_OK_APPLICATIONS = "LAST_OK_APPLICATIONS";
        public static final String SYSTEM_NUMBERS = "SYSTEM_NUMBERS";
        public static final String SYSTEM_NAME = "SYSTEM_NAME";
        public static final String SHARED_PREFERENCES = "SHARED PREFERENCES";
        public static final String SAVE_LAST_OK_APPS = "Saved on LAST_OK_APPLICATIONS";
        public static final String SAVE_SCORE_HISTORY = "Saved every score history";
        public static final String APP_HISTORY = "APP_HISTORY";
        public static final String CENTRALIZADOR_HISTORY = "CENTRALIZADOR_HISTORY";
        public static final String CIELO_PAY_PTO_CORE_HISTORY = "CIELO_PAY_PTO_CORE_HISTORY";
        public static final String CREDENCIAMENTO_HISTORY = "CREDENCIAMENTO_HISTORY";
        public static final String FAC_HISTORY = "FAC_HISTORY";
        public static final String FAROL_HISTORY = "FAROL_HISTORY";
        public static final String PFI_HISTORY = "PFI_HISTORY";
        public static final String RECEBA_MAIS_HISTORY = "RECEBA_MAIS_HISTORY";
        public static final String SIGO_HISTORY = "SIGO_HISTORY";
        public static final String SITE_HISTORY = "SITE_HISTORY";
        public static final String SKYLINE_HISTORY = "SKYLINE_HISTORY";
        public static final String STAR_HISTORY = "STAR_HISTORY";
        public static final String STC_HISTORY = "STC_HISTORY";
        public static final String VSC_HISTORY = "VSC_HISTORY";
        public static final String CAN_HISTORY = "CAN_HISTORY";
        public static final String NGC_HISTORY = "NGC_HISTORY";
        public static final String POS_HISTORY = "POS_HISTORY";
        public static final String HYAKU_HISTORY = "HYAKU_HISTORY";
    }

    public static class Systems {

        public static final String STAR = "STAR";
        public static final String CREDENCIAMENTO = "Credenciamento";
        public static final String RECEBA_MAIS = "Receba Mais";
        public static final String CIELO_PAY_PTO_CORE = "Cielo Pay - PTO Core";
        public static final String FAC = "FAC";
        public static final String APP_CIELO = "App Cielo";
        public static final String SITE = "Site";
        public static final String VSC = "VSC";
        public static final String CENTRALIZADOR_ACC_RANGE = "Centralizador Acc Range";
        public static final String FAROL = "Monetização de Dados";
        public static final String STC = "STC";
        public static final String PFI = "PFI";
        public static final String SKYLINE = "Skyline";
        public static final String SIGO = "SIGO";
        public static final String POS = "POS";
        public static final String CANCELAMENTO = "Cancelamento";
        public static final String NGC = "NGC";
        public static final String HYAKU = "Hyaku";

    }

    public static class Wifi {
        public static final String SIGMA = "SIGMA";
        public static final String VISANET_CORP = "visanet.corp";
    }

    public static final int PASSWORD_TEXT_SIZE = 6;
    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    public static class Log {


        public static final String EXCEPTION_ERROR = "Exception Error";
    }
}
