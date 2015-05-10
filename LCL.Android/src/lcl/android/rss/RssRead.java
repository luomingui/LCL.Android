package lcl.android.rss;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.util.Log;

public class RssRead {

	public List<Message> ReadloadRss(InputStream inputStream) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			RssHandler personparser = new RssHandler();
			parser.parse(inputStream, personparser);
			inputStream.close();
			return personparser.getMessages();
		} catch (Exception e) {
			Log.i("debug",
					"RssRead ReadloadRss InputStream : " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
