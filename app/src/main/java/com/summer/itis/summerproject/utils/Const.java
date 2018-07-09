package com.summer.itis.summerproject.utils;

import com.google.gson.Gson;

/**
 * Created by Ruslan on 18.02.2018.
 */

//обычный класс констант и прочего общего кода
public class Const {

    public final static int FILTER_YEAR = 1950;

    public static final String TAG_LOG = "TAG";

    // Http request
    public final static String MESSAGING_KEY = "Authorization";
    public final static String MESSAGING_TYPE = "Content-Type";

    public final static Integer PAGE_SIZE = 20;
    public final static Integer ZERO_OFFSET = 0;

    // Http response sorting
    public static final String DEFAULT_BOOK_SORT = "author";

    // Intent's constants
    public final static String ID_KEY = "id";
    public final static String NAME_KEY = "name";
    public final static String PHOTO_KEY = "photo";
    public final static String AUTHOR_KEY = "author";

    public final static String BOOK_KEY = "book";
    public final static String CROSSING_KEY = "crossing";
    public final static String USER_KEY = "user";
    public final static String POINT_KEY = "point";

    //Crossing type
    public final static String WATCHER_TYPE = "watcher";
    public final static String OWNER_TYPE = "owner";
    public final static String RESTRICT_OWNER_TYPE = "restrict_owner";
    public final static String FOLLOWER_TYPE = "follower";

    //Friend type
    public final static String ADD_FRIEND = "addF";
    public final static String REMOVE_FRIEND = "removeF";
    public final static String ADD_REQUEST = "addR";
    public final static String REMOVE_REQUEST = "removeR";

    //Friend's list types
    public final static String READER_LIST_TYPE = "type";

    public final static String READER_LIST = "readers";
    public final static String FRIEND_LIST = "friends";
    public final static String REQUEST_LIST = "requests";

    //Firebase constants
    public static final String SEP = "/";
    public static final String QUERY_END = "\uf8ff";

    public static final String QUERY_TYPE = "query";
    public static final String DEFAULT_TYPE = "default";

    //TestType
    public static final String TEST_ONE_TYPE = "test_one_type";
    public static final String TEST_MANY_TYPE = "test_many_type";

    //image path
    public static final String IMAGE_START_PATH = "images/users/";
    public static final String STUB_PATH = "stub";


    public static final Gson gsonConverter = new Gson();
    public static final String COMA = ",";

    //API
    //query
    public static final String FORMAT = "xml";
    public static final String ACTION_QUERY = "query";
    public static final String PROP = "extracts|pageimages|description";
    public static final String EXINTRO = "1";
    public static final String EXPLAINTEXT = "1";
    public static final String PIPROP = "original";
    public static final String PILICENSE = "any";
    public static final String TITLES = "Толстой, Лев Николаевич";
    //opensearch
    public static final String ACTION_SEARCH = "opensearch";
    public static final String UTF_8 = "1";
    public static final String NAMESPACE = "0";
    public static final String SEARCH = "Лев Толстой";

    //Others
    public static final int MAX_UPLOAD_RETRY_MILLIS = 60000; //1 minute

    public static class Profile {
        public static final int MAX_AVATAR_SIZE = 1280; //px, side of square
        public static final int MIN_AVATAR_SIZE = 100; //px, side of square
        public static final int MAX_NAME_LENGTH = 120;
    }


}
