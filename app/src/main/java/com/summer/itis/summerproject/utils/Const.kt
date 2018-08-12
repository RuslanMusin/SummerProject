package com.summer.itis.summerproject.utils

import com.google.gson.Gson

/**
 * Created by Ruslan on 18.02.2018.
 */

//обычный класс констант и прочего общего кода
object Const {

    const val FILTER_YEAR = 1950

    const val TAG_LOG = "TAG"

    const val MAX_LENGTH = 80
    const val MORE_TEXT = "..."

    // Http request
    const val MESSAGING_KEY = "Authorization"
    const val MESSAGING_TYPE = "Content-Type"

    const val PAGE_SIZE = 20
    const val ZERO_OFFSET = 0

    // Http response sorting
    const val DEFAULT_BOOK_SORT = "author"

    // Intent's constants
    const val ID_KEY = "id"
    const val NAME_KEY = "name"
    const val PHOTO_KEY = "photo"
    const val AUTHOR_KEY = "author"

    const val BOOK_KEY = "book"
    const val CROSSING_KEY = "crossing"
    const val USER_KEY = "user"
    const val POINT_KEY = "point"

    //Crossing type
    const val WATCHER_TYPE = "watcher"
    const val OWNER_TYPE = "owner"
    const val RESTRICT_OWNER_TYPE = "restrict_owner"
    const val FOLLOWER_TYPE = "follower"

    //Friend type
    const val ADD_FRIEND = "addF"
    const val REMOVE_FRIEND = "removeF"
    const val ADD_REQUEST = "addR"
    const val REMOVE_REQUEST = "removeR"

    //Friend's list types
    const val READER_LIST_TYPE = "type"

    const val READER_LIST = "readers"
    const val FRIEND_LIST = "friends"
    const val REQUEST_LIST = "requests"

    //Test list types
    const val OFFICIAL_LIST = "OFFICIAL"
    const val USER_LIST = "USER"
    const val MY_LIST = "MY"

    //Firebase constants
    const val SEP = "/"
    const val QUERY_END = "\uf8ff"

    const val QUERY_TYPE = "query"
    const val DEFAULT_TYPE = "default"

    //TestType
    const val TEST_ONE_TYPE = "test_one_type"
    const val TEST_MANY_TYPE = "test_many_type"

    //TestListType
    const val TEST_LIST_TYPE = "test_list"
    const val DEFAULT_ABSTRACT_TESTS ="def_abs_tests"
    const val USER_ABSTRACT_TESTS ="user_abs_tests"
    const val USER_TESTS ="user_tests"

    const val ABSTRACT_CARD_ID = "abs_card_id"
    const val USER_ID = "user_id"

    //TestRelation
    const val WIN_GAME = "win_game"
    const val WIN_AFTER_WIN = "after_win_test"
    const val LOSE_AFTER_WIN = "ore_after_test"
    const val TEST_AFTER_WIN = ""

    const val AFTER_TEST = "after_test"
    const val WIN_AFTER_TEST = "after_win_test"
    const val TEST_AFTER_TEST = "ore_after_test"
    const val LOSE_AFTER_TEST = ""

    const val LOSE_GAME = "lose_game"
    const val WIN_AFTER_LOSE = "after_win_test"
    const val LOSE_AFTER_LOSE = "ore_after_test"
    const val TEST_AFTER_LOSE = ""

    const val BEFORE_TEST = "before_test"

    //GameType
    const val OFFICIAL_TYPE = "official_type"
    const val USER_TYPE = "user_type"

    //UserType
    const val ADMIN_ROLE = "admin_role"
    const val USER_ROLE = "user_role"

    //image path
    const val IMAGE_START_PATH = "images/users/"
    const val STUB_PATH = "path"

    @JvmField
    val gsonConverter = Gson()

    const val COMA = ","

    //API
    //query
    const val FORMAT = "xml"
    const val ACTION_QUERY = "query"
    const val PROP = "extracts|pageimages|description"
    const val EXINTRO = "1"
    const val EXPLAINTEXT = "1"
    const val PIPROP = "original"
    const val PILICENSE = "any"
    const val TITLES = "Толстой, Лев Николаевич"
    //opensearch
    const val ACTION_SEARCH = "opensearch"
    const val UTF_8 = "1"
    const val NAMESPACE = "0"
    const val SEARCH = "Лев Толстой"

    //Others
    const val MAX_UPLOAD_RETRY_MILLIS = 60000 //1 minute

    object Profile {
        val MAX_AVATAR_SIZE = 1280 //px, side of square
        val MIN_AVATAR_SIZE = 100 //px, side of square
        val MAX_NAME_LENGTH = 120
    }


}
