package com.vasa.scheduling.weather;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;






import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class YahooWeatherParser {

    public Weather parse(String zip) throws Exception {
        
    	InputStream inputStream = retrieve( zip );
    	
    	Weather weather = new Weather();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);
        
        NodeList nodi = doc.getElementsByTagName("yweather:forecast"); 
        if (nodi.getLength() > 0) { 
        	Element nodo = (Element)nodi.item(0);
        	weather.setTodaysHigh(Integer.valueOf(nodo.getAttribute("high")).intValue());
        	weather.setTodaysLow(Integer.valueOf(nodo.getAttribute("low")).intValue());
        	weather.setTodaysCondition(nodo.getAttribute("day") + " - " +nodo.getAttribute("text"));
        	
        	nodo = (Element)nodi.item(1);
        	weather.setTomorrowsHigh(Integer.valueOf(nodo.getAttribute("high")).intValue());
        	weather.setTomorrowsLow(Integer.valueOf(nodo.getAttribute("low")).intValue());
        	weather.setTomorrowsCondition(nodo.getAttribute("day") + " - " +nodo.getAttribute("text"));
        	
        	nodo = (Element)nodi.item(2);
        	weather.setDay3Condition(nodo.getAttribute("day") + " - " +nodo.getAttribute("text"));
        	
        	nodo = (Element)nodi.item(3);
        	weather.setDay4Condition(nodo.getAttribute("day") + " - " +nodo.getAttribute("text"));
        	
        	nodo = (Element)nodi.item(4);
        	weather.setDay5Condition(nodo.getAttribute("day") + " - " +nodo.getAttribute("text"));
        }

        return weather;
    }
    
    public InputStream retrieve(String zipcode) throws Exception {
        String url = "http://weather.yahooapis.com/forecastrss?p=" + zipcode;
        URLConnection conn = new URL(url).openConnection();
        return conn.getInputStream();
    }
}




/******************


<?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
		<rss version="2.0" xmlns:yweather="http://xml.weather.yahoo.com/ns/rss/1.0" xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#">
			<channel>
		
<title>Yahoo! Weather - Van Alstyne, TX</title>
<link>http://us.rd.yahoo.com/dailynews/rss/weather/Van_Alstyne__TX/*http://weather.yahoo.com/forecast/USTX1395_f.html</link>
<description>Yahoo! Weather for Van Alstyne, TX</description>
<language>en-us</language>
<lastBuildDate>Mon, 17 Nov 2014 11:53 am CST</lastBuildDate>
<ttl>60</ttl>
<yweather:location city="Van Alstyne" region="TX"   country="US"/>
<yweather:units temperature="F" distance="mi" pressure="in" speed="mph"/>
<yweather:wind chill="30"   direction="330"   speed="13" />
<yweather:atmosphere humidity="47"  visibility="10"  pressure="30.42"  rising="2" />
<yweather:astronomy sunrise="6:56 am"   sunset="5:21 pm"/>
<image>
<title>Yahoo! Weather</title>
<width>142</width>
<height>18</height>
<link>http://weather.yahoo.com</link>
<url>http://l.yimg.com/a/i/brand/purplelogo//uh/us/news-wea.gif</url>
</image>
<item>
<title>Conditions for Van Alstyne, TX at 11:53 am CST</title>
<geo:lat>33.42</geo:lat>
<geo:long>-96.57</geo:long>
<link>http://us.rd.yahoo.com/dailynews/rss/weather/Van_Alstyne__TX/*http://weather.yahoo.com/forecast/USTX1395_f.html</link>
<pubDate>Mon, 17 Nov 2014 11:53 am CST</pubDate>
<yweather:condition  text="Fair"  code="34"  temp="38"  date="Mon, 17 Nov 2014 11:53 am CST" />
<description><![CDATA[
<img src="http://l.yimg.com/a/i/us/we/52/34.gif"/><br />
<b>Current Conditions:</b><br />
Fair, 38 F<BR />
<BR /><b>Forecast:</b><BR />
Mon - Sunny. High: 41 Low: 21<br />
Tue - Sunny. High: 51 Low: 32<br />
Wed - Sunny. High: 58 Low: 37<br />
Thu - Partly Cloudy. High: 63 Low: 50<br />
Fri - AM Showers. High: 64 Low: 53<br />
<br />
<a href="http://us.rd.yahoo.com/dailynews/rss/weather/Van_Alstyne__TX/*http://weather.yahoo.com/forecast/USTX1395_f.html">Full Forecast at Yahoo! Weather</a><BR/><BR/>
(provided by <a href="http://www.weather.com" >The Weather Channel</a>)<br/>
]]></description>
<yweather:forecast day="Mon" date="17 Nov 2014" low="21" high="41" text="Sunny" code="32" />
<yweather:forecast day="Tue" date="18 Nov 2014" low="32" high="51" text="Sunny" code="32" />
<yweather:forecast day="Wed" date="19 Nov 2014" low="37" high="58" text="Sunny" code="32" />
<yweather:forecast day="Thu" date="20 Nov 2014" low="50" high="63" text="Partly Cloudy" code="30" />
<yweather:forecast day="Fri" date="21 Nov 2014" low="53" high="64" text="AM Showers" code="39" />
<guid isPermaLink="false">USTX1395_2014_11_21_7_00_CST</guid>
</item>
</channel>
</rss>

<!-- fan1639.sports.bf1.yahoo.com Mon Nov 17 10:52:44 PST 2014 -->


**************/