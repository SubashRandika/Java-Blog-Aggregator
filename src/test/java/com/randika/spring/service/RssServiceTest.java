
package com.randika.spring.service;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.randika.spring.entity.Item;
import com.randika.spring.exception.RssException;

public class RssServiceTest {
    
    private RssService rssService;
    
    @Before
    public void setUp() throws Exception {
    
        rssService = new RssService();
    }
    
    @Test
    public void testGetItemsFile() throws RssException, ParseException {
    
        List<Item> items = rssService.getItems(new File("test-rss/javavids.xml"));
        assertEquals(10, items.size());
        
        Item firstItem = items.get(0);
        assertEquals("How to solve Source not found error during debug in Eclipse", firstItem.getTitle());
        
        String pubDate = new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate());
        assertEquals("23 06 2014 02:05:49", pubDate);
    }
    
}
