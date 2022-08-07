package kynv1.fsoft.appmoviefinally.utils;


public class Constance {
    public static final String TAG = "logger";

    public static final String PERSON = "person";
    public static final String AVATAR = "avatar";
    public static final String NAME = "userName";
    public static final String EMAIL = "userEmail";
    public static final String BIRTHDAY = "birthday";
    public static final String SEX = "sex";

    public static final String KEY_POPULAR = "popular";
    public static final String KEY_TYPE_MOVIE = "typeMovie";

    public static final String KEY_TITLE_MOVIE = "titlePopular";
    public static final int VALUE_TITLE_POPULAR_MOVIE = 1;
    public static final int VALUE_TITLE_TOP_RATE_MOVIE = 2;
    public static final int VALUE_TITLE_UPCOMING_MOVIE = 3;
    public static final int VALUE_TITLE_NOW_PLAYING_MOVIE = 4;
    public static final String TITLE_POPULAR_MOVIE = "Popular Movies";
    public static final String TITLE_TOP_RATE_MOVIE = "Top Rate Movies";
    public static final String TITLE_UPCOMING_MOVIE = "Upcoming Movies";
    public static final String TITLE_NOW_PLAYING_MOVIE = "Now Playing Movies";
    public static final String SORT_BY_DATE = "Release date:";
    public static final String SORT_BY_RATE = "Rating";



    // URL
    public static final String BASE_URL = "http://api.themoviedb.org/";

    public static final String KEY_VALUE_MOVIE = "e7631ffcb8e766993e5ec0c1f4245f93";
    public static final String KEY_MOVIE_POPULAR = "3/movie/popular";

    public static final String KEY_RATE_MOVIE = "3/movie/top_rated";
    public static final String KEY_UP_COMING = "3/movie/upcoming";
    public static final String KEY_NOW_PLAY = "3/movie/now_playing";
    public static final String KEY_CAST_CREW_LIST = "3/movie/{movieId}/credits";
    public static final String KEY_PATH = "movieId";

    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    // Database
    public static final String DATABASENAME = "databae_movie";
    public static final String TABLE_NAME = "table_movie";
    public static final String TABLE_REMINDER = "reminder";

    public static final String KEY_FILTER = "setting";
    public static final String KEY_FILTER_CATEGORY = "category";
    public static final String KEY_FILTER_MOVIE_WITH_RATE_FROM = "movieWithRateFrom";
    public static final String KEY_FILTER_FROM_RELEASE = "fromRelease";
    public static final String KEY_FILTER_SORT_BY = "RelaseDate";
}
