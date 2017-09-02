package in.lib.manager;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import in.pnutrob.client.alpha.R;
import lombok.Getter;

public class ImageOptionsManager
{
	@Getter private final DisplayImageOptions avatarImageOptions;
	@Getter private final DisplayImageOptions coverImageOptions;
	@Getter private final DisplayImageOptions mediaImageOptions;
	@Getter private final DisplayImageOptions inlineMediaImageOptions;
	@Getter private final DisplayImageOptions centerPostMediaOptions;
	@Getter private final DisplayImageOptions threadAvatarImageOptions;

	private static ImageOptionsManager instance;

	public static ImageOptionsManager getInstance()
	{
		if (instance == null)
		{
			synchronized (ImageOptionsManager.class)
			{
				if (instance == null)
				{
					instance = new ImageOptionsManager();
				}
			}
		}

		return instance;
	}

	private ImageOptionsManager()
	{
		ImageFader avatarFader = new ImageFader(400);

		Builder avatarImageOptionsBuilder = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.displayer(avatarFader)
			.bitmapConfig(Config.RGB_565)
			.showImageOnLoading(R.drawable.default_avatar)
			.showImageForEmptyUri(R.drawable.default_avatar)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.resetViewBeforeLoading(true);

		Builder coverImageOptionsBuilder = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.showImageOnLoading(R.drawable.default_cover)
			.showImageForEmptyUri(R.drawable.default_cover)
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.resetViewBeforeLoading(true);

		Builder threadAvatarImageOptionsBuilder = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.bitmapConfig(Config.RGB_565)
			.showImageOnLoading(R.drawable.default_avatar)
			.showImageForEmptyUri(R.drawable.default_avatar)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.resetViewBeforeLoading(true);

		Builder mediaImageOptionsBuilder = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.bitmapConfig(Config.RGB_565)
			.resetViewBeforeLoading(true)
			.cacheInMemory(true)
			.cacheOnDisc(true);

		Builder inlineMediaImageOptionsBuilder = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.bitmapConfig(Config.RGB_565)
			.resetViewBeforeLoading(true)
			.displayer(avatarFader)
			.cacheInMemory(true);

		Builder centerPostMediaOptionsBuilder = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.bitmapConfig(Config.RGB_565)
			.cacheInMemory(true);

		avatarImageOptions = avatarImageOptionsBuilder.build();
		coverImageOptions = coverImageOptionsBuilder.build();
		threadAvatarImageOptions = threadAvatarImageOptionsBuilder.build();
		mediaImageOptions = mediaImageOptionsBuilder.build();
		inlineMediaImageOptions = inlineMediaImageOptionsBuilder.build();
		centerPostMediaOptions = centerPostMediaOptionsBuilder.build();
	}

	private static class ImageFader extends FadeInBitmapDisplayer
	{
		public ImageFader(int delay)
		{
			super(delay);
		}

		/*@Override public Bitmap display(Bitmap bitmap, ImageView imageView, LoadedFrom loadedFrom)
		{
			if (loadedFrom != LoadedFrom.MEMORY_CACHE)
			{
				return super.display(bitmap, imageView, loadedFrom);
			}
			else
			{
				imageView.setImageBitmap(bitmap);
				return bitmap;
			}
		}*/
	}
}
