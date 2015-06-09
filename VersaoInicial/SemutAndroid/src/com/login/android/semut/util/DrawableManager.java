package com.login.android.semut.util;

/*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class DrawableManager {

	private final Map<String, Drawable> drawableMap;
	private static DrawableManager dm = null;

	private DrawableManager() {
		drawableMap = new HashMap<String, Drawable>();
	}

	public static DrawableManager getDrawableManager() {
		if (DrawableManager.dm == null) {
			DrawableManager.dm = new DrawableManager();
		}
		return DrawableManager.dm;
	}

	public Drawable getDrawable(String urlString) {
		if (drawableMap.containsKey(urlString)) {
			return drawableMap.get(urlString);
		}
		return null;
	}

	public Drawable fetchDrawable(String urlString) {
		if (drawableMap.containsKey(urlString)) {
			return drawableMap.get(urlString);
		}
		try {

			InputStream is = fetch(urlString);

			Drawable drawable = Drawable.createFromStream(is, "src");

			if (drawable != null) {
				drawableMap.put(urlString, drawable);
			} else {
				Log.w(this.getClass().getSimpleName(), "could not get thumbnail");
			}

			return drawable;
		} catch (MalformedURLException e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
			return null;
		} catch (IOException e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
			return null;
		} catch (OutOfMemoryError Err) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", Err);
			System.gc();
			return null;
		}
	}

	private InputStream fetch(String urlString) throws MalformedURLException, IOException {
		
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpGet request = new HttpGet(urlString);
		HttpResponse response = httpClient.execute(request);
		return response.getEntity().getContent();
	}
}