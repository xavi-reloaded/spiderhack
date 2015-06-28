package com.wasshot.feederwerk;

import org.apache.http.*;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

class ContentEncodingInterceptor implements HttpResponseInterceptor {
	
	private static final Set<String> ALLOWED_CONTENT_ENCODINGS = new HashSet<>(Arrays.asList("gzip", "x-gzip", "deflate", "identity"));

	@Override
	public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
		if (hasContent(response)) {
			Header contentEncodingHeader = response.getEntity().getContentEncoding();
			if (contentEncodingHeader != null && containsUnsupportedEncodings(contentEncodingHeader)) {
				overrideContentEncoding(response);
			}
		}
	}
	
	private boolean containsUnsupportedEncodings(Header contentEncodingHeader) {
		HeaderElement[] codecs = contentEncodingHeader.getElements();
		
		for (final HeaderElement codec : codecs) {
			String codecName = codec.getName().toLowerCase(Locale.US);
			if (!ALLOWED_CONTENT_ENCODINGS.contains(codecName)) {
				return true;
			}
		}
		
		return false;
	}

	private void overrideContentEncoding(HttpResponse response) {
		HttpEntity wrapped = new HttpEntityWrapper(response.getEntity()) {
			@Override
			public Header getContentEncoding() {
				return null;
			};
		};
		
		response.setEntity(wrapped);
	}

	private boolean hasContent(HttpResponse response) {
		return response.getEntity() != null && response.getEntity().getContentLength() != 0;
	}

}
