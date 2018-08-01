package in.lib;

public class Constants
{
	// API Constants //
	public static final String API_URL = "https://api.pnut.io/v0/";
	public static final String API_AUTH = "https://api.pnut.io/v0/oauth/access_token";

	public static final String API_POSTS = "posts";
	public static final String API_TIMELINE_STREAM = "posts/streams/me";
	public static final String API_UNIFIED_TIMELINE_STREAM = "posts/streams/unified";
	public static final String API_POST_THREAD = "posts/%s/thread";
	public static final String API_POST_REPOST = "posts/%s/repost";
    public static final String API_POST_BOOKMARK = "posts/%s/bookmark";
	public static final String API_SEARCH_POST = "posts/search";

	//private static final String API_USERS = "users";
    public static final String API_USER_FOLLOW = "users/%s/follow";
	public static final String API_USER_DETAILS = "users/%s/";
	public static final String API_USER_MENTIONS = "users/%s/mentions";
	public static final String API_USER_POSTS = "users/%s/posts";
	public static final String API_USER_FOLLOWERS = "users/%s/followers";
	public static final String API_USER_FOLLOWING = "users/%s/following";
	public static final String API_USER_FILES = "users/%s/files";
	public static final String API_USER_BOOKMARKED = "users/%s/bookmarks";
	public static final String API_USER_ACTIONS = "users/me/actions";
	public static final String API_SEARCH_USER = "users/search";

	public static final String API_CHANNELS = "users/me/channels/subscribed";
	public static final String API_CHANNEL_MESSAGES = "channels/%s/messages";
	//public static final String API_CHANNEL_SUBSCRIBE = "channels/%s/subscribe";
	//public static final String API_CHANNEL_MESSAGE_DETAILS = API_CHANNEL_MESSAGES + "/%s";

	public static final String API_SCOPES = "stream write_post follow messages:io.pnut.core.pm messages:io.pnut.core.chat update_profile files";
	public static final String API_SCOPES_CSV = "stream,write_post,follow,messages:io.pnut.core.pm,messages:io.pnut.core.chat,update_profile,files";

	public static final String API_COUNT = "count";
	public static final String API_BEFORE_ID = "before_id";
	public static final String API_SINCE_ID = "since_id";
	public static final String API_ACCESS_TOKEN = "access_token";
	public static final String API_INCLUDE_DELETED = "include_deleted";
	public static final String API_INCLUDE_RAW = "include_raw";
	public static final String API_INCLUDE_POST_RAW = "include_post_raw";
	public static final String API_INCLUDE_CHANNEL_RAW = "include_channel_raw";
    public static final String API_INCLUDE_MESSAGE_RAW = "include_channel_raw";
	public static final String API_INCLUDE_DIRECTED_POSTS = "include_directed_posts";
	public static final String API_INCLUDE_BOOKMARKED = "include_bookmarked_by";
	public static final String API_INCLUDE_REPOSTERS = "include_reposted_by";
    public static final String API_UPDATE_MARKER = "update_marker";
	public static final String API_REPLY_TO = "reply_to";
	public static final String API_REPOST = "repost_of";
	public static final String API_TEXT = "text";
	public static final String API_CHANNEL_ID = "channel_id";
	//public static final String API_NAME = "name";
	//public static final String API_DESC = "content";
	//public static final String API_LOCALE = "locale";
	//public static final String API_TIMEZONE = "timezone";
	public static final String API_CHANNEL_TYPES = "channel_types";
	public static final String API_ALLOW_RECENT_MESSAGE = "include_recent_message";
	public static final String API_ACTIONS = "filters";

	public static final String CLIENT_TOKEN = "";
	public static final String PASSWORD_GRANT = "";

	public static final String CHANNEL_TYPES = "io.pnut.core.pm,io.pnut.core.chat";

	// Preference constants //
	public static final String PREFS_AUTH = "in.pnutrobin.client.auth";
	public static final String PREFS_VERSION = "version";
	public static final String PREFS_AUTH_USER_ACCESS_TOKEN = PREFS_AUTH + "user_%s_access_token";
	public static final String PREFS_AUTH_SELECTED_USER = PREFS_AUTH + "selected_user";
	public static final String PREFS_USERNAME_TITLE = "user_title";
	public static final String PREFS_COLLAPSED_THREADS = "collapsed_threads";
	public static final String PREFS_SHAKE_REFRESH_ENABLED = "shake_refresh_enabled";
	public static final String PREFS_QUICK_POST_ENABLED = "quick_post_enabled";
	public static final String PREFS_INLINE_WIFI_ENABLED = "inline_image_wifi_enabled";
	public static final String PREFS_STREAM_MARKERS = "stream_markers";
	public static final String PREFS_SINGLE_CLICK_OPTIONS = "single_click_options";
	public static final String PREFS_SHOWHIDE_OPTIONS = "showhide_options";
	public static final String PREFS_IN_APP_VIEWER_OPTIONS = "in_app_viewer_options";
	public static final String PREFS_WEB_READABILITY_MODE_ENABLED = "web_readbility_mode_enabled";
	public static final String PREFS_NON_FOLLOWING_MENTIONS_ENABLED = "non_following_mentions_enabled";
    public static final String PREFS_UNIFIED_TIMELINE_ENABLED = "unified_timeline_enabled";
	public static final String PREFS_EMPHASIS_OPTIONS = "emphasis_options";

	// Response constants //
	public static final String RESPONSE_TIMELINE = "timeline_%s";
	public static final String RESPONSE_MENTIONS = "mentions_%s";
	public static final String RESPONSE_FOLLOWERS = "followers_%s";
	public static final String RESPONSE_FOLLOWINGS = "followings_%s";
	public static final String RESPONSE_USER_STREAM = "user_%s_stream";
	//public static final String RESPONSE_USER = "user_%s";
	public static final String RESPONSE_THREAD = "thread_%s";
	public static final String RESPONSE_CHANNELS = "channels_%s";
	public static final String RESPONSE_CHANNEL_MESSAGES = "channels_messages_%s";
	public static final String RESPONSE_INTERACTIONS = "actions";
	public static final String RESPONSE_BOOKMARKED = "user_%s_bookmarked";
	public static final String RESPONSE_SEARCH_POST = "search_%s";
	public static final String RESPONSE_SEARCH_USER = "search_user_%s";

	// Request constants //
	public static final int REQUEST_CODE_AUTHORIZE = 0x01;

	// Cache constants //
	public static final String CACHE_TIMELINE = "timeline_%s";
	public static final String CACHE_MENTIONS = "mentions_%s";
	public static final String CACHE_FOLLOWERS = "followers_%s";
	public static final String CACHE_FOLLOWINGS = "followings_%s";
	public static final String CACHE_USER_STREAM = "user_%s_stream";
	//public static final String CACHE_USER = "user_%s";
	//public static final String CACHE_THREAD = "thread_%s";
	public static final String CACHE_CHANNELS = "channels_%s";
	public static final String CACHE_CHANNEL_MESSAGES = "channel_messages_%s";
	public static final String CACHE_INTERACTIONS = "actions";
	public static final String CACHE_STARRED = "user_%s_bookmarked";
	public static final String CACHE_AUTOCOMPLETE_USERNAMES = "autocomplete_usernames";
	public static final String CACHE_AUTOCOMPLETE_HASHTAGS = "autocomplete_hashtags";

	// Extra constants //
	public static final String EXTRA_ADAPTER_LIST = "adapter_list";
	public static final String EXTRA_USER = "user";
	public static final String EXTRA_POST = "post";
	public static final String EXTRA_TITLE = "title";
	public static final String EXTRA_REPLY_ALL = "reply_all?";
	public static final String EXTRA_REPLY_EXTRA = "reply_extra";
	public static final String EXTRA_FILE = "file";
	public static final String EXTRA_CHANNEL = "channel";
	public static final String EXTRA_CHANNEL_ID = "channel_id";
	public static final String EXTRA_POST_ID = "post_id";
	public static final String EXTRA_USER_ID = "user_id";
	public static final String EXTRA_START_PAGE = "start_page";
	public static final String EXTRA_SEARCH_TERM = "search_term";
	public static final String EXTRA_DRAFT_POST = "draft_post";
	public static final String EXTRA_SEND = "force_send";
	public static final String EXTRA_NEW_USER = "new_user";
	public static final String EXTRA_FINISH = "finish";
	public static final String EXTRA_PREVIEW_URL = "preview_url";

	public static final int QUICK_POST_ID = 0x83198712;

	// Request constants //
	public static final int REQUEST_STORAGE = 0x001;
	public static final int REQUEST_GALLERY = 0x002;
	public static final int REQUEST_CAMERA = 0x003;
	public static final int REQUEST_ADD_ACCOUNT = 0x004;

	// Bit constants //
	public static final int BIT_STREAM_MARKER_ENABLED = 0x0000001;
	public static final int BIT_STREAM_MARKER_PAST = 0x0000002;

	public static final int BIT_SINGLE_CLICK_HASHTAG = 0x0000001;
	public static final int BIT_SINGLE_CLICK_MENTIONS = 0x0000002;
	public static final int BIT_SINGLE_CLICK_URLS = 0x0000004;

	public static final int BIT_SHOWHIDE_AVATARS = 0x0000001;
	public static final int BIT_SHOWHIDE_INLINE_IMAGES = 0x0000002;
	public static final int BIT_SHOWHIDE_TIMELINE_COVER = 0x0000004;

	public static final int BIT_IN_APP_VIEWER_BROWSER = 0x0000001;
	public static final int BIT_IN_APP_VIEWER_IMAGE = 0x0000002;
	public static final int BIT_IN_APP_VIEWER_YOUTUBE = 0x0000004;

	public static final int BIT_EMPHASIS_ITALIC = 0x0000001;
	public static final int BIT_EMPHASIS_BOLD = 0x0000002;
	public static final int BIT_EMPHASIS_UNDERLINE = 0x0000004;

	// Actions //
	//public static final String ACTION_NEW_POST = "in.pnutrob.posting.new";
	//public static final String ACTION_DELETE_POST = "in.pnutrob.posting.delete";
	//public static final String ACTION_REPOST = "in.pnutrob.posting.repost";
	//public static final String ACTION_STAR_POST = "in.pnutrob.posting.star";
	//public static final String ACTION_NEW_MESSAGE = "in.pnutrob.posting.new_message";
	//public static final String ACTION_DELETE_MESSAGE = "in.pnutrob.posting.delete_message";
	//public static final String ACTION_NEW_CHANNEL = "in.pnutrob.posting.new_channel";

	public static final String ACTION_INTENT_NEW_POST = "in.pnutrob.client.alpha.widget.intent.NEW_POST";
	public static final String ACTION_INTENT_OPEN_APP = "in.pnutrob.client.alpha.widget.intent.OPEN_APP";
	public static final String ACTION_INTENT_THREAD = "in.pnutrob.client.alpha.widget.intent.THREAD";
	public static final String ACTION_INTENT_REFRESH = "in.pnutrob.client.alpha.widget.intent.REFRESH";
	public static final String ACTION_INTENT_RELOAD = "in.pnutrob.client.alpha.widget.intent.RELOAD";
}
