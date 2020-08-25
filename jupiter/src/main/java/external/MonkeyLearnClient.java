package external;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;


public class MonkeyLearnClient {
	private static final String API_KEY = "b7403c347b4393b8dae29c6bac57388055d4a816";// make sure change it to your api key.

	public static void main(String[] args) {
		
		String[] textList = {
				"About the position</strong></h2>\n<p>You love web application development and you enjoy seeing businesses grow. You've been in the business for a few years now. You’ve done the whole startup thing and you're ready to find something a little more meaty. Working as one of many developers isn't appealing to you anymore and the commute to San Francisco isn't doing it for you either. You're ready to be part of a team, and you want to own the product vision. You love making great things used by people, not just creating code, but contributing and leading projects to success.</p>\n<p>Helio has shown 20% quarter over quarter sales growth, and we need a senior engineer to shape and grow <a href=\"http://helio.app/\">our Helio platform</a>. Specifically we’re looking for a full stack engineer (Front-end -&gt; Ruby on Rails -&gt; Networking/AWS) who wants to own the vision. We're looking for a driven individual to push development forward, work with our active and passionate customers, and take Helio to the next level.</p>\n<p><strong>Who we are</strong></p>\n<p>We're a team of driven product designers who believe in rapid prototyping, researching on the fly and teaching people how to make better products. We've been around since 1998, creating world-class products that hundreds of millions of people actually use. We believe Design, with a capital \"D\", can change the world, and we’re making it happen. You’ll have the opportunity to build better products that solve problems and meet our customers needs.</p>\n<p>We want to change the way people create products and services to bring behavioral data into everyday creative decisions.</p>\n<p><strong>What Your Day Will Look Like</strong></p>\n<p>Working closely with the team, you’ll spend time planning and developing on the roadmap. Some days you’ll be working on a new feature. Other days you will be deep into code, addressing architecture or fixing a tricky bug. All the while, working with the team to galvanize the momentum we’ve created with our customers.</p>\n<p>You’ll work with our awesome team to learn Progressive Design — ZURB’s flagship design process — and how it applies to Helio. At the end of the day, you'll find that you are more than just a developer, you’re working with a world-class design organization, helping millions of people design for people and making a difference in the product community -- and that's pretty awesome.</p>\n<h3><strong>What You Bring To The Table</strong></h3>\n<ul>\n<li>5-10 years of development experience working in a startup, independent contractor, or similar environment.</li>\n<li>Should be comfortable in the full stack (Front-end -&gt; Ruby on Rails -&gt; Networking/AWS). You’ll need a solid grasp of Ruby on Rails.</li>\n<li>Very comfortable with SQL (We use PostgreSQL and MySQL).</li>\n<li>Very comfortable with at least one modern front-end framework (React, VueJS, Angular). We use VueJS and AngularJS as well as Stimulus.</li>\n<li>Should be familiar with common software design patterns (observer pattern, facade pattern) .</li>\n<li>Should be familiar with server security updates, <a href=\"https://www.ruby-lang.org/en/\">Ruby security patches</a>, and support third party audits.</li>\n<li>Familiarity with AWS (EC2/RDS/VPC/IAM/etc).</li>\n<li>Familiarity with video encoding and prototyping tools.</li>\n<li>Familiarity with CI/CD (Continuous Integration/Continuous Deploy), we use CircleCI.</li>\n<li>Familiarity with generating financial reports, participant payouts and dealing with GDPR requests for removal.</li>\n</ul>\n<h3><strong>Other skills we’re looking for</strong></h3>\n<ul>\n<li>Natural sense of curiosity, and maintains a strong point of view. You enjoy sharing your ideas and listening to the ideas of your teammates. You write well.</li>\n<li>Experience delivering solid products over and over again.</li>\n<li>An enthusiasm for coaching and teaching people on our team.</li>\n</ul>\n<h3><strong>A little more about us</strong></h3>\n<p>ZURB is a close-knit product design team based in Campbell, CA. We've been around since 1998, have helped more than 500 startups design some kick-ass online products and created many of our own products, like Foundation. Our customers range from large, well-known companies (Netflix, Hulu, eBay, Samsung, Mozilla, Facebook, Columbia) to start-ups. They all came to us because of our reputation for delivering killer ideas that work", };
		List<List<String>> words = extractKeywords(textList);
		for (List<String> ws : words) {
			for (String w : ws) {
				System.out.println(w);
			}
			System.out.println();
		}
	}

	public static List<List<String>> extractKeywords(String[] text){
		if(text == null || text.length == 0)  return new ArrayList<>();
			
		MonkeyLearn ml = new MonkeyLearn(API_KEY);
	
		ExtraParam[] extraParams = {new ExtraParam("max_keywords", "3")};
		MonkeyLearnResponse response;
		
		try {
			response = ml.extractors.extract("ex_YCya9nrn", text, extraParams);
			JSONArray resultArray = response.arrayResult;
			return getKeywords(resultArray);
			
		} catch (MonkeyLearnException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}
	

	private static List<List<String>> getKeywords(JSONArray mlResultArray) {
		List<List<String>> topKeywords = new ArrayList<>();
		
		// Iterate the result array and convert it to our format.
		for (int i = 0; i < mlResultArray.size(); ++i) {
			List<String> keywords = new ArrayList<>();
			JSONArray keywordsArray = (JSONArray) mlResultArray.get(i);
			
			for (int j = 0; j < keywordsArray.size(); ++j) {
				JSONObject keywordObject = (JSONObject) keywordsArray.get(j);
				// We just need the keyword, excluding other fields.
				String keyword = (String) keywordObject.get("keyword");
				keywords.add(keyword);

			}
			
			topKeywords.add(keywords);
		}
		
		return topKeywords;
	}

}
