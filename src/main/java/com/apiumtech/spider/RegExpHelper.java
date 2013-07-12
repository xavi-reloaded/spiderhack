package com.apiumtech.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpHelper
{
	static public String getFirstMatch(String text, String pat, int group)
	{
		String result = "";
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find())
			if(matcher.groupCount() > 0)
				result = matcher.group(group);
		return result;
	}

    static public List<String> getMatches(String text, String pat, int group, boolean diff)
    {
        return getMatches(text, pat, group, diff, 0);
    }

	static public List<String> getMatches(String text, String pat, int group, boolean diff, int flags)
	{
		// Compile pattern
		List<String> matches = new ArrayList<String>();
		Pattern pattern = Pattern.compile(pat, flags);

		// Get current page links
		Matcher matcher = pattern.matcher(text);
		while(matcher.find())
		{
			String groupStr = matcher.group(group);
			int j = diff ? 0 : matches.size();
			for(; j < matches.size(); j++)
			{
				if(matches.get(j).compareTo(groupStr) == 0)
					break;
			}
			if(j == matches.size())
			{
				matches.add(groupStr);
			}
		}
		return matches;
	}

	static public List<List<String>> getMatches(String text, String pat, boolean diff)
	{
		// Compile pattern
		List<List<String>> matches = new ArrayList<List<String>>();
		Pattern pattern = Pattern.compile(pat);

		// Get matches
		if(text != null)
		{
			Matcher matcher = pattern.matcher(text);
			if(matcher.find())
			{
				do
				{
					String fullmatch = matcher.group(0);
					int j = diff ? 0 : matches.size();
					for(; j < matches.size(); j++)
					{
						if(matches.get(j).get(0).compareTo(fullmatch) == 0)
							break;
					}
					if(j == matches.size())
					{
						matches.add(new ArrayList<String>());
						for(int i = 0; i <= matcher.groupCount(); i++)
						{
							matches.get(matches.size() - 1).add(matcher.group(i));
						}
					}
				}
				while(matcher.find());
			}
		}
		return matches;
	}
}
