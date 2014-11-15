package org.springframework.social.tumblr.api;

import java.util.List;

public interface BlogPostsOperations {

	public List<Post> getPosts();

    Posts search(PostsQuery query);

    Posts queue();
    Posts queue(int limit, int offset, PostsQueryFilter filter);

    Posts draft();
    Posts draft(PostsQueryFilter filter);

    Posts submission();
    Posts submission(int offset, PostsQueryFilter filter);

}
