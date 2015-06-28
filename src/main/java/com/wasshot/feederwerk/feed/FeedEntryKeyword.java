package com.wasshot.feederwerk.feed;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A keyword used in a search query
 */
public class FeedEntryKeyword {

	public static enum Mode {
		INCLUDE, EXCLUDE;
	}

	private final String keyword;
	private final Mode mode;

    public FeedEntryKeyword(String keyword, Mode mode) {
        this.keyword = keyword;
        this.mode = mode;
    }

    public static List<FeedEntryKeyword> fromQueryString(String keywords) {
		List<FeedEntryKeyword> list = new ArrayList<>();
		if (keywords != null) {
			for (String keyword : StringUtils.split(keywords)) {
				boolean not = false;
				if (keyword.startsWith("-") || keyword.startsWith("!")) {
					not = true;
					keyword = keyword.substring(1);
				}
				list.add(new FeedEntryKeyword(keyword, not ? Mode.EXCLUDE : Mode.INCLUDE));
			}
		}
		return list;
	}

    public String getKeyword() {
        return keyword;
    }

    public Mode getMode() {
        return mode;
    }
}
