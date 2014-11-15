package org.springframework.social.tumblr.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.tumblr.api.BlogPostsOperations;
import org.springframework.social.tumblr.api.Post;
import org.springframework.social.tumblr.api.Posts;
import org.springframework.social.tumblr.api.PostsQuery;
import org.springframework.social.tumblr.api.PostsQueryFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class BlogPostsTemplate extends AbstractBlogOperations implements BlogPostsOperations {
    public BlogPostsTemplate(RestTemplate restTemplate, boolean isAuthorized, String apiKey, String blogHostname) {
        super(restTemplate, isAuthorized, apiKey, blogHostname);
    }

    public List<Post> getPosts() {
    	requireApiKey();
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("api_key", getApiKey());
        
        Map<String, Object> response = getRestTemplate().getForObject(buildUri("posts", "api_key", getApiKey()).toString(), Map.class);
        return (List<Post>)response.get("posts");
    }

    public Posts search(PostsQuery query) {
        requireApiKey();
        if (query == null) {
            query = new PostsQuery();
        }
        return getRestTemplate().getForObject(buildUri("posts", "api_key", getApiKey(), query.toParameterMap()).toString(), Posts.class);
    }

    public Posts queue() {
        return queue(20, 0, null);
    }

    public Posts queue(int limit, int offset, PostsQueryFilter filter) {
        requireAuthorization();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>(3);
        params.add("limit", Integer.toString(limit));
        params.add("offset", Integer.toString(offset));
        params.add("filter", filter == null ? null : filter.getValue());
        return getRestTemplate().postForObject(buildUri("posts/queue", params), null, Posts.class);
    }

    public Posts draft() {
        return draft(null);
    }

    public Posts draft(PostsQueryFilter filter) {
        requireAuthorization();
        String filterValue = filter == null ? null : filter.getValue();
        return getRestTemplate().postForObject(buildUri("posts/draft", "filter", filterValue), null, Posts.class);
    }

    public Posts submission() {
        return submission(0, null);
    }

    public Posts submission(int offset, PostsQueryFilter filter) {
        requireAuthorization();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>(3);
        params.add("offset", Integer.toString(offset));
        params.add("filter", filter == null ? null : filter.getValue());
        return getRestTemplate().postForObject(buildUri("posts/submission", params), null, Posts.class);
    }
}
