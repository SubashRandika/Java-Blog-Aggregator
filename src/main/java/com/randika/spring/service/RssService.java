
package com.randika.spring.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import com.randika.spring.entity.Item;
import com.randika.spring.exception.RssException;
import com.randika.spring.rss.ObjectFactory;
import com.randika.spring.rss.TRss;
import com.randika.spring.rss.TRssChannel;
import com.randika.spring.rss.TRssItem;

@Service
public class RssService {
    
    public List<Item> getItems(File file) throws RssException, ParseException {
    
        return getItems(new StreamSource(file));
    }
    
    public List<Item> getItems(String url) throws RssException, ParseException {
    
        return getItems(new StreamSource(url));
    }
    
    private List<Item> getItems(Source source) throws RssException, ParseException {
    
        ArrayList<Item> itemList = new ArrayList<Item>();
        
        try {
            
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
            TRss rss = jaxbElement.getValue();
            List<TRssChannel> channels = rss.getChannel();
            
            for (TRssChannel channel : channels) {
                
                List<TRssItem> items = channel.getItem();
                
                for (TRssItem rssItem : items) {
                    
                    Item item = new Item();
                    item.setTitle(rssItem.getTitle());
                    item.setDescription(rssItem.getDescription());
                    item.setLink(rssItem.getLink());
                    Date pubDate =
                            new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss Z", Locale.ENGLISH).parse(rssItem
                                    .getPubDate());
                    item.setPublishedDate(pubDate);
                    itemList.add(item);
                    
                }
            }
            
        } catch (JAXBException jbe) {
            throw new RssException(jbe);
        } catch (ParseException pe) {
            throw new RssException(pe);
        }
        
        return itemList;
    }
    
}
